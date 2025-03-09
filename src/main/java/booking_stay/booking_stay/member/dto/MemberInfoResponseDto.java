package booking_stay.booking_stay.member.dto;

import booking_stay.booking_stay.member.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoResponseDto {
    private String userId;
    private String password;
    private String userName;
    private String email;
    private String phoneNumber;

    public static MemberInfoResponseDto toDto(Member entity) {
        return MemberInfoResponseDto.builder()
                .userId(entity.getUserId())
                .password(entity.getPassword())
                .userName(entity.getUserName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }
}
