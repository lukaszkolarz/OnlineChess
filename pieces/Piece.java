package OnlineChess.pieces;

import OnlineChess.Alliance;
import OnlineChess.Board;
import OnlineChess.Move;

import java.util.List;

public abstract class Piece{
    private final Alliance pieceColor;
    protected int piecePosition_x;
    protected int piecePosition_y;
    public Piece(int position_x,int position_y,final Alliance al)
    {
            this.piecePosition_x=position_x;
            this.piecePosition_y=position_y;
            this.pieceColor=al;
    }


    public abstract List<Move> LegalMoves(Board game_board);

    public boolean MoveIsLegal(int piece_move_x, int piece_move_y)
    {
        return (piece_move_x+this.piecePosition_x>=0)&&(piece_move_x+this.piecePosition_x<=7)
                &&(piece_move_y+this.piecePosition_y>=0)&&(piece_move_y+this.piecePosition_y<=7);
    }
    public abstract void Kill();
    public String getPieceAlliance()
    {
        return this.pieceColor.toString();
    }
    public abstract String toStringPieceType();
}
