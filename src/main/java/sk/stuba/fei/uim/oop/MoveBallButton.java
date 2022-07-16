package sk.stuba.fei.uim.oop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveBallButton implements ActionListener {
    private final Direction direction;
    private final Maze maze;
    private final MoveBallDriver moveBallDriver = new MoveBallDriver();

    public MoveBallButton(Direction direction, Maze maze) {
        super();
        this.direction = direction;
        this.maze = maze;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveBallDriver.moveBall(this.direction, this.maze);
    }
}
