package itgo.it_secondhand.repository;

import itgo.it_secondhand.StubFactory;
import itgo.it_secondhand.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static itgo.it_secondhand.StubFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberLikePostRepositoryTest {

    @Autowired
    MemberLikePostRepository memberLikePostRepository;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    SecondhandPostRepository postRepository;

    private MemberLikePost memberLikePost;
    private Member member;
    private Device device;
    private SecondhandScrapedPost post;

    @BeforeEach
    void setUp(){
        memberLikePost = getMemberLikeSecondhandScrapedPost();

        member = memberLikePost.getMember();
        post = (SecondhandScrapedPost) memberLikePost.getPost();
        device = post.getDevice();
        Category category = device.getCategory();

        memberRepository.save(member);
        categoryRepository.save(category);
        deviceRepository.save(device);
        postRepository.save(post);

        memberLikePostRepository.save(memberLikePost);
    }


    @Test
    public void findByMemberAndPost() throws Exception {
        //given

        //when
        MemberLikePost result = memberLikePostRepository.findByMemberAndPost(member, post).get();

        //then
        assertThat(result.getMember().getId())
                .isEqualTo(member.getId());
        assertThat(result.getPost().getId())
                .isEqualTo(post.getId());
    }


}