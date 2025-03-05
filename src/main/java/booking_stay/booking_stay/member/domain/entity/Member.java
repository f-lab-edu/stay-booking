package booking_stay.booking_stay.member.domain.entity;

import booking_stay.booking_stay.member.dto.MemberCreateRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "MEMBER")
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @Column
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Builder
    public Member(String userId, String password, String userName, String email, String phoneNumber) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public static Member toEntity(MemberCreateRequestDto requestDto) {
        return Member.builder()
                .userId(requestDto.getUserId())
                .password(requestDto.getPassword())
                .userName(requestDto.getUserName())
                .email(requestDto.getEmail())
                .phoneNumber(requestDto.getPhoneNumber())
                .build();
    }
}
