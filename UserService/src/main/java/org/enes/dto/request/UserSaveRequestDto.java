package org.enes.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveRequestDto {
    private String email;
    private String userName;
    Long authId;
}
