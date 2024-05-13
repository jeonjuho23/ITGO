package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.like.*;
import itgo.it_secondhand.domain.MemberLikeLocation;
import itgo.it_secondhand.service.like.DTO.*;
import itgo.it_secondhand.service.like.DeviceLikeServiceImpl;
import itgo.it_secondhand.service.like.LikeService;
import itgo.it_secondhand.service.like.LocationLikeServiceImpl;
import itgo.it_secondhand.service.post.DTO.PostResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.List;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikeRestController {

    private final DeviceLikeServiceImpl deviceLikeService;
    private final LikeService<PostResDTO, Long> postLikeService;
    private final LocationLikeServiceImpl locationLikeService;


    //==  Regist  ==//

    @GetMapping("/regist/device")
    public ResponseEntity<RegistLikeResponseDTO> registDevice(@RequestParam Long memberId, @RequestParam Long deviceId){

        LikeReqDTO<Long> reqDTO = LikeReqDTO.<Long>builder()
                .memberId(memberId)
                .likedThingId(deviceId)
                .build();
        Long registId = deviceLikeService.regist(reqDTO);

        RegistLikeResponseDTO responseDTO = RegistLikeResponseDTO.builder()
                .registId(registId)
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/regist/post")
    public ResponseEntity<RegistLikeResponseDTO> registPost(@RequestParam Long memberId, @RequestParam Long postId){

        LikeReqDTO<Long> reqDTO = LikeReqDTO.<Long>builder()
                .memberId(memberId)
                .likedThingId(postId)
                .build();
        Long registId = postLikeService.regist(reqDTO);

        RegistLikeResponseDTO responseDTO = RegistLikeResponseDTO.builder()
                .registId(registId)
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/regist/location")
    public ResponseEntity<RegistLikeResponseDTO> registLocation(@RequestParam Long memberId, @RequestParam String locationId){

        LikeReqDTO<String> reqDTO = LikeReqDTO.<String>builder()
                .memberId(memberId).likedThingId(locationId).build();
        Long registId = locationLikeService.regist(reqDTO);

        RegistLikeResponseDTO responseDTO = RegistLikeResponseDTO.builder()
                .registId(registId).build();

        return ResponseEntity.ok(responseDTO);
    }


    //==  Delete  ==//

    @PostMapping("/delete/device")
    public ResponseEntity<DeleteLikeResponseDTO> deleteDevice(@RequestParam Long memberId, @RequestParam Long deviceId){

        LikeReqDTO<Long> reqDTO = LikeReqDTO.<Long>builder()
                .memberId(memberId)
                .likedThingId(deviceId)
                .build();
        deviceLikeService.delete(reqDTO);

        DeleteLikeResponseDTO responseDTO = DeleteLikeResponseDTO.builder()
                .msg("성공적으로 삭제되었습니다.")
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/delete/post")
    public ResponseEntity<DeleteLikeResponseDTO> deletePost(@RequestParam Long memberId, @RequestParam Long postId){

        LikeReqDTO<Long> reqDTO = LikeReqDTO.<Long>builder()
                .memberId(memberId)
                .likedThingId(postId)
                .build();
        postLikeService.delete(reqDTO);

        DeleteLikeResponseDTO responseDTO = DeleteLikeResponseDTO.builder()
                .msg("성공적으로 삭제되었습니다.")
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/delete/location")
    public ResponseEntity<DeleteLikeResponseDTO> deleteLocation(@RequestParam Long memberId, @RequestParam Long locationId){

        LikeReqDTO<Long> reqDTO = LikeReqDTO.<Long>builder()
                .memberId(memberId)
                .likedThingId(locationId).build();
        locationLikeService.delete(reqDTO);

        DeleteLikeResponseDTO responseDTO = DeleteLikeResponseDTO.builder()
                .msg("성공적으로 삭제되었습니다.").build();

        return ResponseEntity.ok(responseDTO);
    }


    //==  Find list  ==//

    @GetMapping("/find/device/list")
    public ResponseEntity<List<PostResDTO>> findDeviceList(@RequestParam Long memberId){

        List<PostResDTO> responseDTO = postLikeService.checkList(memberId);

        return ResponseEntity.ok(responseDTO);
    }

//    @GetMapping("/find/post/list")
//    public ResponseEntity<FindLikeListResponseDTO> findPostList(){}

    @GetMapping("/find/location/list")
    public ResponseEntity<List<LocationResDTO<Long>>> findLocationList(@RequestParam Long memberId){

        List<LocationResDTO<Long>> responseDTO = locationLikeService.checkList(memberId);

        return ResponseEntity.ok(responseDTO);
    }



    //== find by keword ==//

    @GetMapping("/find/device/by/keyword")
    public ResponseEntity<List<DeviceResDTO>> findDeviceByKeyword(@RequestParam String keyword){

        List<DeviceResDTO> res = deviceLikeService.findByKeyword(keyword);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/find/location/by/keyword")
    public ResponseEntity<List<LocationResDTO<String>>> findLocationByKeyword(@RequestParam String keyword){

        List<LocationResDTO<String>> res = locationLikeService.findByKeyword(keyword);

        return ResponseEntity.ok(res);
    }


}
