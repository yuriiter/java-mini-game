package sk.stuba.fei.uim.oop;

import javax.swing.*;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;


enum Direction {
    UP, DOWN, LEFT, RIGHT,
    UP_CTRL, DOWN_CTRL, LEFT_CTRL, RIGHT_CTRL
}


public class Game extends Canvas {
    private int successfulMazes = 0;
    private Maze maze;
    private final JComponent[] components = new JComponent[6];
    private final JButton[] controlButtons = new JButton[4];
    private JFrame frame;

    public int getSuccessfulMazes() {
        return this.successfulMazes;
    }

    public void setSuccessfulMazes(int successfulMazes) {
        this.successfulMazes = successfulMazes;
    }

    public Maze getMaze() {
        return this.maze;
    }

    public JLabel getLabel() {
        return (JLabel) this.components[2];
    }

    private void setButtons(JPanel controllerPanel) {
        components[0] = new JButton("Restart");
        components[1] = new JButton("\u2191"); // Arrow up
        components[2] = new JLabel("Count of wins: " + successfulMazes);
        components[3] = new JButton("\u2190"); // Arrow left
        components[4] = new JButton("\u2193"); // Arrow down
        components[5] = new JButton("\u2192"); // Arrow right

        components[2].setBorder(new EmptyBorder(0,10,0,0));
        components[2].setOpaque(true);
        components[2].setBackground(new Color(0xD4D4D4));

        this.controlButtons[0] = (JButton) components[3];
        this.controlButtons[1] = (JButton) components[5];
        this.controlButtons[2] = (JButton) components[1];
        this.controlButtons[3] = (JButton) components[4];

        for(int i = 0; i < 6; i++) {
            controllerPanel.add(components[i]);
        }
    }


    private void setCanvas(JPanel containerPanel) {
        CanvasField canvasField = new CanvasField(this);
        canvasField.setBorder(new EmptyBorder(100,50,0,0));
        containerPanel.add(canvasField);

        this.maze = canvasField.getMaze();

        String UP = "UP";
        String DOWN = "DOWN";
        String LEFT = "LEFT";
        String RIGHT = "RIGHT";

        String UP_CTRL = "UP_CTRL";
        String DOWN_CTRL = "DOWN_CTRL";
        String LEFT_CTRL = "LEFT_CTRL";
        String RIGHT_CTRL = "RIGHT_CTRL";

        int buttonI = 0;

        ((JButton) this.components[0]).addActionListener(new RestartAction(this));

        this.controlButtons[buttonI++].addActionListener(new MoveBallButton(Direction.LEFT, maze));
        this.controlButtons[buttonI++].addActionListener(new MoveBallButton(Direction.RIGHT, maze));
        this.controlButtons[buttonI++].addActionListener(new MoveBallButton(Direction.UP, maze));
        this.controlButtons[buttonI].addActionListener(new MoveBallButton(Direction.DOWN, maze));


        canvasField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), LEFT);
        canvasField.getActionMap().put(LEFT, new MoveBallKey(Direction.LEFT, maze));
        canvasField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), RIGHT);
        canvasField.getActionMap().put(RIGHT, new MoveBallKey(Direction.RIGHT, maze));
        canvasField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), UP);
        canvasField.getActionMap().put(UP, new MoveBallKey(Direction.UP, maze));
        canvasField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), DOWN);
        canvasField.getActionMap().put(DOWN, new MoveBallKey(Direction.DOWN, maze));



        // You can also use arrow keys with CTRL key pressed to move right to the next wall in the direction

        canvasField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, KeyEvent.CTRL_DOWN_MASK), LEFT_CTRL);
        canvasField.getActionMap().put(LEFT_CTRL, new MoveBallKey(Direction.LEFT_CTRL, maze));
        canvasField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, KeyEvent.CTRL_DOWN_MASK), RIGHT_CTRL);
        canvasField.getActionMap().put(RIGHT_CTRL, new MoveBallKey(Direction.RIGHT_CTRL, maze));
        canvasField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, KeyEvent.CTRL_DOWN_MASK), UP_CTRL);
        canvasField.getActionMap().put(UP_CTRL, new MoveBallKey(Direction.UP_CTRL, maze));
        canvasField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, KeyEvent.CTRL_DOWN_MASK), DOWN_CTRL);
        canvasField.getActionMap().put(DOWN_CTRL, new MoveBallKey(Direction.DOWN_CTRL, maze));
    }

    private void initFrame() {
        frame = new JFrame("Rook in maze (Move the ball to find the triangle)");
        frame.setSize(500, 600);
        frame.setResizable(false);
    }

    public void initUI() {
        this.successfulMazes = 0;
        JPanel containerPanel = new JPanel();

        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        JPanel controllerPanel = new JPanel(new GridLayout(2, 3));
        controllerPanel.setMaximumSize(new Dimension(10000, 100));

        this.setButtons(controllerPanel);
        this.setCanvas(containerPanel);

        containerPanel.add(controllerPanel);

        frame.add(containerPanel);
        frame.setVisible(true);
    }


    public Game() {
        this.initFrame();
        this.initUI();
    }
}
