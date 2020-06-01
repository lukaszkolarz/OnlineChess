package Engine;

import Engine.Alliance;
import Engine.pieces.King;
import Engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WhitePlayer extends Player{

    public WhitePlayer(Game game)
    {
        super(Alliance.WHITE);
        this.Pieces=game.chessBoard.getWhitePieces();
        this.yourTurn=true;
        this.game=game;
        for(Piece king : Pieces)
        {
            if(king.getPieceX()==7 && king.getPieceY()==4)
                this.king=(King)king;
        }
        isFirst=true;
    }

    @Override
    public boolean isInCheck() {

        if(this.yourTurn)
        {
            List<Move> OpponentsMoves;
            List<Piece> opponentPieces;
            try{
                opponentPieces = game.chessBoard.getBlackPieces();
                for(Piece piece:opponentPieces)
                {
                    OpponentsMoves=piece.LegalMoves(game.chessBoard);
                    for(Move singleMove:OpponentsMoves)
                    {
                        if(this.king.getPieceX()==singleMove.getNextX() && this.king.getPieceY()==singleMove.getNextY())
                        {
                            this.isCheck=true;
                            System.out.println("Black player make check on you "+piece.toStringPieceType()+" x:"+piece.getPieceX()+" y:"+piece.getPieceY());
                            return true;
                        }
                    }
                }
            }
            catch(Exception e)
            {
                System.out.println("Can't get Black Pieces");
            }

        }
        return false;
    }
    @Override
    public Player getOpponent() {
        return game.getBlackPlayer();
    }
    @Override
    public void updatePieces()
    {
        this.Pieces=game.chessBoard.getWhitePieces();
    }
}
