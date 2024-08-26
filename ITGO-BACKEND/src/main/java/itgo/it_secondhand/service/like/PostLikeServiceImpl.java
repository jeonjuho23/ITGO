package itgo.it_secondhand.service.like;

import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberLikePost;
import itgo.it_secondhand.domain.Post;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.service.like.DTO.LikeReqDTO;
import itgo.it_secondhand.service.post.DTO.PostResDTO;
import itgo.it_secondhand.repository.MemberLikePostRepository;
import itgo.it_secondhand.repository.MemberRepository;
import itgo.it_secondhand.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostLikeServiceImpl implements LikeService<PostResDTO, Long> {

    private final MemberRepository memberRepository;
    private final PostRepository<Post> postRepository;
    private final MemberLikePostRepository memberLikePostRepository;


    @Transactional
    @Override
    public Long regist(LikeReqDTO<Long> likeReqDTO) {
        // 엔티티 조회
        Member member = memberRepository.findById(likeReqDTO.getMemberId())
                .orElseThrow(() -> new RestApiException(CustomExceptionCode.MEMBER_NOT_FOUND));
        Post post = postRepository.findById(likeReqDTO.getLikedThingId())
                .orElseThrow(()-> new RestApiException(CustomExceptionCode.POST_NOT_FOUND));

        // 좋아요 생성
        MemberLikePost memberLikePost = MemberLikePost.createMemberLikePost(member, post);

        // 저장
        MemberLikePost save = memberLikePostRepository.save(memberLikePost);

        return save.getId();
    }

    @Transactional
    @Override
    public void delete(LikeReqDTO<Long> likeReqDTO) {
        // 엔티티 조회
        Member member = memberRepository.findById(likeReqDTO.getMemberId())
                .orElseThrow(() -> new RestApiException(CustomExceptionCode.MEMBER_NOT_FOUND));
        Post post = postRepository.findById(likeReqDTO.getLikedThingId())
                .orElseThrow(() -> new RestApiException(CustomExceptionCode.POST_NOT_FOUND));

        MemberLikePost memberLikePost = memberLikePostRepository.findByMemberAndPost(member, post)
                .orElseThrow(() -> new RestApiException(CustomExceptionCode.LIKE_NOT_FOUND));

        // 좋아요 삭제
        memberLikePostRepository.delete(memberLikePost);

        // 좋아요 카운트 감소
        post.reduceLikeCount();
    }

    @Override
    public List<PostResDTO> checkList(Long memberId) {
        // 여기말고 PostService에서 해야하는거 아닌가?
        return null;
    }

}
