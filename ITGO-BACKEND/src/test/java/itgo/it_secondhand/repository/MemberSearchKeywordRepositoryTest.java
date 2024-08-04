package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Keyword;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberSearchKeyword;
import itgo.it_secondhand.domain.value.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberSearchKeywordRepositoryTest {

    @Autowired
    MemberSearchKeywordRepository memberSearchKeywordRepository;

    private static MemberSearchKeyword newMemberSearchKeyword;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    KeywordRepository keywordRepository;
    private static Member member;
    private static Keyword keyword;

    @BeforeEach
    void setUp(){
        Location location = new Location("city", "street", "zipcode");
        member = Member.builder()
                .location(location)
                .name("name")
                .phone("phone")
                .imgAddress("imgAddress")
                .build();
        keyword = Keyword.create("keyword");

        memberRepository.save(member);
        keywordRepository.save(keyword);


        newMemberSearchKeyword = MemberSearchKeyword.createMemberSearchKeyword(member, keyword);
        memberSearchKeywordRepository.save(newMemberSearchKeyword);
    }


    @Test
    public void findByMember_IdAndKeyword_Id() throws Exception {
        //given
        Long memberId = member.getId();
        Long keywordId = keyword.getId();

        //when
        MemberSearchKeyword result = memberSearchKeywordRepository.findByMember_IdAndKeyword_Id(memberId, keywordId);

        //then
        assertThat(result.getSearchDate()).isEqualTo(newMemberSearchKeyword.getSearchDate());
        assertThat(result.getKeyword().getCount()).isEqualTo(keyword.getCount());
    }


    @Test
    public void findSliceByMember_Id() throws Exception {
        //given
        Long memberId = member.getId();
        Pageable pageable = PageRequest.of(0, 10);

        //when
        Slice<MemberSearchKeyword> result = memberSearchKeywordRepository.findSliceByMember_Id(memberId, pageable);

        //then
        assertThat(result.getContent().get(0).getSearchDate()).isEqualTo(newMemberSearchKeyword.getSearchDate());
    }
}