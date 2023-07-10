package Graphics;

import Event.*;
import Logic.Position;

import javax.swing.table.AbstractTableModel;

public class FieldModel extends AbstractTableModel implements MoveListener, EatListener {

    private final BlockType[][] gameField;

    public FieldModel(){
        this.gameField = new BlockType[25][16];
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                gameField[i][j] = BlockType.EMPTY;
            }
        }
    }

    @Override
    public int getRowCount() {
        return this.gameField.length;
    }

    @Override
    public int getColumnCount() {
        return this.gameField[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.gameField[rowIndex][columnIndex];
    }


    @Override
    public void setValueAt(Object value, int row, int col) {
        this.gameField[row][col] = (BlockType) value;
        fireTableCellUpdated(row, col);
    }

    @Override
    public void processMoveEvent(MoveEvent event) {
        // Head
        Position newHeadPosition = event.getNewHeadPosition();
        Position oldHeadPosition = event.getOldHeadPosition();
        // Tail
        Position oldTailPosition = event.getOldTailPosition();

        setValueAt(BlockType.EMPTY, oldHeadPosition.getY(), oldHeadPosition.getX());

        if(!oldTailPosition.isNull()) {
            setValueAt(BlockType.SNAKE_SEGMENT, oldHeadPosition.getY(), oldHeadPosition.getX());
            setValueAt(BlockType.EMPTY, oldTailPosition.getY(), oldTailPosition.getX());
        }
        setValueAt(BlockType.SNAKE_HEAD, newHeadPosition.getY(), newHeadPosition.getX());

    }
    @Override
    public void processEatEvent(EatEvent event) {
        Position fruitPosition = event.getFruitPosition();
        Position oldTailPosition = event.getOldTailPosition();
        if(!oldTailPosition.isNull()) setValueAt(BlockType.SNAKE_SEGMENT, oldTailPosition.getY(), oldTailPosition.getX());
        setValueAt(BlockType.FRUIT, fruitPosition.getY(), fruitPosition.getX());
    }


}
