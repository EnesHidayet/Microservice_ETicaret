package org.enes.controller;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.enes.domain.User;
import org.enes.dto.request.DefaultRequestDto;
import org.enes.dto.request.UserSaveRequestDto;
import org.enes.dto.request.UserUpdateRequestDto;
import org.enes.exception.ErrorType;
import org.enes.exception.UserServiceException;
import org.enes.service.UserService;
import org.enes.utility.JwtTokenManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.enes.constants.RestApiUrl.*;
/**
 * http://localhost:9094/dev/v1/user
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
/**
 * Loglama işlemleri için kullanıyoruz.
 */
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;

    @Value("${userservice.deger2}")
    private String deger2;

    @Value("${userservice.listem.iki}")
    private String iki;


    @GetMapping("/get-application-properties")
    public String getApplicationProperties(){
        log.trace("Properties Bilgisi...:"+iki);
        log.debug("Properties Bilgisi...:"+iki);
        log.info("Properties Bilgisi...:"+iki);
        log.warn("Properties Bilgisi...:"+iki);
        log.error("Properties Bilgisi...:"+iki);
        System.out.println("Console çıktı...:"+iki);
        return deger2;
    }

    /**
     * /add
     * @param dto
     * @return
     */
    @PostMapping(ADD)
    public ResponseEntity<Void> save(@RequestBody UserSaveRequestDto dto){
        userService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(UPDATE)
    public ResponseEntity<Void> update(UserUpdateRequestDto dto){
        userService.update(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(GET_ALL)
    public ResponseEntity<List<User>> getAll(DefaultRequestDto dto){
        Optional<Long> authId = jwtTokenManager.validateToken(dto.getToken());
        if (authId.isEmpty())
            throw new UserServiceException(ErrorType.INVALID_TOKEN);

        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * Dikkat!!!!!!!!
     * Aşağıya yazılan kod bloğu bir işlemin uzun sürmesi durumunu simüle etmek için eklenmiştir.
     * @return
     */
    @GetMapping("/get-string")
    public ResponseEntity<String> getString(String ad){

        return ResponseEntity.ok(userService.getString(ad));
    }

    @GetMapping("/get-all-by-name")
    public ResponseEntity<Page<User>> getAllByName(String userName, int page, int size, String sortParameter, String sortDirection){
        return ResponseEntity.ok(userService.findAllByUserName(userName, page, size, sortParameter, sortDirection));
    }
}
