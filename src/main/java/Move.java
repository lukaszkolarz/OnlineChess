import java.util.AbstractMap;

public class Move {

    private Board game_board;
    private Piece movePiece;
    private int NextLocation_x;
    private int NextLocation_y;
    public Move(Board board, Piece PieceToMove, int x_destination, int y_destination)
    {
        this.game_board=board;
        this.movePiece=PieceToMove;
        this.NextLocation_x=x_destination;
        this.NextLocation_y=y_destination;
    }
    public Move(Board board,Piece PieceToMove, Field candidate)
    {
        this.game_board=board;
        this.movePiece=PieceToMove;
        this.NextLocation_x=candidate.getX();
        this.NextLocation_y=candidate.getY();
    }
    public AbstractMap.SimpleEntry<Integer,Integer> GetNextLocation()
    {
        return new AbstractMap.SimpleEntry<>(NextLocation_x, NextLocation_y);
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
    public void execute()
    {
    }


}
