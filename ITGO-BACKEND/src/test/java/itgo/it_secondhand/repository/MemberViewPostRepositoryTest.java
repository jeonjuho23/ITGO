package itgo.it_secondhand.repository;

import itgo.it_secondhand.StubFactory;
import itgo.it_secondhand.domain.*;
import itgo.it_secondhand.domain.value.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static itgo.it_secondhand.StubFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberViewPostRepositoryTest {

    @Autowired
    MemberViewPostRepository memberViewPostRepository;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    SecondhandPostRepository postRepository;

    private MemberViewPost memberViewPost;

    private Member member;
    private Device device;
    private SecondhandScrapedPost post;

    @BeforeEach
    void setUp(){
        post = getSecondhandScrapedPost();
        memberViewPost = getMemberViewPost(post);
        device = post.getDevice();
        member = memberViewPost.getMember();
        Category category = device.getCategory();

        memberRepository.save(member);
        categoryRepository.save(category);
        deviceRepository.save(device);
        postRepository.save(post);

        memberViewPostRepository.save(memberViewPost);
    }


    @Test
    public void findTopByMemberAndPostOrderByViewDateDesc() throws Exception {
        //given

        //when
        MemberViewPost result = memberViewPostRepository.findTopByMemberAndPostOrderByViewDateDesc(member, post);

        //then
        assertThat(result.getViewDate())
                .isEqualTo(memberViewPost.getViewDate());
    }
}