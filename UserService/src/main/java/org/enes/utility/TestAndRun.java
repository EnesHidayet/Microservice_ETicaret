package org.enes.utility;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.enes.dto.request.UserRequestDto;
import org.enes.manager.ElasticUserManager;
import org.enes.service.UserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestAndRun {
    private final UserService userService;
    private final ElasticUserManager elasticUserManager;

//    @PostConstruct
    public void start(){
        userService.findAll().forEach(user->{
            UserRequestDto dto = new UserRequestDto(
                    user.getId(),
                    user.getAuthId(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getPhoto(),
                    user.getName(),
                    user.getPhone(),
                    user.getAbout()
            );
            elasticUserManager.save(dto);
        });
    }

}
