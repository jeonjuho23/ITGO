package itgo.it_secondhand.service.Member;


import itgo.it_secondhand.api.DTO.Member.FetchMemberProfileResponseDTO;
import itgo.it_secondhand.api.DTO.Member.MemberDTO;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;


    @Override
    public Member createMember(MemberDTO memberDTO) {
        Member member = Member.builder()
                .phone(memberDTO.getPhone())
                .name(memberDTO.getName())
                .location(memberDTO.getLocation())
                .build();

        String userPhone = member.getPhone();

        if (memberRepository.existsByPhone(userPhone)) {
            log.warn("User phoneNum already exists {}", userPhone);
            Member originalUser = memberRepository.findByPhone(userPhone)
                    .orElseThrow(() -> new RestApiException(CustomExceptionCode.MEMBER_NOT_FOUND));
            log.info("login successful");
            return originalUser;
        }
        return memberRepository.save(member);
    }

    @Override
    public FetchMemberProfileResponseDTO getByCredentials(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RestApiException(CustomExceptionCode.MEMBER_NOT_FOUND));

        return FetchMemberProfileResponseDTO.builder()
                .memberId(member.getId())
                .phone(member.getPhone())
                .imgAddress(member.getImgAddress())
                .location(member.getLocation())
                .build();
    }

    @Override
    public FetchMemberProfileResponseDTO updateMember(MemberDTO memberDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RestApiException(CustomExceptionCode.MEMBER_NOT_FOUND));
        member.updateUser(memberDTO);

        return FetchMemberProfileResponseDTO.builder()
                .memberId(member.getId())
                .phone(member.getPhone())
                .imgAddress(member.getImgAddress())
                .location(member.getLocation())
                .build();
    }
}
