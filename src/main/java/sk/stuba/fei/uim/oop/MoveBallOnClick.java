package sk.stuba.fei.uim.oop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MoveBallOnClick extends MouseAdapter {
    CanvasField canvasField;
    public MoveBallOnClick(CanvasField canvasField) {
        this.canvasField = canvasField;
    }
    public void mouseReleased(MouseEvent e) {
        if(this.canvasField.hasPlayerClicked()) {
            if(this.canvasField.isHoveringPlayer(e.getX(), e.getY())) {
                this.canvasField.setPlayerClicked(false);
                this.canvasField.setMousePos(null);
            }
            Maze maze = this.canvasField.getMaze();
            int[] tileCoordinates = canvasField.getMouseMoveTile();
            if(tileCoordinates == null) {
                return;
            }
            Ball ball = maze.getBall();
            ball.setPosition(tileCoordinates[0] + 1, tileCoordinates[1] + 1);
            this.canvasField.setPlayerClicked(false);
            this.canvasField.setMousePos(null);
        }
        else {
            if(this.canvasField.isHoveringPlayer(e.getX(), e.getY())) {
                this.canvasField.setPlayerClicked(true);
                this.canvasField.setMousePos(new int[] {e.getX(), e.getY()});
            }
        }

    }
}
