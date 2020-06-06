package client;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The CLiSocket class creates a  client  to get another host from server and create
 * socket to be used in p2p communication
 */
public class CliSocket extends PeerSocket {
    private final static Logger logger = LogManager.getLogger("clientNetwork");
    private final int srvPort;
    private final String srvIP;

    /**
     * constructor
     * @param srvIP server ip
     * @param srvPort port where server is listening
     */
    public CliSocket(String srvIP, int srvPort){
        this.srvPort = srvPort;
        this.srvIP = srvIP;
    }

    /**
     * creates socket
     */
    public void newClientSocket() throws IOException {
        client = new Socket(srvIP, srvPort);
        client.setReuseAddress(true);
        out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        logger.info("Associating new connection with server");
    }

    /**
     * receives String from socket receiving buffer
     * @return received buffer as a String
     */
    public String receiveString(){
        String line = null;
        try{
            line  = in.readLine();
            logger.debug("Receiving string");
        } catch (IOException e) {
            logger.error("Read failed");
        }
        return line;
    }

    /**
     * puts String to sending buffer
     * @param message will be put to sending buffer
     */
    public void sendString(String message) {
        try{
            logger.debug("Sending string");
            out.write(message);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Server is no longer available",
                    "ERROR - no connection", JOptionPane.ERROR_MESSAGE);
            logger.fatal("Server no longer available");
            System.exit(-1);
        }
    }

    /**
     * @return input stream
     */
    public BufferedReader getIn(){ return this.in; }

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
            logger.info("Client-client socket closed");
        } catch (IOException e){
            logger.error("Client-client socket cannot be closed");
        }

    }
}
