package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Category;
import itgo.it_secondhand.domain.Device;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberLikeDevice;
import itgo.it_secondhand.domain.value.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberLikeDeviceRepositoryTest {

    @Autowired
    MemberLikeDeviceRepository memberLikeDeviceRepository;

    private static MemberLikeDevice newMemberLikeDevice;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    CategoryRepository categoryRepository;
    private static Member member;
    private static Device device;

    @BeforeEach
    void setUp(){
        member = Member.builder()
                .phone("phone")
                .name("name")
                .imgAddress("imgAddress")
                .location(new Location("city", "street", "zipcode"))
                .build();
        Category category = Category.createCategory("manufacturer", "deviceType");
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

        newMemberLikeDevice = MemberLikeDevice.createMemberLikeDevice(member, device);
        memberLikeDeviceRepository.save(newMemberLikeDevice);
    }


    @Test
    public void findByMemberAndDevice() throws Exception {
        //given

        //when
        MemberLikeDevice result = memberLikeDeviceRepository.findByMemberAndDevice(member, device).get();

        //then
        assertThat(result.getMember().getId()).isEqualTo(member.getId());
        assertThat(result.getLikeDate()).isEqualTo(newMemberLikeDevice.getLikeDate());
    }


    @Test
    public void findAllByMember_Id() throws Exception {
        //given
        Long memberId = member.getId();

        //when
        List<MemberLikeDevice> result = memberLikeDeviceRepository.findAllByMember_Id(memberId);

        //then
        assertThat(result.get(0).getMember().getId()).isEqualTo(member.getId());
        assertThat(result.get(0).getLikeDate()).isEqualTo(newMemberLikeDevice.getLikeDate());
    }
}