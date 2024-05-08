package kauesoares.eestagio.authservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kauesoares.eestagio.authservice.dto.res.AuthResponseDTO;
import kauesoares.eestagio.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> logout() {
        this.authService.logout();

        return ResponseEntity.noContent().build();
    }

}
