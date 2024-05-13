package itgo.it_secondhand.api.DTO.device;

import itgo.it_secondhand.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FindCategoryResponseDTO {
    private List<Category> categoryList;
}
