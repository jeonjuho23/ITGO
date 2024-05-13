package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.SecondhandScrapedPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SecondhandPostRepository extends JpaRepository<SecondhandScrapedPost, Long> {

//    @Query(value = "select s from SecondhandScrapedPost s join fetch s.device " +
//            "where s.device.deviceName like concat('%', :keyword, '%')")
//    Slice<SecondhandScrapedPost> findPostKeywordContaining(@Param("keyword") String keyword, Pageable pageable);

    Slice<SecondhandScrapedPost> findSliceBy(Pageable pageable);

    @Query(value = "select s from SecondhandScrapedPost s " +
                          "where s.id = ANY (select l.post.id from MemberLikePost l where l.member.id = :id)")
    Slice<SecondhandScrapedPost> findLikePostByMember_Id(@Param("id") Long memberId, Pageable pageable);


    @Query(value = "select s from SecondhandScrapedPost s " +
            "where s.device.id =  ANY (select d.id from Device d where lower(function('replace',d.deviceName,' ','')) like lower(concat('%',:keyword,'%'))) ")
    @EntityGraph(attributePaths = {"device","member"})
    Slice<SecondhandScrapedPost> searchSecondhandPostByDeviceName(String keyword, Pageable pageable);

    Slice<SecondhandScrapedPost> findByDevice_Category_Id(Long categoryId, Pageable pageable);

    Slice<SecondhandScrapedPost> findByLocation_City(String city, Pageable pageable);

}
