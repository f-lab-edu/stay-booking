package booking_stay.booking_stay.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_EXIST_USER("NOT_EXIST_USER", "존재하지 않는 회원입니다."),
    EXIST_USER_ID("EXIST_USER_ID", "이미 존재하는 회원 아이디 입니다."),
    WRONG_PASSWORD("WRONG_PASSWORD", "비밀번호가 일치하지 않습니다.");

    private final String code;
    private final String msg;
}
