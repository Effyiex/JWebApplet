package de.prplx.jwa.utilities;

public class JWAVector<Type> {

    private Object[] buffer;

    public JWAVector(Type... buffer) {
        this.buffer = buffer;
    }

    public void add(Type obj) {
        Object[] buffer = new Object[this.buffer.length + 1];
        for(int i = 0; i < this.buffer.length; i++)
        buffer[i] = this.buffer[i];
        buffer[this.buffer.length] = obj;
        this.buffer = buffer;
    }

    public int indexOf(Type obj) {
        for(int i = 0; i < buffer.length; i++)
        if(buffer[i] != null && buffer[i].equals(obj))
        return i;
        return -1;
    }

    public void remove(Type obj) {
        this.remove(JWAVector.this.indexOf(obj));
    }

    public void remove(int index) {
        Object[] buffer = new Object[this.buffer.length - 1];
        for(int i = 0; i < this.buffer.length; i++)
        if(i < index) buffer[i] = this.buffer[i];
        else if(i > index) buffer[i - 1] = this.buffer[i];
        this.buffer = buffer;
    }

    public void forEach(JWAConsumer<Type> consumer) {
        for(Object obj : buffer)
        consumer.accept((Type) obj);
    }

}
