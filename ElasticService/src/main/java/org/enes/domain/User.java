package org.enes.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "user-index")
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
