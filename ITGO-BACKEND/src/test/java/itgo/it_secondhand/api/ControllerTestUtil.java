package itgo.it_secondhand.api;

import itgo.it_secondhand.exception.CustomExceptionCode;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ControllerTestUtil {
    static void checkResponseDataThrowException(ResultActions action, CustomExceptionCode exceptionCode) throws Exception {
        action.andExpect(status().is(exceptionCode.getHttpStatus().value()))
                .andExpect(jsonPath("message")
                        .value(exceptionCode.getMessage()))
                .andExpect(jsonPath("code")
                        .value(exceptionCode.name()))
                .andDo(print());
    }
}
