package kauesoares.eestagio.authservice.controller;

import jakarta.validation.Valid;
import kauesoares.eestagio.authservice.dto.req.PrivateRouteReqDTO;
import kauesoares.eestagio.authservice.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @PostMapping("/private")
    public ResponseEntity<Void> createPrivateRoute(
            @Valid
            @RequestBody
            PrivateRouteReqDTO privateRouteReqDTO
    ) {
        this.routeService.createPrivateRoute(privateRouteReqDTO);

        return ResponseEntity.ok().build();
    }

}
