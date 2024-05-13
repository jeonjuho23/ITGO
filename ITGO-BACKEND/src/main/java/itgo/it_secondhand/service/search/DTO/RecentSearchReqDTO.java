package itgo.it_secondhand.service.search.DTO;

import itgo.it_secondhand.enum_.SortBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RecentSearchReqDTO {
    private Long memberId;
    private int page;
    private int size;
    private SortBy sortBy;
}
