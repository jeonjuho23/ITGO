package itgo.it_secondhand.service.search;

import itgo.it_secondhand.domain.SecondhandScrapedPost;
import itgo.it_secondhand.service.post.DTO.FindPostResDTO;
import itgo.it_secondhand.service.post.DTO.PostResDTO;
import itgo.it_secondhand.service.search.DTO.*;
import org.springframework.data.domain.Slice;

import java.util.List;


public interface SearchService {

    //== 검색 ==//
    public FindPostResDTO keywordSearch(SearchReqDTO searchReqDTO);


    //== 최근 검색 ==//
    public RecentSearchResDTO recentSearches(RecentSearchReqDTO recentSearchReqDTO);


    //== 랭킹 서비스 ==//
    public RankResDTO getRanking(RankReqDTO rankReqDTO);
}
