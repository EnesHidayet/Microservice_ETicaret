package org.enes.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.enes.domain.User;
import org.enes.dto.request.UserRequestDto;
import org.enes.dto.request.UserSaveRequestDto;
import org.enes.dto.request.UserUpdateRequestDto;
import org.enes.exception.ErrorType;
import org.enes.exception.UserServiceException;
import org.enes.manager.ElasticUserManager;
import org.enes.repository.UserRepository;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final CacheManager cacheManager;
    private final ElasticUserManager elasticUserManager;

    public void save(UserSaveRequestDto dto){
        userRepository.save(User.builder()
                        .userName(dto.getUserName())
                        .email(dto.getEmail())
                        .authId(dto.getAuthId())
                .build());

        /**
         * Bu işlem exception fırlatabilir, bu nedenle ya try..catch yaparsınız
         * ya da Object null konrolü yaparsınız.
         */
        Objects.requireNonNull(cacheManager.getCache("user-find-all")).clear();

//        UserRequestDto userRequestDto = new UserRequestDto(
//                user.getId(),
//                user.getAuthId(),
//                user.getUserName(),
//                user.getEmail(),
//                user.getPhoto(),
//                user.getName(),
//                user.getAbout(),
//                user.getPhone()
//        );
//        elasticUserManager.save(userRequestDto);
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
        Objects.requireNonNull(cacheManager.getCache("user-find-all")).clear();
    }

    @Cacheable(value = "user-find-all")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Cacheable(value = "get-string")
    public String getString(String ad) {
        String createString = ad.toUpperCase().concat(" Hoşgeldiniz.");

        try{
            Thread.sleep(3000L);
        }catch (InterruptedException e){
            log.error("Beklenmeyen thread hatası.");
        }
        return createString;
    }

    /**
     *
     * @param userName
     * @param page -> sayfa numarası
     * @param size -> sayfa boyutu
     * @param sortParameter -> sıralama parametresi yani değişken adı
     * @param sortDirection -> sıralamanın yönü yani a..z ya da z..a
     * @return
     */
    public Page<User> findAllByUserName(String userName, int page, int size, String sortParameter, String sortDirection) {
        Pageable pageable;
        /**
         * Bir sayfalama oluşturmak için;
         * 1- bir sayfada kaç kayıt görünecek
         * 2- hangi sayfayı görünütülemek istiyorsun.
         * 3- Sıralama yapmak istiyor musun? yapacaksan hangi alanı sıralayacaksın?
         * 4- sıralama kriteri
         */
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortParameter);
        pageable = PageRequest.of(page,size,sort);
        Page<User> result = userRepository.findAllByUserNameContaining(userName, pageable);
        return result;
    }
}
