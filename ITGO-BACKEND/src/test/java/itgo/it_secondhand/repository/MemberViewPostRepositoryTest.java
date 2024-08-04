package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.*;
import itgo.it_secondhand.domain.value.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberViewPostRepositoryTest {

    @Autowired
    MemberViewPostRepository memberViewPostRepository;

    private static MemberViewPost newMemberViewPost;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    SecondhandPostRepository postRepository;
    private static Member member;
    private static Device device;
    private static SecondhandScrapedPost post;

    @BeforeEach
    void setUp(){
        Location location = new Location("city", "street", "zipcode");
        member = Member.builder()
                .location(location)
                .name("name")
                .phone("phone")
                .imgAddress("imgAddress")
                .build();
        Category category = Category.createCategory("manufacturer", "deviceType");
        device = Device.builder()
                .category(category)
                .detailId("detailId")
                .deviceName("deviceName")
                .launchPrice(1000)
                .releaseDate(LocalDateTime.now())
                .build();

        post = SecondhandScrapedPost.createPost(member, "postTitle", "postContent", "imgFolderAddress", device, 1000, "postUrl", location);

        memberRepository.save(member);
        categoryRepository.save(category);
        deviceRepository.save(device);
        postRepository.save(post);

        newMemberViewPost = MemberViewPost.createMemberViewPost(member, post);
        memberViewPostRepository.save(newMemberViewPost);
    }



    @Test
    public void findTopByMemberAndPostOrderByViewDateDesc() throws Exception {
        //given

        //when
        MemberViewPost result = memberViewPostRepository.findTopByMemberAndPostOrderByViewDateDesc(member, post);

        //then
        assertThat(result.getViewDate()).isEqualTo(newMemberViewPost.getViewDate());
    }
}