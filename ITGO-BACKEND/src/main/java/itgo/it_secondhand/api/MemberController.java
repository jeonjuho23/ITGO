package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.Member.MemberDTO;
import itgo.it_secondhand.api.DTO.Member.ResponseDTO;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.service.Member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
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


}
