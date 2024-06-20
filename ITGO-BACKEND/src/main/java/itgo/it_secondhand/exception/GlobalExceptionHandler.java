package itgo.it_secondhand.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<?> handleCustomException(RestApiException e) {
        ExceptionCode exceptionCode = e.getExceptionCode();
        return handleExceptionInternal(exceptionCode);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        log.warn("handleIllegalArgument", e);
        ExceptionCode exceptionCode = CustomExceptionCode.INVALID_PARAMETER;
        return handleExceptionInternal(exceptionCode, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllException(Exception ex) {
        log.warn("handleAllException", ex);
        ExceptionCode exceptionCode = CustomExceptionCode.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(exceptionCode);
    }


    private ResponseEntity<?> handleExceptionInternal(ExceptionCode exceptionCode) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(makeErrorResponse(exceptionCode));
    }

    private ExceptionResponse makeErrorResponse(ExceptionCode exceptionCode) {
        return ExceptionResponse.builder()
                .code(exceptionCode.name())
                .message(exceptionCode.getMessage())
                .build();
    }

    private ResponseEntity<?> handleExceptionInternal(ExceptionCode exceptionCode, String message) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(makeErrorResponse(exceptionCode, message));
    }

    private ExceptionResponse makeErrorResponse(ExceptionCode exceptionCode, String message) {
        return ExceptionResponse.builder()
                .code(exceptionCode.name())
                .message(message)
                .build();
    }

}
