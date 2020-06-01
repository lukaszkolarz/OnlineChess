package Engine;
import Engine.pieces.Piece;
import java.lang.*;


public final class Field {
    private final int x;
    private final int y;
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
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
}
