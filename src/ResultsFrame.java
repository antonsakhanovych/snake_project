import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ResultsFrame extends JFrame {
    public ResultsFrame(List<Record> recordList){
        setLayout(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Score");



        for(int i = recordList.size() - 1; i >= 0; i--){
            Record rec = recordList.get(i);
            model.addRow(new Object[]{rec.getName(), rec.getScore()});
        }

        JTable table = new JTable(model);


        this.getContentPane().add(table.getTableHeader(), BorderLayout.NORTH);
        this.getContentPane().add(table, BorderLayout.CENTER);

        setSize( 740, 540);
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
