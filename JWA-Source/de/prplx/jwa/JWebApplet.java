package de.prplx.jwa;

import de.prplx.jwa.connection.JWAHandshake;
import de.prplx.jwa.connection.JWASession;
import de.prplx.jwa.connection.JWASocket;
import de.prplx.jwa.rendering.JWAScene;
import de.prplx.jwa.utilities.JWAUtilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;

public class JWebApplet {

    public static final class PortAlreadyBoundException extends Exception {

        public final int port;

        private PortAlreadyBoundException(int port) {
            this.port = port;
        }

    }

    public final JWASession.Manager instances = new JWASession.Manager(this);

    public final ServerSocket socket;
    public final int port;

    private JWAHandshake handshake = JWAHandshake.DEFAULT;
    private JWAScene defaultScene = new JWAScene("EMPTY-SCENE");

    private String webInterface;
    private BufferedImage favicon;

    public JWebApplet(int port) throws PortAlreadyBoundException {
        this.port = port;
        try {
            this.socket = new ServerSocket(port);
        } catch (IOException e) {
            throw new PortAlreadyBoundException(port);
        }
        this.webInterface = JWAUtilities.read(JWebApplet.class, "resources/WebInterface.html");
        try {
            InputStream faviconStream = this.getClass().getResourceAsStream("resources/DefaultFavicon.png");
            this.favicon = ImageIO.read(faviconStream);
            faviconStream.close();
        } catch (IOException e) {
            this.favicon = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        }
    }

    public BufferedImage getFavicon() {
        return favicon;
    }

    public void setFavicon(BufferedImage favicon) {
        this.favicon = favicon;
    }

    public JWAHandshake getHandshake() {
        return handshake;
    }

    public void setHandshake(JWAHandshake handshake) {
        this.handshake = handshake;
    }

    public void setWebInterface(String webInterface) {
        this.webInterface = webInterface;
    }

    public String getWebInterface() {
        return webInterface;
    }

    public void setDefaultScene(JWAScene scene) {
        this.defaultScene = scene;
    }

    public JWAScene getDefaultScene() {
        return defaultScene;
    }

    private boolean active = false;

    public boolean isActive() {
        return active;
    }

    public void start() {
        this.active = true;
        JWAUtilities.initThread(() -> {
            while(active) {
                try {
                    JWASocket client = new JWASocket(this, socket.accept());
                    JWAUtilities.initThread(() -> handshake.handshake(client));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void stop() {
        this.active = false;
        JWASocket lastClient = new JWASocket();
        lastClient.connect("127.0.0.1:" + port);
        lastClient.disconnect();
    }

}
