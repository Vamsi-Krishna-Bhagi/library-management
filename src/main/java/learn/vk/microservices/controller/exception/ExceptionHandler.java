package learn.vk.microservices.controller.exception;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ResponseEntity<NotFoundException> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new NotFoundException(ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<NotFoundException> handleException(Exception ex) {
        return ResponseEntity.internalServerError().body(new NotFoundException(ex.getMessage()));
    }

    @Data
    static class NotFoundException {
        private final String message;

        public NotFoundException(String message) {
            System.out.println("NotFoundException.NotFoundException");
            this.message = message;
        }
    }
}
