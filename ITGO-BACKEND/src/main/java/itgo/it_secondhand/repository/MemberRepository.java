package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByPhone(String phone);

    Boolean existsByPhone(String phone);

}
