package Be_30.Project.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    PASSWORD_NOT_CONFIRMED(404, "Password not confirmed"),
    ANSWER_NOT_FOUND(404, "Answer Not Found"),

    INVALID_MEMBER_STATUS(400, "Invalid member status"),
    QUESTION_NOT_FOUND(404, "Question not found"),

    QUESTION_EXISTS(409, "Question exists"),

    NOT_AUTHORIZED(404, "not authorized");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
