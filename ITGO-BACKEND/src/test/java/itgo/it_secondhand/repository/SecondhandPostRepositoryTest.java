package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.*;
import itgo.it_secondhand.domain.value.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SecondhandPostRepositoryTest {

    @Autowired
    SecondhandPostRepository secondhandPostRepository;

    private static SecondhandScrapedPost newPost;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    MemberLikePostRepository memberLikePostRepository;
    private static Category category;
    private static Member member;
    private static Device device;

    private static final Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    void setUp() {
        Location location = new Location("city", "street", "zipcode");
        member = Member.builder()
                .location(location)
                .name("name")
                .phone("phone")
                .imgAddress("imgAddress")
                .build();
        category = Category.createCategory("manufacturer", "deviceType");
        device = Device.builder()
                .category(category)
                .detailId("detailId")
                .deviceName("deviceName")
                .launchPrice(1000)
                .releaseDate(LocalDateTime.now())
                .build();

        memberRepository.save(member);
        categoryRepository.save(category);
        deviceRepository.save(device);

        newPost = SecondhandScrapedPost.createPost(member, "postTitle", "postContent", "imgFolderAddress", device, 1000, "postUrl", location);
        secondhandPostRepository.save(newPost);

        MemberLikePost memberLikePost = MemberLikePost.createMemberLikePost(member, newPost);
        memberLikePostRepository.save(memberLikePost);
    }


    @Test
    public void findSliceBy() throws Exception {
        //given

        //when
        Slice<SecondhandScrapedPost> result = secondhandPostRepository.findSliceBy(pageable);

        //then
        assertThat(result.getContent().get(0).getId()).isEqualTo(newPost.getId());
    }

    @Test
    public void findLikePostByMember_Id() throws Exception {
        //given
        Long memberId = member.getId();

        //when
        Slice<SecondhandScrapedPost> result = secondhandPostRepository.findLikePostByMember_Id(memberId, pageable);

        //then
        SecondhandScrapedPost savedPost = result.getContent().get(0);
        assertThat(savedPost.getMember().getName()).isEqualTo(member.getName());
        assertThat(savedPost.getDevice().getDeviceName()).isEqualTo(device.getDeviceName());
    }


    @Test
    public void searchSecondhandPostByDeviceName() throws Exception {
        //given
        String keyword = device.getDeviceName();

        //when
        Slice<SecondhandScrapedPost> result = secondhandPostRepository.searchSecondhandPostByDeviceName(keyword, pageable);

        //then
        SecondhandScrapedPost savedPost = result.getContent().get(0);
        assertThat(savedPost.getMember().getName()).isEqualTo(member.getName());
        assertThat(savedPost.getDevice().getDeviceName()).isEqualTo(device.getDeviceName());
    }


    @Test
    public void findByDevice_Category_Id() throws Exception {
        //given
        Long categoryId = category.getId();

        //when
        Slice<SecondhandScrapedPost> result = secondhandPostRepository.findByDevice_Category_Id(categoryId, pageable);

        //then
        SecondhandScrapedPost savedPost = result.getContent().get(0);
        assertThat(savedPost.getMember().getName()).isEqualTo(member.getName());
        assertThat(savedPost.getDevice().getDeviceName()).isEqualTo(device.getDeviceName());
    }


    @Test
    public void findByLocation_City() throws Exception {
        //given
        String city = newPost.getLocation().getCity();

        //when
        Slice<SecondhandScrapedPost> result = secondhandPostRepository.findByLocation_City(city, pageable);

        //then
        SecondhandScrapedPost savedPost = result.getContent().get(0);
        assertThat(savedPost.getMember().getName()).isEqualTo(member.getName());
        assertThat(savedPost.getDevice().getDeviceName()).isEqualTo(device.getDeviceName());
    }
}