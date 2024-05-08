package kauesoares.eestagio.authservice.repository;

import kauesoares.eestagio.authservice.base.BaseRepository;
import kauesoares.eestagio.authservice.model.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
