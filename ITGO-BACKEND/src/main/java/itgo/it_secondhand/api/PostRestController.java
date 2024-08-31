package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.ResponseDTO;
import itgo.it_secondhand.enum_.SortBy;
import itgo.it_secondhand.service.post.DTO.*;
import itgo.it_secondhand.service.post.ScrapingPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static itgo.it_secondhand.api.DTO.ResponseDTO.success;

@RestController
@RequestMapping("/api/v2/posts")
@RequiredArgsConstructor
public class PostRestController {

    private final ScrapingPostService scrapingPostService;

    @GetMapping
    public ResponseEntity<ResponseDTO<?>> findPostList
            (@RequestParam Long memberId,
             @PageableDefault(page = 0, size = 10) Pageable pageable) {

        FindPostReqDTO reqDTO = FindPostReqDTO.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .sortBy(SortBy.RECENT_POST)
                .memberId(memberId)
                .build();

        FindPostResDTO resDTO =
                scrapingPostService.findALlScrapingPostList(reqDTO);

        return ResponseEntity.ok().body(success(resDTO));
    }



    @GetMapping("{postId}")
    public ResponseEntity<ResponseDTO<?>> viewPost
            (@PathVariable(name = "postId") Long postId,
             @RequestParam Long memberId) {

        PostViewReqDTO reqDTO = PostViewReqDTO.builder()
                .memberId(memberId)
                .postId(postId)
                .build();

        ScrapedPostViewResDTO resDTO =
                scrapingPostService.viewScrapingPost(reqDTO);

        return ResponseEntity.ok().body(success(resDTO));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ResponseDTO<?>> findPostByCategory
            (@PathVariable(name = "categoryId") Long categoryId,
             @RequestParam Long memberId,
             @PageableDefault(page = 0, size = 10) Pageable pageable) {

        FindPostByCategoryReqDTO reqDTO = FindPostByCategoryReqDTO.builder()
                .categoryId(categoryId)
                .memberId(memberId)
                .size(pageable.getPageSize())
                .page(pageable.getPageNumber())
                .sortBy(SortBy.RECENT_POST)
                .build();

        FindPostResDTO resDTO =
                scrapingPostService.findScrapingPostListByCategory(reqDTO);

        return ResponseEntity.ok().body(success(resDTO));
    }

    @GetMapping("/location/{city}")
    public ResponseEntity<ResponseDTO<?>> findPostByLocation
            (@PathVariable(name = "city") String city,
             @RequestParam Long memberId,
             @PageableDefault(page = 0, size = 10) Pageable pageable) {

        FindPostByLocationReqDTO reqDTO = FindPostByLocationReqDTO.builder()
                .city(city)
                .memberId(memberId)
                .size(pageable.getPageSize())
                .page(pageable.getPageNumber())
                .sortBy(SortBy.RECENT_POST)
                .build();

        FindPostResDTO resDTO =
                scrapingPostService.findScrapingPostListByLocation(reqDTO);

        return ResponseEntity.ok().body(success(resDTO));
    }


}
