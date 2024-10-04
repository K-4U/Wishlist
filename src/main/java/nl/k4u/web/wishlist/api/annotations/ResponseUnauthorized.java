package nl.k4u.web.wishlist.api.annotations;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import nl.k4u.web.wishlist.api.pojo.ErrorResponse;
import org.springframework.core.annotation.AliasFor;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
        responseCode = "401",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE)
)
public @interface ResponseUnauthorized {

    @AliasFor(
            annotation = ApiResponse.class,
            attribute = "description"
    )
    String value() default "Unauthorized";
}
