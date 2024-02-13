package org.enes.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto {
    String id;
    String photo;
    String name;
    String phone;
    String about;
}
