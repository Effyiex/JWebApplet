package de.prplx.jwa.connection;

import de.prplx.jwa.utilities.JWALogger;

import javax.imageio.ImageIO;
import java.io.IOException;

public interface JWAHandshake {

    JWAHandshake DEFAULT = client -> {

        String[] requestTokens = new String(client.read()).split(" ");
        if(requestTokens.length > 2 && requestTokens[0].equals("GET")) {

            String request = requestTokens[1];
            JWALogger.INFO.log("Web-Request - " + request);
            if(request.equals("/")) {
                client.write(client.applet.getWebInterface().getBytes());
                client.flush();
            } else if(request.equals("/fetch-applet-favicon")) {
                try {
                    ImageIO.write(client.applet.getFavicon(), "PNG", client.socket.getOutputStream());
                } catch (IOException e) {
                    // ignore
                }
                client.flush();
            } else if(request.equals("/generate-applet-token")) {
                client.write(client.applet.instances.register().token.getBytes());
                client.flush();
            } else if(request.startsWith("/render-applet")) {
                String[] args = request.substring(1).split("/");
                if(args.length < 4) {
                    client.write("ERROR: Wrong Syntax -> render-applet/<applet-token>/<width>/<height>".getBytes());
                    client.flush();
                } else {
                    JWASession session = client.applet.instances.get(args[1]);
                    if(session != null) {
                        session.renderSession(Integer.parseInt(args[2]), Integer.parseInt(args[3]));
                        try {
                            ImageIO.write(session.getScreen(), "JPG", client.socket.getOutputStream());
                        } catch (IOException e) {
                            // ignore
                        }
                        client.flush();
                    }
                }
            } else if(request.startsWith("/update-pointer")) {
                String[] args = request.substring(1).split("/");
                if(args.length < 5) {
                    client.write("ERROR: Wrong Syntax -> update-pointer/<applet-token>/<x>/<y>/<state>".getBytes());
                    client.flush();
                } else {
                    JWASession session = client.applet.instances.get(args[1]);
                    if(session != null) {
                        session.updatePointer(Integer.parseInt(args[2]), Integer.parseInt(args[3]), args[4].equals("pressed"));
                        client.write("Successfully updated the pointer.".getBytes());
                        client.flush();
                    }
                }
            } else if(request.startsWith("/fetch-applet-title")) {
                String[] args = request.substring(1).split("/");
                if(args.length < 2) {
                    client.write("ERROR: Wrong Syntax -> fetch-applet-title/<applet-token>".getBytes());
                    client.flush();
                } else {
                    JWASession session = client.applet.instances.get(args[1]);
                    if(session != null) {
                        client.write(session.getScene().label.getBytes());
                        client.flush();
                    }
                }
            }

        }

        client.disconnect();

    };

    void handshake(JWASocket client);

}
