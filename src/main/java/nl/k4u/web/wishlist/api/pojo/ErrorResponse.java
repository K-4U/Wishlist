package nl.k4u.web.wishlist.api.pojo;

import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {

    private String id;
    private int code;
    private List<Throwable> exceptions;
    private String message;

    public void setExceptions(Throwable exception) {
        exceptions = new ArrayList<>();
        while (exception != null) {
            exceptions.add(exception);
            exception = exception.getCause();
        }
    }
}
