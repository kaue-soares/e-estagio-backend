package kauesoares.eestagio.authservice.service;

import kauesoares.eestagio.authservice.config.properties.JwtProperties;
import kauesoares.eestagio.authservice.config.security.AuthUser;
import kauesoares.eestagio.authservice.dto.res.AuthResponseDTO;
import kauesoares.eestagio.authservice.messages.Messages;
import kauesoares.eestagio.authservice.messages.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    private static final String REFRESH_TOKEN_CLAIM = "refreshCode";

    public AuthResponseDTO generateAuthData(AuthUser authUser) {
        Instant now = Instant.now();
        Instant accessTokenExpiresAt = Instant.now().plus(1, ChronoUnit.HOURS);
        Instant refreshTokenExpiresAt = Instant.now().plus(1, ChronoUnit.DAYS);

        String scopes = authUser.getAuthorities().stream()
                .map(GrantedAuthority::toString)
                .collect(Collectors.joining(" "));

        JwtClaimsSet accessTokenClaims = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(accessTokenExpiresAt)
                .subject(authUser.getUsername())
                .claim("scope", scopes)
                .build();

        JwtClaimsSet refreshTokenClaims = JwtClaimsSet.builder()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiresAt(refreshTokenExpiresAt)
                .subject(authUser.getUsername())
                .claim("scope", scopes)
                .claim(REFRESH_TOKEN_CLAIM, authUser.getRefreshCode())
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(accessTokenClaims)).getTokenValue();
        String refreshToken = jwtEncoder.encode(JwtEncoderParameters.from(refreshTokenClaims)).getTokenValue();

        return new AuthResponseDTO(
                "Bearer",
                accessToken,
                accessTokenExpiresAt.toEpochMilli(),
                refreshToken,
                refreshTokenExpiresAt.toEpochMilli()
        );
    }

    public String extractRefreshCode(Jwt jwt) {
        return jwt.getClaim(REFRESH_TOKEN_CLAIM);
    }

    public String extractUsername(Jwt jwt) {
        return jwt.getSubject();
    }

    public Jwt decode(String token) {
        try {
            String withoutBearer = token.replace("Bearer ", "");
            return jwtDecoder.decode(withoutBearer);
        } catch (Exception e) {
            throw new UnauthorizedException(Messages.INVALID_TOKEN);
        }
    }

}
