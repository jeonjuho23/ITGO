package itgo.it_secondhand.service.like;

import itgo.it_secondhand.domain.LocationMongo;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberLikeLocation;
import itgo.it_secondhand.domain.value.Location;
import itgo.it_secondhand.repository.LocationMongoRepository;
import itgo.it_secondhand.repository.MemberLikeLocationRepository;
import itgo.it_secondhand.repository.MemberRepository;
import itgo.it_secondhand.service.like.DTO.LikeReqDTO;
import itgo.it_secondhand.service.like.DTO.LocationLikeResDTO;
import itgo.it_secondhand.service.like.DTO.LocationResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LocationLikeServiceImpl implements LikeService<LocationResDTO<Long>, String> {
    private final MemberRepository memberRepository;
    private final MemberLikeLocationRepository memberLikeLocationRepository;
    private final LocationMongoRepository locationMongoRepository;



    @Transactional
    @Override
    public Long regist(LikeReqDTO<String> likeReqDTO) {

        Member member = memberRepository.findById(likeReqDTO.getMemberId()).orElseThrow();

        LocationMongo location = locationMongoRepository
                .findById(likeReqDTO.getLikedThingId()).orElseThrow();

        // 좋아요 생성
        MemberLikeLocation memberLikeLocation =
                MemberLikeLocation.createMemberLikeLocation(location.getLocation(), member);

        memberLikeLocationRepository.save(memberLikeLocation);

        return memberLikeLocation.getId();
    }

    @Transactional
    @Override
    public void delete(LikeReqDTO<Long> likeReqDTO) {
        try {
            memberLikeLocationRepository.deleteById(likeReqDTO.getLikedThingId());
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
        }
    }

    @Override
    public List<LocationResDTO<Long>> checkList(Long memberId) {

        List<MemberLikeLocation> memberLikeLocationList = memberLikeLocationRepository.findByMember_Id(memberId);

        List<LocationResDTO<Long>> res = new ArrayList<>();
        for(MemberLikeLocation memberLikeLocation: memberLikeLocationList){
            res.add(LocationResDTO.<Long>builder()
                    .id(memberLikeLocation.getId())
                    .location(memberLikeLocation.getLocation()).build());
        }

        return res;
    }

    public List<LocationResDTO<String>> findByKeyword(String keyword){

        List<LocationMongo> locationMongoList =
                locationMongoRepository.findAllByLocation_CityOrLocation_Street(keyword, keyword);

        List<LocationResDTO<String>> res = new ArrayList<>();

        for(LocationMongo locationMongo: locationMongoList){
            res.add(LocationResDTO.<String>builder()
                    .id(locationMongo.getId())
                    .location(locationMongo.getLocation()).build());
        }

        return res;
    }
}
