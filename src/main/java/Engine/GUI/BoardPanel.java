package Engine.GUI;

import Engine.*;
import Engine.pieces.Piece;
import client.CliSocket;
import client.Client;
import client.ClientServerSocket;
import client.PeerSocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel implements MouseListener {
    private List<FieldPanel> boardFields;
    private Game game_board;
    private PeerSocket NetworkPlayer;
    private Player LocalPlayer;
    private Player MovingPlayer;
    private Field sourceField;
    private Field destinationField;
    private Piece toMovePiece;
    private List<Move> moves;

    public BoardPanel()
    {
        super(new GridLayout(8,8));
        this.game_board=new Game();
        this.boardFields=new ArrayList<FieldPanel>();
        for(int i=1;i<=8;i++)
        {
            for(int j=1;j<=8;j++)
            {
                FieldPanel fieldPanel=new FieldPanel(i,j,this.game_board.chessBoard);
                this.add(fieldPanel);
                this.boardFields.add(fieldPanel);
            }
        }
        this.addMouseListener(this);
        MovingPlayer=game_board.getWhitePlayer();
    }

    private void redrawBoard(Board chessBoard) {
        removeAll();
        for(FieldPanel fieldPan:boardFields)
        {
            fieldPan.drawField(chessBoard);
            add(fieldPan);
        }
        validate();
        repaint();
    }
    public Game getGame()
    {
        return this.game_board;
    }
    public void setSock(CliSocket gameSocket)
    {
        this.NetworkPlayer=gameSocket;
    }

    public void setSock(ClientServerSocket gameSocket)
    {
        this.NetworkPlayer=gameSocket;
    }

    public void setLocalPlayer(Player local)
    {
        this.LocalPlayer=local;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        if (checkInstance()) {
            return;
        } else {
            int coordinateX = mouseEvent.getY() / 75;
            int coordinateY = mouseEvent.getX() / 75;
            System.out.println(coordinateX + " " + coordinateY);
            if (sourceField == null) {
                //Check if it's first move
                if (game_board.getWhitePlayer().isItFirstMove() && LocalPlayer instanceof WhitePlayer) {
                    //Change token that white player moves and black player can't
                    MovingPlayer = game_board.getWhitePlayer();
                    MovingPlayer.changeToken(true);
                    game_board.getBlackPlayer().changeToken(false);

                    sourceField = game_board.chessBoard.getField(coordinateX, coordinateY);
                    toMovePiece = sourceField.getPiece();
                    //if you clicked on other alliance piece or field without piece then start again
                    if (toMovePiece == null || toMovePiece.getAlliance() != Alliance.WHITE) {
                        sourceField = null;
                        toMovePiece = null;
                    }
                    //else print out and highlight legal moves if field is occupied highlight in color red otherwise highlight in color green
                    else {
                        FieldPanel ToMoveElement = ((FieldPanel) this.getComponent(8 * (coordinateX) + coordinateY));
                        ToMoveElement.changeFieldColorToNeutral();
                        this.moves = toMovePiece.LegalMoves(game_board.chessBoard);
                        for (Move simple : moves) {
                            System.out.println("Move x:" + simple.getNextX() + " y:" + simple.getNextY() + " piece position:" + simple.getPiece().toStringPieceType() + " x:" + simple.getPiece().getPieceX() + " y:" + simple.getPiece().getPieceY());
                            if (game_board.chessBoard.getField(simple.getNextX(), simple.getNextY()).isFieldOccupied())
                                ((FieldPanel) this.getComponent(8 * (simple.getNextX()) + simple.getNextY())).changeFieldColorToAttack();
                            else
                                ((FieldPanel) this.getComponent(8 * (simple.getNextX()) + simple.getNextY())).changeFieldColorToNeutral();
                        }
                    }
                }
                //If it's not the first move
                else {
                    //get piece from clicked field
                    sourceField = game_board.chessBoard.getField(coordinateX, coordinateY);
                    toMovePiece = sourceField.getPiece();


                    //if clicked piece is not the  same alliance as player or you didn't clicked on piece reset sourceField and piece to move
                    if (toMovePiece == null || toMovePiece.getAlliance() != MovingPlayer.getPlayerAlliance()) {
                        sourceField = null;
                        toMovePiece = null;
                    }
                    //Else highlight moves as earlier in else have to put statemant with is player in check or checkmate
                    else {
                        FieldPanel ToMoveElement = ((FieldPanel) this.getComponent(8 * (coordinateX) + coordinateY));
                        ToMoveElement.changeFieldColorToNeutral();
                        this.moves = toMovePiece.LegalMoves(game_board.chessBoard);
                        for (Move simple : moves) {
                            System.out.println("Move x:" + simple.getNextX() + " y:" + simple.getNextY() + " piece position:" + simple.getPiece().toStringPieceType() + " x:" + simple.getPiece().getPieceX() + " y:" + simple.getPiece().getPieceY());
                            if (game_board.chessBoard.getField(simple.getNextX(), simple.getNextY()).isFieldOccupied())
                                ((FieldPanel) this.getComponent(8 * (simple.getNextX()) + simple.getNextY())).changeFieldColorToAttack();
                            else
                                ((FieldPanel) this.getComponent(8 * (simple.getNextX()) + simple.getNextY())).changeFieldColorToNeutral();
                        }
                    }
                }

            }
            //Second click
            //If we already set sourceField and piece to move
            else {
                coordinateX = mouseEvent.getY() / 75;
                coordinateY = mouseEvent.getX() / 75;
                System.out.println(coordinateX + " " + coordinateY);
                //If we clicked on same field as in the first click just reset board
                if (sourceField.getX() == coordinateX && sourceField.getY() == coordinateY) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            redrawBoard(game_board.chessBoard);
                        }

                    });
                    sourceField = null;
                    destinationField = null;
                    toMovePiece = null;

                } else {
                    //If we clicked on other field
                    destinationField = game_board.chessBoard.getField(coordinateX, coordinateY);
                    Move destinationMove = new Move(game_board.chessBoard, toMovePiece, destinationField);
                    // List<Move> moves = toMovePiece.LegalMoves(game_board.chessBoard);
                    System.out.println("Move we choose x:" + destinationMove.getNextX() + " y:" + destinationMove.getNextY());
                    for (Move sample : this.moves) {
                        System.out.println("Move x:" + sample.getNextX() + " y:" + sample.getNextY() + " piece position:" + sample.getPiece().toStringPieceType() + " x:" + sample.getPiece().getPieceX() + " y:" + sample.getPiece().getPieceY());
                        System.out.println("Move is: " + sample.equals(destinationMove));
                    }
                    //Here we check if move is one of the valid ones and then if it is redraw board and update pawns
                    for (Move destination : this.moves) {
                        if (destination.equals(destinationMove)) {
                            destinationMove.updateBoard();
                            game_board.getWhitePlayer().updatePieces();
                            game_board.getBlackPlayer().updatePieces();
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("Redrawing board ");
                                    redrawBoard(game_board.chessBoard);
                                }

                            });
                            StringBuilder s = new StringBuilder();
                            s.append("x").append(sourceField.getX()).append("y").append(sourceField.getY())
                                    .append("x").append(destinationField.getX()).append("y").append(destinationField.getY());
                            System.out.println("Message: " + s.toString());
                            //We set piece to move as null , destination field as null and change the player who will now make a move and changing he's token
                            NetworkPlayer.sendString("new position");
                            NetworkPlayer.sendString(s.toString());
                            sourceField = null;
                            destinationField = null;
                            toMovePiece = null;
                            if (MovingPlayer.isItFirstMove()) {
                                MovingPlayer.setItsSecond();
                                //  NetworkPlayer.send("false");
                            }
                            MovingPlayer.changeToken(false);
                            MovingPlayer = MovingPlayer.getOpponent();
                            MovingPlayer.changeToken(true);

                            break;
                        }

                    }
                    if (sourceField != null && destinationField != null) {
                        destinationField = null;
                    }
                }
            }
        }
    }

    public synchronized void receiveMessage(String frame)
    {

        char[] message=frame.toCharArray();
        System.out.println("Received message: "+message[0]+" "+message[1]+" "+message[2]+" "+message[3]);
        if(message.length==8) {
            sourceField = game_board.chessBoard.getField(Character.getNumericValue(message[1]), Character.getNumericValue(message[3]));
            destinationField = game_board.chessBoard.getField(Character.getNumericValue(message[5]), Character.getNumericValue(message[7]));
            toMovePiece = sourceField.getPiece();
            Move destinationMove = new Move(game_board.chessBoard, toMovePiece, destinationField);
            destinationMove.updateBoard();
            game_board.getWhitePlayer().updatePieces();
            game_board.getBlackPlayer().updatePieces();
            SwingUtilities.invokeLater(new Runnable(){
                @Override
                public void run() {
                    redrawBoard(game_board.chessBoard);
                }

            });
            ResetWholeMove();
        }
        else
        {
            game_board.getWhitePlayer().setItsSecond();
        }
        MovingPlayer = MovingPlayer.getOpponent();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
    void ResetSource()
    {
        this.sourceField=null;
    }
    void ResetSourceAndPiece()
    {
        this.sourceField=null;
        this.toMovePiece=null;
    }
    void ResetWholeMove()
    {
        this.sourceField=null;
        this.destinationField=null;
        this.toMovePiece=null;
    }

    public boolean checkInstance(){
        return (MovingPlayer instanceof WhitePlayer && LocalPlayer instanceof BlackPlayer) ||
                (MovingPlayer instanceof BlackPlayer && LocalPlayer instanceof WhitePlayer);
    }

}
