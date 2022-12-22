package Be_30.Project.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    ANSWER_NOT_FOUND(404, "Answer Not Found"),
    MEMBER_EXISTS(409, "Member exists"),
    INVALID_MEMBER_STATUS(400, "Invalid member status");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
