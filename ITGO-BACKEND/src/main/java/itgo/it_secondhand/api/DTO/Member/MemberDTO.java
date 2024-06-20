package itgo.it_secondhand.api.DTO.Member;

import itgo.it_secondhand.domain.value.Location;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class MemberDTO {
    @NotNull
    private String phone;
    @NotNull
    private String name;
    private String imgAddress;
    private Location location;
}
