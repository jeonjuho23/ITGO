package itgo.it_secondhand.service.search;

import itgo.it_secondhand.domain.Keyword;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberSearchKeyword;
import itgo.it_secondhand.domain.SecondhandScrapedPost;
import itgo.it_secondhand.enum_.SortBy;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.repository.*;
import itgo.it_secondhand.service.post.DTO.FindPostResDTO;
import itgo.it_secondhand.service.post.ScrapingPostServiceImpl;
import itgo.it_secondhand.service.search.DTO.RecentSearchReqDTO;
import itgo.it_secondhand.service.search.DTO.RecentSearchResDTO;
import itgo.it_secondhand.service.search.DTO.SearchReqDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceImplTest {


    @InjectMocks
    SearchServiceImpl searchService;

    @Mock
    SecondhandPostRepository secondhandPostRepository;
    @Mock
    KeywordRepository keywordRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    MemberSearchKeywordRepository memberSearchKeywordRepository;
    @Mock
    DeviceRepository deviceRepository;
    @Mock
    ScrapingPostServiceImpl scrapingPostService;


    @Test
    public void keywordSearch() throws Exception {
        //given
        String keyword = "keyword";

        SecondhandScrapedPost post = SecondhandScrapedPost.builder()
                .build();
        Slice<SecondhandScrapedPost> posts = new SliceImpl<>(List.of(post));
        when(secondhandPostRepository.searchSecondhandPostByDeviceName(anyString(), any(Pageable.class)))
                .thenReturn(posts);

        Keyword dbKeyword = Keyword.builder()
                .id(1L).keyword(keyword).build();
        int count = dbKeyword.getCount();
        when(keywordRepository.findByKeyword(anyString()))
                .thenReturn(dbKeyword);

        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Member.class)));

        MemberSearchKeyword memberSearchKeyword = MemberSearchKeyword.createMemberSearchKeyword(mock(Member.class), dbKeyword);
        LocalDateTime createDate = memberSearchKeyword.getSearchDate();
        when(memberSearchKeywordRepository.findByMember_IdAndKeyword_Id(anyLong(), anyLong()))
                .thenReturn(memberSearchKeyword);


        when(scrapingPostService.setFindPostDTO(any(Member.class), any(Slice.class)))
                .thenReturn(mock(FindPostResDTO.class));


        SearchReqDTO request = new SearchReqDTO(1L, keyword, 0, 10, SortBy.RECENT_SEARCH);

        //when
        FindPostResDTO response = searchService.keywordSearch(request);

        //then
        // increaseSearchCount() 로직 수행 확인
        assertThat(memberSearchKeyword.getKeyword().getCount()).isGreaterThan(count);
        assertThat(memberSearchKeyword.getSearchDate()).isNotEqualTo(createDate);

        assertThat(response).isNotNull();

        verify(scrapingPostService, times(1))
                .setFindPostDTO(any(Member.class), any(Slice.class));
        verify(keywordRepository, never()).save(any());
        verify(memberSearchKeywordRepository, never()).save(any());
    }


    @Test
    public void recentSearches() throws Exception {
        //given
        String keyword = "keyword";
        MemberSearchKeyword memberSearchKeyword =
                MemberSearchKeyword
                        .createMemberSearchKeyword(mock(Member.class), Keyword.create(keyword));
        Slice<MemberSearchKeyword> memberSearchKeywords = new SliceImpl<>(List.of(memberSearchKeyword));
        when(memberSearchKeywordRepository.findSliceByMember_Id(anyLong(), any(Pageable.class)))
                .thenReturn(memberSearchKeywords);


        RecentSearchReqDTO request = new RecentSearchReqDTO(1L, 0, 10, SortBy.RECENT_SEARCH);

        //when
        RecentSearchResDTO response = searchService.recentSearches(request);

        //then
        assertThat(response.getKeywordList().get(0)).isEqualTo(keyword);
    }

    @Test
    public void recentSearchesWithHasNextTrue() throws Exception {
        //given
        String keyword = "keyword";
        MemberSearchKeyword memberSearchKeyword =
                MemberSearchKeyword
                        .createMemberSearchKeyword(mock(Member.class), Keyword.create(keyword));
        Slice<MemberSearchKeyword> memberSearchKeywords = new SliceImpl<>(List.of(memberSearchKeyword), mock(Pageable.class), true);
        when(memberSearchKeywordRepository.findSliceByMember_Id(anyLong(), any(Pageable.class)))
                .thenReturn(memberSearchKeywords);


        RecentSearchReqDTO request = new RecentSearchReqDTO(1L, 0, 1, SortBy.RECENT_SEARCH);

        //when
        RecentSearchResDTO response = searchService.recentSearches(request);

        //then
        assertThat(response.getKeywordList().get(0)).isEqualTo(keyword);
        assertThat(response.getHasNext()).isTrue();

    }

    @Test
    public void recentSearchesThrowPageNotFoundException() throws Exception {
        //given
        Slice<MemberSearchKeyword> memberSearchKeywords = new SliceImpl<>(new ArrayList<>());
        when(memberSearchKeywordRepository.findSliceByMember_Id(anyLong(), any(Pageable.class)))
                .thenReturn(memberSearchKeywords);


        RecentSearchReqDTO request = new RecentSearchReqDTO(1L, 0, 10, SortBy.RECENT_SEARCH);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            searchService.recentSearches(request);
        });

        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.PAGE_NOT_FOUND);
    }


}