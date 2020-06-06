package Game;

import client.Client;
import client.PeerSocket;

public class Main {

    public static void main(String[] args) {
        Client client = new Client();
        client.send("Hello peer");      //helps with synchronization
        client.receiveString();
        Player player = new Player(client);
    }
}