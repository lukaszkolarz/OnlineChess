package Engine.pieces;

import Engine.Alliance;
import Engine.Board;
import Engine.Move;

import java.util.List;

public abstract class Piece{
    protected final Alliance pieceColor;
    protected int piecePosition_x;
    protected int piecePosition_y;
    protected boolean isFirstMove;
    public Piece(int position_x,int position_y,final Alliance al)
    {
            this.piecePosition_x=position_x;
            this.piecePosition_y=position_y;
            this.pieceColor=al;
            this.isFirstMove=true;

    }


    public abstract List<Move> LegalMoves(Board game_board);
    public abstract List<Move> LegalMovesStandard(Board game_board);
    public boolean MoveIsLegal(int piece_move_x, int piece_move_y)
    {
        return (piece_move_x+this.piecePosition_x>=0)&&(piece_move_x+this.piecePosition_x<=7)
                &&(piece_move_y+this.piecePosition_y>=0)&&(piece_move_y+this.piecePosition_y<=7);
    }
    public abstract void Kill();
    public String getPieceAlliance()
    {
        return this.pieceColor.toStringAlliance();
    }
    public int getPieceX(){ return this.piecePosition_x; }
    public int getPieceY(){ return this.piecePosition_y; }
    public Alliance getAlliance() { return this.pieceColor; }
    public void setX(int x)
    {
        this.piecePosition_x=x;
    }
    public void setY(int y)
    {
        this.piecePosition_y=y;
    }
    public abstract String toStringPieceType();
    public void isSecondMove()
    {
        this.isFirstMove=false;
    }
    public boolean isEqual(Piece piece)
    {
        return (this.piecePosition_x==piece.piecePosition_x && this.piecePosition_y==piece.piecePosition_y);
    }
}
