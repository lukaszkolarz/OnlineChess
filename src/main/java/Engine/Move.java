package Engine;

import Engine.pieces.Piece;


import java.util.List;

public class Move {

    private Board game_board;
    private Piece movePiece;
    private int NextLocation_x;
    private int NextLocation_y;
    public Move(Board board,Piece PieceToMove,int x_destination,int y_destination)
    {
        this.game_board=board;
        this.movePiece=PieceToMove;
        this.NextLocation_x=x_destination;
        this.NextLocation_y=y_destination;
    }
    public Move(Board board,Piece PieceToMove,Field candidate)
    {
        this.game_board=board;
        this.movePiece=PieceToMove;
        this.NextLocation_x=candidate.getX();
        this.NextLocation_y=candidate.getY();
    }
    public boolean equals(Move moveToMake)
    {
        if(this.NextLocation_x==moveToMake.getNextX() && this.NextLocation_y==moveToMake.getNextY() && this.movePiece==moveToMake.getPiece())
            return true;
        else
            return false;
    }
    public Board getBoard()
    {
        return this.game_board;
    }
    public int getNextX()
    {
        return this.NextLocation_x;
    }
    public int getNextY()
    {
        return this.NextLocation_y;
    }
    public Piece getPiece(){ return this.movePiece;}
    public void updateBoard()
    {
        int x=this.movePiece.getPieceX();
        int y=this.movePiece.getPieceY();
        if(game_board.getField(this.NextLocation_x,this.NextLocation_y).isFieldOccupied())
        {
            Piece toKill=game_board.getField(this.NextLocation_x,this.NextLocation_y).getPiece();
            if(toKill.getAlliance()==Alliance.WHITE)
            {
                List<Piece> whitePieces=game_board.getWhitePieces();
                for(int i=0;i<whitePieces.size();i++)
                {
                    if(toKill.isEqual(whitePieces.get(i)))
                    {
                        System.out.println("Piece deleted: "+toKill.toStringPieceType()+" "+toKill.getPieceAlliance()+" x:"+toKill.getPieceX()+" y:"+toKill.getPieceY()+" by:x"+movePiece.getPieceX()+" y"+movePiece.getPieceY()+" al:"+movePiece.getAlliance()+" "+movePiece.toStringPieceType());
                        game_board.getWhitePieces().remove(i);
                        break;
                    }
                }
            }
            else
            {
                List<Piece> blackPieces=game_board.getBlackPieces();
                for(int i=0;i<blackPieces.size();i++)
                {
                    if(toKill.isEqual(blackPieces.get(i)))
                    {
                        System.out.println("Piece deleted: "+toKill.toStringPieceType()+" "+toKill.getPieceAlliance()+" x:"+toKill.getPieceX()+" y:"+toKill.getPieceY()+" by:x"+movePiece.getPieceX()+" y"+movePiece.getPieceY()+" al:"+movePiece.getAlliance()+" "+movePiece.toStringPieceType());
                        game_board.getBlackPieces().remove(i);
                        break;
                    }
                }
            }
        }

            this.game_board.setPieceOnField(this.movePiece, this.NextLocation_x, this.NextLocation_y);
            this.game_board.setClearField(x, y);

        System.out.println(movePiece.toStringPieceType()+" moves to field x:"+NextLocation_x+" y:"+NextLocation_y);
    }


}
