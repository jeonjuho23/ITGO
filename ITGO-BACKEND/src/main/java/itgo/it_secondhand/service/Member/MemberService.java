package itgo.it_secondhand.service.Member;

import itgo.it_secondhand.api.DTO.Member.MemberDTO;
import itgo.it_secondhand.domain.Member;

public interface MemberService {

    Member createMember(MemberDTO memberDTO);

    Member getByCredentials(String phone);

    Member updateMember(MemberDTO memberDTO,String phone);


}
