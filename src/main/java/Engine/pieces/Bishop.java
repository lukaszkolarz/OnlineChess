package Engine.pieces;

import Engine.*;
import java.util.List;
import java.util.ArrayList;
public class Bishop extends Piece{
    private final int[][] moves={
            {-1,-1},
            {-1,1},
            {1,1},
            {1,-1}
    };
    public Bishop(int row,int col,Alliance all)
    {
        super(row,col,all);
    }
    @Override
    public List<Move> LegalMoves(Board game_board)
    {
        List<Move> PossibleMoves=new ArrayList<Move>();
        for(int row=0;row<4;row++)
        {
            int PositionX=moves[row][0];
            int PositionY=moves[row][1];
           while(MoveIsLegal(PositionX,PositionY))
           {
              final Field candidate=game_board.getField(this.piecePosition_x+PositionX,this.piecePosition_y+PositionY);
              if(!candidate.isFieldOccupied())
              {
                  PossibleMoves.add(new Move(game_board,this,candidate));
              }
              if(candidate.isFieldOccupied())
              {
                  if(candidate.getPiece().getPieceAlliance()!=this.getPieceAlliance())
                  {
                      PossibleMoves.add(new Move(game_board,this,candidate));
                      break;
                  }
                  else
                  {
                      break;
                  }
              }
              PositionX+=moves[row][0];
              PositionY+=moves[row][1];
           }
        }
        return PossibleMoves;
    }
    @Override
    public void Kill()
    {

    }
    @Override
    public String toStringPieceType()
    {
        return "Bishop";
    }
}

