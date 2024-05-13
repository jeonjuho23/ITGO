package itgo.it_secondhand.service.search.DTO;

import itgo.it_secondhand.enum_.SortBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SearchReqDTO {
    private Long memberId;
    private String keyword;
    private int page;
    private int size;
    private SortBy sortBy;
}
