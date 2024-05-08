package kauesoares.eestagio.authservice.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import kauesoares.eestagio.authservice.validation.annotation.Password;

public record UserReqDTO(
        @NotEmpty
        @Email
        String email,

        @NotEmpty
        @Password
        String password
) {
}
