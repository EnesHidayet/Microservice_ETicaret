package org.enes.service;

import lombok.RequiredArgsConstructor;
import org.enes.domain.User;
import org.enes.dto.request.UserSaveRequestDto;
import org.enes.dto.request.UserUpdateRequestDto;
import org.enes.exception.ErrorType;
import org.enes.exception.UserServiceException;
import org.enes.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void save(UserSaveRequestDto dto){
        userRepository.save(User.builder()
                        .userName(dto.getUserName())
                        .email(dto.getEmail())
                        .authId(dto.getAuthId())
                .build());
    }

    public void update(UserUpdateRequestDto dto) {
        Optional<User> user = userRepository.findById(dto.getId());
        if (user.isEmpty()){
            throw new UserServiceException(ErrorType.ERROR_INVALID_USER_ID);
        }
        User updateUser = user.get();
        updateUser.setAbout(dto.getAbout());
        updateUser.setPhoto(dto.getPhoto());
        updateUser.setName(dto.getName());
        updateUser.setPhone(dto.getPhone());
        userRepository.save(updateUser);
    }
}
