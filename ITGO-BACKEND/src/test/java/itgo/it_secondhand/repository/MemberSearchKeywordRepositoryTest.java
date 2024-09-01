package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Keyword;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberSearchKeyword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import static itgo.it_secondhand.StubFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberSearchKeywordRepositoryTest {

    @Autowired
    MemberSearchKeywordRepository memberSearchKeywordRepository;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    KeywordRepository keywordRepository;

    private MemberSearchKeyword memberSearchKeyword;

    private Member member;
    private Keyword keyword;

    @BeforeEach
    void setUp(){
        memberSearchKeyword = getMemberSearchKeyword();

        member = memberSearchKeyword.getMember();
        keyword = memberSearchKeyword.getKeyword();

        memberRepository.save(member);
        keywordRepository.save(keyword);

        memberSearchKeywordRepository.save(memberSearchKeyword);
    }


    @Test
    public void findByMember_IdAndKeyword_Id() throws Exception {
        //given
        Long memberId = member.getId();
        Long keywordId = keyword.getId();

        //when
        MemberSearchKeyword result = memberSearchKeywordRepository.findByMember_IdAndKeyword_Id(memberId, keywordId);

        //then
        assertThat(result.getSearchDate())
                .isEqualTo(memberSearchKeyword.getSearchDate());
        assertThat(result.getKeyword().getCount())
                .isEqualTo(keyword.getCount());
    }


    @Test
    public void findSliceByMember_Id() throws Exception {
        //given
        Long memberId = member.getId();
        Pageable pageable = getPageable();

        //when
        Slice<MemberSearchKeyword> result = memberSearchKeywordRepository.findSliceByMember_Id(memberId, pageable);

        //then
        assertThat(result.getContent().get(0).getSearchDate())
                .isEqualTo(memberSearchKeyword.getSearchDate());
    }
}