package client;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;


/**
 * The Client is a class to managing connection with server ant choosing perr to connect with.
 * Client manage also connection p2p.
 */
public class Client implements ActionListener {

    private final static Logger logger = LogManager.getLogger("clientNetwork");
    private String name, opponentName;
    private String serverAddress;
    private int serverPort;
    private Integer hostsNamesAmount;
    private CliSocket client, clientClientSocket;
    private JButton connectButton, insertNameButton, refreshButton, connectHostButton;
    private JFrame connectionWindow, insertNameWindow, chooseHostWindow;
    private JTextField serverAddressInput, serverPortInput, nameInput;
    private DefaultListModel model;
    private JList list;
    private ListeningClient listener;
    private ClientServerSocket clientServerSocket;
    private String  clientServerPort = "8123";
    private boolean isServer, isReady=true;

    /**
     * constructor
     */
    public Client(){
        displayInitWindow();
    }

    /**
     * refreshes list of users connected with server in the moment
     */
    private void updateHostNames(){
        client.sendString("refresh");
        hostsNamesAmount = Integer.parseInt(listener.getMessage());
        model.removeAllElements();
        for (int i=0; i<hostsNamesAmount; i++){
            String temp = listener.getMessage();
            if (!temp.equals(name)) {
                model.addElement(temp);
            }
        }
        logger.info("Updating all hosts' names");
    }

    /**
     * GUI - inserting connection data
     */
    private void displayInitWindow(){
        connectionWindow = new JFrame();                                            //define connection window
        connectionWindow.setTitle("Connection with server");
        connectionWindow.setLayout(null);
        connectionWindow.setSize(300,130);
        connectionWindow.setResizable(false);

        JLabel serverAddressLabel = new JLabel("Server address:");             //add IP label
        serverAddressLabel.setBounds(20,20,130,20);              //older reshape()
        connectionWindow.add(serverAddressLabel);

        serverAddressInput = new JTextField("");                                    //add IP textfield
        serverAddressInput.setBounds(120,20,155,20);
        connectionWindow.add(serverAddressInput);


        JLabel serverPortLabel = new JLabel("Server port:");                   //add port label
        serverPortLabel.setBounds(20,60,130,20);
        connectionWindow.add(serverPortLabel);

        serverPortInput = new JTextField("");                                       //add port textfield
        serverPortInput.setBounds(95,60,60,20);
        connectionWindow.add(serverPortInput);

        connectButton = new JButton("Connect");                                //add connect button
        connectButton.setBounds(170,55,100,30);
        connectionWindow.add(connectButton);
        connectButton.addActionListener(this);                                   //set button handler

        connectionWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            //display connection window
        connectionWindow.setLocationRelativeTo(null);
        connectionWindow.setVisible(true);
    }

    /**
     * GUI - inserting username
     */
    private void insertName(){
        insertNameWindow = new JFrame();
        insertNameWindow.setTitle("Connection with server");
        insertNameWindow.setLayout(null);
        insertNameWindow.setSize(300, 130);
        insertNameWindow.setResizable(false);

        JLabel nameLabel = new JLabel("Insert your name:");
        nameLabel.setBounds(20, 20, 130, 20);
        insertNameWindow.add(nameLabel);

        nameInput = new JTextField("");
        nameInput.setBounds(130, 20, 155, 20);
        insertNameWindow.add(nameInput);

        insertNameButton = new JButton("OK");
        insertNameButton.setBounds(100, 55, 100, 30);
        insertNameWindow.add(insertNameButton);
        insertNameButton.addActionListener(this);

        insertNameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        insertNameWindow.setLocationRelativeTo(null);
        insertNameWindow.setVisible(true);
    }

    /**
     * GUI - choosing right host
     */
    public void chooseHost(){

        chooseHostWindow = new JFrame();
        chooseHostWindow.setTitle("Choosing host");
        chooseHostWindow.setLayout(null);
        chooseHostWindow.setSize(250, 320);
        chooseHostWindow.setResizable(false);

        JLabel hostListLabel = new JLabel("Available hosts:");
        hostListLabel.setBounds(20, 15, 200, 20);
        chooseHostWindow.add(hostListLabel);

        model = new DefaultListModel();
        list = new JList(model);

        updateHostNames();

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setBounds(20, 40, 200, 200);
        chooseHostWindow.add(listScroller);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(20, 240, 100, 30);
        chooseHostWindow.add(refreshButton);
        refreshButton.addActionListener(this);

        connectHostButton = new JButton("Connect");
        connectHostButton.setBounds(130, 240, 100, 30);
        chooseHostWindow.add(connectHostButton);
        connectHostButton.addActionListener(this);

        chooseHostWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chooseHostWindow.setLocationRelativeTo(null);
        chooseHostWindow.setVisible(true);
    }

