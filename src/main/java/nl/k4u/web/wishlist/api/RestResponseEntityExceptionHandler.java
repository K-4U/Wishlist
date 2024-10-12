package nl.k4u.web.wishlist.api;

import com.fasterxml.jackson.databind.JsonMappingException;
import nl.k4u.web.wishlist.api.pojo.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;


@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    public static final int UNKNOWN_ERROR = 500;
    public static final int NOT_FOUND = 404;
    public static final int AUTHENTICATION_ERROR = 401;
    public static final int ACCESS_DENIED = 403;
    public static final int CONFLICT = 409;
    public static final int MALFORMED_REQUEST = -3;
    private static final Logger LOG = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    public static ErrorResponse getErrorResponse(Exception ex, Integer errorCode) {
        ErrorResponse body = new ErrorResponse();
        body.setCode(errorCode);
        body.setExceptions(ex);
        body.setMessage(ex.getMessage());
        body.setId(UUID.randomUUID().toString());

        return body;
    }

    /**
     * This handles errors that appear after the result is returned by the controller, for example an exception in
     * object-to-json transformation
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse body = getErrorResponse(ex, UNKNOWN_ERROR);
        return handleInternalError(ex, request, body);
    }

    /**
     * This method handles any other error that is not expected before the result is returned by the controller
     */
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        ErrorResponse body = getErrorResponse(ex, UNKNOWN_ERROR);
        return handleInternalError(ex, request, body);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleJsonMappingException(JsonMappingException ex, WebRequest request) {
        ErrorResponse body = getErrorResponse(ex, MALFORMED_REQUEST);
        return handleInternalError(ex, request, body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleException(BadCredentialsException ex, WebRequest request) {
        ErrorResponse body = getErrorResponse(ex, AUTHENTICATION_ERROR);
        return handleInternalError(ex, request, body);
    }

    /**
     * This method handles any other error that is not expected before the result is returned by the controller
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleException(AccessDeniedException ex, WebRequest request) {
        ErrorResponse body = getErrorResponse(ex, ACCESS_DENIED);
        return handleInternalError(ex, request, body);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse body = getErrorResponse(ex, MALFORMED_REQUEST);
        LOG.error(request.getContextPath(), body.getId(), ex);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> handleInternalError(Exception ex, WebRequest request, ErrorResponse body) {
        //log internal errors
        LOG.error(request.getContextPath(), body.getId(), ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.valueOf(body.getCode()), request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponse body = getErrorResponse(ex, MALFORMED_REQUEST);
        LOG.error(request.getContextPath(), body.getId(), ex);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * This method handles any other error that is not expected before the result is returned by the controller
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleDefaultException(Exception ex, WebRequest request) {
        ErrorResponse body = getErrorResponse(ex, UNKNOWN_ERROR);
        return handleInternalError(ex, request, body);
    }

}
