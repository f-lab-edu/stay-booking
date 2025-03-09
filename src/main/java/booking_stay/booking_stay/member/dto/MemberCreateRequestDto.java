package booking_stay.booking_stay.member.dto;

import lombok.Getter;

@Getter
public class MemberCreateRequestDto {
    private String userId;

    private String password;

    private String userName;

    private String email;

    private String phoneNumber;

}
