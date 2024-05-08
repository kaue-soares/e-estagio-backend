package kauesoares.eestagio.authservice.service;

import kauesoares.eestagio.authservice.messages.Messages;
import kauesoares.eestagio.authservice.messages.exception.BadRequestException;
import kauesoares.eestagio.authservice.model.User;
import kauesoares.eestagio.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(Messages.USER_NOT_FOUND));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
