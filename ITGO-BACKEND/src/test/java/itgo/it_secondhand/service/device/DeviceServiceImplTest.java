package itgo.it_secondhand.service.device;

import itgo.it_secondhand.domain.Category;
import itgo.it_secondhand.service.device.DTO.FindDeviceListByCategoryReqDTO;
import itgo.it_secondhand.service.device.DTO.FindDeviceListResDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@Slf4j
class DeviceServiceImplTest {
    @Autowired
    DeviceService deviceService;

    @Rollback(value = false)
    @Test
    public void findDeviceListByCategory() throws Exception {
        // given

        FindDeviceListByCategoryReqDTO request = FindDeviceListByCategoryReqDTO.builder()
                .category(1L)
                .size(10).page(0).build();

        // when
        FindDeviceListResDTO deviceListByCategory = deviceService.findDeviceListByCategory(request);

        // then
        assertEquals("제조사", deviceListByCategory.getDeviceList().get(1).getManufacturer());
    }



}