package itgo.it_secondhand.api;

import itgo.it_secondhand.domain.value.Location;
import itgo.it_secondhand.enum_.SortBy;
import itgo.it_secondhand.service.post.DTO.*;
import itgo.it_secondhand.service.post.ScrapingPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/posts")
@RequiredArgsConstructor
public class PostRestController {

    private final ScrapingPostService scrapingPostService;


    // 현재 ResponseEntity의 제네릭으로 Service에서 받은 응답객체가 들어간다.
    // 이렇게 하면 HTTP Response에 다른 데이터가 필요하다면 Service에서 받은 응답객체에 종속적이므로 문제가 생긴다.
    // 전용 응답 DTO로 변환하여 HTTP Response 할 수 있도록 하자.


    @GetMapping
    public ResponseEntity<FindPostResDTO> findPostList
            (@RequestParam Long memberId,
             @PageableDefault(page = 0, size = 10) Pageable pageable) {

        FindPostReqDTO reqDTO = FindPostReqDTO.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .sortBy(SortBy.RECENT_POST)
                .memberId(memberId)
                .build();

        FindPostResDTO responseDTO = scrapingPostService.findALlScrapingPostList(reqDTO);

        return ResponseEntity.ok(responseDTO);
    }



    @GetMapping("{postId}")
    public ResponseEntity<ScrapedPostViewResDTO> viewPost
            (@PathVariable(name = "postId") Long postId,
             @RequestParam Long memberId,
             @PageableDefault(page = 0, size = 10) Pageable pageable) {

        PostViewReqDTO reqDTO = PostViewReqDTO.builder()
                .memberId(memberId)
                .postId(postId)
                .build();

        ScrapedPostViewResDTO responseDTO = scrapingPostService.viewScrapingPost(reqDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<FindPostResDTO> findPostByCategory
            (@PathVariable(name = "categoryId") Long categoryId, @RequestParam Long memberId,
             @PageableDefault(page = 0, size = 10) Pageable pageable) {

        FindPostByCategoryReqDTO reqDTO = FindPostByCategoryReqDTO.builder()
                .categoryId(categoryId)
                .memberId(memberId)
                .size(pageable.getPageSize()).page(pageable.getPageNumber()).sortBy(SortBy.RECENT_POST)
                .build();

        FindPostResDTO scrapingPostListByCategory = scrapingPostService.findScrapingPostListByCategory(reqDTO);

        return ResponseEntity.ok(
                FindPostResDTO.builder()
                        .posts(scrapingPostListByCategory.getPosts())
                        .hasNext(scrapingPostListByCategory.getHasNext())
                        .build());
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<FindPostResDTO> findPostByLocation
            (@PathVariable(name = "locationId") String city,
             @RequestParam Long memberId,
             @PageableDefault(page = 0, size = 10) Pageable pageable) {

        FindPostByLocationReqDTO reqDTO = FindPostByLocationReqDTO.builder()
                .city(city)
                .memberId(memberId)
                .size(pageable.getPageSize()).page(pageable.getPageNumber()).sortBy(SortBy.RECENT_POST)
                .build();

        FindPostResDTO scrapingPostListByCategory = scrapingPostService.findScrapingPostListByLocation(reqDTO);

        return ResponseEntity.ok(
                FindPostResDTO.builder()
                        .posts(scrapingPostListByCategory.getPosts())
                        .hasNext(scrapingPostListByCategory.getHasNext())
                        .build());
    }


}
