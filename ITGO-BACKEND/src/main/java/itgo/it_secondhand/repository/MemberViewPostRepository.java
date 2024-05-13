package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberViewPost;
import itgo.it_secondhand.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.NoSuchElementException;

public interface MemberViewPostRepository extends JpaRepository<MemberViewPost, Long> {
    MemberViewPost findTopByMemberAndPostOrderByViewDateDesc(Member member, Post post);
}
