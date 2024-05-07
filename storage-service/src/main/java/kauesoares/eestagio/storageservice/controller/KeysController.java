package kauesoares.eestagio.storageservice.controller;

import kauesoares.eestagio.storageservice.dto.res.KeyResDTO;
import kauesoares.eestagio.storageservice.service.KeysService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keys")
@RequiredArgsConstructor
public class KeysController {

    private final KeysService keysService;

    @GetMapping("/private")
    public ResponseEntity<KeyResDTO> getPrivateKey() {
        return ResponseEntity.ok(this.keysService.getPrivateKey());
    }

    @GetMapping("/public")
    public ResponseEntity<KeyResDTO> getPublicKey() {
        return ResponseEntity.ok(this.keysService.getPublicKey());
    }

}
