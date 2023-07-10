package Event;


import Logic.Position;

import java.util.EventObject;

public class EatEvent extends EventObject {
    private final Position fruitPosition;
    private final Position oldTailPosition;

    public EatEvent(Object source, Position fruitPosition, Position oldTailPosition) {
        super(source);
        this.fruitPosition = fruitPosition;
        this.oldTailPosition = oldTailPosition;
    }

    public Position getFruitPosition() {
        return fruitPosition;
    }

    public Position getOldTailPosition() {
        return oldTailPosition;
    }
}
