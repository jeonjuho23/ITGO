package itgo.it_secondhand.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import itgo.it_secondhand.service.notification.DTO.CheckNotificationReqDTO;
import itgo.it_secondhand.service.notification.DTO.CheckNotificationResDTO;
import itgo.it_secondhand.service.notification.DTO.ManageNotificationReqDTO;
import itgo.it_secondhand.service.notification.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        requestParams.add("memberId", "1");

        //when
        ResultActions action = mockMvc
                .perform(get(requestUrl)
                        .params(requestParams)
                );

        //then
        verify(notificationService, times(1))
                .findNotificationList(any(CheckNotificationReqDTO.class));
        action.andExpect(status().isOk())
                .andExpect(jsonPath("data").isNotEmpty())
                .andDo(print());
    }


    @Test
    public void deleteNotification() throws Exception {
        //given
        doNothing().when(notificationService).deleteNotification(any(ManageNotificationReqDTO.class));

        String requestUrl = sb.append("/{notificationId}").toString();
        int notificationId = 1;
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", "1");

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl, notificationId)
                        .params(requestParams)
                );

        //then
        verify(notificationService, times(1))
                .deleteNotification(any(ManageNotificationReqDTO.class));
        action.andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    public void deleteAllNotification() throws Exception {
        //given
        doNothing().when(notificationService).deleteAllNotification(any(ManageNotificationReqDTO.class));

        String requestUrl = sb.toString();
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("memberId", "1");

        //when
        ResultActions action = mockMvc
                .perform(delete(requestUrl)
                        .params(requestParams)
                );

        //then
        verify(notificationService, times(1))
                .deleteAllNotification(any(ManageNotificationReqDTO.class));
        action.andExpect(status().isOk())
                .andDo(print());
    }


}