package client;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * The Client.ListeningClient class creates other thread using to listen on the connected socket.
 * Thread synchronization mechanisms are implemented inside.
 */
public class ListeningClient extends Thread{

    private final static Logger logger = LogManager.getLogger("clientNetwork");
    private final BufferedReader in;
    private String message;
    private final Client client;
    private boolean haveMessage = false;

    /**
     * constructor
     * @param in connected socket reading buffer
     * @param client concrete Client
     */
    public ListeningClient(BufferedReader in, Client client){
        this.in = in;
        this.client = client;
        message = "";
    }

    /**
     * divides received packets to different actions
     */
    @Override
    public void run() {
        while (true) {
            String input;
            input = receiveString();
            try{
            if (input.equals("new connection")) {
                String peerName = receiveString();
                int response = JOptionPane.showConfirmDialog(null,
                        "Do you want to connect with " + peerName + "?",
                        "Checks", JOptionPane.YES_NO_OPTION);
                if (response == 0) {
                    this.client.accept(peerName);
                } else {
                    this.client.refuse(peerName);
                }
            } else {
                setMessage(input);
            }
        } catch (NullPointerException e){
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    /**
     * receives messages
     * @return received message in String
     */
    private String receiveString(){
        String line = null;
        try{
            line = in.readLine();
        } catch (IOException e){
            this.interrupt();
            logger.error("Read failed or closing socket");
        }
        return line;
    }

    /**
     * waits until there is no message
     * @return message
     */
    public synchronized String getMessage(){
        while(!this.haveMessage){
            try {
                wait();
            } catch (InterruptedException e) {
                logger.error("Cannot wait for get message", e);
            }
        }
        this.haveMessage = false;
        notifyAll();
        return this.message;
    }

    /**
     * waits until there is unread message
     * @param sms received message
     */
    public synchronized void setMessage(String sms){
        while(this.haveMessage){
            try {
                wait();
            } catch (InterruptedException e) {
                logger.error("Cannot wait for set if there is new message", e);
            }
        }
        this.haveMessage = true;
        this.message = sms;
        notifyAll();
    }
}
