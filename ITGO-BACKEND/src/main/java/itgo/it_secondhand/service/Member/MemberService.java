package itgo.it_secondhand.service.Member;

import itgo.it_secondhand.api.DTO.Member.FetchMemberProfileResponseDTO;
import itgo.it_secondhand.api.DTO.Member.MemberDTO;
import itgo.it_secondhand.domain.Member;

public interface MemberService {

    Member createMember(MemberDTO memberDTO);

    FetchMemberProfileResponseDTO getByCredentials(Long memberId);

    FetchMemberProfileResponseDTO updateMember(MemberDTO memberDTO, Long memberId);


}
