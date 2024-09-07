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

    @GetMapping("/posts/search")
    public ResponseEntity<ResponseDTO<?>> keywordSearch
            (@RequestParam Long memberId,
             @RequestParam String keyword,
             @PageableDefault(page = 0, size = 10) Pageable pageable){

        SearchReqDTO reqDTO = SearchReqDTO.builder()
                .memberId(memberId)
                .keyword(keyword)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .sortBy(SortBy.RECENT_POST)
                .build();

        FindPostResDTO resDTO =
                searchService.keywordSearch(reqDTO);

        return ResponseEntity.ok().body(success(resDTO));
    }

    @GetMapping("/search/recent")
    public ResponseEntity<ResponseDTO<?>> recentSearches
            (@RequestParam Long memberId,
             @PageableDefault(page = 0, size = 10) Pageable pageable){

        RecentSearchReqDTO reqDTO = RecentSearchReqDTO.builder()
                .memberId(memberId)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .sortBy(SortBy.RECENT_SEARCH)
                .build();

        RecentSearchResDTO resDTO =
                searchService.recentSearches(reqDTO);

        return ResponseEntity.ok().body(success(resDTO));
    }


    @GetMapping("/posts/ranking")
    public ResponseEntity<ResponseDTO<?>> ranking
            (@PageableDefault(page = 0, size = 10) Pageable pageable){

        RankReqDTO reqDTO = RankReqDTO.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .build();

        RankResDTO resDTO =
                searchService.getRanking(reqDTO);

        return ResponseEntity.ok().body(success(resDTO));
    }




}
