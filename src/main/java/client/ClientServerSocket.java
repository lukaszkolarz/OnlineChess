package client;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The ServerSocket implement server socket in ONE of two hosts which
 * want to connect i p2p way.
 */
public class ClientServerSocket extends PeerSocket {

    private static final Logger logger = LogManager.getLogger("clientNetwork");
    private ServerSocket server;
    int port;

    /**
     * constructor
     * @param port on this port client will listen
     */
    public ClientServerSocket(String port){
        this.port = Integer.parseInt(port);
        try {
            server = new ServerSocket(this.port);
            server.setReuseAddress(true);
            logger.info("Creating new Client-Server socket");
        } catch (IOException e) {
            logger.error("Cannot listen on port: " + port);
        }
    }

    /**
     * accepting connection
     */
    public void acceptConnection() {
        try {
            client = server.accept();
            logger.info("Peer connection accepted by server");
        } catch (IOException e) {
            logger.error("Accept failed");
        }
    }

    /**
     * initializes sockets reading and writing buffer
     */
    public void initSocket(){
        try {
            in = new BufferedReader((new InputStreamReader(client.getInputStream())));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            logger.debug("Initializing writing and reading buffer");
        } catch (IOException e) {
            logger.fatal("Read failed!");
            System.exit(-1);
        }
    }

    /**
     * receives String from socket receiving buffer
     * @return received buffer as a String
     */
        public String receiveString(){
            String line = null;
            try{
                line = in.readLine();
                logger.debug("Receiving string from peer");
            } catch (IOException e){
                logger.error("Read failed");
            }
            return line;
        }

    /**
     * puts String to sending buffer
     * @param line will be put to sending buffer
     */
    public void sendString(String line){
        try {
            logger.debug("Sending string to peer");
            out.write(line);
            out.newLine();
            out.flush();
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Opponent is no longer available",
                    "ERROR - no connection", JOptionPane.ERROR_MESSAGE);
            logger.fatal("Server no longer available");
            System.exit(-1);
        }
    }

    /**
     * @return stream to send objects
     * @throws IOException - if an I/O error occurs when creating the output stream or if the socket is not connected
     */
    public ObjectOutputStream getObjectOut() throws IOException {
        return new ObjectOutputStream(client.getOutputStream());
    }

    /**
     * @return stream to receive objects
     * @throws IOException - if an I/O error occurs when creating the output stream or if the socket is not connected
     */
    public ObjectInputStream getObjectIn() throws IOException {
        return new ObjectInputStream(client.getInputStream());
    }

    public void closeSocket() {
        try {
            client.close();
            in.close();
            out.close();
            logger.info("Client-server socket closed");
        } catch (IOException e){
            logger.error("Client-server socket cannot be closed");
        }

    }
}
