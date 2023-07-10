import Event.EatEvent;
import Event.EatListener;
import Event.CollisionListener;
import Event.CollisionEvent;

import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.stream.Collectors;

public class ScorePanel extends JPanel implements EatListener, CollisionListener {
    private long points;

    public ScorePanel(){
        this.points = 0;

        setPreferredSize(new Dimension(100, 20));
        setVisible(true);
    }

    @Override
    public void processEatEvent(EatEvent ignored) {
        points++;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("Score: " + points, this.getWidth()/2, this.getHeight()/2);
    }

    @Override
    public void processCollisionEvent(CollisionEvent ignored) {
        String name = JOptionPane.showInputDialog(null, "Game Over! Your score is: " + points + "\n Enter your name to save the score:");
        if (name != null) {
            FileManager.writeInfo(name, points);
        }
        List<Record> records = FileManager.readInfo().stream()
                .collect(Collectors.groupingBy(Record::getName,
                        Collectors.maxBy(Comparator.comparingLong(Record::getScore))))
                .values().stream()
                .map(recordOptional -> recordOptional.orElse(null))
                .filter(Objects::nonNull)
                .sorted(Comparator.reverseOrder())
                .limit(10)
                .sorted()
                .toList();

        new ResultsFrame(records);
    }
}