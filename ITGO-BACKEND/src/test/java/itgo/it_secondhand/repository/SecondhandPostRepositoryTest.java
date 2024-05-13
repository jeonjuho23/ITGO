package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.*;
import itgo.it_secondhand.domain.value.Location;
import itgo.it_secondhand.service.like.PostLikeServiceImpl;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@Slf4j
class SecondhandPostRepositoryTest {

    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    SecondhandPostRepository secondhandPostRepository;
    @Autowired
    PostLikeServiceImpl postLikeService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    EntityManager em;


    // 멤버, 카테고리, 기기, 게시글의 임시 데이터 저장
    void fullDataSetting() {
        Member joongna = Member.createMember("","joongna","",new Location("","",""));
        Member member = Member.createMember("phone", "name", "imgAddress", new Location("city", "street", "zipcode"));
        memberRepository.save(joongna);
        memberRepository.save(member);

        List<Device> deviceList= new ArrayList<>();
        List<SecondhandScrapedPost> postList = new ArrayList<>();
        Category category = Category.createCategory("기타","기타");

        Device etcDevice = Device.createDevice("기타", 0, category, LocalDateTime.now(),"");
        deviceList.add(etcDevice);

        for (int i = 0; i < 30; i++) {
            Device device = Device.createDevice("deviceName" + i, 1000, category, LocalDateTime.now(),"");
            deviceList.add(device);
            postList.add(SecondhandScrapedPost.createPost(joongna, "title"+i, "content", "imgFolderAddress", etcDevice, 1000, "postUrl",new Location("city","street","zipcode")));
        }

        categoryRepository.save(category);
        deviceRepository.saveAll(deviceList);
        secondhandPostRepository.saveAll(postList);

        em.flush();
        em.clear();
    }

    @Test
    @Rollback(value = false)
    public void likeQueryTest() throws Exception {
        // given
        // 멤버, 게시글, 기기 데이터 생성
        fullDataSetting();
        int page = 0;
        int size = 20;
        String keyword = "1";

        Pageable pageable = PageRequest.of(page, size);


        // when
//        Slice<SecondhandScrapedPost> result = secondhandPostRepository.findPostKeywordContaining(keyword, pageable);
        Slice<SecondhandScrapedPost> result = secondhandPostRepository.searchSecondhandPostByDeviceName(keyword, pageable);
    }


    @Test
    @Rollback(value = false)
    public void findLikePost() throws Exception{

//        List<Long> registIdList = new ArrayList<>();
//        for(int i=1; i<12; i++){
//            registIdList.add(postLikeService.regist(new LikeReqDTO(1L, (long) i)));
//        }

        int page = 1;
        int size = 20;

        Pageable pageable = PageRequest.of(page, size);

        // when
        Slice<SecondhandScrapedPost> tet = secondhandPostRepository.findLikePostByMember_Id(1L, pageable);



//
//
//        // then
//        assertEquals(size, result.getSize(), "size는 같아야합니다.");
////        assertTrue(result.hasNext(),"총 데이터 30개 출력 데이터는 페이지 0에서 10개이므로 다음 데이터가 있습니다.");
//        assertTrue(result.hasContent(),"content가 있습니다.");


    }


//    @Test
////    @Rollback(value = false)
//    public void likeQueryTest2() throws Exception {
//        // given
//        fullDataSetting();
//
//        List<Long> registIdList = new ArrayList<>();
//        for(int i=0; i<12; i++){
//            registIdList.add(postLikeService.regist(new LikeReqDTO(0L, (long) i)));
//        }
//
//        Pageable pageable = PageRequest.of(0, 10);
//
//        // when
//        Slice<SecondhandScrapedPost> result = secondhandPostRepository.findLikePostByMember_Id(0L, pageable);
//
//        // then
//        assertTrue(result.hasContent(), "등록했으니 있어야 합니다.");
//    }

}