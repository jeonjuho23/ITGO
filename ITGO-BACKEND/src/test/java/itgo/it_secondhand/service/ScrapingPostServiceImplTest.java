package itgo.it_secondhand.service;

import itgo.it_secondhand.domain.*;
import itgo.it_secondhand.domain.value.Location;
import itgo.it_secondhand.enum_.SortBy;
import itgo.it_secondhand.repository.*;
import itgo.it_secondhand.service.post.DTO.*;
import itgo.it_secondhand.service.post.ScrapingPostService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
@Transactional
class ScrapingPostServiceImplTest {

    @Autowired
    ScrapingPostService scrapingPostService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository<SecondhandScrapedPost> postRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryBySiteRepository categoryBySiteRepository;

    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void viewTest() throws Exception {
        // given
//        ViewTestDTO memberAndPost = createMemberAndPost();

//        PostViewReqDTO postViewReqDTO = new PostViewReqDTO(memberAndPost.getMemberId(), memberAndPost.postId);
        PostViewReqDTO postViewReqDTO = new PostViewReqDTO(90L, 10L);

        // when
        ScrapedPostViewResDTO first = scrapingPostService.viewScrapingPost(postViewReqDTO);

        ScrapedPostViewResDTO second = scrapingPostService.viewScrapingPost(postViewReqDTO);

        ScrapedPostViewResDTO third = scrapingPostService.viewScrapingPost(postViewReqDTO);

        // then

        assertEquals(first.getViewCount()+1, second.getViewCount(), "조회수가 증가합니다.");
        assertEquals(second.getViewCount()+1, third.getViewCount(), "조회수가 증가합니다.");

    }

    @Test
    @Rollback(value = false)
    public void findLikePostList() throws Exception {
        // given

        FindPostReqDTO findPostReqDTO = FindPostReqDTO.builder()
                .memberId(1L)
                .page(0).size(10).sortBy(SortBy.RECENT_POST)
                .build();

        // when
        FindPostResDTO findPostResDTO = scrapingPostService.findLikeScrapingPostList(findPostReqDTO);

        // then
        assertEquals(findPostResDTO.getPosts().size(), 3, "`````````");
    }

    @Test
    @Rollback
    public void findPostList() throws Exception {
        // given
        FindPostReqDTO reqDTO = FindPostReqDTO.builder()
                .memberId(1L)
                .page(0).size(10).sortBy(SortBy.RECENT_POST)
                .build();

        // when
        FindPostResDTO allScrapingPostList = scrapingPostService.findALlScrapingPostList(reqDTO);

        // then
        assertEquals(allScrapingPostList.getPosts().get(1).getMemberName(), "joongna","현재 저장된 게시글은 모두 중고나라에서 스크래핑 되었습니다.");
    }

    @Test
    @Rollback(value = false)
    public void findPostByCategory() throws Exception {
        // given

        // Request 객체 생성
        FindPostByCategoryReqDTO req = FindPostByCategoryReqDTO.builder()
                .categoryId(1L).memberId(1L)
                .page(1).size(10).sortBy(SortBy.RECENT_POST)
                .build();

        // when
        FindPostResDTO res = scrapingPostService.findScrapingPostListByCategory(req);

        // then
        assertEquals(res.getPosts().size(), 10, "가져온 리스트의 사이즈는 10");
    }

    @Test
    @Rollback(value = false)
    public void saveCategoryData() throws Exception {

        // given
        List<Category> categoryList = new ArrayList<>();

        Category sam_mobile = Category.createCategory("삼성","스마트폰");
        Category sam_tablet = Category.createCategory("삼성", "태블릿");
        Category sam_laptop = Category.createCategory("삼성", "노트북");

        Category apple_mobile = Category.createCategory("애플", "스마트폰");
        Category apple_tablet = Category.createCategory("애플", "태블릿");
        Category apple_laptop = Category.createCategory("애플", "노트북");

        Category lg_mobile = Category.createCategory("엘쥐","스마트폰");
        Category lg_laptop = Category.createCategory("엘쥐", "노트북");

        Category etc_mobile = Category.createCategory("기타", "스마트폰");
        Category etc_tablet = Category.createCategory("기타", "태블릿");
        Category etc_laptop = Category.createCategory("기타", "노트북");

        categoryList.add(sam_mobile);
        categoryList.add(sam_tablet);
        categoryList.add(sam_laptop);
        categoryList.add(apple_mobile);
        categoryList.add(apple_tablet);
        categoryList.add(apple_laptop);
        categoryList.add(lg_mobile);
        categoryList.add(lg_laptop);
        categoryList.add(etc_mobile);
        categoryList.add(etc_tablet);
        categoryList.add(etc_laptop);

        Category etc = categoryRepository.findByManufacturer("기타");

        // category by 중고나라
        List<CategoryBySite> categoryBySiteList = new ArrayList<>();

        categoryBySiteList.add(CategoryBySite.createCategoryBySite(etc, "중고나라", "1150"));

        categoryBySiteList.add(CategoryBySite.createCategoryBySite(sam_mobile, "중고나라", "1150"));
        categoryBySiteList.add(CategoryBySite.createCategoryBySite(sam_tablet, "중고나라", "1154"));
        categoryBySiteList.add(CategoryBySite.createCategoryBySite(sam_laptop, "중고나라", "1193"));

        categoryBySiteList.add(CategoryBySite.createCategoryBySite(apple_mobile, "중고나라", "1151"));
        categoryBySiteList.add(CategoryBySite.createCategoryBySite(apple_tablet, "중고나라", "1155"));
        categoryBySiteList.add(CategoryBySite.createCategoryBySite(apple_laptop, "중고나라", "1194"));

        categoryBySiteList.add(CategoryBySite.createCategoryBySite(lg_mobile, "중고나라", "1152"));
        categoryBySiteList.add(CategoryBySite.createCategoryBySite(lg_laptop, "중고나라", "1195"));

        categoryBySiteList.add(CategoryBySite.createCategoryBySite(etc_mobile, "중고나라", "1153"));
        categoryBySiteList.add(CategoryBySite.createCategoryBySite(etc_tablet, "중고나라", "1156"));
        categoryBySiteList.add(CategoryBySite.createCategoryBySite(etc_laptop, "중고나라", "1196"));

        categoryRepository.saveAllAndFlush(categoryList);
        categoryBySiteRepository.saveAllAndFlush(categoryBySiteList);

        em.clear();

        // when
        List<CategoryBySite> all = categoryBySiteRepository.findAll();

        // then
        assertEquals(all.size(), categoryBySiteList.size(), "입력한 데이터와 출력한 데이터의 갯수는 동일합니다.");

    }

    @Data
    @AllArgsConstructor
    static class ViewTestDTO{
        private Long memberId;
        private Long postId;
    }

    public ViewTestDTO createMemberAndPost(){
        Location location = new Location("city", "street", "zipcode");
        Member member = Member.createMember("phone", "name", "address", location);
        Category category = Category.createCategory("제조사", "기기 종류");
        Device device = Device.createDevice("deviceName", 1000,category, LocalDateTime.now(),"");
        SecondhandScrapedPost secondhandScrapedPost = SecondhandScrapedPost.
                createPost(member,"postTitle","postContent","imgFolderAddress",
                        device,1000,"postUrl",location);
        memberRepository.save(member);
        deviceRepository.save(device);
        postRepository.save(secondhandScrapedPost);

        ViewTestDTO viewTestDTO = new ViewTestDTO(member.getId(), secondhandScrapedPost.getId());

        em.flush();
        em.clear();

        return viewTestDTO;
    }



}