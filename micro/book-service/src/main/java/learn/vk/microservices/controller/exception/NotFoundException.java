package learn.vk.microservices.controller.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
public class NotFoundException extends RuntimeException{
    final String message;
    public NotFoundException(String message) {
       this.message = message;
    }
}
