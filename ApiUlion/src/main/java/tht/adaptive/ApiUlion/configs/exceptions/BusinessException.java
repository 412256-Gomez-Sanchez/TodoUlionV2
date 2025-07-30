package tht.adaptive.ApiUlion.configs.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class BusinessException extends ResponseStatusException {
    private final HttpStatus status;
    public BusinessException(HttpStatus status, String message) {
        super(status, message);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
