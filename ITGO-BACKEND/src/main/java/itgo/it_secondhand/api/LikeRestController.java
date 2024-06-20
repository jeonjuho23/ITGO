package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.ResponseDTO;
import itgo.it_secondhand.api.DTO.like.DeleteLikeResponseDTO;
import itgo.it_secondhand.api.DTO.like.RegistLikeResponseDTO;
import itgo.it_secondhand.enum_.SortBy;
import itgo.it_secondhand.service.like.DTO.DeviceResDTO;
import itgo.it_secondhand.service.like.DTO.LikeReqDTO;
import itgo.it_secondhand.service.like.DTO.LocationResDTO;
import itgo.it_secondhand.service.like.DeviceLikeServiceImpl;
import itgo.it_secondhand.service.like.LikeService;
import itgo.it_secondhand.service.like.LocationLikeServiceImpl;
import itgo.it_secondhand.service.post.DTO.FindPostReqDTO;
import itgo.it_secondhand.service.post.DTO.FindPostResDTO;
import itgo.it_secondhand.service.post.DTO.PostResDTO;
import itgo.it_secondhand.service.post.ScrapingPostService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static itgo.it_secondhand.api.DTO.ResponseDTO.success;

@RestController
@RequestMapping("/api/v2/{memberId}/like")
@RequiredArgsConstructor
public class LikeRestController {

    private final DeviceLikeServiceImpl deviceLikeService;
    private final LikeService<PostResDTO, Long> postLikeService;
    private final LocationLikeServiceImpl locationLikeService;
    private final ScrapingPostService scrapingPostService;


    //==  Regist  ==//

    @PostMapping("/devices")
    public ResponseEntity<ResponseDTO<?>> registDevice
            (@PathVariable(name = "memberId") Long memberId,
             @RequestBody @NotNull Long deviceId){

        LikeReqDTO<Long> reqDTO = LikeReqDTO.<Long>builder()
                .memberId(memberId)
                .likedThingId(deviceId)
                .build();
        Long registId = deviceLikeService.regist(reqDTO);

        RegistLikeResponseDTO responseDTO = RegistLikeResponseDTO.builder()
                .registId(registId)
                .build();

        return ResponseEntity.ok().body(success(responseDTO));
    }

    @PostMapping("/posts")
    public ResponseEntity<ResponseDTO<?>> registPost
            (@PathVariable(name = "memberId") Long memberId,
             @RequestBody @NotNull Long postId){

        LikeReqDTO<Long> reqDTO = LikeReqDTO.<Long>builder()
                .memberId(memberId)
                .likedThingId(postId)
                .build();
        Long registId = postLikeService.regist(reqDTO);

        RegistLikeResponseDTO responseDTO = RegistLikeResponseDTO.builder()
                .registId(registId)
                .build();

        return ResponseEntity.ok().body(success(responseDTO));
    }

    @PostMapping("/locations")
    public ResponseEntity<ResponseDTO<?>> registLocation
            (@PathVariable(name = "memberId") Long memberId,
             @RequestBody @NotNull  String locationId){

        LikeReqDTO<String> reqDTO = LikeReqDTO.<String>builder()
                .memberId(memberId).likedThingId(locationId).build();
        Long registId = locationLikeService.regist(reqDTO);

        RegistLikeResponseDTO responseDTO = RegistLikeResponseDTO.builder()
                .registId(registId).build();

        return ResponseEntity.ok().body(success(responseDTO));
    }


    //==  Delete  ==//

    @DeleteMapping("/devices")
    public ResponseEntity<ResponseDTO<?>> deleteDevice
            (@PathVariable(name = "memberId") Long memberId,
             @RequestParam Long deviceId){

        LikeReqDTO<Long> reqDTO = LikeReqDTO.<Long>builder()
                .memberId(memberId)
                .likedThingId(deviceId)
                .build();
        deviceLikeService.delete(reqDTO);

        DeleteLikeResponseDTO responseDTO = DeleteLikeResponseDTO.builder()
                .msg("성공적으로 삭제되었습니다.")
                .build();

        return ResponseEntity.ok().body(success(responseDTO));
    }

    @DeleteMapping("/posts")
    public ResponseEntity<ResponseDTO<?>> deletePost
            (@PathVariable(name = "memberId") Long memberId,
             @RequestParam Long postId){

        LikeReqDTO<Long> reqDTO = LikeReqDTO.<Long>builder()
                .memberId(memberId)
                .likedThingId(postId)
                .build();
        postLikeService.delete(reqDTO);

        DeleteLikeResponseDTO responseDTO = DeleteLikeResponseDTO.builder()
                .msg("성공적으로 삭제되었습니다.")
                .build();

        return ResponseEntity.ok().body(success(responseDTO));
    }

    @DeleteMapping("/locations")
    public ResponseEntity<ResponseDTO<?>> deleteLocation
            (@PathVariable(name = "memberId") Long memberId,
             @RequestParam Long locationId){

        LikeReqDTO<Long> reqDTO = LikeReqDTO.<Long>builder()
                .memberId(memberId)
                .likedThingId(locationId).build();
        locationLikeService.delete(reqDTO);

        DeleteLikeResponseDTO responseDTO = DeleteLikeResponseDTO.builder()
                .msg("성공적으로 삭제되었습니다.").build();

        return ResponseEntity.ok().body(success(responseDTO));
    }


    //==  Find list  ==//

    @GetMapping("/devices")
    public ResponseEntity<ResponseDTO<?>> findDeviceList
            (@PathVariable(name = "memberId") Long memberId,
             @PageableDefault(page = 0, size = 10) Pageable pageable){

        List<PostResDTO> responseDTO = postLikeService.checkList(memberId);

        return ResponseEntity.ok().body(success(responseDTO));
    }

    @GetMapping("/posts")
    public ResponseEntity<ResponseDTO<?>> findLikedPostList
            (@PathVariable(name = "memberId") Long memberId,
             @PageableDefault(page = 0, size = 10) Pageable pageable) {

        FindPostReqDTO reqDTO = FindPostReqDTO.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .sortBy(SortBy.RECENT_POST)
                .memberId(memberId)
                .build();

        FindPostResDTO responseDTO = scrapingPostService.findLikeScrapingPostList(reqDTO);

        return ResponseEntity.ok().body(success(responseDTO));
    }
    @GetMapping("/locations")
    public ResponseEntity<ResponseDTO<?>> findLocationList
            (@PathVariable(name = "memberId") Long memberId,
             @PageableDefault(page = 0, size = 10) Pageable pageable){

        List<LocationResDTO<Long>> responseDTO = locationLikeService.checkList(memberId);

        return ResponseEntity.ok().body(success(responseDTO));
    }



    //== find by keword ==//

    @GetMapping("/devices/search")
    public ResponseEntity<ResponseDTO<?>> findDeviceByKeyword
            (@PathVariable(name = "memberId") Long memberId, @RequestParam String keyword,
             @PageableDefault(page = 0, size = 10) Pageable pageable){

        List<DeviceResDTO> res = deviceLikeService.findByKeyword(keyword);

        return ResponseEntity.ok().body(success(res));
    }

    @GetMapping("/locations/search")
    public ResponseEntity<ResponseDTO<?>> findLocationByKeyword
            (@PathVariable(name = "memberId") Long memberId, @RequestParam String keyword,
             @PageableDefault(page = 0, size = 10) Pageable pageable){

        List<LocationResDTO<String>> res = locationLikeService.findByKeyword(keyword);

        return ResponseEntity.ok().body(success(res));
    }


}
