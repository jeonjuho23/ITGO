package itgo.it_secondhand.exception;

import lombok.Builder;

@Builder
public record ExceptionResponse(String code, String message) {

}
