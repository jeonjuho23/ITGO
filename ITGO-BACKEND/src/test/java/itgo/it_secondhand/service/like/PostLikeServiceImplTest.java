package itgo.it_secondhand.service.like;

import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberLikePost;
import itgo.it_secondhand.domain.Post;
import itgo.it_secondhand.domain.SecondhandScrapedPost;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.repository.MemberLikePostRepository;
import itgo.it_secondhand.repository.MemberRepository;
import itgo.it_secondhand.repository.PostRepository;
import itgo.it_secondhand.service.like.DTO.LikeReqDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostLikeServiceImplTest {

    @InjectMocks
    PostLikeServiceImpl postLikeService;

    @Mock
    MemberRepository memberRepository;
    @Mock
    PostRepository<Post> postRepository;
    @Mock
    MemberLikePostRepository memberLikePostRepository;


    @Test
    public void regist() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Member.class)));
        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Post.class)));

        Long memberLikePostId = 1L;
        MemberLikePost memberLikePost = MemberLikePost.builder()
                .id(memberLikePostId).build();
        when(memberLikePostRepository.save(any(MemberLikePost.class)))
                .thenReturn(memberLikePost);


        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        Long response = postLikeService.regist(request);

        //then
        assertThat(response).isEqualTo(memberLikePostId);
    }

    @Test
    public void registThrowMemberNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.empty());


        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            postLikeService.regist(request);
        });

        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.MEMBER_NOT_FOUND);
    }

    @Test
    public void registThrowPostNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Member.class)));
        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.empty());


        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            postLikeService.regist(request);
        });

        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.POST_NOT_FOUND);
    }


    @Test
    public void delete() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Member.class)));
        Post post = SecondhandScrapedPost.createPost();
        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(post));
        when(memberLikePostRepository.findByMemberAndPost(any(Member.class), any(Post.class)))
                .thenReturn(Optional.of(mock(MemberLikePost.class)));

        int postLikeCount = post.getPostLikeCount();

        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        postLikeService.delete(request);

        //then
        assertThat(post.getPostLikeCount()).isLessThan(postLikeCount);
    }

    @Test
    public void deleteThrowMemberNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.empty());


        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            postLikeService.delete(request);
        });

        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.MEMBER_NOT_FOUND);
    }

    @Test
    public void deleteThrowPostNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Member.class)));
        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.empty());


        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            postLikeService.delete(request);
        });

        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.POST_NOT_FOUND);
    }

    @Test
    public void deleteThrowLikeNotFoundException() throws Exception {
        //given
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Member.class)));
        when(postRepository.findById(anyLong()))
                .thenReturn(Optional.of(mock(Post.class)));
        when(memberLikePostRepository.findByMemberAndPost(any(Member.class), any(Post.class)))
                .thenReturn(Optional.empty());


        LikeReqDTO<Long> request = new LikeReqDTO<>(1L, 1L);

        //when
        RestApiException exception = assertThrows(RestApiException.class, () -> {
            postLikeService.delete(request);
        });

        //then
        assertThat(exception.getExceptionCode()).isEqualTo(CustomExceptionCode.LIKE_NOT_FOUND);
    }


}