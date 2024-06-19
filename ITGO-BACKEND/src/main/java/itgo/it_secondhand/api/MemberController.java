package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.Member.MemberDTO;
import itgo.it_secondhand.api.DTO.Member.ResponseDTO;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.service.Member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v2/members")
public class MemberController {

    @Autowired
    MemberService memberService;


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody MemberDTO memberDTO){
        log.info("User registration");
        try{
            if(memberDTO == null || memberDTO.getPhone() == null){
                throw new RuntimeException("User phoneNum null");
            }
            Member registeredUser = memberService.createMember(memberDTO);
            final Member responseUserDTO = Member.builder()
                    .phone(registeredUser.getPhone())
                    .name(registeredUser.getName())
                    .location(registeredUser.getLocation())
                    .build();

            return ResponseEntity.ok(responseUserDTO);
        }catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/{memberId}/profiles")
    public ResponseEntity<?> myProfile(@PathVariable(name = "memberId") Long memberId){
        log.info("마이페이지 called");
        Member user = memberService.getByCredentials(memberId);
        if (user != null){
            return ResponseEntity.ok().body(user);
        }else{
            ResponseDTO responseDTO=new ResponseDTO().builder()
                    .error("mypage error")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PatchMapping("/{memberId}/profiles")
    public ResponseEntity<?> updateProfile(@PathVariable(name = "memberId") Long memberId, @RequestBody MemberDTO memberDTO){
        log.info("updateProfile 호출");
        try{
            Member responseUser = memberService.updateMember(memberDTO, memberDTO.getPhone());
            return ResponseEntity.ok(responseUser);
        }catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


}
