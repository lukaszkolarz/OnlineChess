package server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * The SrvThread class describes one thread which is connected with one host.
 */
public class SrvThread implements Runnable {

    private static Logger logger;
    private final Socket client;
    private BufferedReader in;
    private BufferedWriter out;
    private String name;
    private final ServerSingleton communicator;
    private SrvThread opponent = null;
    private ArrayList<String> names;
    private Integer namesSize;

    /**
     * constructor of the class
     * @param client connected ServerSocket
     */
    public SrvThread(Socket client) {
        logger = LogManager.getLogger("server");
        communicator = ServerSingleton.getSingleton();
        this.client = client;
        names = new ArrayList<String>();
        logger.info("New server thread created");
    }

    /**
     * override function to start thread
     */
    public void run() {
        logger.debug("New server thread started");
        streamInit();
        name = read();
        logger.info("Received username");
        communicator.addNewThread(this, name);
        String message;

        while (true) {
            message = read();
            try {
                if (message.equals("refresh")) {
                    sendAllNames();
                } else if (message.equals("connect")) {
                    sendInvitation();
                } else if (message.equals("accept")) {
                    accept();
                } else if (message.equals("refuse")) {
                    refuse();
                } else if (message.equals("end")) {
                    break;
                } else {
                    logger.error("Invalid input.");
                }
            } catch (NullPointerException e) {
                logger.fatal("Client is unavailable. Closing connection.");
                communicator.removeByName(this.name);
                break;
            }
        }
        try {
            client.close();
            in.close();
            out.close();
            logger.info("Connection closed.");
            Thread.currentThread().interrupt();
            logger.info("Thread closed.");
        } catch (IOException e) {
            logger.error("Cannot close connection");
        }
    }

    /**
     * receives invited opponent's name and starts communication between threads
     */
    private synchronized void sendInvitation(){
        String opponentName = read();
        try {
            if ((this.opponent = communicator.getThreadByName(opponentName)) != null) {
                this.opponent.setOpponent(this);
                this.opponent.send("new connection");
                this.opponent.send(this.name);
                logger.info("Invitation send");
            }
        } catch (java.lang.IndexOutOfBoundsException e) {
            this.send("not available");
            logger.error("Cannot reach. Host is unavailable");
        }
        }

    /**
     * accepts invitation
     */
    private synchronized void accept(){
            this.opponent.send("invitation accepted");
            this.opponent.invitationAccepted();
            logger.info("Invitation accepted");
        }

    /**
     * refuses invitation
     */
    private synchronized void refuse(){
            this.opponent.send("invitation refused");
            this.opponent.invitationRefused();
            logger.info("Invitation refused");
        }

    /**
     * initializes streams to connected socket
     */
    public void streamInit(){
        try{
            this.in = new BufferedReader((new InputStreamReader(client.getInputStream())));
            this.out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            logger.fatal("Init streams failed", e);
            System.exit(-1);
        }
    }

    /**
     * reads String from socket buffer
     * @return read String
     */
    public String read(){
        String line = null;
        try{
            line = in.readLine();
            logger.debug("Received String.");
        } catch (IOException e){
            logger.error("Read failed");
        }
        return line;
    }

    /**
     * sends String to socket buffer
     * @param line String which will be send
     */
    public void send(String line){
        try {
            out.write(line);
            out.newLine();
            out.flush();
        } catch(IOException e) {
            logger.fatal("Cannot reach client");
            communicator.removeByName(name);
            try {
                client.close();
                in.close();
                out.close();
                logger.info("Connection closed");
                Thread.currentThread().interrupt();
                logger.info("Thread closed");
            } catch (IOException io) {
                logger.fatal("Cannot close broken pipe. Turning off server.");
                System.exit(-1);
            }
        }
    }

    /**
     * get all connected hosts and sends their names
     */
    private void sendAllNames(){
        names = communicator.getAllNames();
        namesSize = names.size();
        send(namesSize.toString());
        for (int i=0; i<namesSize; i++){
            send(names.get(i));
        }
    }

    /**
     * sends back the invitation had been accepted
     */
    public synchronized void invitationAccepted(){
        if (this.opponent != null){
            this.send(this.opponent.getIp().substring(1));
            this.send("8123");
            communicator.removeByName(this.name);
            communicator.removeByName(opponent.name);
        }
    }

    /**
     * sends back that the invitation has been rejected
     */
    public synchronized void invitationRefused(){
        if (this.opponent!= null){
            this.opponent = null;
        }
    }

    /**
     * returns 'client' ip
     * @return ip address in String
     */
    public String getIp() {
        InetAddress ip = this.client.getInetAddress();
        return ip.toString();
    }

    /**
     * sets opponent field
     * @param opponent SrvThread object
     */
    public void setOpponent(SrvThread opponent){
        this.opponent = opponent;
    }
}
