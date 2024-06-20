package itgo.it_secondhand.api.DTO.Member;

import itgo.it_secondhand.domain.value.Location;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FetchMemberProfileResponseDTO {
    private Long memberId;
    private String phone;
    private String imgAddress;
    private Location location;
}
