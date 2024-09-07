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

        CheckNotificationReqDTO reqDTO = CheckNotificationReqDTO.builder()
                .memberId(memberId)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize()).build();

        CheckNotificationResDTO resDTO =
                notificationService.findNotificationList(reqDTO);

        return ResponseEntity.ok().body(success(resDTO));
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ResponseDTO<?>> deleteNotification
            (@RequestParam Long memberId,
             @PathVariable(name = "notificationId") int messageIndex){

        ManageNotificationReqDTO reqDTO = ManageNotificationReqDTO.builder()
                .memberId(memberId)
                .messageIndex(messageIndex)
                .build();

        notificationService.deleteNotification(reqDTO);

        return ResponseEntity.ok().body(success());
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO<?>> deleteAllNotification
            (@RequestParam Long memberId){

        ManageNotificationReqDTO reqDTO = ManageNotificationReqDTO.builder()
                .memberId(memberId)
                .build();

        notificationService.deleteAllNotification(reqDTO);

        return ResponseEntity.ok().body(success());
    }



}
