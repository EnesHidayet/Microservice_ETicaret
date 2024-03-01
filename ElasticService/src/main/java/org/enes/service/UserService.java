package org.enes.service;

import lombok.RequiredArgsConstructor;
import org.enes.domain.User;
import org.enes.dto.request.UserRequestDto;
import org.enes.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void save(UserRequestDto dto){
        User user = User.builder()
                .id(dto.id())
                .phone(dto.phone())
                .name(dto.name())
                .photo(dto.photo())
                .userName(dto.userName())
                .about(dto.about())
                .email(dto.email())
                .authId(dto.authId())
                .build();
        userRepository.save(user);
    }

    public void  update(UserRequestDto dto){
        User user = User.builder()
                .id(dto.id())
                .phone(dto.phone())
                .name(dto.name())
                .photo(dto.photo())
                .userName(dto.userName())
                .about(dto.about())
                .email(dto.email())
                .authId(dto.authId())
                .build();
        userRepository.save(user);
    }

    public Iterable<User> getAll(){
        return userRepository.findAll();
    }
}
