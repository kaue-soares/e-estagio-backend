package kauesoares.eestagio.authservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import kauesoares.eestagio.authservice.dto.req.UserReqDTO;
import kauesoares.eestagio.authservice.dto.res.AuthResponseDTO;
import kauesoares.eestagio.authservice.dto.res.UserResDTO;
import kauesoares.eestagio.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @SecurityRequirement(name = "basicAuth")
    public ResponseEntity<AuthResponseDTO> login(
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                this.authService.login(authentication));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(
            @RequestHeader("Authorization") String authorization
    ) {
        return ResponseEntity.ok(
                this.authService.refresh(authorization));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authorization
    ) {
        this.authService.logout(authorization);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate")
    public ResponseEntity<Void> validate(
            @RequestHeader("Authorization") String authorization
    ) {
        // TODO:
        // receive the URL and the token
        // check if the URL is open, close or requires a specific role

        // create end point for register the URL and the role
        // create end point for delete the URL and the role

        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResDTO> register(
            @Valid
            @RequestBody
            UserReqDTO userReqDTO,

            UriComponentsBuilder uriComponentsBuilder
    ) {
        UserResDTO userResDTO = this.authService.register(userReqDTO);

        URI uri = uriComponentsBuilder
                .path("/auth/{id}")
                .buildAndExpand(userResDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(userResDTO);
    }

}
