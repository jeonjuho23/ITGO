package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberLikePost;
import itgo.it_secondhand.domain.SecondhandScrapedPost;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
@Transactional
class MemberLikePostRepositoryTest {

    @Autowired
    MemberLikePostRepository memberLikePostRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    SecondhandPostRepository secondhandPostRepository;

    @Test
    public void test() throws Exception {
        // given
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).get();
        Long postId = 1L;
//        SecondhandScrapedPost secondhandScrapedPost = SecondhandPostRepository.findById(postId).get();

//        MemberLikePost byMemberIdAndPostId = memberLikePostRepository.findByMemberAndPost(member, secondhandScrapedPost);




        // when

        // then

    }
}