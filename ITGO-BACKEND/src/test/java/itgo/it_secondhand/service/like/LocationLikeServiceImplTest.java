package itgo.it_secondhand.service.like;

import itgo.it_secondhand.domain.LocationMongo;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberLikeLocation;
import itgo.it_secondhand.domain.value.Location;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.repository.LocationMongoRepository;
import itgo.it_secondhand.repository.MemberLikeLocationRepository;
import itgo.it_secondhand.repository.MemberRepository;
import itgo.it_secondhand.service.like.DTO.LikeReqDTO;
import itgo.it_secondhand.service.like.DTO.LocationResDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationLikeServiceImplTest {

    @InjectMocks
    LocationLikeServiceImpl locationLikeService;

    @Mock
    MemberRepository memberRepository;
    @Mock
    MemberLikeLocationRepository memberLikeLocationRepository;
    @Mock
    LocationMongoRepository locationMongoRepository;


    @Test
    public void regist() throws Exception {
        //given
        when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Mockito.mock(Member.class)));
        when(locationMongoRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(Mockito.mock(LocationMongo.class)));

        Long memberLikeLocationId = 1L;
        MemberLikeLocation memberLikeLocation = MemberLikeLocation.builder()
                .id(memberLikeLocationId).build();
        when(memberLikeLocationRepository.save(Mockito.any(MemberLikeLocation.class)))
                .thenReturn(memberLikeLocation);


        LikeReqDTO<String> request = new LikeReqDTO<>(1L, "id");

        //when
        Long response = locationLikeService.regist(request);

        //then
        assertThat(response).isEqualTo(memberLikeLocation.getId());
    }


    @Test
    public void registThrowMemberNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        LikeReqDTO<String> request = new LikeReqDTO<>(1L, "id");

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            locationLikeService.regist(request);
        });

        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.MEMBER_NOT_FOUND);
    }

    @Test
    public void registThrowLocationNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(Mockito.mock(Member.class)));
        when(locationMongoRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());


        LikeReqDTO<String> request = new LikeReqDTO<>(1L, "id");

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            locationLikeService.regist(request);
        });

        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.LOCATION_NOT_FOUND);
    }


    @Test
    public void delete() throws Exception {
        //given
        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        locationLikeService.delete(request);

        //then
    }
    
    
    @Test
    public void checkList() throws Exception {
        //given
        Long id = 1L;
        Location location = new Location("city", "street", "zipcode");
        MemberLikeLocation memberLikeLocation = MemberLikeLocation.builder()
                .id(id).location(location)
                .build();
        List<MemberLikeLocation> memberLikeLocationsList = new ArrayList<>(List.of(memberLikeLocation));
        when(memberLikeLocationRepository.findByMember_Id(Mockito.anyLong()))
                .thenReturn(memberLikeLocationsList);


        Long request = 1L;

        //when
        List<LocationResDTO<Long>> response = locationLikeService.checkList(request);

        //then
        assertThat(response.get(0).getId()).isEqualTo(id);
        assertThat(response.get(0).getLocation()).isEqualTo(location);
    }

    @Test
    public void checkListThrowNoLikeListException() throws Exception {
        //given
        List<MemberLikeLocation> memberLikeLocationsList = new ArrayList<>();
        when(memberLikeLocationRepository.findByMember_Id(Mockito.anyLong()))
                .thenReturn(memberLikeLocationsList);


        Long request = 1L;

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            locationLikeService.checkList(request);
        });

        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.NO_LIKE_LIST);
    }


    @Test
    public void findByKeyword() throws Exception {
        //given
        String id = "id";
        Location location = new Location("city", "street", "zipcode");
        LocationMongo locationMongo = LocationMongo.builder()
                .id(id).location(location)
                .build();
        List<LocationMongo> locationMongoList = new ArrayList<>(List.of(locationMongo));
        when(locationMongoRepository.findAllByLocation_CityOrLocation_Street(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(locationMongoList);


        String request = "keyword";

        //when
        List<LocationResDTO<String>> response = locationLikeService.findByKeyword(request);

        //then
        assertThat(response.get(0).getId()).isEqualTo(id);
        assertThat(response.get(0).getLocation()).isEqualTo(location);
    }

    @Test
    public void findByKeywordThrowLocationNotFoundException() throws Exception {
        //given
        List<LocationMongo> locationMongoList = new ArrayList<>();
        when(locationMongoRepository.findAllByLocation_CityOrLocation_Street(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(locationMongoList);


        String request = "keyword";

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            locationLikeService.findByKeyword(request);
        });

        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.LOCATION_NOT_FOUND);
    }

}