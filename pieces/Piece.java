package OnlineChess.pieces;

import OnlineChess.Alliance;
import OnlineChess.Board;
import OnlineChess.Move;

import java.util.List;

public abstract class Piece{
    protected final Alliance pieceColor;
    protected int piecePosition_x;
    protected int piecePosition_y;
    public Piece(int position_x,int position_y,Alliance al)
    {
            this.piecePosition_x=position_x;
            this.piecePosition_y=position_y;
            this.pieceColor=al;
    }

    public abstract List<Move> LegalMoves(Board game_board);
    public abstract boolean MoveIsLegal(Move piece_move);
    public abstract void Kill();
    public Alliance getPieceAlliance()
    {
        return this.pieceColor;
    }

}
