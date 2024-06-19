package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.Member.MemberDTO;
import itgo.it_secondhand.api.DTO.ResponseDTO;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.service.Member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static itgo.it_secondhand.api.DTO.ResponseDTO.error;
import static itgo.it_secondhand.api.DTO.ResponseDTO.success;

@Slf4j
@RestController
@RequestMapping("/api/v2/members")
public class MemberController {

    @Autowired
    MemberService memberService;


    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO<?>> registerUser(@RequestBody MemberDTO memberDTO){
        log.info("User registration");
        try{
            if(memberDTO == null || memberDTO.getPhone() == null){
                throw new RuntimeException("User phoneNum null");
            }
            Member registeredUser = memberService.createMember(memberDTO);
            Member responseUserDTO = Member.builder()
                    .phone(registeredUser.getPhone())
                    .name(registeredUser.getName())
                    .location(registeredUser.getLocation())
                    .build();

            return ResponseEntity.ok()
                    .body(success(responseUserDTO));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(error(e.getMessage()));
        }
    }

    @GetMapping("/{memberId}/profiles")
    public ResponseEntity<ResponseDTO<?>> myProfile(@PathVariable(name = "memberId") Long memberId){
        log.info("마이페이지 called");
        Member user = memberService.getByCredentials(memberId);
        if (user != null){
            return ResponseEntity.ok().body(success(user));
        }else{
            return ResponseEntity.badRequest().body(error("noSuchMember"));
        }

    }

    @PatchMapping("/{memberId}/profiles")
    public ResponseEntity<ResponseDTO<?>> updateProfile(@PathVariable(name = "memberId") Long memberId, @RequestBody MemberDTO memberDTO){
        log.info("updateProfile 호출");
        try{
            Member responseUser = memberService.updateMember(memberDTO, memberDTO.getPhone());
            return ResponseEntity.ok().body(success(responseUser));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(error(e.getMessage()));
        }

    }


}
