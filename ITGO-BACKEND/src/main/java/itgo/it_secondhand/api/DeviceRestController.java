package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.ResponseDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static itgo.it_secondhand.api.DTO.ResponseDTO.success;

@RestController
@RequestMapping("/api/v2/devices")
@RequiredArgsConstructor
public class DeviceRestController {
    private final DeviceService deviceService;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<ResponseDTO<?>> findDeviceList
            (@RequestParam int page, @RequestParam int size, @RequestParam SortBy sortBy) {

        FindDeviceListResDTO deviceList = deviceService.findDeviceList(
                FindDeviceListReqDTO.builder()
                        .page(page).size(size).build());

        FindDeviceListResponseDTO findDeviceListResponseDTO = FindDeviceListResponseDTO.builder()
                .deviceList(deviceList.getDeviceList())
                .hasNext(deviceList.getHasNext())
                .build();

        return ResponseEntity.ok().body(success(findDeviceListResponseDTO));
    }

    @GetMapping("/by/{categoryId}")
    public ResponseEntity<ResponseDTO<?>> findDeviceListByCategory
            (@RequestParam int page, @RequestParam int size, @RequestParam SortBy sortBy,
             @PathVariable(name = "categoryId") Long category) {

        FindDeviceListResDTO deviceList = deviceService.findDeviceListByCategory(
                FindDeviceListByCategoryReqDTO.builder()
                        .page(page).size(size)
                        .category(category)
                        .build());

        FindDeviceListResponseDTO findDeviceListResponseDTO = FindDeviceListResponseDTO.builder()
                .deviceList(deviceList.getDeviceList())
                .hasNext(deviceList.getHasNext())
                .build();

        return ResponseEntity.ok().body(success(findDeviceListResponseDTO));
    }

    @GetMapping("/categories")
    public ResponseEntity<ResponseDTO<?>> findCategory() {

        List<Category> categories = categoryRepository.findAll();

        FindCategoryResponseDTO findCategoryResponseDTO = FindCategoryResponseDTO.builder()
                .categoryList(categories).build();

        return ResponseEntity.ok().body(success(findCategoryResponseDTO));
    }


    @GetMapping("/mobile/{deviceId}")
    public ResponseEntity<ResponseDTO<?>> findMobileInfo
            (@PathVariable(name = "deviceId") String detailId) {

        FindDeviceInfoResDTO<MobileInfo> deviceInfo =
                deviceService.findMobileInfo(FindDeviceInfoReqDTO.builder()
                        .detailId(detailId).build());

        FindDeviceInfoResponseDTO<MobileInfo> findDeviceInfoResponseDTO = FindDeviceInfoResponseDTO.<MobileInfo>builder()
                .info(deviceInfo.getInfo())
                .build();
        return ResponseEntity.ok().body(success(findDeviceInfoResponseDTO));
    }

    @GetMapping("/laptop/{deviceId}")
    public ResponseEntity<ResponseDTO<?>> findLaptopInfo
            (@PathVariable(name = "deviceId") String detailId) {

        FindDeviceInfoResDTO<LaptopInfo> deviceInfo =
                deviceService.findLaptopInfo(FindDeviceInfoReqDTO.builder()
                        .detailId(detailId).build());

        FindDeviceInfoResponseDTO<LaptopInfo> findDeviceInfoResponseDTO = FindDeviceInfoResponseDTO.<LaptopInfo>builder()
                .info(deviceInfo.getInfo())
                .build();
        return ResponseEntity.ok().body(success(findDeviceInfoResponseDTO));
    }


}
