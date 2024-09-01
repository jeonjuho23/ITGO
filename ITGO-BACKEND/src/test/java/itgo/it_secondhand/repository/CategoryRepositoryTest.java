package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;


    @Test
    public void findAllCategories() throws Exception {
        //given
        List<Category> saveList = setUpCategories();

        //when
        List<Category> allCategories = categoryRepository.findAll();

        //then
        assertThat(allCategories.size())
                .isEqualTo(saveList.size());
    }

    private List<Category> setUpCategories() {
        List<Category> saveList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            saveList.add(createCategory(i));
        }
        categoryRepository.saveAllAndFlush(saveList);
        return saveList;
    }

    private Category createCategory(int index) {
        return categoryRepository.save(Category.createCategory("manufacturer_" + index, "deviceType_" + index));
    }

}