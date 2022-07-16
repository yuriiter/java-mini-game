package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MoveBallKey extends AbstractAction {
    private final Direction direction;
    private final Maze maze;
    private final MoveBallDriver moveBallDriver = new MoveBallDriver();

    public MoveBallKey(Direction direction, Maze maze) {
        super();
        this.direction = direction;
        this.maze = maze;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveBallDriver.moveBall(this.direction, this.maze);
    }
}
