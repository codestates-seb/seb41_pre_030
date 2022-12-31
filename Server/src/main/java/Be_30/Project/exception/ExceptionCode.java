package Be_30.Project.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    OAUTH_MEMBER_NOT_FOUND(404, "Oauth Member Not Found"),
    OAUTH_MEMBER_NOT_MATCH(400, "Oauth Platform Not Match"),
    PASSWORD_NOT_CONFIRMED(404, "Password not confirmed"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    VOTE_NOT_FOUND(404, "Vote not found"),
    ADOPT_NOT_ALLOWED(405, "Adopt not allowed"),

    INVALID_MEMBER_STATUS(400, "Invalid member status"),
    QUESTION_NOT_FOUND(404, "Question not found"),
    QUESTION_EXISTS(409, "Question exists"),

    FILE_SAVE_FAILED(500, "File Save Failed"),

    NOT_AUTHORIZED(404, "not authorized"),
    TOKEN_NOT_FOUND(404, "token not found");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
