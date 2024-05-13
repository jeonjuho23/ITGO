package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.CategoryBySite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryBySiteRepository extends JpaRepository<CategoryBySite, Long> {
}
