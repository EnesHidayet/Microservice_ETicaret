package org.enes.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @Size(min = 3, max = 64)
    @NotNull
    String userName;
    @Size(min = 8, max = 32)
    @NotNull
    String password;
}
