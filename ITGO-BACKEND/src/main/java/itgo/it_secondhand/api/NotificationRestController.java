package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.ResponseDTO;
import itgo.it_secondhand.service.notification.DTO.CheckNotificationReqDTO;
import itgo.it_secondhand.service.notification.DTO.CheckNotificationResDTO;
import itgo.it_secondhand.service.notification.DTO.ManageNotificationReqDTO;
import itgo.it_secondhand.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static itgo.it_secondhand.api.DTO.ResponseDTO.success;

@RestController
@RequestMapping("/api/v2/notifications")
@RequiredArgsConstructor
public class NotificationRestController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<ResponseDTO<?>> findNotificationList
            (@RequestParam Long memberId,
             @PageableDefault(page = 0, size = 10) Pageable pageable){

        CheckNotificationReqDTO findReqDTO = CheckNotificationReqDTO.builder()
                .memberId(memberId)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize()).build();

        CheckNotificationResDTO notificationList = notificationService.findNotificationList(findReqDTO);

        return ResponseEntity.ok().body(success(notificationList));
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ResponseDTO<?>> deleteNotification
            (@RequestParam Long memberId,
             @PathVariable(name = "notificationId") int messageIndex){

        ManageNotificationReqDTO deleteReqDTO = ManageNotificationReqDTO.builder()
                .memberId(memberId)
                .messageIndex(messageIndex)
                .build();

        notificationService.deleteNotification(deleteReqDTO);

        return ResponseEntity.ok().body(success());
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO<?>> deleteAllNotification
            (@RequestParam Long memberId){

        ManageNotificationReqDTO deleteReqDTO = ManageNotificationReqDTO.builder()
                .memberId(memberId)
                .build();

        notificationService.deleteAllNotification(deleteReqDTO);

        return ResponseEntity.ok().body(success());
    }



}
