package itgo.it_secondhand.service.post;

import itgo.it_secondhand.domain.*;
import itgo.it_secondhand.enum_.SortBy;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.repository.MemberLikePostRepository;
import itgo.it_secondhand.repository.MemberRepository;
import itgo.it_secondhand.repository.MemberViewPostRepository;
import itgo.it_secondhand.repository.SecondhandPostRepository;
import itgo.it_secondhand.service.post.DTO.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.Optional;

import static itgo.it_secondhand.StubFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScrapingPostServiceImplTest {

    @Spy
    @InjectMocks
    ScrapingPostServiceImpl scrapingPostService;

    @Mock
    MemberRepository memberRepository;
    @Mock
    SecondhandPostRepository secondhandPostRepository;
    @Mock
    MemberViewPostRepository memberViewPostRepository;
    @Mock
    MemberLikePostRepository memberLikePostRepository;


    @Test
    public void viewScrapingPost() throws Exception {
        //given
        Member member = getMember();
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));

        SecondhandScrapedPost secondhandScrapedPost = getSecondhandScrapedPost();
        when(secondhandPostRepository.findById(anyLong()))
                .thenReturn(Optional.of(secondhandScrapedPost));

        MemberViewPost memberViewPost = getMemberViewPost(secondhandScrapedPost);
        when(memberViewPostRepository.findTopByMemberAndPostOrderByViewDateDesc(any(Member.class), any(Post.class)))
                .thenReturn(memberViewPost);
        when(memberLikePostRepository.findByMemberAndPost(any(Member.class), any(Post.class)))
                .thenReturn(Optional.empty());

        int viewCount = secondhandScrapedPost.getPostViewCount();

        PostViewReqDTO request = new PostViewReqDTO(1L, 1L);

        //when
        ScrapedPostViewResDTO response = scrapingPostService.viewScrapingPost(request);

        //then
        assertThat(response.getViewCount())
                .isGreaterThan(viewCount);
        assertThat(response.getIsLike())
                .isFalse();
    }

    @Test
    public void viewScrapingPostThrowMemberNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        PostViewReqDTO request = new PostViewReqDTO(1L, 1L);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            scrapingPostService.viewScrapingPost(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.MEMBER_NOT_FOUND);
    }

    @Test
    public void viewScrapingPostThrowPostNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Member.class)));
        when(secondhandPostRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        PostViewReqDTO request = new PostViewReqDTO(1L, 1L);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            scrapingPostService.viewScrapingPost(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.POST_NOT_FOUND);
    }

    @Test
    public void viewScrapingPostWithFirstView() throws Exception {
        //given
        Member member = getMember();
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));

        SecondhandScrapedPost secondhandScrapedPost = getSecondhandScrapedPost();
        when(secondhandPostRepository.findById(anyLong()))
                .thenReturn(Optional.of(secondhandScrapedPost));
        when(memberViewPostRepository.findTopByMemberAndPostOrderByViewDateDesc(any(Member.class), any(Post.class)))
                .thenReturn(null);
        when(memberLikePostRepository.findByMemberAndPost(any(Member.class), any(Post.class)))
                .thenReturn(Optional.empty());

        PostViewReqDTO request = new PostViewReqDTO(1L, 1L);

        //when
        ScrapedPostViewResDTO response = scrapingPostService.viewScrapingPost(request);

        //then
        assertThat(response.getViewCount())
                .isEqualTo(secondhandScrapedPost.getPostViewCount());
        assertThat(response.getIsLike())
                .isFalse();
    }

    @Test
    public void viewScrapingPostWithLike() throws Exception {
        //given
        Member member = getMember();
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));

        SecondhandScrapedPost secondhandScrapedPost = getSecondhandScrapedPost();
        when(secondhandPostRepository.findById(anyLong()))
                .thenReturn(Optional.of(secondhandScrapedPost));
        when(memberViewPostRepository.findTopByMemberAndPostOrderByViewDateDesc(any(Member.class), any(Post.class)))
                .thenReturn(mock(MemberViewPost.class));
        when(memberLikePostRepository.findByMemberAndPost(any(Member.class), any(Post.class)))
                .thenReturn(Optional.of(mock(MemberLikePost.class)));

        PostViewReqDTO request = new PostViewReqDTO(1L, 1L);

        //when
        ScrapedPostViewResDTO response = scrapingPostService.viewScrapingPost(request);

        //then
        assertThat(response.getIsLike())
                .isTrue();
    }


    @Test
    public void findAllScrapingPostList() throws Exception {
        //given
        Slice<SecondhandScrapedPost> posts = getSecondhandScrapedPostSlice();
        when(secondhandPostRepository.findSliceBy(any(Pageable.class)))
                .thenReturn(posts);
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Member.class)));

        doReturn(mock(FindPostResDTO.class))
                .when(scrapingPostService)
                .setFindPostDTO(any(Member.class), any(Slice.class));

        FindPostReqDTO request = new FindPostReqDTO(1L, 0, 1, SortBy.RECENT_POST);

        //when
        FindPostResDTO response = scrapingPostService.findALlScrapingPostList(request);

        //then
        assertThat(response)
                .isNotNull();
        verify(scrapingPostService, times(1))
                .setFindPostDTO(any(Member.class), any(Slice.class));
    }

    @Test
    public void findAllScrapingPostListThrowPageNotFoundException() throws Exception {
        //given
        Slice<SecondhandScrapedPost> posts = new SliceImpl<>(new ArrayList<>());
        when(secondhandPostRepository.findSliceBy(any(Pageable.class)))
                .thenReturn(posts);

        FindPostReqDTO request = new FindPostReqDTO(1L, 0, 1, SortBy.RECENT_POST);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            scrapingPostService.findALlScrapingPostList(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.PAGE_NOT_FOUND);
    }

    @Test
    public void findAllScrapingPostListThrowMemberNotFoundException() throws Exception {
        //given
        Slice<SecondhandScrapedPost> posts = getSecondhandScrapedPostSlice();
        when(secondhandPostRepository.findSliceBy(any(Pageable.class)))
                .thenReturn(posts);
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        FindPostReqDTO request = new FindPostReqDTO(1L, 0, 1, SortBy.RECENT_POST);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            scrapingPostService.findALlScrapingPostList(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.MEMBER_NOT_FOUND);
    }


    @Test
    public void findLikeScrapingPostList() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Member.class)));

        Slice<SecondhandScrapedPost> posts = getSecondhandScrapedPostSlice();
        when(secondhandPostRepository.findLikePostByMember_Id(anyLong(), any(Pageable.class)))
                .thenReturn(posts);

        doReturn(mock(FindPostResDTO.class))
                .when(scrapingPostService)
                .setFindPostDTO(any(Member.class), any(Slice.class));

        FindPostReqDTO request = new FindPostReqDTO(1L, 0, 1, SortBy.RECENT_POST);

        //when
        FindPostResDTO response = scrapingPostService.findLikeScrapingPostList(request);

        //then
        assertThat(response)
                .isNotNull();
        verify(scrapingPostService, times(1))
                .setFindPostDTO(any(Member.class), any(Slice.class));
    }

    @Test
    public void findLikeScrapingPostListThrowMemberNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        FindPostReqDTO request = new FindPostReqDTO(1L, 0, 1, SortBy.RECENT_POST);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            scrapingPostService.findLikeScrapingPostList(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.MEMBER_NOT_FOUND);
    }

    @Test
    public void findLikeScrapingPostListThrowPageNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Member.class)));

        Slice<SecondhandScrapedPost> posts = new SliceImpl<>(new ArrayList<>());
        when(secondhandPostRepository.findLikePostByMember_Id(anyLong(), any(Pageable.class)))
                .thenReturn(posts);

        FindPostReqDTO request = new FindPostReqDTO(1L, 0, 1, SortBy.RECENT_POST);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            scrapingPostService.findLikeScrapingPostList(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.PAGE_NOT_FOUND);
    }


    @Test
    public void findScrapingPostListByCategory() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Member.class)));

        Slice<SecondhandScrapedPost> posts = getSecondhandScrapedPostSlice();
        when(secondhandPostRepository.findByDevice_Category_Id(anyLong(), any(Pageable.class)))
                .thenReturn(posts);

        doReturn(mock(FindPostResDTO.class))
                .when(scrapingPostService)
                .setFindPostDTO(any(Member.class), any(Slice.class));

        FindPostByCategoryReqDTO request = new FindPostByCategoryReqDTO(1L, 1L, 0, 10, SortBy.RECENT_POST);

        //when
        FindPostResDTO response = scrapingPostService.findScrapingPostListByCategory(request);

        //then
        assertThat(response)
                .isNotNull();
        verify(scrapingPostService, times(1))
                .setFindPostDTO(any(Member.class), any(Slice.class));
    }

    @Test
    public void findScrapingPostListByCategoryThrowMemberNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        FindPostByCategoryReqDTO request = new FindPostByCategoryReqDTO(1L, 1L, 0, 10, SortBy.RECENT_POST);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            scrapingPostService.findScrapingPostListByCategory(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.MEMBER_NOT_FOUND);
    }

    @Test
    public void findScrapingPostListByCategoryThrowPageNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Member.class)));

        Slice<SecondhandScrapedPost> posts = new SliceImpl<>(new ArrayList<>());
        when(secondhandPostRepository.findByDevice_Category_Id(anyLong(), any(Pageable.class)))
                .thenReturn(posts);

        FindPostByCategoryReqDTO request = new FindPostByCategoryReqDTO(1L, 1L, 0, 10, SortBy.RECENT_POST);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            scrapingPostService.findScrapingPostListByCategory(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.PAGE_NOT_FOUND);
    }


    @Test
    public void findScrapingPostListByLocation() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Member.class)));

        Slice<SecondhandScrapedPost> posts = getSecondhandScrapedPostSlice();
        when(secondhandPostRepository.findByLocation_City(anyString(), any(Pageable.class)))
                .thenReturn(posts);

        doReturn(mock(FindPostResDTO.class))
                .when(scrapingPostService)
                .setFindPostDTO(any(Member.class), any(Slice.class));

        FindPostByLocationReqDTO request = new FindPostByLocationReqDTO("city", 1L, 10, 0, SortBy.RECENT_POST);

        //when
        FindPostResDTO response = scrapingPostService.findScrapingPostListByLocation(request);

        //then
        assertThat(response)
                .isNotNull();
        verify(scrapingPostService, times(1))
                .setFindPostDTO(any(Member.class), any(Slice.class));
    }

    @Test
    public void findScrapingPostListByLocationThrowMemberNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        FindPostByLocationReqDTO request = new FindPostByLocationReqDTO("city", 1L, 10, 0, SortBy.RECENT_POST);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            scrapingPostService.findScrapingPostListByLocation(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.MEMBER_NOT_FOUND);
    }

    @Test
    public void findScrapingPostListByLocationThrowPageNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Member.class)));

        Slice<SecondhandScrapedPost> posts = new SliceImpl<>(new ArrayList<>());
        when(secondhandPostRepository.findByLocation_City(anyString(), any(Pageable.class)))
                .thenReturn(posts);

        FindPostByLocationReqDTO request = new FindPostByLocationReqDTO("city", 1L, 10, 0, SortBy.RECENT_POST);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            scrapingPostService.findScrapingPostListByLocation(request);
        });

        //then
        assertThat(exception.getExceptionCode())
                .isEqualTo(CustomExceptionCode.PAGE_NOT_FOUND);
    }


    @Test
    public void setFindPostDTOWithLikeAndView() throws Exception {
        //given
        Slice<SecondhandScrapedPost> posts = getSecondhandScrapedPostSlice();
        when(memberLikePostRepository.findByMemberAndPost(any(Member.class), any(Post.class)))
                .thenReturn(Optional.of(mock(MemberLikePost.class)));
        when(memberViewPostRepository.findTopByMemberAndPostOrderByViewDateDesc(any(Member.class), any(Post.class)))
                .thenReturn(mock(MemberViewPost.class));

        //when
        FindPostResDTO response = scrapingPostService.setFindPostDTO(mock(Member.class), posts);

        //then
        assertThat(response.getPosts().get(0).getIsLike())
                .isTrue();
        assertThat(response.getPosts().get(0).getIsView())
                .isTrue();
    }

    @Test
    public void setFindPostDTOWithNoLikeAndView() throws Exception {
        //given
        Slice<SecondhandScrapedPost> posts = getSecondhandScrapedPostSlice();
        when(memberLikePostRepository.findByMemberAndPost(any(Member.class), any(Post.class)))
                .thenReturn(Optional.empty());
        when(memberViewPostRepository.findTopByMemberAndPostOrderByViewDateDesc(any(Member.class), any(Post.class)))
                .thenReturn(mock(MemberViewPost.class));

        //when
        FindPostResDTO response = scrapingPostService.setFindPostDTO(mock(Member.class), posts);

        //then
        assertThat(response.getPosts().get(0).getIsLike())
                .isFalse();
        assertThat(response.getPosts().get(0).getIsView())
                .isTrue();
    }

    @Test
    public void setFindPostDTOWithLikeAndNoView() throws Exception {
        //given
        Slice<SecondhandScrapedPost> posts = getSecondhandScrapedPostSlice();
        when(memberLikePostRepository.findByMemberAndPost(any(Member.class), any(Post.class)))
                .thenReturn(Optional.of(mock(MemberLikePost.class)));
        when(memberViewPostRepository.findTopByMemberAndPostOrderByViewDateDesc(any(Member.class), any(Post.class)))
                .thenReturn(null);

        //when
        FindPostResDTO response = scrapingPostService.setFindPostDTO(mock(Member.class), posts);

        //then
        assertThat(response.getPosts().get(0).getIsLike())
                .isTrue();
        assertThat(response.getPosts().get(0).getIsView())
                .isFalse();
    }

    @Test
    public void setFindPostDTOWithNoLikeAndNoView() throws Exception {
        //given
        Slice<SecondhandScrapedPost> posts = getSecondhandScrapedPostSlice();
        when(memberLikePostRepository.findByMemberAndPost(any(Member.class), any(Post.class)))
                .thenReturn(Optional.empty());
        when(memberViewPostRepository.findTopByMemberAndPostOrderByViewDateDesc(any(Member.class), any(Post.class)))
                .thenReturn(null);

        //when
        FindPostResDTO response = scrapingPostService.setFindPostDTO(mock(Member.class), posts);

        //then
        assertThat(response.getPosts().get(0).getIsLike())
                .isFalse();
        assertThat(response.getPosts().get(0).getIsView())
                .isFalse();
    }

}