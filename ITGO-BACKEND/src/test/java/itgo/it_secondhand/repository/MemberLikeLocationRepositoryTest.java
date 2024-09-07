package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberLikeLocation;
import itgo.it_secondhand.domain.value.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static itgo.it_secondhand.StubFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberLikeLocationRepositoryTest {

    @Autowired
    MemberLikeLocationRepository memberLikeLocationRepository;

    @Autowired
    MemberRepository memberRepository;

    private MemberLikeLocation memberLikeLocation;
    private Member member;

    @BeforeEach
    void setUp(){
        memberLikeLocation = getMemberLikeLocation();

        member = memberLikeLocation.getMember();

        memberRepository.save(member);

        memberLikeLocationRepository.save(memberLikeLocation);
    }


    @Test
    public void findByMember_Id() throws Exception {
        //given
        Long memberId = member.getId();

        //when
        List<MemberLikeLocation> result = memberLikeLocationRepository.findByMember_Id(memberId);

        //then
        assertThat(result.get(0).getLocation().getCity())
                .isEqualTo(memberLikeLocation.getLocation().getCity());
        assertThat(result.get(0).getLikeDate())
                .isEqualTo(memberLikeLocation.getLikeDate());
    }
}