package itgo.it_secondhand.api.DTO;

import itgo.it_secondhand.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseDTO<T> {
    private String message;
    private T data;

    public static <T> ResponseDTO<T> success(T data) {
        return new ResponseDTO<>("SUCCESS", data);
    }
    public static <T> ResponseDTO<T> success(){
        return new ResponseDTO<>("SUCCESS", null);
    }
    public static <T> ResponseDTO<T> error(String message){
        return new ResponseDTO<>(message, null);
    }
}

