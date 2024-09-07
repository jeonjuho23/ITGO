package itgo.it_secondhand.service.device;

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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.Optional;

import static itgo.it_secondhand.StubFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
        Slice<Device> deviceSlice = getDeviceSlice();
        when(deviceRepository.findSliceBy(any(Pageable.class)))
                .thenReturn(deviceSlice);

        Optional<MobileInfo> optionalMobileInfo = Optional.of(getMobileInfo());
        when(mobileInfoRepository.findById(anyString()))
                .thenReturn(optionalMobileInfo);

        Pageable pageable = getPageable();
        FindDeviceListReqDTO request = FindDeviceListReqDTO.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();

        //when
        FindDeviceListResDTO response = deviceService.findDeviceList(request);

        //then
        assertThat(response.getDeviceList().get(0).getImage())
                .isEqualTo(optionalMobileInfo.get().getImage());
        assertThat(response.getDeviceList().get(0).getDeviceName())
                .isEqualTo(deviceSlice.getContent().get(0).getDeviceName());
        assertThat(response.getHasNext())
                .isFalse();
    }


    @Test
    public void findDeviceListThrowPageNotFoundException() throws Exception {
        //given
        Slice<Device> deviceSlice = getDeviceSlice(new ArrayList<>());
        when(deviceRepository.findSliceBy(any(Pageable.class)))
                .thenReturn(deviceSlice);

        Pageable pageable = getPageable();
        FindDeviceListReqDTO request = FindDeviceListReqDTO.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceService.findDeviceList(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.PAGE_NOT_FOUND);
    }


    @Test
    public void findDeviceListByCategory() throws Exception {
        //given
        Slice<Device> deviceSlice = getDeviceSlice();
        when(deviceRepository.findSliceByCategory_Id(any(Pageable.class), anyLong()))
                .thenReturn(deviceSlice);

        Optional<MobileInfo> mobileInfo = Optional.of(getMobileInfo());
        when(mobileInfoRepository.findById(anyString()))
                .thenReturn(mobileInfo);

        Pageable pageable = getPageable();
        FindDeviceListByCategoryReqDTO request = FindDeviceListByCategoryReqDTO.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .category(1L)
                .build();

        //when
        FindDeviceListResDTO response = deviceService.findDeviceListByCategory(request);

        //then
        assertThat(response.getDeviceList().get(0).getImage())
                .isEqualTo(mobileInfo.get().getImage());
        assertThat(response.getDeviceList().get(0).getDeviceName())
                .isEqualTo(deviceSlice.getContent().get(0).getDeviceName());
        assertThat(response.getHasNext())
                .isFalse();
    }

    @Test
    public void findDeviceListByCategoryThrowPageNotFoundException() throws Exception {
        //given
        Slice<Device> deviceSlice = getDeviceSlice(new ArrayList<>());
        when(deviceRepository.findSliceByCategory_Id(any(Pageable.class), anyLong()))
                .thenReturn(deviceSlice);

        Pageable pageable = getPageable();
        FindDeviceListByCategoryReqDTO request = FindDeviceListByCategoryReqDTO.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .category(1L)
                .build();

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceService.findDeviceListByCategory(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.PAGE_NOT_FOUND);
    }


    @Test
    public void findMobileInfo() throws Exception {
        //given
        Optional<MobileInfo> optionalMobileInfo = getOptionalMobileInfo();
        when(mobileInfoRepository.findById(anyString()))
                .thenReturn(optionalMobileInfo);

        FindDeviceInfoReqDTO request = new FindDeviceInfoReqDTO(optionalMobileInfo.get().getId());

        //when
        FindDeviceInfoResDTO<MobileInfo> response = deviceService.findMobileInfo(request);

        //then
        assertThat(response.getInfo().getId())
                .isEqualTo(request.getDetailId());
        assertThat(response.getInfo().getModelname())
                .isEqualTo(optionalMobileInfo.get().getModelname());
    }


    @Test
    public void findMobileInfoThrowDeviceNotFoundException() throws Exception {
        //given
        Optional<MobileInfo> mobileInfo = Optional.empty();
        when(mobileInfoRepository.findById(anyString()))
                .thenReturn(mobileInfo);

        FindDeviceInfoReqDTO request = new FindDeviceInfoReqDTO("detailId");

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceService.findMobileInfo(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.DEVICE_NOT_FOUND);
    }

    @Test
    public void findLaptopInfo() throws Exception {
        //given
        Optional<LaptopInfo> optionalLaptopInfo = getOptionalLaptopInfo();
        when(laptopInfoRepository.findById(anyString()))
                .thenReturn(optionalLaptopInfo);

        FindDeviceInfoReqDTO request = new FindDeviceInfoReqDTO(optionalLaptopInfo.get().getId());

        //when
        FindDeviceInfoResDTO<LaptopInfo> response = deviceService.findLaptopInfo(request);

        //then
        assertThat(response.getInfo().getId())
                .isEqualTo(request.getDetailId());
        assertThat(response.getInfo().getModelname())
                .isEqualTo(optionalLaptopInfo.get().getModelname());
    }

    @Test
    public void findLaptopInfoThrowDeviceNotFoundException() throws Exception {
        //given
        Optional<LaptopInfo> laptopInfo = Optional.empty();
        when(laptopInfoRepository.findById(anyString()))
                .thenReturn(laptopInfo);

        FindDeviceInfoReqDTO request = new FindDeviceInfoReqDTO("detailId");

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            deviceService.findLaptopInfo(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.DEVICE_NOT_FOUND);
    }

}