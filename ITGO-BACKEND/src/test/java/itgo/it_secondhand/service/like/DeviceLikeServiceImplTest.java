package itgo.it_secondhand.service.like;

import itgo.it_secondhand.domain.Device;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberLikeDevice;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.repository.DeviceRepository;
import itgo.it_secondhand.repository.MemberLikeDeviceRepository;
import itgo.it_secondhand.repository.MemberRepository;
import itgo.it_secondhand.service.like.DTO.DeviceLikeResDTO;
import itgo.it_secondhand.service.like.DTO.DeviceResDTO;
import itgo.it_secondhand.service.like.DTO.LikeReqDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceLikeServiceImplTest {

    @InjectMocks
    DeviceLikeServiceImpl deviceLikeService;

    @Mock
    DeviceRepository deviceRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    MemberLikeDeviceRepository memberLikeDeviceRepository;


    @Test
    public void regist() throws Exception {
        //given
        when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Mockito.mock(Member.class)));
        when(deviceRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Mockito.mock(Device.class)));

        MemberLikeDevice memberLikeDevice = MemberLikeDevice.builder()
                .id(1L).build();
        when(memberLikeDeviceRepository.save(Mockito.any(MemberLikeDevice.class)))
                .thenReturn(memberLikeDevice);


        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        Long response = deviceLikeService.regist(request);

        //then
        assertThat(response).isEqualTo(memberLikeDevice.getId());
    }


    @Test
    public void registThrowMemberNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceLikeService.regist(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.MEMBER_NOT_FOUND);

    }


    @Test
    public void registThrowDeviceNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Mockito.mock(Member.class)));
        when(deviceRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());


        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceLikeService.regist(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.DEVICE_NOT_FOUND);
    }


    @Test
    public void delete() throws Exception {
        //given
        when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Mockito.mock(Member.class)));
        when(deviceRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Mockito.mock(Device.class)));

        when(memberLikeDeviceRepository.findByMemberAndDevice(Mockito.any(Member.class), Mockito.any(Device.class)))
                .thenReturn(Optional.of(Mockito.mock(MemberLikeDevice.class)));


        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        deviceLikeService.delete(request);

        //then

    }


    @Test
    public void deleteThrowMemberNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());


        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceLikeService.delete(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.MEMBER_NOT_FOUND);

    }


    @Test
    public void deleteThrowDeviceNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Mockito.mock(Member.class)));
        when(deviceRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());


        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceLikeService.delete(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.DEVICE_NOT_FOUND);
    }


    @Test
    public void deleteThrowLikeNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Mockito.mock(Member.class)));
        when(deviceRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Mockito.mock(Device.class)));

        when(memberLikeDeviceRepository.findByMemberAndDevice(Mockito.any(Member.class), Mockito.any(Device.class)))
                .thenReturn(Optional.empty());


        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceLikeService.delete(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.LIKE_NOT_FOUND);
    }

    @Test
    public void checkList() throws Exception {
        //given
        Device device = Device.builder().id(1L).deviceName("deviceName").build();
        MemberLikeDevice memberLikeDevice = MemberLikeDevice.builder().device(device).build();

        List<MemberLikeDevice> list = new ArrayList<>(List.of(memberLikeDevice));
        when(memberLikeDeviceRepository.findAllByMember_Id(Mockito.anyLong()))
                .thenReturn(list);


        Long request = 1L;

        //when
        List<DeviceLikeResDTO> response = deviceLikeService.checkList(request);

        //then
        assertThat(response.get(0).getDeviceId()).isEqualTo(list.get(0).getDevice().getId());
        assertThat(response.get(0).getDeviceName()).isEqualTo(list.get(0).getDevice().getDeviceName());

    }


    @Test
    public void checkListThrowNoLikeListException() throws Exception {
        //given
        List<MemberLikeDevice> list = new ArrayList<>();
        when(memberLikeDeviceRepository.findAllByMember_Id(Mockito.anyLong()))
                .thenReturn(list);


        Long request = 1L;

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceLikeService.checkList(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.NO_LIKE_LIST);

    }


    @Test
    public void findByKeyword() throws Exception {
        //given

        Device device = Device.builder().id(1L).deviceName("deviceName").build();
        List<Device> list = new ArrayList<>(List.of(device));
        when(deviceRepository.searchDeviceByDeviceName(Mockito.anyString()))
                .thenReturn(list);


        String request = "keyword";

        //when
        List<DeviceResDTO> response = deviceLikeService.findByKeyword(request);

        //then
        assertThat(response.get(0).getId()).isEqualTo(list.get(0).getId());
        assertThat(response.get(0).getDeviceName()).isEqualTo(list.get(0).getDeviceName());

    }


    @Test
    public void findByKeywordThrowNoLikeListException() throws Exception {
        //given
        List<Device> list = new ArrayList<>();
        when(deviceRepository.searchDeviceByDeviceName(Mockito.anyString()))
                .thenReturn(list);


        String request = "keyword";

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceLikeService.findByKeyword(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.NO_LIKE_LIST);

    }

}