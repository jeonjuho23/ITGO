package itgo.it_secondhand.exception;

import itgo.it_secondhand.api.DTO.ResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GlobalExceptionHandlerTest {

    @Autowired
    GlobalExceptionHandler restControllerAdvice;


    @Test
    public void handleCustomException() throws Exception {
        //given
        // 임의의 예외 코드 제공
        // (enum 을 사용하므로 다른 예외 코드들을 제공해도 동일한 결과를 보장)
        CustomExceptionCode customExceptionCode = CustomExceptionCode.MEMBER_NOT_FOUND;
        RestApiException exception = new RestApiException(customExceptionCode);

        //when
        ResponseEntity<?> response = restControllerAdvice.handleCustomException(exception);

        //then
        assertThat(response.getStatusCode())
                .isEqualTo(customExceptionCode.getHttpStatus());
        assertThat(((ResponseDTO<?>) response.getBody()).getMessage())
                .isEqualTo(customExceptionCode.getMessage());
    }


    @Test
    public void handleIllegalArgument() throws Exception {
        //given
        CustomExceptionCode expectedExceptionCode = CustomExceptionCode.INVALID_PARAMETER;
        IllegalArgumentException exception =
                new IllegalArgumentException(expectedExceptionCode.getMessage());

        //when
        ResponseEntity<?> response = restControllerAdvice.handleIllegalArgument(exception);

        //then
        assertThat(response.getStatusCode())
                .isEqualTo(expectedExceptionCode.getHttpStatus());
        assertThat(((ResponseDTO<?>) response.getBody()).getMessage())
                .isEqualTo(expectedExceptionCode.getMessage());
    }


    @Test
    public void handleAllException() throws Exception {
        //given
        CustomExceptionCode expectedExceptionCode = CustomExceptionCode.INTERNAL_SERVER_ERROR;
        Exception exception =
                new Exception(expectedExceptionCode.getMessage());

        //when
        ResponseEntity<?> response = restControllerAdvice.handleAllException(exception);

        //then
        assertThat(response.getStatusCode())
                .isEqualTo(expectedExceptionCode.getHttpStatus());
        assertThat(((ResponseDTO<?>) response.getBody()).getMessage())
                .isEqualTo(expectedExceptionCode.getMessage());
    }

}