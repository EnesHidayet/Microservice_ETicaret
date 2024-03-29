package org.enes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.enes.constants.RestApiUrl;
import org.enes.dto.request.LoginRequestDto;
import org.enes.dto.request.RegisterRequestDto;
import org.enes.entity.Auth;
import org.enes.exception.AuthServiceException;
import org.enes.exception.ErrorType;
import org.enes.service.AuthService;
import org.enes.utility.JwtTokenManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.enes.constants.RestApiUrl.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {
    private final AuthService authService;
    private final JwtTokenManager jwtTokenManager;

    @PostMapping(REGISTER)
    public ResponseEntity<Boolean> register(@RequestBody @Valid RegisterRequestDto registerRequestDto){
        return ResponseEntity.ok(authService.register(registerRequestDto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        Optional<Auth> auth = authService.doLogin(loginRequestDto);
        if (auth.isEmpty())
            throw new AuthServiceException(ErrorType.ERROR_INVALID_LOGIN_PARAMETER);
        Optional<String> token = jwtTokenManager.createToken(auth.get().getId());
        if (token.isEmpty())
            throw new AuthServiceException(ErrorType.ERROR_CREATE_TOKEN);
        return ResponseEntity.ok(token.get());
    }
}
