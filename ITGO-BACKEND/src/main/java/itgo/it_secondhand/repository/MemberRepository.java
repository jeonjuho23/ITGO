package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByPhone(String phone);

    Boolean existsByPhone(String phone);

}
