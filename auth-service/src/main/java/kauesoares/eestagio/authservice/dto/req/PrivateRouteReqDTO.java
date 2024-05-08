package kauesoares.eestagio.authservice.dto.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kauesoares.eestagio.authservice.domain.HttpMethod;
import kauesoares.eestagio.authservice.domain.PathParamType;
import kauesoares.eestagio.authservice.domain.Role;

import java.util.Map;
import java.util.Set;

public record PrivateRouteReqDTO(
        String name,

        @NotNull
        HttpMethod method,

        @NotNull
        Set<Role> allowedRoles,

        @NotEmpty
        String path,

        Map<String, PathParamType> params
) {}