    /**
     * send a message with p2p connection request to server
     * @param opponentName concrete user, who is asked for connection
     */
    private void assignConnection(String opponentName){
        client.sendString("connect");
        client.sendString(opponentName);
        String message = "";
        message = listener.getMessage();
        if (message.equals("invitation accepted")) {
            logger.info("Connection accepted by opponent");
            String ip = listener.getMessage();
            String port = listener.getMessage();
            clientClientSocket = new CliSocket(ip, Integer.parseInt(port));
            chooseHostWindow.setVisible(false);
            initSocket();
        } else if (message.equals("invitation refused")){
            logger.info("Invitation refused by opponent");
            JOptionPane.showMessageDialog(null, "Invitation refused by the user",
                    "WARNING", JOptionPane.WARNING_MESSAGE);
            list.clearSelection();
            updateHostNames();
        } else if (message.equals("not available")){
            JOptionPane.showMessageDialog(null, "Host is no longer available",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            list.clearSelection();
            updateHostNames();
        }
    }

    /**
     * invoke when the action occurs
     * @param e action
     */
    @Override
    public void actionPerformed(ActionEvent e) {                                    //buton handler
        if (e.getSource() == connectButton) {
            if (serverAddressInput.getText().length() == 0 || serverPortInput.getText().length() == 0) {
                logger.warn("Unable to read server address - field is empty");
                JOptionPane.showMessageDialog(null, "Please fill all the fields",
                        "WARNING", JOptionPane.WARNING_MESSAGE);
            } else {
                serverAddress = serverAddressInput.getText().replaceAll(" ", "");
                try {
                    serverPort = Integer.parseInt(serverPortInput.getText().replaceAll(" ", ""));
                } catch (NumberFormatException numberFormatException) {
                    logger.error("Port number NaN");
                    JOptionPane.showMessageDialog(null, "Port number requires a natural number value",
                            "NaN", JOptionPane.ERROR_MESSAGE);
                    serverPortInput.setText("");
                    return;
                }

                client = new CliSocket(serverAddress, serverPort);
                try {
                    client.newClientSocket();
                    connectionWindow.setVisible(false);
                    insertName();
                } catch (IOException ioException) {
                    logger.error("No server available on: " + serverAddress + ":" + serverPort);
                    JOptionPane.showMessageDialog(null, "No server available on this ip",
                            "NO SERVER", JOptionPane.ERROR_MESSAGE);
                serverAddressInput.setText("");
                serverPortInput.setText("");
                }
            }
        }
        else if (e.getSource() == insertNameButton){
            if (nameInput.getText().length() == 0){
                logger.warn("Unable to read user name - field is empty");
                JOptionPane.showMessageDialog(null, "Please insert your name",
                        "WARNING", JOptionPane.WARNING_MESSAGE);
            } else {
                name = nameInput.getText().replaceAll(" ", "");
                client.sendString(name);
                insertNameWindow.setVisible(false);
                listener = new ListeningClient(client.getIn(), this);
                logger.info("Name inserted");
                listener.start();
                chooseHost();
            }
        } else if (e.getSource() == refreshButton){
            updateHostNames();
        } else if (e.getSource() == connectHostButton){
            assignConnection(list.getSelectedValue().toString());
        }
    }

    /**
     * accepts asking for connection
     * @param name peer username
     */
    public void accept(String name){
        opponentName = name;
        client.sendString("accept");
        logger.info("Accepting connection");
        this.clientServerSocket = new ClientServerSocket(clientServerPort);
        clientServerSocket.acceptConnection();
        chooseHostWindow.setVisible(false);
        initSocket();
    }

    /**
     * refuses asking for connection
     * @param name peer username
     */
    public void refuse(String name){
        opponentName = name;
        logger.info("Refusing connection");
        client.sendString("refuse");
        list.clearSelection();
    }

    /**
     * initializes p2p socket
     * depends on situation it will be CliSocket or ClientServerSocket
     */
    public synchronized void initSocket(){
        client.sendString("end");
        client.closeSocket();
        logger.info("Connection with server is closed");

        if (clientClientSocket == null){
            clientServerSocket.initSocket();
            isServer = true;
            isReady = false;
            notify();
            send("connected with peer");
            logger.info("First received message: " + receiveString());
        } else if (clientServerSocket == null){
            try {
                clientClientSocket.newClientSocket();
            }
            catch (IOException e){
                logger.fatal("Cannot create client socket");
                System.exit(-1);
            }
            isServer = false;
            isReady = false;
            notify();
            logger.info("First received message: " + receiveString());
            send("connected with peer");
        }   else {
            JOptionPane.showMessageDialog(null,
                    "Cannot connect with peer",
                    "OK",JOptionPane.INFORMATION_MESSAGE);
            logger.fatal("Cannot connect with peer");
            System.exit(-1);
        }
        logger.debug("Is server: " + isServer);
    }

    /**
     * sends String to peer
     * @param data String which will be write
     */
    public void send(String data){
        waiting();
        if (isServer){
            clientServerSocket.sendString(data);
        }else{
            clientClientSocket.sendString(data);
        }
    }

    /**
     * receiving String from peer
     * @return received String
     */
    public String receiveString(){
        waiting();
        if (isServer){
            return clientServerSocket.receiveString();
        }else{
            return clientClientSocket.receiveString();
        }
    }

    /**
     * lets receiveFromHost and sendToHost wait for initialize p2p connection
     */
    private synchronized void waiting() {
        while (isReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                logger.error("Error in waiting", e);
            }
        }
    }

    public void closePeerSocket(){
        if (clientClientSocket == null){
            try {
                clientServerSocket.closeSocket();
                logger.info("Client-client connection is closed");
            } catch (NullPointerException e) {
                logger.error("No socket to close");
            }
        } else if (clientServerSocket == null){
            try {
                clientClientSocket.closeSocket();
                logger.info("Client-client connection is closed");
            }
            catch (NullPointerException e){
                logger.error("No socket to close");
            }
        }
        System.exit(0);
    }

    public boolean getIsServer(){
        return isServer;
    }


    /**
     * Additional object send functions
     */
    public void processData(Object message) throws IOException{

    }
}
