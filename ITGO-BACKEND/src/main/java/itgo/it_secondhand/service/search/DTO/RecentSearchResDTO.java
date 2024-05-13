package itgo.it_secondhand.service.search.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecentSearchResDTO {
    private List<String> keywordList;
    private Boolean hasNext;

}
