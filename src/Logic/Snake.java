package Logic;

import Event.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Snake extends Thread implements KeyListener {
    private final List<MoveListener> moveListeners;
    private final List<EatListener> eatListeners;
    private final List<CollisionListener> collisionListeners;
    private Direction direction;

    private boolean dirLocked;
    private boolean running;
    private final int fieldHeight;
    private final int fieldWidth;
    private final Position head;
    private final List<Position> body;
    private final Fruit fruit;

    private final Position oldHeadPosition;
    private final Position oldTailPosition;

    public Snake(int fieldHeight, int fieldWidth){
        this.direction = Direction.RIGHT;
        this.dirLocked = false;
        this.running = true;

        this.moveListeners = Collections.synchronizedList(new ArrayList<>());
        this.eatListeners = Collections.synchronizedList(new ArrayList<>());
        this.collisionListeners = Collections.synchronizedList(new ArrayList<>());

        this.body = Collections.synchronizedList(new ArrayList<>());
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;

        this.oldTailPosition = new Position(-1, -1);
        this.head = new Position(5, 10);
        this.oldHeadPosition = new Position(this.head);

        this.fruit = new Fruit();
    }

    public void init(){
        fireMoveEvent(head, head, this.oldTailPosition);
        fireEatEvent(fruit.getFruitPos(), this.oldTailPosition);
    }


    @Override
    public void run() {
        while(running){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.move();
            unlockKeys();
        }
    }

    private void stopThread(){
        this.running = false;
    }
    public void addMoveListener(MoveListener listener){
        this.moveListeners.add(listener);
    }
    public void addEatListener(EatListener listener){
        this.eatListeners.add(listener);
    }
    public void addCollisionListener(CollisionListener listener){
        this.collisionListeners.add(listener);
    }

    protected void fireMoveEvent(Position newHeadPosition, Position oldHeadPosition, Position oldTailPosition){
        MoveEvent event = new MoveEvent(this, newHeadPosition, oldHeadPosition, oldTailPosition);
        for(MoveListener listener: this.moveListeners){
            listener.processMoveEvent(event);
        }
    }

    protected void fireEatEvent(Position fruitPosition, Position oldTailPosition){
        EatEvent event = new EatEvent(this, fruitPosition, oldTailPosition);
        for(EatListener listener: this.eatListeners){
            listener.processEatEvent(event);
        }
    }

    protected void fireCollisionEvent(){
        CollisionEvent event = new CollisionEvent(this);
        for (CollisionListener listener: this.collisionListeners){
            listener.processCollisionEvent(event);
        }
    }

    private boolean withInBoundaries(){
        int x = head.getX();
        int y = head.getY();
        return 0 <= y && y < fieldHeight && 0 <= x && x < fieldWidth;
    }

    private boolean hasEatenHimself(){
        for(Position segment: this.body){
            if (head.equals(segment)) return true;
        }
        return false;
    }

    private void move(){
        this.oldHeadPosition.setPos(this.head);
        moveHead(head);
        if(!this.body.isEmpty()) handleTailMovement();
        if(!hasEatenHimself() && withInBoundaries()){
            fireMoveEvent(head, this.oldHeadPosition, this.oldTailPosition);
        } else {
            handleCollision();
        }
        if (fruit.getFruitPos().equals(head)) handleFruitCollision();
    }


    private void moveHead(Position head){
        switch (this.direction){
            case UP -> head.setY(head.getY() - 1);
            case DOWN -> head.setY(head.getY() + 1);
            case LEFT -> head.setX(head.getX() - 1);
            case RIGHT -> head.setX(head.getX() + 1);
        }
    }

    private void handleCollision(){
        stopThread();
        fireCollisionEvent();
    }

    private void handleTailMovement(){
        Position tail = this.body.remove(body.size()-1);
        this.oldTailPosition.setPos(tail);
        tail.setPos(this.oldHeadPosition);
        this.body.add(0, tail);
    }
    private void handleFruitCollision(){
        if (this.body.isEmpty()) this.oldTailPosition.setPos(this.oldHeadPosition);
        this.body.add(new Position(this.oldTailPosition));
        fruit.setRandomPos();
        fireEatEvent(fruit.getFruitPos(), oldTailPosition);
    }


    @Override
    public void keyTyped(KeyEvent ignored) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        Direction newDir = switch ( keyCode ) {
            case KeyEvent.VK_UP -> Direction.UP;
            case KeyEvent.VK_DOWN -> Direction.DOWN;
            case KeyEvent.VK_LEFT -> Direction.LEFT;
            case KeyEvent.VK_RIGHT -> Direction.RIGHT;
            default -> direction;
        };
        if(direction.getOpposite() != newDir && direction != newDir && !this.dirLocked){
            direction = newDir;
            this.dirLocked = true;
        }
    }

    private void unlockKeys(){
        this.dirLocked = false;
    }

    @Override
    public void keyReleased(KeyEvent ignored) {}


    private class Fruit{
        private Position fruitPos;
        public Fruit(){
            fruitPos = new Position(10, 10);
        }

        public void setRandomPos(){
            if(body.size() + 1 == fieldHeight * fieldWidth){
                handleCollision();
            }
            do{
                fruitPos.setX((int)(Math.random() * fieldWidth));
                fruitPos.setY((int)(Math.random() * fieldHeight));
            } while(!isOnEmptyPlace());
        }

        private boolean isOnEmptyPlace(){
            if(fruitPos.equals(head)) return false;
            for (Position pos: body){
                if (fruitPos.equals(pos)) return false;
            }
            return true;
        }

        public Position getFruitPos() {
            return fruitPos;
        }
    }

}
