package com.promotion.amongapi.advice.handler;

import com.promotion.amongapi.advice.ErrorResopnse;
import com.promotion.amongapi.advice.ErrorStatus;
import com.promotion.amongapi.advice.exception.*;
import com.promotion.amongapi.domain.dto.AccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;

@RestControllerAdvice
public class IntegratedExceptionHandler {
    @ExceptionHandler(AuthorizeKeyNotFoundException.class)
    public ResponseEntity<ErrorResopnse> handleException(AuthorizeKeyNotFoundException exception) {
        return getErrorResponse(HttpStatus.BAD_REQUEST, ErrorStatus.AUTHORIZE_KEY_NOT_FOUND, "authorize key (" + exception.getAuthorizeKey() + ") 를 찾을 수 없습니다! ");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResopnse> handleException(EntityNotFoundException exception) {
        return getErrorResponse(HttpStatus.BAD_REQUEST, ErrorStatus.ENTITY_NOT_FOUND,
                "요청하신 정보를 찾을 수 없습니다!\n" + exception.getMessage());
    }

    @ExceptionHandler(PermissionNotMatchException.class)
    public ResponseEntity<ErrorResopnse> handleException(PermissionNotMatchException exception) {
        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorStatus.PERMISSION_NOT_MATCH,
                "서버측 에러입니다! 담당자에게 다음 메세지를 통해 문의하세요\n" + exception.getMessage());
    }

    @ExceptionHandler(RequestLimitExceededException.class)
    public ResponseEntity<ErrorResopnse> handleException(RequestLimitExceededException exception) {
        return getErrorResponse(HttpStatus.TOO_MANY_REQUESTS, ErrorStatus.REQUEST_LIMIT_EXCEEDED,
                "해당 인증키의 분당 최대 요청횟수인 " + exception.getRequestLimit() + "회를 넘긴 요청입니다!");
    }

    @ExceptionHandler(UnknownStrategyException.class)
    public ResponseEntity<ErrorResopnse> handleException(UnknownStrategyException exception) {
        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorStatus.UNKNOWN_STRATEGY,
                "서버측 에러입니다! 담당자에게 다음 메세지를 통해 문의하세요\n" + exception.getMessage());
    }

    @ExceptionHandler(WrongConditionTypeException.class)
    public ResponseEntity<ErrorResopnse> handleException(WrongConditionTypeException exception) {
        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorStatus.WRONG_CONDITION_TYPE,
                "서버측 에러입니다! 담당자에게 다음 메세지를 통해 문의하세요\n" + exception.getMessage());
    }

    @ExceptionHandler(WrongPermissionIdxException.class)
    public ResponseEntity<ErrorResopnse> handleException(WrongPermissionIdxException exception) {
        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorStatus.WRONG_PERMISSION,
                "서버측 에러입니다! 담당자에게 다음 메세지를 통해 문의하세요\n" + exception.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<ErrorResopnse> handleException(EntityAlreadyExistException exception) {
        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorStatus.ENTITY_ALREADY_EXIST,
                "서버층 에러입니다! 담당자에게 다음 메세지를 통해 문의하세요\n" + exception.getMessage());
    }

    @ExceptionHandler(PermissionDeniedPageAccessException.class)
    public ResponseEntity<ErrorResopnse> handleException(PermissionDeniedPageAccessException exception) {
        return getErrorResponse(HttpStatus.FORBIDDEN, ErrorStatus.WRONG_PERMISSION,
                "해당 페이지에 접근권한이 없습니다!\n" + Arrays.toString(exception.getUsablePermission()) + "중 하나의 권한이 필요합니다!");
    }

    @ExceptionHandler(AccountAlreadyExistException.class)
    public ResponseEntity<ErrorResopnse> handleException(AccountAlreadyExistException exception) {
        return getErrorResponse(HttpStatus.BAD_REQUEST, ErrorStatus.ENTITY_ALREADY_EXIST,
                "이미 등록된 계정입니다! [" + ((AccountDto)exception.getEntity()).getEmail() + "]");
    }

    @ExceptionHandler(AnotherUserTokenException.class)
    public ResponseEntity<ErrorResopnse> handleException(AnotherUserTokenException exception) {
        return getErrorResponse(HttpStatus.FORBIDDEN, ErrorStatus.WRONG_TOKEN_DATA, "해당 토큰으로 조회할 수 없는 계정입니다!");
    }

    @ExceptionHandler(WrongTokenException.class)
    public ResponseEntity<ErrorResopnse> handleException(WrongTokenException exception) {
        return getErrorResponse(HttpStatus.BAD_REQUEST, ErrorStatus.WRONG_TOKEN_DATA, "잘못된 jwt 토큰 입니다!");
    }

    @ExceptionHandler(TokenAlreadyExistException.class)
    public ResponseEntity<ErrorResopnse> handleException(TokenAlreadyExistException exception) {
        return getErrorResponse(HttpStatus.BAD_REQUEST, ErrorStatus.ENTITY_ALREADY_EXIST, "이미 존재하는 토큰 입니다!");
    }

    private ResponseEntity<ErrorResopnse> getErrorResponse(HttpStatus httpStatus, ErrorStatus errStatus, String errMsg) {
        return ResponseEntity
                .status(httpStatus)
                .body(new ErrorResopnse(errMsg, errStatus));
    }
}
