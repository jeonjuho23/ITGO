package itgo.it_secondhand.api;

import itgo.it_secondhand.api.DTO.notification.*;
import itgo.it_secondhand.service.notification.DTO.CheckNotificationReqDTO;
import itgo.it_secondhand.service.notification.DTO.CheckNotificationResDTO;
import itgo.it_secondhand.service.notification.DTO.ManageNotificationReqDTO;
import itgo.it_secondhand.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/notifications")
@RequiredArgsConstructor
public class NotificationRestController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<FindNotificationResponseDTO> findNotificationList
            (@RequestParam Long memberId,
             @PageableDefault(page = 0, size = 10) Pageable pageable){

        CheckNotificationReqDTO findReqDTO = CheckNotificationReqDTO.builder()
                .memberId(memberId)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize()).build();

        CheckNotificationResDTO notificationList = notificationService.findNotificationList(findReqDTO);

        FindNotificationResponseDTO responseDTO = FindNotificationResponseDTO.builder()
                .notificationList(notificationList.getNotificationMessageList())
                .build();

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<DeleteNotificationResponseDTO> deleteNotification
            (@RequestParam Long memberId,
             @PathVariable(name = "notificationId") int messageIndex){

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

    @DeleteMapping
    public ResponseEntity<DeleteNotificationResponseDTO> deleteAllNotification
            (@RequestParam Long memberId){

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
