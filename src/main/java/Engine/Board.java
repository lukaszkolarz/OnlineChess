package Engine;
import Engine.pieces.*;


import static Engine.Alliance.BLACK;
import static Engine.Alliance.WHITE;

import java.util.*;

public class Board {
    private Field[][] board;
    private List<Piece> WhitePieces;
    private List<Piece> BlackPieces;

    public Board(Builder boardbuild)
    {
        this.board=boardbuild.board;
        this.WhitePieces=boardbuild.WhitePieces;
        this.BlackPieces=boardbuild.BlackPieces;
    }
    public Field getField(int row,int col)
    {
        if(row>7 || row<0 || col>7 || col<0)
        {
            throw new ArrayIndexOutOfBoundsException("You went over with the index x:"+row+" y:"+col);
        }
        return board[row][col];
    }
    public boolean CheckIfFieldsHaveProperCoordinates()
    {
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<8;j++)
            {
                if(board[i][j].getX()!=i || board[i][j].getY()!=j)
                {
                    System.out.println("Not correctly constructed board wrong x/y in x:"+i+" y:"+j);
                    return false;
                }
            }
        }
        return true;
    }
    public List<Piece> getWhitePieces()
    {
        for(Piece white: WhitePieces)
        {
            System.out.println("X:"+white.getPieceX()+" Y:"+white.getPieceY()+" type:"+white.toStringPieceType()+" "+white.getPieceAlliance());
        }
        return this.WhitePieces;
    }
    public List<Piece> getBlackPieces()
    {
        //System.out.println("Something is wrong");
        for(Piece black: BlackPieces)
        {
            System.out.println("X:"+black.getPieceX()+" Y:"+black.getPieceY()+" type:"+black.toStringPieceType()+" "+black.getPieceAlliance());
        }
        return this.BlackPieces;
    }
    public void setClearField(int x,int y)
    {
        board[x][y]=new Field(x,y);
    }
    public void setPieceOnField(Piece piece,int nextX,int nextY)
    {
        piece.setX(nextX);
        piece.setY(nextY);
        board[nextX][nextY].setPiece(piece);
    }
    public static final class Builder
    {
        private Field[][] board;
        private List<Piece> WhitePieces;
        private List<Piece> BlackPieces;
        public Builder()
        {
            this.WhitePieces=new ArrayList<>();
            this.BlackPieces=new ArrayList<>();
            board=new Field[8][8];
            for(int i=0;i<8;i++)
            {
                for(int j=0;j<8;j++)
                {
                    this.board[i][j]=new Field(i,j);
                }
            }
        }
        public Board build()
        {
         return new Board(this);
        }
        public Builder setWhitePiece(Piece piece)
        {
            boolean check=true;
                if(piece.getAlliance()!=WHITE)
                    check=false;

            if(check) {
                this.WhitePieces.add(piece);
                this.board[piece.getPieceX()][piece.getPieceY()].setPiece(piece);
            }
            else
            {
                System.out.println("Something went wrong in initialization of white piece type "+piece.toStringPieceType()+" x:"+piece.getPieceX()+" y:"+piece.getPieceY());
            }
            return this;
        }
        public Builder setBlackPiece(Piece piece)
        {
            boolean check=true;
                if(piece.getAlliance()!=BLACK)
                    check=false;
            if(check) {
                this.BlackPieces.add(piece);
                this.board[piece.getPieceX()][piece.getPieceY()].setPiece(piece);
            }
            else
            {
                System.out.println("Something went wrong in initialization of black piece type "+piece.toStringPieceType()+" x:"+piece.getPieceX()+" y:"+piece.getPieceY());
            }
            return this;
        }

    }

}
