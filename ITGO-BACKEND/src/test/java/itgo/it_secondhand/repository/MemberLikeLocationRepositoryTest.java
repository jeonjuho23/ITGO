package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberLikeLocation;
import itgo.it_secondhand.domain.value.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberLikeLocationRepositoryTest {

    @Autowired
    MemberLikeLocationRepository memberLikeLocationRepository;

    private static MemberLikeLocation newMemberLikeLocation;

    @Autowired
    MemberRepository memberRepository;
    private static Member member;
    private static Location location;
    @BeforeEach
    void setUp(){
        location = new Location("city", "street", "zipcode");
        member = Member.builder()
                .location(location)
                .name("name")
                .phone("phone")
                .imgAddress("imgAddress")
                .build();

        memberRepository.save(member);


        newMemberLikeLocation = MemberLikeLocation.createMemberLikeLocation(location, member);
        memberLikeLocationRepository.save(newMemberLikeLocation);
    }


    @Test
    public void findByMember_Id() throws Exception {
        //given
        Long memberId = member.getId();

        //when
        List<MemberLikeLocation> result = memberLikeLocationRepository.findByMember_Id(memberId);

        //then
        assertThat(result.get(0).getLocation()).isEqualTo(location);
        assertThat(result.get(0).getLikeDate()).isEqualTo(newMemberLikeLocation.getLikeDate());
    }
}