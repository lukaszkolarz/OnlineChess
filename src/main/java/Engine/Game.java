package Engine;

import Engine.pieces.*;

import static Engine.Alliance.BLACK;
import static Engine.Alliance.WHITE;

public class Game {
    private WhitePlayer Player1;
    private BlackPlayer Player2;
    public Board chessBoard;
    private boolean isCheckMate;
    public Game()
    {
        this.chessBoard=new Board.Builder().setWhitePiece(new Rook(7,0,WHITE))
                                           .setWhitePiece(new Knight(7,1,WHITE))
                                           .setWhitePiece(new Bishop(7,2,WHITE))
                                           .setWhitePiece(new Queen(7,3,WHITE))
                                           .setWhitePiece(new King(7,4,WHITE))
                                           .setWhitePiece(new Bishop(7,5,WHITE))
                                           .setWhitePiece(new Knight(7,6,WHITE))
                                           .setWhitePiece(new Rook(7,7,WHITE))
                                           .setWhitePiece(new Pawn(6,0,WHITE))
                                           .setWhitePiece(new Pawn(6,1,WHITE))
                                           .setWhitePiece(new Pawn(6,2,WHITE))
                                           .setWhitePiece(new Pawn(6,3,WHITE))
                                           .setWhitePiece(new Pawn(6,4,WHITE))
                                           .setWhitePiece(new Pawn(6,5,WHITE))
                                           .setWhitePiece(new Pawn(6,6,WHITE))
                                           .setWhitePiece(new Pawn(6,7,WHITE))
                                           .setBlackPiece(new Rook(0,0,BLACK))
                                           .setBlackPiece(new Knight(0,1,BLACK))
                                           .setBlackPiece(new Bishop(0,2,BLACK))
                                           .setBlackPiece(new Queen(0,3,BLACK))
                                           .setBlackPiece(new King(0,4,BLACK))
                                           .setBlackPiece(new Bishop(0,5,BLACK))
                                           .setBlackPiece(new Knight(0,6,BLACK))
                                           .setBlackPiece(new Rook(0,7,BLACK))
                                           .setBlackPiece(new Pawn(1,0,BLACK))
                                           .setBlackPiece(new Pawn(1,1,BLACK))
                                           .setBlackPiece(new Pawn(1,2,BLACK))
                                           .setBlackPiece(new Pawn(1,3,BLACK))
                                           .setBlackPiece(new Pawn(1,4,BLACK))
                                           .setBlackPiece(new Pawn(1,5,BLACK))
                                           .setBlackPiece(new Pawn(1,6,BLACK))
                                           .setBlackPiece(new Pawn(1,7,BLACK))
                                           .build();

        this.chessBoard.CheckIfFieldsHaveProperCoordinates();
        this.Player1=new WhitePlayer(this);
        this.Player2=new BlackPlayer(this);
        this.isCheckMate=false;

    }
    public WhitePlayer getWhitePlayer()
    {
        return this.Player1;
    }
    public BlackPlayer getBlackPlayer()
    {
        return this.Player2;
    }
}
