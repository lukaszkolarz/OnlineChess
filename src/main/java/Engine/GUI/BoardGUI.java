package Engine.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//
// Singleton pattern - Lazy Initialization
//
public class BoardGUI extends JFrame {
    private Dimension WINDOW_size=new Dimension(615,660);
    private Dimension FIELD_size=new Dimension(50,50);
    private BoardPanel chess_board;
    private static BoardGUI instance;
    private BoardGUI()
    {
        super("Chess");
        JMenuBar Menu=addMenuBar();
        this.chess_board=new BoardPanel();
        this.chess_board.setBounds(0,0,600,600);
        this.chess_board.setBackground(Color.white);
        this.add(this.chess_board);
        this.setSize(WINDOW_size);
        this.setJMenuBar(Menu);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
    }
    public static BoardGUI getInstance()
    {
        if(instance==null) {
            instance=new BoardGUI();
        }
        return instance;
    }
    private JMenuBar addMenuBar()
    {
        JMenuBar Menu=new JMenuBar();
        Menu.add(createFileMenu());
        return Menu;
    }
    private JMenu createFileMenu()
    {
        JMenu fileMenu=new JMenu("File");
        JMenuItem exitGameItem=new JMenuItem("Exit");
        exitGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        fileMenu.add(exitGameItem);
        return fileMenu;
    }
}
