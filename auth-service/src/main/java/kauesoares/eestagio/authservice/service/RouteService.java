package kauesoares.eestagio.authservice.service;

import jakarta.transaction.Transactional;
import kauesoares.eestagio.authservice.domain.PathParamType;
import kauesoares.eestagio.authservice.dto.req.PrivateRouteReqDTO;
import kauesoares.eestagio.authservice.messages.Messages;
import kauesoares.eestagio.authservice.messages.exception.BadRequestException;
import kauesoares.eestagio.authservice.model.Route;
import kauesoares.eestagio.authservice.model.RouteParam;
import kauesoares.eestagio.authservice.repository.RouteParamRepository;
import kauesoares.eestagio.authservice.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final RouteParamRepository routeParamRepository;

    @Transactional
    public void createPrivateRoute(PrivateRouteReqDTO privateRouteReqDTO) {

        String path = privateRouteReqDTO.path();
        Map<String, PathParamType> params = privateRouteReqDTO.params();

        if (!path.startsWith("/"))
            throw new BadRequestException(Messages.MISSING_SLASH_IN_PATH);

        if (!path.matches("^/[a-zA-Z0-9-{}]+(/[a-zA-Z0-9-{}]+)*$"))
            throw new BadRequestException(Messages.UNHALLOWED_CHARACTERS_IN_PATH);

        String[] parts = path.substring(1).split("/");

        if (parts[0].contains("{") || parts[0].contains("}"))
            throw new BadRequestException(Messages.VARIABLE_PATH_PARAM_NOT_ALLOWED_AT_FIRST_POSITION);

        Route route = Route.builder()
                .name(privateRouteReqDTO.name())
                .isPrivate(true)
                .path(path)
                .method(privateRouteReqDTO.method())
                .allowedRoles(privateRouteReqDTO.allowedRoles())
                .build();

        if (params != null)
            params.forEach((name, type) -> {
                if (!path.contains("{" + name + "}"))
                    throw new BadRequestException(Messages.INVALID_PATH_PARAM_NAME, name);

                RouteParam routeParam = RouteParam.builder()
                        .name(name)
                        .type(type)
                        .route(route)
                        .build();

                this.routeParamRepository.save(routeParam);
            });

        this.routeRepository.save(route);

    }
}
