package itgo.it_secondhand.service.device;

import itgo.it_secondhand.domain.Category;
import itgo.it_secondhand.domain.Device;
import itgo.it_secondhand.domain.LaptopInfo;
import itgo.it_secondhand.domain.MobileInfo;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.repository.DeviceRepository;
import itgo.it_secondhand.repository.LaptopInfoRepository;
import itgo.it_secondhand.repository.MobileInfoRepository;
import itgo.it_secondhand.service.device.DTO.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceServiceImplTest {

    @InjectMocks
    DeviceServiceImpl deviceService;

    @Mock
    DeviceRepository deviceRepository;
    @Mock
    LaptopInfoRepository laptopInfoRepository;
    @Mock
    MobileInfoRepository mobileInfoRepository;


    @Test
    public void findDeviceList() throws Exception {
        //given
        Device device = Device.builder()
                .id(1L)
                .deviceName("deviceName")
                .releaseDate(LocalDateTime.now())
                .launchPrice(1000)
                .detailId("detailId")
                .category(Category.createCategory("manufacturer", "deviceType"))
                .build();
        List<Device> content = new ArrayList<>(List.of(device));
        Pageable pageable = PageRequest.of(0, 10);
        Slice<Device> deviceSlice = new SliceImpl<>(content, pageable, false);
        when(deviceRepository.findSliceBy(Mockito.any(Pageable.class)))
                .thenReturn(deviceSlice);

        Optional<MobileInfo> mobileInfo = Optional.of(MobileInfo.builder().image("image").build());
        when(mobileInfoRepository.findById(Mockito.anyString()))
                .thenReturn(mobileInfo);


        FindDeviceListReqDTO request = FindDeviceListReqDTO.builder()
                .page(0).size(10).build();

        //when
        FindDeviceListResDTO response = deviceService.findDeviceList(request);

        //then
        assertThat(response.getDeviceList().get(0).getImage()).isEqualTo(mobileInfo.get().getImage());
        assertThat(response.getDeviceList().get(0).getDeviceName()).isEqualTo(content.get(0).getDeviceName());
        assertThat(response.getHasNext()).isFalse();

    }


    @Test
    public void findDeviceListThrowPageNotFoundException() throws Exception {
        //given
        List<Device> content = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 10);
        Slice<Device> deviceSlice = new SliceImpl<>(content, pageable, false);
        when(deviceRepository.findSliceBy(Mockito.any(Pageable.class)))
                .thenReturn(deviceSlice);


        FindDeviceListReqDTO request = FindDeviceListReqDTO.builder()
                .page(0).size(10).build();

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceService.findDeviceList(request);
        });


        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.PAGE_NOT_FOUND);

    }


    @Test
    public void findDeviceListByCategory() throws Exception {
        //given
        Device device = Device.builder()
                .id(1L)
                .deviceName("deviceName")
                .releaseDate(LocalDateTime.now())
                .launchPrice(1000)
                .detailId("detailId")
                .category(Category.createCategory("manufacturer", "deviceType"))
                .build();
        List<Device> content = new ArrayList<>(List.of(device));
        Pageable pageable = PageRequest.of(0, 10);
        Slice<Device> deviceSlice = new SliceImpl<>(content, pageable, false);
        when(deviceRepository.findSliceByCategory_Id(Mockito.any(Pageable.class), Mockito.anyLong()))
                .thenReturn(deviceSlice);

        Optional<MobileInfo> mobileInfo = Optional.of(MobileInfo.builder().image("image").build());
        when(mobileInfoRepository.findById(Mockito.anyString()))
                .thenReturn(mobileInfo);


        FindDeviceListByCategoryReqDTO request =
                new FindDeviceListByCategoryReqDTO(0, 10, 1L);

        //when
        FindDeviceListResDTO response = deviceService.findDeviceListByCategory(request);

        //then
        assertThat(response.getDeviceList().get(0).getImage()).isEqualTo(mobileInfo.get().getImage());
        assertThat(response.getDeviceList().get(0).getDeviceName()).isEqualTo(content.get(0).getDeviceName());
        assertThat(response.getHasNext()).isFalse();

    }

    @Test
    public void findDeviceListByCategoryThrowPageNotFoundException() throws Exception {
        //given
        List<Device> content = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 10);
        Slice<Device> deviceSlice = new SliceImpl<>(content, pageable, false);
        when(deviceRepository.findSliceByCategory_Id(Mockito.any(Pageable.class), Mockito.anyLong()))
                .thenReturn(deviceSlice);


        FindDeviceListByCategoryReqDTO request =
                new FindDeviceListByCategoryReqDTO(0, 10, 1L);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceService.findDeviceListByCategory(request);
        });


        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.PAGE_NOT_FOUND);

    }


    @Test
    public void findMobileInfo() throws Exception {
        //given
        String detailId = "detailId";
        Optional<MobileInfo> mobileInfo = Optional.of(MobileInfo.builder().id(detailId).modelname("modelName").build());
        when(mobileInfoRepository.findById(Mockito.anyString()))
                .thenReturn(mobileInfo);


        FindDeviceInfoReqDTO request = new FindDeviceInfoReqDTO(detailId);

        //when
        FindDeviceInfoResDTO<MobileInfo> response = deviceService.findMobileInfo(request);

        //then
        assertThat(response.getInfo().getId()).isEqualTo(request.getDetailId());
        assertThat(response.getInfo().getModelname()).isEqualTo(mobileInfo.get().getModelname());

    }


    @Test
    public void findMobileInfoThrowDeviceNotFoundException() throws Exception {
        //given
        Optional<MobileInfo> mobileInfo = Optional.empty();
        when(mobileInfoRepository.findById(Mockito.anyString()))
                .thenReturn(mobileInfo);


        String detailId = "detailId";
        FindDeviceInfoReqDTO request = new FindDeviceInfoReqDTO(detailId);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceService.findMobileInfo(request);
        });

        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.DEVICE_NOT_FOUND);

    }

    @Test
    public void findLaptopInfo() throws Exception {
        //given
        String detailId = "detailId";
        Optional<LaptopInfo> laptopInfo = Optional.of(LaptopInfo.builder().id(detailId).modelname("modelName").build());
        when(laptopInfoRepository.findById(Mockito.anyString()))
                .thenReturn(laptopInfo);

        FindDeviceInfoReqDTO request = new FindDeviceInfoReqDTO(detailId);

        //when
        FindDeviceInfoResDTO<LaptopInfo> response = deviceService.findLaptopInfo(request);

        //then
        assertThat(response.getInfo().getId()).isEqualTo(request.getDetailId());
        assertThat(response.getInfo().getModelname()).isEqualTo(laptopInfo.get().getModelname());

    }

    @Test
    public void findLaptopInfoThrowDeviceNotFoundException() throws Exception {
        //given
        Optional<LaptopInfo> laptopInfo = Optional.empty();
        when(laptopInfoRepository.findById(Mockito.anyString()))
                .thenReturn(laptopInfo);


        String detailId = "detailId";
        FindDeviceInfoReqDTO request = new FindDeviceInfoReqDTO(detailId);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceService.findLaptopInfo(request);
        });

        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.DEVICE_NOT_FOUND);

    }

}