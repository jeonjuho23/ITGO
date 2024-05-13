package itgo.it_secondhand.api;

import itgo.it_secondhand.domain.value.Location;
import itgo.it_secondhand.enum_.SortBy;
import itgo.it_secondhand.service.post.DTO.*;
import itgo.it_secondhand.service.post.ScrapingPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostRestController {

    private final ScrapingPostService scrapingPostService;


    // 현재 ResponseEntity의 제네릭으로 Service에서 받은 응답객체가 들어간다.
    // 이렇게 하면 HTTP Response에 다른 데이터가 필요하다면 Service에서 받은 응답객체에 종속적이므로 문제가 생긴다.
    // 전용 응답 DTO로 변환하여 HTTP Response 할 수 있도록 하자.


    @GetMapping("/find/all/list")
    public ResponseEntity<FindPostResDTO> findPostList(@RequestParam Long memberId
            , @RequestParam int page, @RequestParam int size,@RequestParam  SortBy sortBy){

        FindPostReqDTO reqDTO = FindPostReqDTO.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .memberId(memberId)
                .build();

        FindPostResDTO responseDTO = scrapingPostService.findALlScrapingPostList(reqDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/find/like/list")
    public ResponseEntity<FindPostResDTO> findLikedPostList(@RequestParam Long memberId
            , @RequestParam int page, @RequestParam int size, @RequestParam SortBy sortBy){

        FindPostReqDTO reqDTO = FindPostReqDTO.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .memberId(memberId)
                .build();

        FindPostResDTO responseDTO = scrapingPostService.findLikeScrapingPostList(reqDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/view")
    public ResponseEntity<ScrapedPostViewResDTO> viewPost(@RequestParam Long memberId, @RequestParam Long postId){

        PostViewReqDTO reqDTO = PostViewReqDTO.builder()
                .memberId(memberId)
                .postId(postId)
                .build();

        ScrapedPostViewResDTO responseDTO = scrapingPostService.viewScrapingPost(reqDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/find/by/category")
    public ResponseEntity<FindPostResDTO> findPostByCategory(@RequestParam Long categoryId, @RequestParam Long memberId,
                            @RequestParam int size, @RequestParam int page, @RequestParam SortBy sortBy){

        FindPostByCategoryReqDTO reqDTO = FindPostByCategoryReqDTO.builder()
                .categoryId(categoryId)
                .memberId(memberId)
                .size(size).page(page).sortBy(sortBy)
                .build();

        FindPostResDTO scrapingPostListByCategory = scrapingPostService.findScrapingPostListByCategory(reqDTO);

        return ResponseEntity.ok(
                FindPostResDTO.builder()
                        .posts(scrapingPostListByCategory.getPosts())
                        .hasNext(scrapingPostListByCategory.getHasNext())
                        .build());
    }

    @GetMapping("/find/by/city")
    public ResponseEntity<FindPostResDTO> findPostByLocation(@RequestParam String city, @RequestParam Long memberId,
                                                             @RequestParam int size, @RequestParam int page, @RequestParam SortBy sortBy){

        FindPostByLocationReqDTO reqDTO = FindPostByLocationReqDTO.builder()
                .city(city)
                .memberId(memberId)
                .size(size).page(page).sortBy(sortBy)
                .build();

        FindPostResDTO scrapingPostListByCategory = scrapingPostService.findScrapingPostListByLocation(reqDTO);

        return ResponseEntity.ok(
                FindPostResDTO.builder()
                        .posts(scrapingPostListByCategory.getPosts())
                        .hasNext(scrapingPostListByCategory.getHasNext())
                        .build());
    }



}
