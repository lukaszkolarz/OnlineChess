package Engine;

import Engine.pieces.King;
import Engine.pieces.Piece;

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

    public abstract void updatePieces();

}
