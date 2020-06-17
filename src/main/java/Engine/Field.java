package Engine;
import Engine.pieces.Piece;
import java.lang.*;


public final class Field {
    private final Integer x;
    private final Integer y;
    private boolean occupied;
    private Piece piece;
    public Field(int row,int col)
    {
        this.x=row;
        this.y=col;
        this.occupied=false;
        this.piece=null;
    }
    public Field(int row,int col,Piece piece)
    {
        this.x=row;
        this.y=col;
        this.occupied=true;
        this.piece=piece;
    }
    public boolean isFieldOccupied()
    {
        return this.occupied;
    }
    public Piece getPiece()
    {
        if(piece!=null)
             return this.piece;
        else
            return null;
    }
    public void setPiece(Piece p)
    {
        this.piece=p;
        this.occupied=true;
    }
    public Integer getX()
    {
        return x;
    }
    public Integer getY()
    {
        return y;
    }
}
