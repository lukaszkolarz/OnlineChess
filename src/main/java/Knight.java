import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    private final int[][] moves= {
            {-2, 1},
            {-1, 2},
            {1, 2},
            {2, 1},
            {2, -1},
            {1, -2},
            {-1, -2},
            {-2, -1}
    };
    public Knight(int row,int col,Alliance all)
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
        return "Knight";
    }
    @Override
    public void Kill() {

    }

}
