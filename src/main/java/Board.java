

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.NoSuchElementException;
public class Board {
    private Field[][] board=new Field[8][8];
    private List<Piece> WhitePieces;
    private List<Piece> BlackPieces;

    public Field getField(int row,int col)
    {
        if(row>7 || row<0 || col>7 || col<0)
        {
            throw new ArrayIndexOutOfBoundsException("You went over with the index");
        }
        return board[row][col];
    }
    public boolean CheckIfFieldsHaveProperCoordinates()
    {
       // Queue<Pair<Integer,Integer>> PropCoordinates=new LinkedList<>();
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

}
