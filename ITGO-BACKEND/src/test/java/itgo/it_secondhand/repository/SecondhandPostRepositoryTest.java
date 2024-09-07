package itgo.it_secondhand.repository;

import itgo.it_secondhand.StubFactory;
import itgo.it_secondhand.domain.*;
import itgo.it_secondhand.domain.value.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;

import static itgo.it_secondhand.StubFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SecondhandPostRepositoryTest {

    @Autowired
    SecondhandPostRepository secondhandPostRepository;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    MemberLikePostRepository memberLikePostRepository;

    private SecondhandScrapedPost post;
    private Category category;
    private Member member;
    private Device device;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MemberLikePost memberLikePost = getMemberLikeSecondhandScrapedPost();

        member = memberLikePost.getMember();
        post = (SecondhandScrapedPost) memberLikePost.getPost();
        device = post.getDevice();
        category = device.getCategory();

        pageable = getPageable();

        memberRepository.save(member);
        categoryRepository.save(category);
        deviceRepository.save(device);
        secondhandPostRepository.save(post);

        memberLikePostRepository.save(memberLikePost);
    }


    @Test
    public void findSliceBy() throws Exception {
        //given

        //when
        Slice<SecondhandScrapedPost> result = secondhandPostRepository.findSliceBy(pageable);

        //then
        assertThat(result.getContent().get(0).getId())
                .isEqualTo(post.getId());
    }

    @Test
    public void findLikePostByMember_Id() throws Exception {
        //given
        Long memberId = member.getId();

        //when
        Slice<SecondhandScrapedPost> result = secondhandPostRepository.findLikePostByMember_Id(memberId, pageable);

        //then
        SecondhandScrapedPost savedPost = result.getContent().get(0);
        assertThat(savedPost.getMember().getName())
                .isEqualTo(member.getName());
        assertThat(savedPost.getDevice().getDeviceName())
                .isEqualTo(device.getDeviceName());
    }


    @Test
    public void searchSecondhandPostByDeviceName() throws Exception {
        //given
        String keyword = device.getDeviceName();

        //when
        Slice<SecondhandScrapedPost> result = secondhandPostRepository.searchSecondhandPostByDeviceName(keyword, pageable);

        //then
        SecondhandScrapedPost savedPost = result.getContent().get(0);
        assertThat(savedPost.getMember().getName())
                .isEqualTo(member.getName());
        assertThat(savedPost.getDevice().getDeviceName())
                .isEqualTo(device.getDeviceName());
    }


    @Test
    public void findByDevice_Category_Id() throws Exception {
        //given
        Long categoryId = category.getId();

        //when
        Slice<SecondhandScrapedPost> result = secondhandPostRepository.findByDevice_Category_Id(categoryId, pageable);

        //then
        SecondhandScrapedPost savedPost = result.getContent().get(0);
        assertThat(savedPost.getMember().getName())
                .isEqualTo(member.getName());
        assertThat(savedPost.getDevice().getDeviceName())
                .isEqualTo(device.getDeviceName());
    }


    @Test
    public void findByLocation_City() throws Exception {
        //given
        String city = post.getLocation().getCity();

        //when
        Slice<SecondhandScrapedPost> result = secondhandPostRepository.findByLocation_City(city, pageable);

        //then
        SecondhandScrapedPost savedPost = result.getContent().get(0);
        assertThat(savedPost.getMember().getName())
                .isEqualTo(member.getName());
        assertThat(savedPost.getDevice().getDeviceName())
                .isEqualTo(device.getDeviceName());
    }
}