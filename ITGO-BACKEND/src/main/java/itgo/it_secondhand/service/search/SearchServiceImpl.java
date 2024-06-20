package itgo.it_secondhand.service.search;

import itgo.it_secondhand.domain.*;
import itgo.it_secondhand.enum_.SortBy;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.repository.*;
import itgo.it_secondhand.service.post.DTO.FindPostResDTO;
import itgo.it_secondhand.service.post.ScrapingPostServiceImpl;
import itgo.it_secondhand.service.search.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SecondhandPostRepository secondhandPostRepository;
    private final KeywordRepository keywordRepository;
    private final MemberRepository memberRepository;
    private final MemberSearchKeywordRepository memberSearchKeywordRepository;
    private final DeviceRepository deviceRepository;

    private final ScrapingPostServiceImpl scrapingPostService;

    @Transactional
    @Override
    public FindPostResDTO keywordSearch(SearchReqDTO searchReqDTO) {

        Pageable pageable =
                PageRequest.of(searchReqDTO.getPage(), searchReqDTO.getSize(), Sort.by(searchReqDTO.getSortBy().label()));

        // 검색
        Slice<SecondhandScrapedPost> posts =
                secondhandPostRepository.searchSecondhandPostByDeviceName(searchReqDTO.getKeyword().replace(" ", ""), pageable);

        if (posts.isEmpty()) throw new RestApiException(CustomExceptionCode.PAGE_NOT_FOUND);

        // 키워드 검색 연관 기능
        Keyword dbKeyword = keywordRepository.findByKeyword(searchReqDTO.getKeyword());
        if(dbKeyword == null){
            // DB에 없으면
            dbKeyword = Keyword.create(searchReqDTO.getKeyword());
            keywordRepository.save(dbKeyword);
        }

        Member member = memberRepository.findById(searchReqDTO.getMemberId())
                .orElseThrow(() -> new RestApiException(CustomExceptionCode.MEMBER_NOT_FOUND));

        MemberSearchKeyword memberSearchKeyword = memberSearchKeywordRepository.findByMember_IdAndKeyword_Id(searchReqDTO.getMemberId(), dbKeyword.getId());
        if(memberSearchKeyword == null){
            memberSearchKeyword = MemberSearchKeyword.createMemberSearchKeyword(member, dbKeyword);
            memberSearchKeywordRepository.save(memberSearchKeyword);
        }

        // 검색 count 증가
        memberSearchKeyword.increaseSearchCount();

        // res 변환
        return scrapingPostService.setFindPostDTO(member, posts);
    }

    @Override
    public RecentSearchResDTO recentSearches(RecentSearchReqDTO recentSearchReqDTO) {

        Pageable pageable =
                PageRequest.of(recentSearchReqDTO.getPage(), recentSearchReqDTO.getSize(), Sort.by(Sort.Direction.DESC,recentSearchReqDTO.getSortBy().label()));

        // 조회
        Slice<MemberSearchKeyword> memberSearchKeywords = memberSearchKeywordRepository.findSliceByMember_Id(recentSearchReqDTO.getMemberId(), pageable);

        if (memberSearchKeywords.isEmpty()) throw new RestApiException(CustomExceptionCode.PAGE_NOT_FOUND);

        List<String> keywordList = new ArrayList<>();
        for(MemberSearchKeyword memberSearchKeyword : memberSearchKeywords.getContent()){
            keywordList.add(memberSearchKeyword.getKeyword().getKeyword());
        }

        return new RecentSearchResDTO(keywordList, memberSearchKeywords.hasNext());
    }

    @Override
    public RankResDTO getRanking(RankReqDTO rankReqDTO) {

        Pageable pageable
                = PageRequest.of(rankReqDTO.getPage(), rankReqDTO.getSize(), Sort.by(SortBy.COUNT_DEVICE.label()));

        //** 회의 필요.


        return null;
    }
}
