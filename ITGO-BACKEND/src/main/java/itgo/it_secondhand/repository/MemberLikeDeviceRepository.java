package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Device;
import itgo.it_secondhand.domain.Member;
import itgo.it_secondhand.domain.MemberLikeDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberLikeDeviceRepository extends JpaRepository<MemberLikeDevice, Long> {
    Optional<MemberLikeDevice> findByMemberAndDevice(Member member, Device device);

    List<MemberLikeDevice> findAllByMember_Id(Long memberId);
}
