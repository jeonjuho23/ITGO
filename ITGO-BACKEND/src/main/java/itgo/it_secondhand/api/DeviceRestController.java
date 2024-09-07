package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.ResponseDTO;
import itgo.it_secondhand.domain.Category;
import itgo.it_secondhand.domain.LaptopInfo;
import itgo.it_secondhand.domain.MobileInfo;
import itgo.it_secondhand.repository.CategoryRepository;
import itgo.it_secondhand.service.device.DTO.*;
import itgo.it_secondhand.service.device.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            (@PageableDefault(page = 0, size = 10) Pageable pageable) {

        FindDeviceListReqDTO reqDTO = FindDeviceListReqDTO.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();

        FindDeviceListResDTO resDTO =
                deviceService.findDeviceList(reqDTO);

        return ResponseEntity.ok().body(success(resDTO));
    }

    @GetMapping("/by/{categoryId}")
    public ResponseEntity<ResponseDTO<?>> findDeviceListByCategory
            (@PathVariable(name = "categoryId") Long category,
             @PageableDefault(page = 0, size = 10) Pageable pageable) {

        FindDeviceListByCategoryReqDTO reqDTO = FindDeviceListByCategoryReqDTO.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .category(category)
                .build();

        FindDeviceListResDTO resDTO =
                deviceService.findDeviceListByCategory(reqDTO);

        return ResponseEntity.ok().body(success(resDTO));
    }

    @GetMapping("/categories")
    public ResponseEntity<ResponseDTO<?>> findCategory() {

        List<Category> resDTO =
                categoryRepository.findAll();

        return ResponseEntity.ok().body(success(resDTO));
    }


    @GetMapping("/mobile/{detailId}")
    public ResponseEntity<ResponseDTO<?>> findMobileInfo
            (@PathVariable(name = "detailId") String detailId) {

        FindDeviceInfoReqDTO reqDTO = FindDeviceInfoReqDTO.builder()
                .detailId(detailId)
                .build();

        FindDeviceInfoResDTO<MobileInfo> resDTO =
                deviceService.findMobileInfo(reqDTO);

        return ResponseEntity.ok().body(success(resDTO));
    }

    @GetMapping("/laptop/{detailId}")
    public ResponseEntity<ResponseDTO<?>> findLaptopInfo
            (@PathVariable(name = "detailId") String detailId) {

        FindDeviceInfoReqDTO reqDTO = FindDeviceInfoReqDTO.builder()
                .detailId(detailId)
                .build();

        FindDeviceInfoResDTO<LaptopInfo> resDTO =
                deviceService.findLaptopInfo(reqDTO);

        return ResponseEntity.ok().body(success(resDTO));
    }


}
