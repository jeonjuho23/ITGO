package itgo.it_secondhand.service;

import itgo.it_secondhand.domain.Category;
import itgo.it_secondhand.domain.Device;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberLikeDevice;
import itgo.it_secondhand.domain.value.Location;
import itgo.it_secondhand.service.like.DTO.DeviceLikeResDTO;
import itgo.it_secondhand.service.like.DTO.LikeReqDTO;
import itgo.it_secondhand.repository.DeviceRepository;
import itgo.it_secondhand.repository.MemberLikeDeviceRepository;
import itgo.it_secondhand.repository.MemberRepository;
import itgo.it_secondhand.service.like.DeviceLikeServiceImpl;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Slf4j
class DeviceLikeServiceImplTest {

    @Autowired
    DeviceLikeServiceImpl deviceAlarmService;
    @Autowired
    MemberLikeDeviceRepository memberLikeDeviceRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DeviceRepository deviceRepository;


    @Autowired
    EntityManager em;

    public LikeReqDTO createLike() {
        Member member = Member.createMember("phone", "name", "imgAddress", new Location("city", "street", "zipcode"));

        Category category = Category.createCategory("제조사","기기 종류");
        Device device = Device.createDevice("deviceName", 1000, category, LocalDateTime.now(),"");

        memberRepository.save(member);
        deviceRepository.save(device);

        em.flush();
        em.clear();

        return new LikeReqDTO(member.getId(), device.getId());
    }


    @Test
    @Rollback(value = false)
    public void registTest() throws Exception {
        // given
        LikeReqDTO likeReqDTO = createLike();

        // when
        Long registId = deviceAlarmService.regist(likeReqDTO);

        // then
        MemberLikeDevice memberLikeDevice = memberLikeDeviceRepository.findById(registId).get();

        log.info("deviceId : "+ memberLikeDevice.getDevice().getId());
        assertEquals(likeReqDTO.getMemberId(), memberLikeDevice.getMember().getId(), "member가 달라??");
        assertEquals(likeReqDTO.getLikedThingId(), memberLikeDevice.getDevice().getId(), "device가 달라??");
    }
    
    @Test
    public void deleteTest() throws Exception {
        // given
        Member member = memberRepository.findAll().get(0);
        Device device = deviceRepository.findAll().get(0);

        LikeReqDTO likeReqDTO = new LikeReqDTO(member.getId(), device.getId());

        // when
        deviceAlarmService.delete(likeReqDTO);

        // then
    }

    @Test
    public void repositoryTest() throws Exception {
        // given
        MemberLikeDevice memberLikeDevice = memberLikeDeviceRepository.findAll().get(0);

        Member member = memberRepository.findById(memberLikeDevice.getMember().getId()).get();
        Device device = deviceRepository.findById(memberLikeDevice.getDevice().getId()).get();


        // when
        MemberLikeDevice byMemberAndDevice = memberLikeDeviceRepository.findByMemberAndDevice(member, device);

        // then
        log.info(byMemberAndDevice.getMember().getName());
        assertEquals(byMemberAndDevice, memberLikeDevice, "같은 값의 FK로 조회했으니 같아야 합니다.");

    }

    @Test
    public void checkListTest() throws Exception {
        // given
        Long memberId = 1L;

        // when
        List<DeviceLikeResDTO> checkList = deviceAlarmService.checkList(memberId);

        // then

        System.out.println("check list : " + checkList.get(0).getDeviceId() + " , "+checkList.get(0).getDeviceName());

    }

    
}