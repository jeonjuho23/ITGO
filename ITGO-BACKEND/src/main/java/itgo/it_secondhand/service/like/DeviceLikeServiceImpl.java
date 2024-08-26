package itgo.it_secondhand.service.like;


import itgo.it_secondhand.domain.Device;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberLikeDevice;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.service.like.DTO.DeviceLikeResDTO;
import itgo.it_secondhand.service.like.DTO.DeviceResDTO;
import itgo.it_secondhand.service.like.DTO.LikeReqDTO;
import itgo.it_secondhand.repository.DeviceRepository;
import itgo.it_secondhand.repository.MemberLikeDeviceRepository;
import itgo.it_secondhand.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeviceLikeServiceImpl implements LikeService<DeviceLikeResDTO, Long> {

    private final DeviceRepository deviceRepository;
    private final MemberRepository memberRepository;
    private final MemberLikeDeviceRepository memberLikeDeviceRepository;

    @Transactional
    @Override
    public Long regist(LikeReqDTO<Long> likeReqDTO) {

        // 엔티티 조회
        Member member = memberRepository.findById(likeReqDTO.getMemberId())
                .orElseThrow(()-> new RestApiException(CustomExceptionCode.MEMBER_NOT_FOUND));
        Device device = deviceRepository.findById(likeReqDTO.getLikedThingId())
                .orElseThrow(()-> new RestApiException(CustomExceptionCode.DEVICE_NOT_FOUND));

        // 좋아요 생성
        MemberLikeDevice memberLikeDevice = MemberLikeDevice.createMemberLikeDevice(member, device);

        // 저장
        MemberLikeDevice save = memberLikeDeviceRepository.save(memberLikeDevice);

        return save.getId();
    }

    @Transactional
    @Override
    public void  delete(LikeReqDTO<Long> likeReqDTO) {
        // 엔티티 조회
        Member member = memberRepository.findById(likeReqDTO.getMemberId())
                .orElseThrow(()-> new RestApiException(CustomExceptionCode.MEMBER_NOT_FOUND));
        Device device = deviceRepository.findById(likeReqDTO.getLikedThingId())
                .orElseThrow(()-> new RestApiException(CustomExceptionCode.DEVICE_NOT_FOUND));

        // 좋아요 삭제 후 저장
        MemberLikeDevice memberLikeDevice = memberLikeDeviceRepository.findByMemberAndDevice(member, device)
                .orElseThrow(()->new RestApiException(CustomExceptionCode.LIKE_NOT_FOUND));

        memberLikeDeviceRepository.delete(memberLikeDevice);

    }

    @Override
    public List<DeviceLikeResDTO> checkList(Long memberId) {

        List<MemberLikeDevice> memberLikeDeviceList = memberLikeDeviceRepository.findAllByMember_Id(memberId);

        if (memberLikeDeviceList.isEmpty()) throw new RestApiException(CustomExceptionCode.NO_LIKE_LIST);

        List<DeviceLikeResDTO> deviceLikeListResDTO = new ArrayList<>();
        for(MemberLikeDevice memberLikeDevice : memberLikeDeviceList){
            deviceLikeListResDTO.add(
                    new DeviceLikeResDTO(
                            memberLikeDevice.getDevice().getId(),
                            memberLikeDevice.getDevice().getDeviceName()
                    )
            );
        }

        return deviceLikeListResDTO;
    }

    public List<DeviceResDTO> findByKeyword(String keyword){

        List<Device> deviceList = deviceRepository.searchDeviceByDeviceName(keyword.replace(" ", ""));

        if (deviceList.isEmpty()) throw new RestApiException(CustomExceptionCode.NO_LIKE_LIST);

        List<DeviceResDTO> deviceResDTO = new ArrayList<>();
        for(Device device: deviceList){
            deviceResDTO.add(DeviceResDTO.builder()
                    .id(device.getId())
                    .deviceName(device.getDeviceName()).build());
        }

        return deviceResDTO;
    }
}
