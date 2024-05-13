package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.notification.*;
import itgo.it_secondhand.service.notification.DTO.CheckNotificationReqDTO;
import itgo.it_secondhand.service.notification.DTO.CheckNotificationResDTO;
import itgo.it_secondhand.service.notification.DTO.ManageNotificationReqDTO;
import itgo.it_secondhand.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationRestController {

    private final NotificationService notificationService;

    @GetMapping("/find")
    public ResponseEntity<FindNotificationResponseDTO> findNotificationList(@RequestParam Long memberId, @RequestParam int page, @RequestParam int size){

        CheckNotificationReqDTO findReqDTO = CheckNotificationReqDTO.builder()
                .memberId(memberId)
                .page(page).size(size).build();

        CheckNotificationResDTO notificationList = notificationService.findNotificationList(findReqDTO);

        FindNotificationResponseDTO responseDTO = FindNotificationResponseDTO.builder()
                .notificationList(notificationList.getNotificationMessageList())
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/delete")
    public ResponseEntity<DeleteNotificationResponseDTO> deleteNotification(@RequestParam Long memberId, @RequestParam int messageIndex){

        ManageNotificationReqDTO deleteReqDTO = ManageNotificationReqDTO.builder()
                .memberId(memberId)
                .messageIndex(messageIndex)
                .build();

        notificationService.deleteNotification(deleteReqDTO);

        DeleteNotificationResponseDTO responseDTO = DeleteNotificationResponseDTO.builder()
                .msg("정상적으로 삭제되었습니다.")
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/delete/all")
    public ResponseEntity<DeleteNotificationResponseDTO> deleteAllNotification(@RequestParam Long memberId){

        ManageNotificationReqDTO deleteReqDTO = ManageNotificationReqDTO.builder()
                .memberId(memberId)
                .build();

        notificationService.deleteAllNotification(deleteReqDTO);

        DeleteNotificationResponseDTO responseDTO = DeleteNotificationResponseDTO.builder()
                .msg("정상적으로 삭제되었습니다.")
                .build();

        return ResponseEntity.ok(responseDTO);
    }



}
