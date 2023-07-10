package Logic;

public enum Direction {
    UP, LEFT, DOWN, RIGHT;

    public Direction getOpposite(){
        Direction[] values = Direction.values();
        return values[(this.ordinal() + 2) % values.length];
    }

}
