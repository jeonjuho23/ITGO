package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.MemberSearchKeyword;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberSearchKeywordRepository extends JpaRepository<MemberSearchKeyword, Long> {
    MemberSearchKeyword findByMember_IdAndKeyword_Id(Long memberId, Long keywordId);

    @EntityGraph(attributePaths = {"keyword"})
    Slice<MemberSearchKeyword> findSliceByMember_Id(Long memberId, Pageable pageable);
}
