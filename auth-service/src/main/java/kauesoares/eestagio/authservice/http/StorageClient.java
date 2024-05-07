package kauesoares.eestagio.authservice.http;

import kauesoares.eestagio.authservice.dto.res.KeyResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${services.storage.name}")
public interface StorageClient {

    @RequestMapping(method = RequestMethod.GET, value = "/keys/public")
    KeyResDTO getPublicKey();

    @RequestMapping(method = RequestMethod.GET, value = "/keys/private")
    KeyResDTO getPrivateKey();
}
