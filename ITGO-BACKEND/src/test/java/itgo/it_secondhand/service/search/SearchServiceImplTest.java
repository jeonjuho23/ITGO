package itgo.it_secondhand.service.search;

import itgo.it_secondhand.domain.Category;
import itgo.it_secondhand.domain.Device;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.SecondhandScrapedPost;
import itgo.it_secondhand.domain.value.Location;
import itgo.it_secondhand.enum_.SortBy;
import itgo.it_secondhand.repository.*;
import itgo.it_secondhand.service.post.DTO.FindPostDTO;
import itgo.it_secondhand.service.search.DTO.RecentSearchReqDTO;
import itgo.it_secondhand.service.search.DTO.SearchReqDTO;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback(value = false)
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SearchServiceImplTest {

    @Autowired
    SearchService searchService;
    @Autowired
    KeywordRepository keywordRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    SecondhandPostRepository secondhandPostRepository;
    @Autowired
    EntityManager em;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryBySiteRepository categoryBySiteRepository;

    @Order(2)
    @Test
    @Rollback(value = false)
    public void recentSearchTest() throws Exception {
        // given
        RecentSearchReqDTO req = new RecentSearchReqDTO(1L, 0, 10, SortBy.RECENT_SEARCH);

        // when
        List<String> res = searchService.recentSearches(req).getKeywordList();

        // then
        assertEquals("de", res.get(0), "마지막으로 등록된 검색어는 de 입니다..");


    }



    @Order(1)
    @Test
    @Rollback(value = false)
    public void searchKeywordCountTest() throws Exception {
        // given
        setMemberAndPostAndDevice();
        SearchReqDTO req1 = new SearchReqDTO(1L, "deviceName", 0, 30, SortBy.RECENT_POST);
        SearchReqDTO req2 = new SearchReqDTO(1L, "device", 0, 30, SortBy.RECENT_POST);
        SearchReqDTO req3 = new SearchReqDTO(1L, "de", 0, 30,SortBy.RECENT_POST);

        List<List<FindPostDTO>> posts = new ArrayList<>();

        // when
        //3
        posts.add(searchService.keywordSearch(req1).getPosts());
        em.flush();
        em.clear();
        posts.add(searchService.keywordSearch(req1).getPosts());
        em.flush();
        em.clear();
        posts.add(searchService.keywordSearch(req1).getPosts());
        em.flush();
        em.clear();

        //4
        posts.add(searchService.keywordSearch(req2).getPosts());
        em.flush();
        em.clear();
        posts.add(searchService.keywordSearch(req2).getPosts());
        em.flush();
        em.clear();
        posts.add(searchService.keywordSearch(req2).getPosts());
        em.flush();
        em.clear();
        posts.add(searchService.keywordSearch(req2).getPosts());
        em.flush();
        em.clear();

        //5
        posts.add(searchService.keywordSearch(req3).getPosts());
        em.flush();
        em.clear();
        posts.add(searchService.keywordSearch(req3).getPosts());
        em.flush();
        em.clear();
        posts.add(searchService.keywordSearch(req3).getPosts());
        em.flush();
        em.clear();
        posts.add(searchService.keywordSearch(req3).getPosts());
        em.flush();
        em.clear();
        posts.add(searchService.keywordSearch(req3).getPosts());
        em.flush();
        em.clear();


        // then
        log.info("hh");


    }


    void setMemberAndPostAndDevice() {
        Member member = Member.createMember("phone", "name", "imgAddress", new Location("city", "street", "zipcode"));

        List<Device> deviceList= new ArrayList<>();
        List<SecondhandScrapedPost> postList = new ArrayList<>();
        Category category = Category.createCategory("제조사", "기기 종류");

        for (int i = 0; i < 30; i++) {
            Device device = Device.createDevice("deviceName" + i, 1000, category, LocalDateTime.now(),"");
            deviceList.add(device);
            postList.add(SecondhandScrapedPost.createPost(member, "title"+i, "content", "imgFolderAddress", device, 1000, "postUrl", new Location("city","street","zipcode")));
        }

        memberRepository.saveAndFlush(member);
        categoryRepository.saveAndFlush(category);
        deviceRepository.saveAllAndFlush(deviceList);
        secondhandPostRepository.saveAllAndFlush(postList);

        em.clear();
    }
}