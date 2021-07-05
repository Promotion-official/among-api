package com.promotion.amongapi.advice.handler;

import com.promotion.amongapi.advice.ErrorResopnse;
import com.promotion.amongapi.advice.ErrorStatus;
import com.promotion.amongapi.advice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

public class IntegratedExceptionHandler {
    @ExceptionHandler(AuthorizeKeyNotFoundException.class)
    public ResponseEntity<ErrorResopnse> handleException(AuthorizeKeyNotFoundException exception) {
        return gerErrorResponse(HttpStatus.BAD_REQUEST, ErrorStatus.AUTHORIZE_KEY_NOT_FOUND, "authorize key (" + exception.getAuthorizeKey() + ") 를 찾을 수 없습니다! ");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResopnse> handleException(EntityNotFoundException exception) {
        return gerErrorResponse(HttpStatus.BAD_REQUEST, ErrorStatus.ENTITY_NOT_FOUND, "요청하신 정보를 찾을 수 없습니다!");
    }

    @ExceptionHandler(PermissionNotMatchException.class)
    public ResponseEntity<ErrorResopnse> handleException(PermissionNotMatchException exception) {
        return gerErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorStatus.PERMISSION_NOT_MATCH, "서버측 에러입니다! 담당자에게 다음 메세지를 통해 문의하세요\n" + exception.getMessage());
    }

    @ExceptionHandler(RequestLimitExceededException.class)
    public ResponseEntity<ErrorResopnse> handleException(RequestLimitExceededException exception) {
        return gerErrorResponse(HttpStatus.TOO_MANY_REQUESTS, ErrorStatus.REQUEST_LIMIT_EXCEEDED, "해당 인증키의 분당 최대 요청횟수인 " + exception.getRequestLimit() + "회를 넘긴 요청입니다!");
    }

    @ExceptionHandler(UnknownStrategyException.class)
    public ResponseEntity<ErrorResopnse> handleException(UnknownStrategyException exception) {
        return gerErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorStatus.UNKNOWN_STRATEGY, "서버측 에러입니다! 담당자에게 다음 메세지를 통해 문의하세요\n" + exception.getMessage());
    }

    @ExceptionHandler(WrongConditionTypeException.class)
    public ResponseEntity<ErrorResopnse> handleException(WrongConditionTypeException exception) {
        return gerErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorStatus.WRONG_CONDITION_TYPE,"서버측 에러입니다! 담당자에게 다음 메세지를 통해 문의하세요\n" + exception.getMessage());
    }

    @ExceptionHandler(WrongPermissionIdxException.class)
    public ResponseEntity<ErrorResopnse> handleException(WrongPermissionIdxException exception) {
        return gerErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorStatus.WRONG_PERMISSION, "서버측 에러입니다! 담당자에게 다음 메세지를 통해 문의하세요\n" + exception.getMessage());
    }

    private ResponseEntity<ErrorResopnse> gerErrorResponse(HttpStatus httpStatus, ErrorStatus errStatus, String errMsg) {
        return ResponseEntity
                .status(httpStatus)
                .body(new ErrorResopnse(errMsg, errStatus));
    }
}
