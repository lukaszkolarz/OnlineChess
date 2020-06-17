package Engine.pieces;

import java.lang.*;
import Engine.*;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import static java.util.stream.Collectors.collectingAndThen;
import java.util.stream.Collectors;
import java.util.Collections;

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
    //Legal moves jak pobieram wszystkie piony przeciwnika to pobieram też krola ktory wywoluje taka sama funkcje u siebie pobierajac naszego krola i tak na zmiane
    @Override
    public List<Move> LegalMoves(Board game_board)
    {
        List<Move> PossibleMoves=new ArrayList<Move>();
        List<Piece> opponentPieces;
        if(this.getAlliance()==Alliance.WHITE)
        {
            opponentPieces=game_board.getBlackPieces();
        }
        else
        {
            opponentPieces=game_board.getWhitePieces();
        }

        for(int row=0;row<8;row++) {

            if (MoveIsLegal(moves[row][0], moves[row][1])) {
                Field candidateField = game_board.getField(this.piecePosition_x + moves[row][0], this.piecePosition_y + moves[row][1]);

                if(!candidateField.isFieldOccupied()){
                        if(NeutralSafeMove(opponentPieces,game_board,candidateField)) {
                            PossibleMoves.add(new Move(game_board, this, candidateField));
                            continue;
                        }
                }

                if (candidateField.isFieldOccupied()) {
                    if(candidateField.getPiece().getPieceAlliance()!=this.getPieceAlliance()) {
                        if(AttackSafeMove(opponentPieces,game_board,candidateField)) {
                            PossibleMoves.add(new Move(game_board, this, candidateField));
                        }
                    }
                }
            }
        }
        return PossibleMoves;
    }
    public List<Move> LegalMovesStandard(Board game_board)
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
    public boolean NeutralSafeMove(List<Piece> opponentPieces,Board game_board,Field candidate)
    {

        for(Piece opponent: opponentPieces)
        {
            boolean exists;
            if(opponent instanceof King)
                exists=AttacksOnTileSearch(candidate,((King) opponent).LegalMovesStandard(game_board));
            else
                exists=AttacksOnTileSearch(candidate,opponent.LegalMoves(game_board));
            if(exists)
                return false;
        }
        return true;
    }
    public boolean AttacksOnTileSearch(Field candidate,List<Move> moves)
    {
        return moves.stream()
                    .anyMatch(move-> candidate.getX().equals(move.getNextX()) && candidate.getY().equals(move.getNextY()));

    }
    public boolean AttackSafeMove(List<Piece> opponentPieces,Board game_board,Field candidate)
    {
        for(Piece opponent: opponentPieces)
        {
            boolean exists;
                exists=AttacksOnTileSearch(candidate,opponent.LegalMovesStandard(game_board));

            if(exists)
                return false;
        }
        return true;
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
