package itgo.it_secondhand.service.post.DTO;

import itgo.it_secondhand.enum_.SortBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FindPostByLocationReqDTO {
    private String city;
    private Long memberId;
    private int size;
    private int page;
    private SortBy sortBy;
}
