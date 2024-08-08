package itgo.it_secondhand.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionCode implements ExceptionCode {

    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 페이지를 찾을 수 없습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 매개변수입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 문제가 발생되었습니다."),
    DEVICE_NOT_FOUND(HttpStatus.NOT_FOUND, "기기 정보를 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    NO_LIKE_LIST(HttpStatus.NO_CONTENT, "관심 리스트가 없습니다."),
    LOCATION_NOT_FOUND(HttpStatus.NOT_FOUND, "위치정보를 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글 정보를 찾을 수 없습니다."),
    NO_NOTIFICATION_LIST(HttpStatus.NO_CONTENT, "알람 메세지가 없습니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요 등록된 것을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
