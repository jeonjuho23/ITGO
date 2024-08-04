package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.value.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    private static Member member;

    @BeforeEach
    void setUp(){
        Location location = new Location("city", "street", "zipcode");
        member = Member.builder()
                .location(location)
                .name("name")
                .phone("phone")
                .imgAddress("imgAddress")
                .build();

        memberRepository.save(member);
    }


    @Test
    public void findByPhone() throws Exception {
        //given
        String phone = member.getPhone();

        //when
        Optional<Member> optionalResult = memberRepository.findByPhone(phone);

        //then
        assertThat(optionalResult.isPresent()).isTrue();

        Member result = optionalResult.orElseThrow();
        assertThat(result.getId()).isEqualTo(member.getId());
    }


    @Test
    public void existByPhone() throws Exception {
        //given
        String phone = member.getPhone();

        //when
        Boolean result = memberRepository.existsByPhone(phone);

        //then
        assertThat(result).isTrue();
    }
}