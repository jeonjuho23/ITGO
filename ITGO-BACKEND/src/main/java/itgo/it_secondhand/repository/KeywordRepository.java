package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Key;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findByKeyword(String keyword);
}
