package itgo.it_secondhand.api;

import itgo.it_secondhand.enum_.SortBy;
import itgo.it_secondhand.service.post.DTO.FindPostResDTO;
import itgo.it_secondhand.service.search.DTO.*;
import itgo.it_secondhand.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchRestController {

    private final SearchService searchService;

    // 현재 ResponseEntity의 제네릭으로 Service에서 받은 응답객체가 들어간다.
    // 이렇게 하면 HTTP Response에 다른 데이터가 필요하다면 Service에서 받은 응답객체에 종속적이므로 문제가 생긴다.
    // 전용 응답 DTO로 변환하여 HTTP Response 할 수 있도록 하자.


    @GetMapping("/keyword")
    public ResponseEntity<FindPostResDTO> keywordSearch(@RequestParam Long memberId, @RequestParam String keyword
            , @RequestParam int page, @RequestParam int size, @RequestParam SortBy sortBy){

        SearchReqDTO reqDTO = SearchReqDTO.builder()
                .memberId(memberId)
                .keyword(keyword)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .build();

        FindPostResDTO responseDTO = searchService.keywordSearch(reqDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/recent/searches")
    public ResponseEntity<RecentSearchResDTO> recentSearches(@RequestParam Long memberId
            , @RequestParam int page, @RequestParam int size, @RequestParam SortBy sortBy){

        RecentSearchReqDTO reqDTO = RecentSearchReqDTO.builder()
                .memberId(memberId)
                .page(page)
                .size(size)
                .sortBy(sortBy).build();

        RecentSearchResDTO responseDTO = searchService.recentSearches(reqDTO);

        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping("/ranking")
    public ResponseEntity<RankResDTO> ranking(@RequestParam int page, @RequestParam int size){

        RankReqDTO reqDTO = RankReqDTO.builder()
                .page(page)
                .size(size)
                .build();

        RankResDTO responseDTO = searchService.getRanking(reqDTO);

        return ResponseEntity.ok(responseDTO);
    }




}
