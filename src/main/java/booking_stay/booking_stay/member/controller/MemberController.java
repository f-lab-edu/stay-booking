package booking_stay.booking_stay.member.controller;

import booking_stay.booking_stay.member.dto.LoginRequestDto;
import booking_stay.booking_stay.member.dto.MemberCreateRequestDto;
import booking_stay.booking_stay.member.dto.MemberInfoResponseDto;
import booking_stay.booking_stay.member.service.MemberService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/account")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto) {
        return memberService.loginMember(loginRequestDto) ? "SUCCESS" : "FAIL";
    }

    @GetMapping("/member")
    public MemberInfoResponseDto getMemberInfo(
            @RequestParam(value = "/userId") String userId
    ) {
        return memberService.getMemberInfoByUserId(userId);
    }

    @PostMapping("/member")
    public String createMember(@RequestBody MemberCreateRequestDto memberCreateRequestDto) {
        return memberService.createMember(memberCreateRequestDto);
    }

//    @GetMapping("/logout")
//    public void logout(){
//
//    }
}
