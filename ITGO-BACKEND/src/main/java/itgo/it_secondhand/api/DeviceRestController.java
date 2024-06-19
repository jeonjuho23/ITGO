package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.device.FindCategoryResponseDTO;
import itgo.it_secondhand.api.DTO.device.FindDeviceInfoResponseDTO;
import itgo.it_secondhand.api.DTO.device.FindDeviceListResponseDTO;
import itgo.it_secondhand.domain.Category;
import itgo.it_secondhand.domain.LaptopInfo;
import itgo.it_secondhand.domain.MobileInfo;
import itgo.it_secondhand.enum_.SortBy;
import itgo.it_secondhand.repository.CategoryRepository;
import itgo.it_secondhand.service.device.DTO.*;
import itgo.it_secondhand.service.device.DeviceService;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.process.internal.ScanningCoordinator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/devices")
@RequiredArgsConstructor
public class DeviceRestController {
    private final DeviceService deviceService;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<FindDeviceListResponseDTO> findDeviceList
            (@RequestParam int page, @RequestParam int size, @RequestParam SortBy sortBy) {

        FindDeviceListResDTO deviceList = deviceService.findDeviceList(
                FindDeviceListReqDTO.builder()
                        .page(page).size(size).build());


        return ResponseEntity.ok(
                FindDeviceListResponseDTO.builder()
                        .deviceList(deviceList.getDeviceList())
                        .hasNext(deviceList.getHasNext())
                        .build());
    }

    @GetMapping("/by/{categoryId}")
    public ResponseEntity<FindDeviceListResponseDTO> findDeviceListByCategory
            (@RequestParam int page, @RequestParam int size, @RequestParam SortBy sortBy,
             @PathVariable(name = "categoryId") Long category) {

        FindDeviceListResDTO deviceList = deviceService.findDeviceListByCategory(
                FindDeviceListByCategoryReqDTO.builder()
                        .page(page).size(size)
                        .category(category)
                        .build());


        return ResponseEntity.ok(
                FindDeviceListResponseDTO.builder()
                        .deviceList(deviceList.getDeviceList())
                        .hasNext(deviceList.getHasNext())
                        .build());
    }

    @GetMapping("/categories")
    public ResponseEntity<FindCategoryResponseDTO> findCategory() {

        List<Category> categories = categoryRepository.findAll();

        return ResponseEntity.ok(
                FindCategoryResponseDTO.builder()
                        .categoryList(categories).build()
        );
    }


    @GetMapping("/mobile/{deviceId}")
    public ResponseEntity<FindDeviceInfoResponseDTO<MobileInfo>> findMobileInfo
            (@PathVariable(name = "deviceId") String detailId) {

        FindDeviceInfoResDTO<MobileInfo> deviceInfo =
                deviceService.findMobileInfo(FindDeviceInfoReqDTO.builder()
                        .detailId(detailId).build());

        return ResponseEntity.ok(FindDeviceInfoResponseDTO.<MobileInfo>builder()
                .info(deviceInfo.getInfo())
                .build());
    }

    @GetMapping("/laptop/{deviceId}")
    public ResponseEntity<FindDeviceInfoResponseDTO<LaptopInfo>> findLaptopInfo
            (@PathVariable(name = "deviceId") String detailId) {

        FindDeviceInfoResDTO<LaptopInfo> deviceInfo =
                deviceService.findLaptopInfo(FindDeviceInfoReqDTO.builder()
                        .detailId(detailId).build());

        return ResponseEntity.ok(FindDeviceInfoResponseDTO.<LaptopInfo>builder()
                .info(deviceInfo.getInfo())
                .build());
    }


}
