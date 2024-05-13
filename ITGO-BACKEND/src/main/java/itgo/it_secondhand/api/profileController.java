package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.Member.MemberDTO;
import itgo.it_secondhand.api.DTO.Member.ResponseDTO;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.service.Member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/profile")
public class profileController {

    @Autowired
    MemberService memberService;

    @PostMapping
    public ResponseEntity<?> myProfile(@RequestParam String phoneNum){
        log.info("마이페이지 called");
        Member user = memberService.getByCredentials(phoneNum);
        if (user != null){
            return ResponseEntity.ok().body(user);
        }else{
            ResponseDTO responseDTO=new ResponseDTO().builder()
                    .error("mypage error")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody MemberDTO memberDTO){
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
