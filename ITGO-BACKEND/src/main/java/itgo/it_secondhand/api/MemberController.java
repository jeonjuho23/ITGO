package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.Member.FetchMemberProfileResponseDTO;
import itgo.it_secondhand.api.DTO.Member.MemberDTO;
import itgo.it_secondhand.api.DTO.ResponseDTO;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.service.Member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static itgo.it_secondhand.api.DTO.ResponseDTO.success;

@Slf4j
@RestController
@RequestMapping("/api/v2/members")
public class MemberController {

    @Autowired
    MemberService memberService;


    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO<?>> registerUser(@RequestBody MemberDTO memberDTO) {
        log.info("User registration");

        Member registeredUser = memberService.createMember(memberDTO);

        Long registeredUserId = registeredUser.getId();

        return ResponseEntity.ok()
                .body(success(registeredUserId));
    }

    @GetMapping("/{memberId}/profiles")
    public ResponseEntity<ResponseDTO<?>> myProfile(@PathVariable(name = "memberId") Long memberId) {
        log.info("마이페이지 called");
        FetchMemberProfileResponseDTO response = memberService.getByCredentials(memberId);

        return ResponseEntity.ok().body(success(response));
    }

    @PatchMapping("/{memberId}/profiles")
    public ResponseEntity<ResponseDTO<?>> updateProfile
            (@PathVariable(name = "memberId") Long memberId, @RequestBody MemberDTO memberDTO) {
        log.info("updateProfile 호출");
        FetchMemberProfileResponseDTO response = memberService.updateMember(memberDTO, memberId);
        return ResponseEntity.ok().body(success(response));
    }


}
