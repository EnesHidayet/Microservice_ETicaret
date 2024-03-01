package org.enes.manager;

import org.enes.dto.request.UserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.enes.constants.RestApiUrl.*;

@FeignClient(name = "elastic", url = "http://localhost:8080/dev/v1/elastic/user")
public interface ElasticUserManager {
    @PostMapping(ADD)
    ResponseEntity<Void> save(@RequestBody UserRequestDto dto);
}
