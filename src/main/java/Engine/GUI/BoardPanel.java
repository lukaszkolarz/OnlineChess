package Engine.GUI;

import Engine.*;
import Engine.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel implements MouseListener {
    private List<FieldPanel> boardFields;
    private Game game_board;
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

    //Logic behind mouseClicked
    //1. get FieldPanel which we clicked and change his color to neutral
    //2. get List<Move> moves by take field which we clicked from chessBoard getPiece from that field and use method legal moves
    //3. in for loop iterate throught possible moves
    //4. from board we getField of possible move and if is occupied we change color to attack = RED
    //5. else we higlight field as green
    //6. if sourceField!=null we check if we click the same field if we do that we cancel the move and redrawBoard
    //7.otherwise we create move object and check if field which we clicked is a proper move from LegalMoves list
    //8. If it is we make a move and redraw whole board
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        //Get Coordinates
        int coordinateX=mouseEvent.getY()/75;
        int coordinateY=mouseEvent.getX()/75;
        System.out.println(coordinateX+" "+coordinateY);
        //First click sourceField==null
        if(MovingPlayer.getToken()) {
            if (sourceField == null) {
                //Check if it's first move
                if (game_board.getWhitePlayer().isItFirstMove()) {
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
                                    redrawBoard(game_board.chessBoard);
                                }

                            });
                            //We set piece to move as null , destination field as null and change the player who will now make a move and changing he's token
                            sourceField = null;
                            destinationField = null;
                            toMovePiece = null;
                            if (MovingPlayer.isItFirstMove()) {
                                MovingPlayer.setItsSecond();
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
}
