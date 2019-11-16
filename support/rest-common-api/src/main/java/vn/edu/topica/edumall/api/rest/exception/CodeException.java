package vn.edu.topica.edumall.api.rest.exception;

import org.springframework.http.HttpStatus;

public interface CodeException {

  /**
   * @return value should be less than 100 ({@link HttpStatus#CONTINUE}) <br/>
   * and greater than 511 ({@link HttpStatus#NETWORK_AUTHENTICATION_REQUIRED})
   * <br/> to differentiate it from the other HttpStatuses
   * <br/> null to use code equal HttpStatus
   */
  Integer getUserFriendlyExceptionCode();

  String getUserFriendlyMessage();
}
