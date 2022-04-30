package de.prplx.jwa.utilities;

public final class JWAAtomic<Type> {

    private Type value;

    public JWAAtomic(Type initial) {
        this.value = initial;
    }

    public JWAAtomic() {
        this.value = null;
    }

    public void set(Type value) {
        this.value = value;
    }

    public Type get() {
        return value;
    }

}
