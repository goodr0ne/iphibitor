package goodr0ne.iphibitor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IphibitorControllerRequestLimitHandler {

  @ResponseStatus(HttpStatus.BAD_GATEWAY)
  @ExceptionHandler(IphibitorRequestLimitReachedException.class)
  public void handleConflict() {}
}
