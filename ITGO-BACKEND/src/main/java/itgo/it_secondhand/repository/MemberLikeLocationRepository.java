package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.MemberLikeLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberLikeLocationRepository extends JpaRepository<MemberLikeLocation, Long> {

    List<MemberLikeLocation> findByMember_Id(Long memberId);
}
