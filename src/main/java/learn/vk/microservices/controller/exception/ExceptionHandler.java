package learn.vk.microservices.controller.exception;

import learn.vk.microservices.dto.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.badRequest().body(buildResponseEntity(ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({GenericException.class})
    public ResponseEntity<Object> handleException(GenericException ex) {
        return ResponseEntity.internalServerError().body(buildResponseEntity(ex.getMessage()));
    }

    private Object buildResponseEntity(String message) {
        return new Message(message);
    }
}
