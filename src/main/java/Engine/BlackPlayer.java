package Engine;

import Engine.pieces.King;
import Engine.pieces.Piece;

import java.util.List;

public class BlackPlayer extends Player{


    public BlackPlayer(Game game)
    {
        super(Alliance.BLACK);
        this.Pieces=game.chessBoard.getBlackPieces();
        this.yourTurn=false;
        this.game=game;
        for(Piece king : Pieces)
        {
            if(king.getPieceX()==0 && king.getPieceY()==4)
                this.king=(King)king;
        }
        isFirst=false;
    }
   public boolean isInCheck()
   {
       if(this.yourTurn)
       {
           List<Move> OpponentsMoves;
           List<Piece> opponentPieces;
           try {
               opponentPieces = game.chessBoard.getWhitePieces();
               for(Piece piece:opponentPieces)
               {
                   OpponentsMoves=piece.LegalMoves(game.chessBoard);
                   for(Move singleMove:OpponentsMoves)
                   {
                       if(this.king.getPieceX()==singleMove.getNextX() && this.king.getPieceY()==singleMove.getNextY())
                       {
                           this.isCheck=true;
                           System.out.println("White player make check on you "+piece.toStringPieceType()+" x:"+piece.getPieceX()+" y:"+piece.getPieceY());
                           return true;
                       }
                   }
               }
           }
           catch(Exception e)
           {
               System.out.println("Can't get White Pieces");
           }

       }
       return false;
   }
  public boolean isInCheckMate()
   {
       if(this.yourTurn && this.isInCheck())
       {
           if(this.king.LegalMoves(game.chessBoard).isEmpty())
           {
               //YOU LOSE
           }
       }
       return false;
   }

    @Override
    public Player getOpponent() {
        return game.getWhitePlayer();
    }
    @Override
    public void updatePieces()
    {
        this.Pieces=game.chessBoard.getBlackPieces();
    }
}
