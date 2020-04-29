package OnlineChess;
import OnlineChess.pieces.Piece;
import java.lang.*;

//Composite design pattern used
//Small classes and convenient grouping easy to maintain and read
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
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
}
