package sk.stuba.fei.uim.oop;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MousePositioning implements MouseMotionListener {
    private CanvasField canvasField;

    public MousePositioning(CanvasField canvasField) {
        this.canvasField = canvasField;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(this.canvasField.hasPlayerClicked()) {
            canvasField.setMousePos(new int[]{e.getX(), e.getY()});
        }
        else {
            canvasField.setMousePos(null);
        }
    }
}
