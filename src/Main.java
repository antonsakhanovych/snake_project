import Graphics.Block;
import Graphics.FieldModel;
import Logic.Snake;
import Event.CollisionListener;
import Event.CollisionEvent;


import javax.swing.*;
import java.awt.*;

public class Main extends JFrame implements CollisionListener {

    private static int squareSize = 30;
    public Main(){
        setLayout(new BorderLayout());

        JTable playField = new JTable();
        playField.setRowHeight(squareSize);

        Block cell = new Block();
        playField.setDefaultRenderer(Object.class, cell);
        FieldModel model = new FieldModel();
        playField.setModel(model);


        ScorePanel scorePanel = new ScorePanel();

        this.getContentPane().add(scorePanel, BorderLayout.NORTH);
        this.getContentPane().add(playField, BorderLayout.CENTER);

        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();

        Snake snake = new Snake(rowCount, columnCount);
        playField.addKeyListener(snake);

        snake.addMoveListener(model);
        snake.addEatListener(model);
        // Initialize the board
        snake.init();

        // add score label as a listener
        snake.addEatListener(scorePanel);


        snake.addCollisionListener(this);
        snake.addCollisionListener(scorePanel);
        // start snake
        snake.start();

        this.setVisible(true);
        this.setSize( columnCount * squareSize, (rowCount * squareSize) + scorePanel.getSize().height + getInsets().top + 5);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    @Override
    public void processCollisionEvent(CollisionEvent event) {
        this.dispose();
    }
}