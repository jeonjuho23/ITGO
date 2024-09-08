package itgo.it_secondhand.api.DTO;

import lombok.AllArgsConstructor;
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
    public static <T> ResponseDTO<T> error(String message, T code){
        return new ResponseDTO<>(message, code);
    }

}

