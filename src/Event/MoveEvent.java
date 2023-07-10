package Event;

import Logic.Position;

import java.util.EventObject;
public class MoveEvent extends EventObject {

    private final Position newHeadPosition;
    private final Position oldHeadPosition;
    private final Position oldTailPosition;

    public MoveEvent(Object source, Position newHeadPosition, Position oldHeadPosition, Position oldTailPosition) {
        super(source);
        this.newHeadPosition = newHeadPosition;
        this.oldHeadPosition = oldHeadPosition;
        this.oldTailPosition = oldTailPosition;
    }

    public Position getNewHeadPosition() {
        return newHeadPosition;
    }

    public Position getOldHeadPosition() {
        return oldHeadPosition;
    }

    public Position getOldTailPosition() {
        return oldTailPosition;
    }
}
