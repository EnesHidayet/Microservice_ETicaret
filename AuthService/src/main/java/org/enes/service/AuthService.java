package org.enes.service;

import lombok.RequiredArgsConstructor;
import org.enes.dto.request.LoginRequestDto;
import org.enes.dto.request.RegisterRequestDto;
import org.enes.dto.request.UserSaveRequestDto;
import org.enes.entity.Auth;
import org.enes.manager.UserProfileManager;
import org.enes.repository.AuthRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final UserProfileManager manager;

    public Boolean register(RegisterRequestDto registerRequestDto){
        Auth auth = Auth.builder()
                .email(registerRequestDto.getEmail())
                .userName(registerRequestDto.getUserName())
                .password(registerRequestDto.getPassword())
                .createAt(System.currentTimeMillis())
                .updateAt(System.currentTimeMillis())
                .isActive(true)
                .build();
        authRepository.save(auth);
        /**
         * Burada kullanıcıyı authDB ye kaydettik sonra UserService e Profil oluşturması için bilgilerini iletmemiz
         * gereklidir.
         */
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .authId(auth.getId())
                .userName(auth.getUserName())
                .email(auth.getEmail())
                .build();
        manager.save(userSaveRequestDto);
        return true;
    }

    public Optional<Auth> doLogin(LoginRequestDto loginRequestDto){
        Optional<Auth> auth = authRepository.findOptionalByUserNameAndPassword(loginRequestDto.getUserName(), loginRequestDto.getPassword());
        return auth;
    }
}
