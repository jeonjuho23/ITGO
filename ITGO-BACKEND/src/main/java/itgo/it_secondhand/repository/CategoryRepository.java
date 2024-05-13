package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByManufacturer(String manufacturer);

    Category findByManufacturerAndDeviceType(String manufacturer, String deviceType);
}
