package Engine.pieces;

import Engine.Board;
import Engine.Field;
import Engine.Move;
import Engine.Alliance;

import java.util.List;
import java.util.ArrayList;

public class Pawn extends Piece{
    private final int[][] moves={
            {1,0}
    };
    private boolean isFirstMove;

    public Pawn(int row,int col,Alliance all)
    {
        super(row,col,all);
        this.isFirstMove=true;

    }
    //First checks piece color then checks standard move if is legal then checks if this is first move and can move by 2
    //next check attack options
    //PION ŹLE ZBIJA I PORUSZA SIĘ 2 do przodu po pierwszym ruchu
    public List<Move> LegalMoves(Board game_board)
    {
        this.isItFirstMove();
        List<Move> PossibleMoves=new ArrayList<>();
        Field candidate;
        if(this.pieceColor.isWhite()) {
            if (MoveIsLegal(-1, 0)) {
                candidate = game_board.getField(this.piecePosition_x - 1, this.piecePosition_y);
                if (!candidate.isFieldOccupied()) {
                    PossibleMoves.add(new Move(game_board, this, candidate));
                    if (MoveIsLegal(-2, 0)) {
                        candidate = game_board.getField(this.piecePosition_x - 2, this.piecePosition_y);
                        if (isFirstMove && !candidate.isFieldOccupied()) {
                            PossibleMoves.add(new Move(game_board, this, candidate));
                            isFirstMove=false;
                        }
                    }
                }
            }
            if(MoveIsLegal(-1,-1)) {
                candidate=game_board.getField(this.piecePosition_x-1,this.piecePosition_y-1);
                if(candidate.isFieldOccupied() && candidate.getPiece().pieceColor.isBlack())
                {
                    PossibleMoves.add(new Move(game_board,this,candidate));
                }
            }
            if(MoveIsLegal(-1,1)) {
                candidate=game_board.getField(this.piecePosition_x-1,this.piecePosition_y+1);
                if(candidate.isFieldOccupied()&& candidate.getPiece().pieceColor.isBlack())
                {
                    PossibleMoves.add(new Move(game_board,this,candidate));
                }
            }
        }
        if(this.pieceColor.isBlack()) {
            if (MoveIsLegal(1, 0)) {
                candidate = game_board.getField(this.piecePosition_x + 1, this.piecePosition_y);
                if (!candidate.isFieldOccupied()) {
                    PossibleMoves.add(new Move(game_board, this, candidate));
                    if (MoveIsLegal(+2, 0)) {
                        candidate = game_board.getField(this.piecePosition_x + 2, this.piecePosition_y);
                        if (isFirstMove && !candidate.isFieldOccupied()) {
                            PossibleMoves.add(new Move(game_board, this, candidate));
                            isFirstMove=false;
                        }
                    }
                }
            }
            if(MoveIsLegal(1,-1)) {
                candidate=game_board.getField(this.piecePosition_x+1,this.piecePosition_y-1);
                if(candidate.isFieldOccupied() && candidate.getPiece().pieceColor.isWhite())
                {
                    PossibleMoves.add(new Move(game_board,this,candidate));
                }
            }
            if(MoveIsLegal(1,1)) {
                candidate=game_board.getField(this.piecePosition_x+1,this.piecePosition_y+1);
                if(candidate.isFieldOccupied()&& candidate.getPiece().pieceColor.isWhite())
                {
                    PossibleMoves.add(new Move(game_board,this,candidate));
                }
            }
        }



        return PossibleMoves;
    }

    public void isItFirstMove()
    {
        if(this.pieceColor.isBlack() && this.piecePosition_x==1)
            isFirstMove=true;
        if(this.pieceColor.isWhite() && this.piecePosition_x==6)
            isFirstMove=true;
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
