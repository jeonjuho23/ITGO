package itgo.it_secondhand.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ExceptionResponse {

    private final String code;
    private final String message;
}
