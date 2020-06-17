package Engine;

import Engine.pieces.King;
import Engine.pieces.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected Alliance playerType;
    protected List<Piece> Pieces;
    protected List<Move> PossibleMoves;
    protected boolean isCheck;
    protected Game game;
    protected King king;
    protected boolean yourTurn;
    protected boolean isFirst;
    public Player(Alliance type)
    {
        this.playerType=type;
        this.isCheck=false;
    }
    public boolean isInCheck()
    {
        return isCheck;
    }
    public Alliance getPlayerAlliance()
    {
        return playerType;
    }
    public abstract Player getOpponent();
    public void changeToken(boolean value)
    {
        this.yourTurn=value;
    }
    public List<Piece> getPieces()
    {
        return this.Pieces;
    }
    public boolean isItFirstMove()
    {
        return isFirst;
    }
    public void setItsSecond()
    {
        isFirst=false;
    }
    public King getKing()
    {
        return this.king;
    }
    public abstract List<Move> haveEscapeMoves(Board game_board);
    public abstract List<Move> countKingAttacks(Board game_board);
    public boolean getToken()
    {
        return this.yourTurn ;
    }
    public abstract List<Move> canBeAttacked(Board game_board,Field candidate);
    public abstract boolean isInCheckMate();
    public abstract List<Piece> kingAttackingPieces(Board game_board);
    public List<Point> getProtectFields(int x1, int y1, int x2, int y2)
    {
        List<Point> points=new ArrayList<>();
        if(x1>x2 && y1>y2)
        {
            while(x1!=x2 && y1!=y2)
            {
                points.add(new Point(x2+1,y2+1));
                x2+=1;
                y2+=1;
            }
        }
        if(x1>x2 && y1<y2)
        {
            while(x1!=x2 && y1!=y2)
            {
                points.add(new Point(x2+1,y2-1));
                x2+=1;
                y2-=1;
            }
        }
        if(x1>x2 && y1==y2)
        {
            while(x1!=x2)
            {
                points.add(new Point(x2+1,y2));
                x2+=1;
            }
        }
        if(x1==x2 && y1>y2)
        {
            while(y1!=y2)
            {
                points.add(new Point(x2,y2+1));
                y2+=1;
            }
        }
        if(x1==x2 && y1<y2)
        {
            while(y1!=y2)
            {
                points.add(new Point(x1,y1+1));
                y1+=1;
            }
        }
        if(x1<x2 && y1<y2)
        {
            while(x1!=x2 && y1!=y2)
            {
                points.add(new Point(x1+1,y1+1));
                y1+=1;
                x1+=1;
            }
        }
        if(x1<x2 && y1==y2)
        {
            while(x1!=x2)
            {
                points.add(new Point(x1+1,y1));
                x1+=1;
            }
        }
        if(x1<x2 && y1>y2)
        {
            while(x1!=x2 && y1!=y2)
            {
                points.add(new Point(x1+1,y1-1));
                x1+=1;
                y1-=1;
            }
        }
        return points;
    }
    public abstract List<Move> kingProtectMoves(Board game_board);
    public abstract List<Move> lookForSafeMoves(Board game_board,Piece toProtect,Piece attacking);
   // public abstract List<Move> protectKingMoves(Board game_board);
    public abstract void updatePieces();
    public void print(List<Move> moves)
    {
        for(Move move:moves)
        {
            System.out.println("Piece x:"+move.getPiece().getPieceX()+" y:"+move.getPiece().getPieceY()+" where x:"+move.getNextX()+" y"+move.getNextY());
        }
    }

}
