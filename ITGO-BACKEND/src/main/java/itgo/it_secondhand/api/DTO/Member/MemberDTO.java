package itgo.it_secondhand.api.DTO.Member;

import itgo.it_secondhand.domain.value.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDTO {
    private String phone;
    private String name;
    private String imgAddress;
    private Location location;
}
