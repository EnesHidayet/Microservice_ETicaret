package org.enes.service;

import lombok.RequiredArgsConstructor;
import org.enes.dto.request.LoginRequestDto;
import org.enes.dto.request.RegisterRequestDto;
import org.enes.dto.request.UserSaveRequestDto;
import org.enes.entity.Auth;
import org.enes.manager.UserProfileManager;
import org.enes.rabbitmq.model.CreateUserModel;
import org.enes.rabbitmq.producer.CreateUserProducer;
import org.enes.repository.AuthRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final UserProfileManager manager;
    private final CreateUserProducer createUserProducer;

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
//        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
//                .authId(auth.getId())
//                .userName(auth.getUserName())
//                .email(auth.getEmail())
//                .build();
//        manager.save(userSaveRequestDto);

        /**
         * DİKKAT!!!
         * Burada userService e kayıt işlemi FeignClient ile yapılıyordu ancak bu işlem büyük riskler barındırır.
         * Bu nedenle rabbitMQ ile sistemin sorunsuz ve kayıpsız bir şekilde ilerlemesini sağlıyoruz.
         */
        createUserProducer.sendMessage(CreateUserModel.builder()
                        .authId(auth.getId())
                        .email(auth.getEmail())
                        .userName(auth.getUserName())
                .build());
        return true;
    }

    public Optional<Auth> doLogin(LoginRequestDto loginRequestDto){
        Optional<Auth> auth = authRepository.findOptionalByUserNameAndPassword(loginRequestDto.getUserName(), loginRequestDto.getPassword());
        return auth;
    }
}
