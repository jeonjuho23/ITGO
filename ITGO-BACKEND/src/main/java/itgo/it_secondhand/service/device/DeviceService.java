package itgo.it_secondhand.service.device;

import itgo.it_secondhand.domain.LaptopInfo;
import itgo.it_secondhand.domain.MobileInfo;
import itgo.it_secondhand.service.device.DTO.*;

public interface DeviceService {

    public FindDeviceListResDTO findDeviceList(FindDeviceListReqDTO findDeviceListReqDTO);
    public FindDeviceListResDTO findDeviceListByCategory(FindDeviceListByCategoryReqDTO findDeviceListByCategoryReqDTO);

    public FindDeviceInfoResDTO<MobileInfo> findMobileInfo(FindDeviceInfoReqDTO findDeviceInfoReqDTO);
    public FindDeviceInfoResDTO<LaptopInfo> findLaptopInfo(FindDeviceInfoReqDTO findDeviceInfoReqDTO);

}
