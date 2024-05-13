package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PostRepository<T extends Post> extends JpaRepository<T, Long> {

}
