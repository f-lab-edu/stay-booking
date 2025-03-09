package booking_stay.booking_stay.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotBlank(message = "아이디가 입력되지 않았습니다.")
    private String userId;

    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    private String password;
}
