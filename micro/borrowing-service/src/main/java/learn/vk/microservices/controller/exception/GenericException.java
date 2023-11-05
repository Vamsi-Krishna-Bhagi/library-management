package learn.vk.microservices.controller.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
public class GenericException extends RuntimeException{
    final String message;
    public GenericException(String message) {
        this.message = message;
    }
}
