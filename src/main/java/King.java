import java.lang.*;
import java.util.List;
import java.util.ArrayList;

public class King extends Piece{
    private final int[][] moves={
            {-1,0},
            {-1,1},
            {0,1},
            {1,1},
            {1,0},
            {1,-1},
            {0,-1},
            {-1,-1}
            };
    public King(int row,int col,Alliance all)
    {
        super(row,col,all);
    }
    @Override
    public List<Move> LegalMoves(Board game_board)
    {
        List<Move> PossibleMoves=new ArrayList<Move>();
        for(int row=0;row<8;row++) {
            if (MoveIsLegal(moves[row][0], moves[row][1])) {

                final Field candidate = game_board.getField(this.piecePosition_x + moves[row][0], this.piecePosition_y + moves[row][1]);
                if (!candidate.isFieldOccupied()) {
                    PossibleMoves.add(new Move(game_board,this,candidate));
                    continue;
                }
                if (candidate.isFieldOccupied()) {
                    if(candidate.getPiece().getPieceAlliance()!=this.getPieceAlliance())
                        PossibleMoves.add(new Move(game_board,this,candidate));
                    continue;
                }
            }
        }
        return PossibleMoves;
    }
    @Override
    public String toStringPieceType()
    {
        return "King";
    }
    @Override
    public void Kill()
    {

    }
}
