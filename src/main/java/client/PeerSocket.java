package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public abstract class PeerSocket {
    protected BufferedWriter out;
    protected BufferedReader in;
    protected Socket client;

    public abstract void closeSocket();
    public abstract void sendString(String message);
    public abstract String receiveString();
    public BufferedReader getInputStream(){
        return in;
    }
}
