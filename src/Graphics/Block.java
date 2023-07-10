package Graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Block extends JPanel implements TableCellRenderer {

    private static BufferedImage headImage;
    private static BufferedImage segImage;
    private static BufferedImage fruitImage;

    static {
        try {
            headImage = ImageIO.read(new File("src/resources/head.png"));
            segImage = ImageIO.read(new File("src/resources/body.png"));
            fruitImage = ImageIO.read(new File("src/resources/fruit.png"));

        } catch (IOException e) {
             segImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
             headImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
             fruitImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

             Graphics2D blueGraphics = segImage.createGraphics();
             Graphics2D redGraphics = headImage.createGraphics();
             Graphics2D orangeGraphics = fruitImage.createGraphics();

             blueGraphics.setColor(Color.BLUE);
             redGraphics.setColor(Color.RED);
             orangeGraphics.setColor(Color.ORANGE);

            // Draw a filled rectangle on each BufferedImage
             blueGraphics.fillRect(0, 0, 100, 100);
             redGraphics.fillRect(0, 0, 100, 100);
             orangeGraphics.fillRect(0, 0, 100, 100);

            // Dispose of the graphics context of each BufferedImage
             blueGraphics.dispose();
             redGraphics.dispose();
             orangeGraphics.dispose();
        }
    }

    private BlockType blockType;

    public Block(){
        blockType = BlockType.EMPTY;
    }


    protected void setImage(BlockType blockType){
        this.blockType = blockType;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(blockType == null || blockType == BlockType.EMPTY){
            return;
        }
        switch (blockType){
            case SNAKE_HEAD -> {
                g.drawImage(headImage, 0, 0, getWidth(), getHeight(), this);
            }
            case SNAKE_SEGMENT -> {
                g.drawImage(segImage, 0, 0, getWidth(), getHeight(),this);
            }
            case FRUIT -> {
                g.drawImage(fruitImage, 0, 0, getWidth(), getHeight(),this);
            }
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        BlockType type = (BlockType)value;
        this.setImage(type);
        return this;
    }
}
