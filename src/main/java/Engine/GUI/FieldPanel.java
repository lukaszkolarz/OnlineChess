package Engine.GUI;

import Engine.Board;
import Engine.Field;
import Engine.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FieldPanel extends JPanel {
    private int FieldX;
    private int FieldY;
    private Dimension fieldSize = new Dimension(75, 75);



    public FieldPanel(int place_x, int place_y,Board game_board) {
        super(new GridBagLayout());
        this.setBounds(0, 0, 75, 75);
        this.FieldX=place_x;
        this.FieldY=place_y;
        assignFieldColor(place_x, place_y);
        assignFieldThePieceImage(game_board);


    }
    public int getPanelX()
    {
        return FieldX;
    }
    public int getPanelY()
    {
        return FieldY;
    }

    public void assignFieldColor(int x, int y) {
        if ((x % 2 == 0 && y % 2 == 1) || (x % 2 == 1 && y % 2 == 0)) {
            this.setBackground(new Color(139, 69, 19));
        }
        if ((x % 2 == 1 && y % 2 == 1) || (x % 2 == 0 && y % 2 == 0)) {
            this.setBackground(new Color(255, 222, 173));
        }
    }
    public void changeFieldColorToAttack()
    {
        this.setBackground(Color.RED);
    }
    public void changeFieldColorToNeutral()
    {
        this.setBackground(Color.GREEN);
    }

    public void assignFieldThePieceImage(Board game_board)
    {
        if(game_board.getField(this.FieldX-1,this.FieldY-1).isFieldOccupied())
        {
            StringBuilder path=new StringBuilder();
            path.append("src/main/java/Engine/GUI/PiecesImage/")
                    .append(game_board.getField(this.FieldX-1,this.FieldY-1).getPiece().getPieceAlliance())
                    .append(game_board.getField(this.FieldX-1,this.FieldY-1).getPiece().toStringPieceType())
                    .append(".png");
            try {
                BufferedImage img= ImageIO.read(new File(path.toString()));
                this.add(new JLabel(new ImageIcon(img)));
            } catch (IOException e) {
                System.out.println("Piece image not found and neither loaded to field "+path.toString());
            }
        }
    }
    public void drawField(Board game_board)
    {
        removeAll();
        assignFieldColor(FieldX,FieldY);
        assignFieldThePieceImage(game_board);
        validate();
        repaint();
    }
}
