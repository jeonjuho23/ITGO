package itgo.it_secondhand.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import itgo.it_secondhand.exception.CustomExceptionCode;
import itgo.it_secondhand.exception.RestApiException;
import itgo.it_secondhand.service.notification.DTO.CheckNotificationReqDTO;
import itgo.it_secondhand.service.notification.DTO.CheckNotificationResDTO;
import itgo.it_secondhand.service.notification.DTO.ManageNotificationReqDTO;
import itgo.it_secondhand.service.notification.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static itgo.it_secondhand.api.ControllerTestUtil.checkResponseDataThrowException;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(NotificationRestController.class)
class NotificationRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    NotificationServiceImpl notificationService;

    StringBuilder sb;
    private static final CustomExceptionCode exceptionCode
            = CustomExceptionCode.INTERNAL_SERVER_ERROR;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    void setUp(){
        sb = new StringBuilder("/api/v2/notifications");
    }

    @Test
    public void findNotificationList() throws Exception {
        //given
        when(notificationService.findNotificationList(any(CheckNotificationReqDTO.class)))
                .thenReturn(mock(CheckNotificationResDTO.class));

        String requestUrl = sb.toString();
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", objectMapper.writeValueAsString(1L));

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data.notificationMessageList").isArray())
                .andDo(print());
    }


    @Test
    public void findNotificationListThrowServiceException() throws Exception {
        //given
        when(notificationService.findNotificationList(any(CheckNotificationReqDTO.class)))
                .thenThrow(new RestApiException(exceptionCode));

        String requestUrl = sb.toString();
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", objectMapper.writeValueAsString(1L));

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }

    @Test
    public void deleteNotification() throws Exception {
        //given
        doNothing().when(notificationService).deleteNotification(any(ManageNotificationReqDTO.class));

        int notificationId = 1;
        String requestUrl = sb.append("/{notificationId}").toString();
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", objectMapper.writeValueAsString(1L));

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl, notificationId)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("message").value("SUCCESS"))
                .andExpect(jsonPath("data").isEmpty())
                .andDo(print());
    }


    @Test
    public void deleteNotificationThrowServiceException() throws Exception {
        //given
        doThrow(new RestApiException(exceptionCode))
                .when(notificationService)
                .deleteNotification(any(ManageNotificationReqDTO.class));

        int notificationId = 1;
        String requestUrl = sb.append("/{notificationId}").toString();
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", objectMapper.writeValueAsString(1L));

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl, notificationId)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }


    @Test
    public void deleteAllNotification() throws Exception {
        //given
        doNothing().when(notificationService).deleteAllNotification(any(ManageNotificationReqDTO.class));

        String requestUrl = sb.toString();
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", objectMapper.writeValueAsString(1L));

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("message").value("SUCCESS"))
                .andExpect(jsonPath("data").isEmpty())
                .andDo(print());
    }


    @Test
    public void deleteAllNotificationThrowServiceException() throws Exception {
        //given
        doThrow(new RestApiException(exceptionCode))
                .when(notificationService)
                .deleteAllNotification(any(ManageNotificationReqDTO.class));

        String requestUrl = sb.toString();
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", objectMapper.writeValueAsString(1L));

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON));

        //then
        checkResponseDataThrowException(action, exceptionCode);
    }

}