package org.enes.domain;

import java.io.Serializable;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User implements Serializable {
    @Id
    String id;
    /**
     * Kullanıcı kayıt olurken Auth bilgilerini eşleştirmek üzere
     * auth_id bilgisini tutuyoruz.
     */
    Long authId;
    String userName;
    String email;
    String photo;
    String name;
    String phone;
    String about;
}
