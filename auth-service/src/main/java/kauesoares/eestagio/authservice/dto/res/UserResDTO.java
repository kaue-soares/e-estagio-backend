package kauesoares.eestagio.authservice.dto.res;

import kauesoares.eestagio.authservice.domain.Role;
import kauesoares.eestagio.authservice.model.User;

import java.util.Set;

public record UserResDTO(
        Long id,
        String email,
        Set<Role> roles
) {

    public static UserResDTO fromModel(User user) {
        return new UserResDTO(
                user.getId(),
                user.getEmail(),
                user.getRoles()
        );
    }

}
