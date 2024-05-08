package kauesoares.eestagio.authservice.service;

import kauesoares.eestagio.authservice.config.security.AuthUser;
import kauesoares.eestagio.authservice.domain.Role;
import kauesoares.eestagio.authservice.dto.req.UserReqDTO;
import kauesoares.eestagio.authservice.dto.res.AuthResponseDTO;
import kauesoares.eestagio.authservice.dto.res.UserResDTO;
import kauesoares.eestagio.authservice.messages.Messages;
import kauesoares.eestagio.authservice.messages.exception.UnauthorizedException;
import kauesoares.eestagio.authservice.model.User;
import kauesoares.eestagio.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDTO login(Authentication authentication) {
        User user = this.userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException(Messages.USER_NOT_FOUND));

        user.setRefreshCode(UUID.randomUUID().toString());
        this.userRepository.save(user);

        AuthUser authUser = new AuthUser(user);

        return this.jwtService.generateAuthData(authUser);
    }

    public AuthResponseDTO refresh(String authorization) {

        Jwt jwt = this.jwtService.decode(authorization);

        String username = this.jwtService.extractUsername(jwt);
        String refreshCode = this.jwtService.extractRefreshCode(jwt);

        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UnauthorizedException(Messages.USER_NOT_FOUND));

        if (refreshCode == null)
            throw new UnauthorizedException(Messages.NOT_A_REFRESH_TOKEN);

        if (!refreshCode.equals(user.getRefreshCode()))
            throw new UnauthorizedException(Messages.INVALID_REFRESH_CODE);

        if (Objects.requireNonNull(jwt.getExpiresAt()).isBefore(Instant.now()))
            throw new UnauthorizedException(Messages.EXPIRED_REFRESH_TOKEN);

        user.setRefreshCode(UUID.randomUUID().toString());

        this.userRepository.save(user);

        AuthUser authUser = new AuthUser(user);

        return this.jwtService.generateAuthData(authUser);
    }

    public void logout(String authorization) {

        Jwt jwt = this.jwtService.decode(authorization);

        String username = this.jwtService.extractUsername(jwt);

        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UnauthorizedException(Messages.USER_NOT_FOUND));

        user.setRefreshCode(null);
        this.userRepository.save(user);
    }

    public UserResDTO register(UserReqDTO userReqDTO) {

        if (this.existsByEmail(userReqDTO.email()))
            throw new UnauthorizedException(Messages.EMAIL_ALREADY_EXISTS);

        User user = new User();

        user.setEmail(userReqDTO.email());
        user.setPassword(this.passwordEncoder.encode(userReqDTO.password()));
        user.setRoles(Set.of(Role.USER));

        return UserResDTO.fromModel(this.save(user));

    }

    public User save(User user) {
        return this.userRepository.save(user);
    }

    private boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }
}
