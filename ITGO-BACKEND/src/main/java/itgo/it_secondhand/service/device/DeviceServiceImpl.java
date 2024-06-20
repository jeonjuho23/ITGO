package itgo.it_secondhand.service.device;

import itgo.it_secondhand.domain.Device;
import itgo.it_secondhand.domain.LaptopInfo;
import itgo.it_secondhand.domain.MobileInfo;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.repository.LaptopInfoRepository;
import itgo.it_secondhand.repository.DeviceRepository;
import itgo.it_secondhand.repository.MobileInfoRepository;
import itgo.it_secondhand.service.device.DTO.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository deviceRepository;
    private final MobileInfoRepository mobileInfoRepository;
    private final LaptopInfoRepository laptopInfoRepository;


    @Override
    public FindDeviceListResDTO findDeviceList(FindDeviceListReqDTO findDeviceListReqDTO) {

        Pageable pageable = PageRequest.of(findDeviceListReqDTO.getPage(), findDeviceListReqDTO.getSize());
        Slice<Device> deviceSlice = deviceRepository.findSliceBy(pageable);

        if (deviceSlice.isEmpty()) throw new RestApiException(CustomExceptionCode.PAGE_NOT_FOUND);

        return getFindDeviceListResDTO(deviceSlice);
    }

    @Override
    public FindDeviceListResDTO findDeviceListByCategory(FindDeviceListByCategoryReqDTO findDeviceListByCategoryReqDTO) {

        Pageable pageable = PageRequest.of(findDeviceListByCategoryReqDTO.getPage(), findDeviceListByCategoryReqDTO.getSize());
        Slice<Device> deviceSlice = deviceRepository.findSliceByCategory_Id(pageable, findDeviceListByCategoryReqDTO.getCategory());

        if (deviceSlice.isEmpty()) throw new RestApiException(CustomExceptionCode.PAGE_NOT_FOUND);

        return getFindDeviceListResDTO(deviceSlice);
    }

    private FindDeviceListResDTO getFindDeviceListResDTO(Slice<Device> deviceSlice) {
        List<FindDeviceDTO> findDeviceList = new ArrayList<>();
        for (Device device: deviceSlice.getContent()){
            String image = "";
            if(device.getDetailId() != null) {
                MobileInfo mobileInfo = mobileInfoRepository.findById(device.getDetailId())
                        .orElse(MobileInfo.builder().build());
                image = mobileInfo.getImage();
            }
            findDeviceList.add(new FindDeviceDTO(device, image));
        }

        return FindDeviceListResDTO.builder()
                .deviceList(findDeviceList)
                .hasNext(deviceSlice.hasNext()).build();
    }


    @Override
    public FindDeviceInfoResDTO<MobileInfo> findMobileInfo(FindDeviceInfoReqDTO findDeviceInfoReqDTO) {

        MobileInfo mobileInfo = mobileInfoRepository.findById(findDeviceInfoReqDTO.getDetailId())
                .orElseThrow(()-> new RestApiException(CustomExceptionCode.DEVICE_NOT_FOUND));

        return FindDeviceInfoResDTO.<MobileInfo>builder()
                .info(mobileInfo).build();
    }

    @Override
    public FindDeviceInfoResDTO<LaptopInfo> findLaptopInfo(FindDeviceInfoReqDTO findDeviceInfoReqDTO) {

        LaptopInfo laptopInfo = laptopInfoRepository.findById(findDeviceInfoReqDTO.getDetailId())
                .orElseThrow(()-> new RestApiException(CustomExceptionCode.DEVICE_NOT_FOUND));

        return FindDeviceInfoResDTO.<LaptopInfo>builder()
                .info(laptopInfo).build();
    }


}
