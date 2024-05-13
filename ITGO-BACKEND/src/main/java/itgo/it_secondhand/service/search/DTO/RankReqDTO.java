package itgo.it_secondhand.service.search.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RankReqDTO {
    private int page;
    private int size;
}
