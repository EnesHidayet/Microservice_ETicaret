package org.enes.manager;

import org.enes.dto.request.UserSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.enes.constants.RestApiUrl.ADD;

/**
 * Microsevisler arasında iletişimi RestAPI üzerinden sağlamak için kullanılır.
 * İletişim kurulacak servisin controller katmanına istek atar.
 * İki adet parametresini özellikle kullanmalıyız.
 * 1- url: istek atılacak controller sınıfına erişim adresini yazıyoruz.
 * 2- name(optional: yazılan her bir manager için bir isim veriyoruz.zorunlu değildir ancak
 * aynı ismi taşıyan birden fazla manager olursa sistem hata verir.Sorunu anlamanız mümkün olmayabilir.
 * Kullanırken dikkatli olun.
 */
@FeignClient(url = "http://localhost:9094/dev/v1/user",name = "userProfileManager")
public interface UserProfileManager {
    @PostMapping(ADD)
    ResponseEntity<Void> save(@RequestBody UserSaveRequestDto dto);
}
