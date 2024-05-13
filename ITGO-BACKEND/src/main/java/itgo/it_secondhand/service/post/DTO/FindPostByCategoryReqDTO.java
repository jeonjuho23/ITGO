package itgo.it_secondhand.service.post.DTO;

import itgo.it_secondhand.enum_.SortBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FindPostByCategoryReqDTO {
    private Long categoryId;
    private Long memberId;
    private int page;
    private int size;
    private SortBy sortBy;
}
