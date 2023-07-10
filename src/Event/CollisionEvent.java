package Event;

import java.util.EventObject;

public class CollisionEvent extends EventObject {
    public CollisionEvent(Object source) {
        super(source);
    }
}
