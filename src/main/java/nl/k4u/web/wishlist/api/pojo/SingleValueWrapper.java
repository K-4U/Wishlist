package nl.k4u.web.wishlist.api.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

public class SingleValueWrapper<T> {

    @Schema(required = true)
    T value;

    public SingleValueWrapper() {
    }

    public SingleValueWrapper(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
