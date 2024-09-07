package itgo.it_secondhand.repository;

import itgo.it_secondhand.StubFactory;
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

import static itgo.it_secondhand.StubFactory.getMemberLikeDevice;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberLikeDeviceRepositoryTest {

    @Autowired
    MemberLikeDeviceRepository memberLikeDeviceRepository;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    CategoryRepository categoryRepository;

    private MemberLikeDevice memberLikeDevice;
    private Member member;
    private Device device;

    @BeforeEach
    void setUp(){
        memberLikeDevice = getMemberLikeDevice();

        member = memberLikeDevice.getMember();
        device = memberLikeDevice.getDevice();
        Category category = device.getCategory();

        memberRepository.save(member);
        categoryRepository.save(category);
        deviceRepository.save(device);

        memberLikeDeviceRepository.save(memberLikeDevice);
    }


    @Test
    public void findByMemberAndDevice() throws Exception {
        //given

        //when
        MemberLikeDevice result = memberLikeDeviceRepository.findByMemberAndDevice(member, device).get();

        //then
        assertThat(result.getMember().getId())
                .isEqualTo(member.getId());
        assertThat(result.getLikeDate())
                .isEqualTo(memberLikeDevice.getLikeDate());
    }


    @Test
    public void findAllByMember_Id() throws Exception {
        //given
        Long memberId = member.getId();

        //when
        List<MemberLikeDevice> result = memberLikeDeviceRepository.findAllByMember_Id(memberId);

        //then
        assertThat(result.get(0).getMember().getId())
                .isEqualTo(member.getId());
        assertThat(result.get(0).getLikeDate())
                .isEqualTo(memberLikeDevice.getLikeDate());
    }
}