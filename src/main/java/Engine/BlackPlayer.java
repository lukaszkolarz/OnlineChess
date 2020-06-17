package Engine;

import Engine.pieces.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;

public class BlackPlayer extends Player{

    private final static Logger logger = LogManager.getLogger("game");

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
                           logger.debug("White player make check on you "+piece.toStringPieceType()+" x:" +
                                   piece.getPieceX() + " y:"+piece.getPieceY());
                           return true;
                       }
                   }
               }
           }
           catch(Exception e)
           {
               logger.error("Can't get White Pieces");
           }


       return false;
   }
  public boolean isInCheckMate()
   {
       if(this.isInCheck())
       {
           if(this.king.LegalMoves(game.chessBoard).isEmpty())
           {
               return true;
           }
       }
       return false;
   }

    @Override
    public Player getOpponent() {
        return game.getWhitePlayer();
    }

    @Override
    public List<Move> haveEscapeMoves(Board game_board) {
        List<Move> attackingMoves=countKingAttacks(game_board);
        List<Move> escapeMoves=new ArrayList<>();
        if(attackingMoves.size()==1)
        {
            Move attack=attackingMoves.get(0);
            List<Move> attacks=canBeAttacked(game_board,game_board.getField(attack.getPiece().getPieceX(), attack.getPiece().getPieceY()));
            List<Move> kingMoves=king.LegalMoves(game_board);
            List<Move> protectKingMoves=kingProtectMoves(game_board);
            if(attacks.size()!=0)
            {
                logger.debug("Attack moves: ");
                print(attacks);
                escapeMoves.addAll(attacks);
            }
            if(kingMoves.size()!=0)
            {
                logger.debug("King moves: ");
                print(kingMoves);
                escapeMoves.addAll(kingMoves);
            }
            if(protectKingMoves.size()!=0)
            {
                logger.debug("King protect moves: ");
                print(protectKingMoves);
                escapeMoves.addAll(protectKingMoves);
            }
        }
        if(attackingMoves.size()>=2)
        {
            if(king.LegalMoves(game_board).size()!=0)
            {
                escapeMoves.addAll(king.LegalMoves(game_board));
            }
        }

        return escapeMoves;
    }
    @Override
    public List<Piece> kingAttackingPieces(Board game_board)
    {
        List<Piece> opponentPieces=this.getOpponent().getPieces();
        List<Piece> kingAttackers=new ArrayList<>();
        for(Piece opponent:opponentPieces)
        {
            List<Move> opponentMoves=opponent.LegalMoves(game_board);
            if(opponentMoves.stream().anyMatch(move-> move.getNextX()==king.getPieceX() && move.getNextY()==king.getPieceY()))
            {
                kingAttackers.add(opponent);
            }
        }
        return kingAttackers;
    }
    @Override
    public List<Move> kingProtectMoves(Board game_board)
    {
        List<Piece> attackingKingPieces=kingAttackingPieces(game_board);
        if(attackingKingPieces.size()>=2)
            return Collections.emptyList();
        if(attackingKingPieces.size()==1)
        {
            Piece attackingPiece=attackingKingPieces.get(0);
            if(attackingPiece instanceof Rook || attackingPiece instanceof Queen || attackingPiece instanceof Bishop) {
                List<Move> safeMoves=lookForSafeMoves(game_board,king,attackingPiece);
                if(safeMoves.size()!=0) {
                    return safeMoves;
                }
            }
            else {
                return Collections.emptyList();
            }
        }
        return Collections.emptyList();
    }
    @Override
    public List<Move> lookForSafeMoves(Board game_board,Piece toProtect,Piece attacking)
    {
        List<Piece> pieces;
        List<Move> safeMoves=new ArrayList<>();
        if(toProtect.getAlliance()==Alliance.WHITE)
        {
            pieces=game_board.getWhitePieces();
        }
        else
        {
            pieces=game_board.getBlackPieces();
        }
        List<Move> attackMoves=attacking.LegalMoves(game_board);
        List<Point> protectFields=getProtectFields(king.getPieceX(),king.getPieceY(),attacking.getPieceX(),attacking.getPieceY());
        for(Point p1: protectFields) {
            for (Piece protector : pieces) {
                if (protector instanceof King) {
                    continue;
                } else {
                    List<Move> moves = protector.LegalMoves(game_board);
                    safeMoves.addAll(moves.stream().filter(protectMove -> protectMove.getNextX() == (int)p1.getX() && protectMove.getNextY() == (int)p1.getY()).collect(collectingAndThen(Collectors.toList(), Collections::unmodifiableList)));
                }
            }
        }
        return safeMoves;
    }

    @Override
    public List<Move> countKingAttacks(Board game_board) {

        List<Piece> opponentPieces=this.getOpponent().getPieces();
        List<Move>  attackMoves=new ArrayList<>();
        for(Piece opponent: opponentPieces)
        {
            List<Move> moves=opponent.LegalMoves(game_board);
            attackMoves.addAll(moves.stream()
                    .filter(move-> king.getPieceX()==move.getNextX() && king.getPieceY()==move.getNextY())
                    .collect(collectingAndThen(Collectors.toList(), Collections::unmodifiableList)));
        }
        return attackMoves;
    }

    @Override
    public List<Move> canBeAttacked(Board game_board, Field candidate) {
        List<Move> attackMoves=new ArrayList<>();
        for(Piece piece: Pieces)
        {
            List<Move> moves=piece.LegalMoves(game_board);
            attackMoves.addAll(moves.stream().filter(move-> candidate.getX().equals(move.getNextX()) && candidate.getY().equals(move.getNextY())).collect(collectingAndThen(Collectors.toList(),Collections::unmodifiableList)));
        }
        return attackMoves;
    }


    public void updatePieces()
    {
        this.Pieces=game.chessBoard.getBlackPieces();
    }


}
