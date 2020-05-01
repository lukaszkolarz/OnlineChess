import java.util.List;
import java.util.ArrayList;

public class Pawn extends Piece{
    private final int[][] moves={
            {1,0}
    };
    public Pawn(int row,int col,Alliance all)
    {
        super(row,col,all);
    }
    public List<Move> LegalMoves(Board game_board)
    {
        return new ArrayList<Move>();
    }
    @Override
    public String toStringPieceType()
    {
        return "Pawn";
    }
    @Override
    public void Kill()
    {

    }
}
