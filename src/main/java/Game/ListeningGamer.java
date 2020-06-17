package Game;

import Engine.GUI.BoardPanel;
import client.Client;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;

public class ListeningGamer extends Thread{

    private final static Logger logger = LogManager.getLogger("clientNetwork");
    private final BufferedReader in;
    private String message;
    private boolean haveMessage = false;
    private final BoardPanel panel;

    /**
     * constructor
     * @param panel is actual game-panel
     */
    public ListeningGamer(BoardPanel panel, BufferedReader in){
        this.panel = panel;
        this.in = in;
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
                if (input.equals("new position")) {
                    String position = receiveString();
                    panel.receiveMessage(position);
                }
                else if(input.equals("Win")) {
                    panel.endOfGame("Win");
                }
                else {
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
            logger.debug("Received string on another thread");
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
