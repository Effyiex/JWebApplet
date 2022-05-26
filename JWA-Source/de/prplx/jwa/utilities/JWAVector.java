package de.prplx.jwa.utilities;

public class JWAVector<Type> {

    // Seals the Vector... No Item can be added/removed
    private boolean sealed = false;

    // Allows multiple Threads to work with the same Vector
    private boolean inUse = false;

    private Object[] buffer;

    public JWAVector(Type... buffer) {
        this.buffer = buffer;
    }

    public void add(Type obj) {
        if(sealed) return;
        while(inUse) Thread.yield();
        this.inUse = true;
        Object[] buffer = new Object[this.buffer.length + 1];
        for(int i = 0; i < this.buffer.length; i++)
        buffer[i] = this.buffer[i];
        buffer[this.buffer.length] = obj;
        this.buffer = buffer;
        this.inUse = false;
    }

    public int indexOf(Type obj) {
        for(int i = 0; i < buffer.length; i++)
        if(buffer[i] != null && buffer[i].equals(obj))
        return i;
        return -1;
    }

    public Type remove(Type obj) {
        if(sealed) return null;
        return remove(JWAVector.this.indexOf(obj));
    }

    public Type remove(int index) {
        if(sealed) return null;
        while(inUse) Thread.yield();
        this.inUse = true;
        Object[] buffer = new Object[this.buffer.length - 1];
        Type output = null;
        for(int i = 0; i < this.buffer.length; i++)
        if(i < index && 0 <= i && i < this.buffer.length && i < buffer.length) buffer[i] = this.buffer[i];
        else if(i > index && 0 <= i - 1 && i < this.buffer.length && i - 1 < buffer.length) buffer[i - 1] = this.buffer[i];
        else output = (Type) this.buffer[i];
        this.buffer = buffer;
        this.inUse = false;
        return output;
    }

    public void update(Type... values) {
        this.update(0, values);
    }

    public void update(int startIndex, Type... values) {
        while(inUse) Thread.yield();
        this.inUse = true;
        for(int i = startIndex; i < startIndex + values.length; i++)
        this.buffer[i] = values[i - startIndex];
        this.inUse = false;
    }

    public Type get(int index) {
        return (Type) buffer[index];
    }

    public void forEach(JWAConsumer<Type> consumer) {
        for(Object obj : buffer)
        consumer.accept((Type) obj);
    }

    public int dimension() {
        return buffer.length;
    }

    public JWAVector<Type> seal() {
        this.sealed = true;
        return this;
    }

}
