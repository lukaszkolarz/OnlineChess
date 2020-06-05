package Game;

import client.Client;
import client.ClientServerSocket;
import client.PeerSocket;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Player implements ActionListener {
    private final static Logger logger = LogManager.getLogger("game");
    private final Client gameSocket;
    private String color;
    private JButton black, white;
    private JFrame colorWindow;

    public Player(Client gameSocket){
        this.gameSocket = gameSocket;
        chooseColor();
    }

    private void chooseColor(){
        if (gameSocket.getIsServer()){
            logger.debug("Peer is a server");
            chooseColorWindow();
        } else {
            logger.debug("Peer is a client");
            color = gameSocket.receiveString();
            logger.debug("Received color:" + color);
            JOptionPane.showMessageDialog(null, "Your color: " + color,
                    "COLOR", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void chooseColorWindow(){
        colorWindow = new JFrame();
        colorWindow.setTitle("Choose your color");
        colorWindow.setLayout(null);
        colorWindow.setSize(400, 130);
        colorWindow.setResizable(false);

        JLabel infoLabel = new JLabel("Please choose color you want to play with:");
        infoLabel.setBounds(20, 20, 200, 20);
        colorWindow.add(infoLabel);

        white = new JButton("WHITE");
        white.setBounds(100, 55, 100, 30);
        colorWindow.add(white);
        white.addActionListener(this);

        black = new JButton("BLACK");
        black.setBounds(200, 55, 100, 30);
        colorWindow.add(black);
        black.addActionListener(this);

        colorWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        colorWindow.setLocationRelativeTo(null);
        colorWindow.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == white){
            this.color = "White";
            gameSocket.send("Black");
            logger.debug("Chosen color " + color);
            colorWindow.setVisible(false);

        } else if (e.getSource() == black){
            this.color = "Black";
            gameSocket.send("White");
            logger.debug("Chosen color " + color);
            colorWindow.setVisible(false);
        }
    }
}
