package de.prplx.jwa.connection;

import de.prplx.jwa.JWebApplet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class JWASocket {

    public final JWebApplet applet;
    public final Socket socket;

    public JWASocket(JWebApplet applet, Socket socket) {
        this.socket = socket;
        this.applet = applet;
    }

    public JWASocket(JWebApplet applet) {
        this.applet = applet;
        this.socket = new Socket();
    }

    public JWASocket() {
        this(null);
    }

    public void connect(String address) {
        int port = address.contains(":") ? Integer.parseInt(address.substring(address.indexOf(':'))) : 80;
        SocketAddress host = new InetSocketAddress(address.substring(0, address.indexOf(':')), port);
        try {
            this.socket.connect(host);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] read() {
        byte[] buffer = new byte[0];
        try {
            buffer = new byte[socket.getInputStream().available()];
            this.socket.getInputStream().read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public void write(byte[] bytes) {
        try {
            this.socket.getOutputStream().write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flush() {
        try {
            this.socket.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
