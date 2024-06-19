package itgo.it_secondhand.service.Member;


import itgo.it_secondhand.api.DTO.Member.MemberDTO;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberRepository memberRepository;


    @Override
    public Member createMember(MemberDTO memberDTO) {
        Member member = Member.builder()
                .phone(memberDTO.getPhone())
                .name(memberDTO.getName())
                .location(memberDTO.getLocation())
                .build();

        if(member == null || member.getPhone() ==null){
            throw new RuntimeException("Invalid arguments");
        }
        final String userPhone = member.getPhone();

        if(memberRepository.existsByPhone(userPhone)){
            log.warn("User phoneNum already exists {}",userPhone);
            final Member originalUser = memberRepository.findByPhone(userPhone);
            log.info("login successful");
            if(originalUser != null){
                return originalUser;
            }
        }
        member.setName(UUID.randomUUID().toString());
        return memberRepository.save(member);
    }

    @Override
    public Member getByCredentials(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

    @Override
    public Member updateMember(MemberDTO memberDTO, String phone) {
        Member userCheck=memberRepository.findByPhone(phone);
        log.info("수정전 user: {}",userCheck);
        Member user = Member.builder()
                .phone(memberDTO.getPhone())
                .name(memberDTO.getName())
                .imgAddress(memberDTO.getImgAddress())
                .location(memberDTO.getLocation())
                .build();
        userCheck.updateUser(user);
        memberRepository.save(userCheck);
        return userCheck;
    }
}
