package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.ResponseDTO;
import itgo.it_secondhand.enum_.SortBy;
import itgo.it_secondhand.service.post.DTO.FindPostResDTO;
import itgo.it_secondhand.service.search.DTO.*;
import itgo.it_secondhand.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static itgo.it_secondhand.api.DTO.ResponseDTO.success;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
public class SearchRestController {

    private final SearchService searchService;

    // 현재 ResponseEntity의 제네릭으로 Service에서 받은 응답객체가 들어간다.
    // 이렇게 하면 HTTP Response에 다른 데이터가 필요하다면 Service에서 받은 응답객체에 종속적이므로 문제가 생긴다.
    // 전용 응답 DTO로 변환하여 HTTP Response 할 수 있도록 하자.


    @GetMapping("/posts/search")
    public ResponseEntity<ResponseDTO<?>> keywordSearch
            (@RequestParam Long memberId, @RequestParam String keyword,
             @PageableDefault(page = 0, size = 10) Pageable pageable){

        SearchReqDTO reqDTO = SearchReqDTO.builder()
                .memberId(memberId)
                .keyword(keyword)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .sortBy(SortBy.RECENT_POST)
                .build();

        FindPostResDTO responseDTO = searchService.keywordSearch(reqDTO);

        return ResponseEntity.ok().body(success(responseDTO));
    }

    @GetMapping("/search/recent")
    public ResponseEntity<ResponseDTO<?>> recentSearches
            (@RequestParam Long memberId,
             @PageableDefault(page = 0, size = 10) Pageable pageable){

        RecentSearchReqDTO reqDTO = RecentSearchReqDTO.builder()
                .memberId(memberId)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .sortBy(SortBy.RECENT_SEARCH).build();

        RecentSearchResDTO responseDTO = searchService.recentSearches(reqDTO);

        return ResponseEntity.ok().body(success(responseDTO));
    }


    @GetMapping("/posts/ranking")
    public ResponseEntity<ResponseDTO<?>> ranking
            (@PageableDefault(page = 0, size = 10) Pageable pageable){

        RankReqDTO reqDTO = RankReqDTO.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();

        RankResDTO responseDTO = searchService.getRanking(reqDTO);

        return ResponseEntity.ok().body(success(responseDTO));
    }




}
