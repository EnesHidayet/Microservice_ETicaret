package org.enes.rabbitmq.model;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserModel implements Serializable {
    String userName;
    String email;
    Long authId;
}
