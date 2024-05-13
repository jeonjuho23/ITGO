package itgo.it_secondhand.repository;


import itgo.it_secondhand.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface NotificationRepository extends MongoRepository<Notification,String> {
    Notification findByMemberId(Long memberId);
    void deleteByMemberId(Long memberId);
}
