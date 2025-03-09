package booking_stay.booking_stay.member.service.impl;

import booking_stay.booking_stay.common.BookingException;
import booking_stay.booking_stay.common.ErrorCode;
import booking_stay.booking_stay.member.domain.entity.Member;
import booking_stay.booking_stay.member.domain.repository.MemberRepository;
import booking_stay.booking_stay.member.dto.LoginRequestDto;
import booking_stay.booking_stay.member.dto.MemberCreateRequestDto;
import booking_stay.booking_stay.member.dto.MemberInfoResponseDto;
import booking_stay.booking_stay.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Boolean loginMember(LoginRequestDto loginRequestDto) {

        Member member = memberRepository.findUserByUserId(loginRequestDto.getUserId())
                .orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_USER));

        if (!member.getPassword().equals(loginRequestDto.getPassword()))
            throw new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.WRONG_PASSWORD);

        return true;
    }

    @Override
    public MemberInfoResponseDto getMemberInfoByUserId(String userId) {

        Member member = memberRepository.findUserByUserId(userId)
                .orElseThrow(()-> new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.NOT_EXIST_USER));

        return MemberInfoResponseDto.toDto(member);
    }

    @Override
    public String createMember(MemberCreateRequestDto memberCreateRequestDto) {

        if (memberRepository.existsMemberByUserId(memberCreateRequestDto.getUserId()))
            throw new BookingException(HttpStatus.BAD_REQUEST, ErrorCode.EXIST_USER_ID);

        memberRepository.save(Member.toEntity(memberCreateRequestDto));

        return "회원가입 성공";
    }
}
