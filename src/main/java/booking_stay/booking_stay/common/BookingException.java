package booking_stay.booking_stay.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class BookingException extends RuntimeException{
    private final HttpStatus status;
    private final ErrorCode errorCode;
    private final String detail;

    public BookingException(HttpStatus httpStatus, ErrorCode errorCode, String detail) {
        this.status = httpStatus;
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public BookingException(HttpStatus httpStatus, ErrorCode errorCode) {
        this.status = httpStatus;
        this.errorCode = errorCode;
        this.detail = "";
    }

}
