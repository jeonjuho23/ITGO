package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberLikePost;
import itgo.it_secondhand.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MemberLikePostRepository extends JpaRepository<MemberLikePost, Long> {
    Optional<MemberLikePost> findByMemberAndPost(Member member, Post post);
}
