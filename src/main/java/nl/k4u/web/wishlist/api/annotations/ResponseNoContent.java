package nl.k4u.web.wishlist.api.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

@Target({METHOD, TYPE, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
        responseCode = "204",
        content = @Content(schema = @Schema)
)
public @interface ResponseNoContent {

    @AliasFor(
            annotation = ApiResponse.class,
            attribute = "description"
    )
    String value() default "";
}
