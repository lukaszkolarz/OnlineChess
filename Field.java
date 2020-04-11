package OnlineChess;
import OnlineChess.pieces.Piece;

import java.lang.*;
//Composite design pattern used
//Small classes and convenient grouping easy to maintain and read
public abstract class Field {
    protected final int x;
    protected final int y;
    Field(int row,int col)
    {
        this.x=row;
        this.y=col;
    }
    public abstract boolean isFieldOccupied();
    public abstract Piece getPiece();
    public static final class EmptyField extends Field {
        public EmptyField(int row, int col) {
            super(row, col);
        }

        @Override
        public boolean isFieldOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }
    public static final class OccupiedField extends Field {
           private Piece pieceOnField;
           public OccupiedField(int row,int col,Piece onField)
           {
               super(row,col);
               this.pieceOnField=onField;
           }
           @Override
           public boolean isFieldOccupied()
           {
               return true;
           }
           @Override
           public Piece getPiece()
           {
               return this.pieceOnField;
           }

    }
}
