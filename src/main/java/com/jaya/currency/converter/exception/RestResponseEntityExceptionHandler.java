package com.jaya.currency.converter.exception;

import com.jaya.currency.converter.model.ErrorStatusResponse;
import io.micrometer.core.lang.NonNullApi;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handler for all checked exceptions.
 */
@Slf4j
@NonNullApi
@ControllerAdvice
@SuppressWarnings("unused")
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Method for all generic exceptions.
   */
  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<Object> genericException(
      Exception exception, HttpServletRequest httpServletRequest) {
    ErrorStatusResponse apiError =
        ErrorStatusResponse.builder()
            .description(exception.getMessage())
            .build();
    return dealingWithResponse(apiError, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Method for all unsupported currency exceptions.
   */
  @ExceptionHandler(value = {FeesUnavailableException.class})
  public ResponseEntity<Object> genericException(
      FeesUnavailableException exception, HttpServletRequest httpServletRequest) {
    ErrorStatusResponse apiError =
        ErrorStatusResponse.builder()
            .description(exception.getMessage())
            .build();
    return dealingWithResponse(apiError, HttpHeaders.EMPTY, HttpStatus.PRECONDITION_FAILED);
  }

  private ResponseEntity<Object> dealingWithResponse(Object apiError,
                                                     HttpHeaders headers,
                                                     HttpStatus status) {
    log.error(apiError.toString());
    return new ResponseEntity<>(apiError, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    ErrorStatusResponse apiError =
        ErrorStatusResponse.builder()
            .description(exception.getMessage())
            .build();
    return dealingWithResponse(apiError, headers, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException exception, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    ErrorStatusResponse apiError =
        ErrorStatusResponse.builder()
            .description(exception.getMessage())
            .build();
    return dealingWithResponse(apiError, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(
      ServletRequestBindingException exception, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    ErrorStatusResponse apiError =
        ErrorStatusResponse.builder()
            .description(exception.getMessage())
            .build();
    return dealingWithResponse(apiError, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException exception,
                                                      HttpHeaders headers,
                                                      HttpStatus status, WebRequest request) {
    ErrorStatusResponse apiError =
        ErrorStatusResponse.builder()
            .description(exception.getMessage())
            .build();
    return dealingWithResponse(apiError, headers, status);

  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    ErrorStatusResponse apiError =
        ErrorStatusResponse.builder()
            .description(exception.getCause().getMessage())
            .build();

    return dealingWithResponse(apiError, headers, HttpStatus.NOT_ACCEPTABLE);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    ErrorStatusResponse apiError =
        ErrorStatusResponse.builder()
            .description(processFieldError(Optional.of(exception.getFieldErrors())
                .orElse(Collections.emptyList())))
            .build();
    return dealingWithResponse(apiError, headers, status);
  }

  private String processFieldError(List<FieldError> fieldErrors) {
    return fieldErrors.parallelStream()
        .map(e -> e.getField() + " : " + e.getDefaultMessage())
        .collect(Collectors.joining(", "));
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
      MissingServletRequestPartException exception, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    ErrorStatusResponse apiError =
        ErrorStatusResponse.builder()
            .description(exception.getMessage())
            .build();
    return dealingWithResponse(apiError, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(BindException exception, HttpHeaders headers,
                                                       HttpStatus status, WebRequest request) {
    ErrorStatusResponse apiError =
        ErrorStatusResponse.builder()
            .description(exception.getMessage())
            .build();
    return dealingWithResponse(apiError, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exception,
                                                                 HttpHeaders headers,
                                                                 HttpStatus status,
                                                                 WebRequest request) {
    ErrorStatusResponse apiError =
        ErrorStatusResponse.builder()
            .description(exception.getMessage())
            .build();
    return dealingWithResponse(apiError, headers, status);
  }

  @Override
  protected ResponseEntity<Object> handleAsyncRequestTimeoutException(
      AsyncRequestTimeoutException exception, HttpHeaders headers, HttpStatus status,
      WebRequest webRequest) {
    ErrorStatusResponse apiError =
        ErrorStatusResponse.builder()
            .description(exception.getMessage())
            .build();
    return dealingWithResponse(apiError, headers, status);
  }

}
