package booking_stay.booking_stay.member.service;

import booking_stay.booking_stay.member.dto.LoginRequestDto;
import booking_stay.booking_stay.member.dto.MemberCreateRequestDto;
import booking_stay.booking_stay.member.dto.MemberInfoResponseDto;


public interface MemberService {

    Boolean loginMember(LoginRequestDto loginRequestDto);

    MemberInfoResponseDto getMemberInfoByUserId(String userId);

    String createMember(MemberCreateRequestDto memberCreateRequestDto);

}
