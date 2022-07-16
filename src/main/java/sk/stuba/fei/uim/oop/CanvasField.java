package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;


public class CanvasField extends JPanel implements ActionListener {
    private final int winWidth = 485;
    private final int canvasSide = 400;
    private final double start = ((double) this.winWidth - (double) this.canvasSide) / 2.0;
    private final int containerStrokeWidth = 6;
    private final int innerLength = this.canvasSide - containerStrokeWidth;
    private final double littleSide = (double) innerLength / (double) Maze.DIMENSION;
    private final double insideStart = (this.start + ((double) containerStrokeWidth / 2.0));

    private boolean playerClicked = false;

    private Maze maze = new Maze();
    private Game game;

    private int[] mousePos;
    private int[] mouseMoveTile;

    public Game getGame() {
        return this.game;
    }

    public boolean hasPlayerClicked() {
        return this.playerClicked;
    }
    public void setPlayerClicked(boolean playerClicked) {
        this.playerClicked = playerClicked;
    }

    public int[] getMousePos() {
        return mousePos;
    }


    public void setMousePos(int[] mousePos) {
        this.mousePos = mousePos;
    }

    public int[] getMouseMoveTile() {
        return mouseMoveTile;
    }

    public CanvasField(Game game) {
        this.game = game;
        addMouseMotionListener(new MousePositioning(this));
        addMouseListener(new MoveBallOnClick(this));
        Timer t = new Timer(1000 / 60,this);
        t.start();
    }

    public Maze getMaze() {
        return this.maze;
    }

    public void drawing(){
        repaint();
    }


    private void updateLabel() {
        int successfulMazes = this.game.getSuccessfulMazes();
        successfulMazes++;
        this.game.setSuccessfulMazes(successfulMazes);
        this.game.getLabel().setText("Count of wins: " + successfulMazes);
    }


    public boolean isHoveringPlayer(int mouseX, int mouseY) {
        int[] startPosition = maze.getBall().getPosition();
        startPosition = new int[]{startPosition[0] - 1, startPosition[1] - 1};

        int xSquare = (int) ((mouseX - this.insideStart) / littleSide);
        int ySquare = (int) ((mouseY - this.insideStart) / littleSide);

        if(startPosition[0] == xSquare && startPosition[1] == ySquare) {
            return true;
        }
        else {
            return false;
        }
    }

    private void drawMaze(Graphics2D g2) {
        Color wallColor = new Color(108,122,224);
        g2.setColor(wallColor);

        for(int x = 1; x <= Maze.DIMENSION; x++) {
            for(int y = 1; y <= Maze.DIMENSION; y++) {
                double xPix1 = this.insideStart + (x - 1) * littleSide;
                double xPix2 = this.insideStart + (x) * littleSide;
                double yPix1 = this.insideStart + (y - 1) * littleSide;
                double yPix2 = this.insideStart + y * littleSide;

                if(maze.getSouth()[x][y]) g2.draw(new Line2D.Double(xPix1, yPix1, xPix2, yPix1));
                if(maze.getNorth()[x][y]) g2.draw(new Line2D.Double(xPix1, yPix2, xPix2, yPix2));
                if(maze.getWest()[x][y])  g2.draw(new Line2D.Double(xPix1, yPix1, xPix1, yPix2));
                if(maze.getEast()[x][y]) g2.draw(new Line2D.Double(xPix2, yPix1, xPix2, yPix2));
            }
        }

        int[] startPosition = maze.getBall().getPosition();
        int[] endPosition = maze.getEndPosition();

        startPosition = new int[]{startPosition[0] - 1, startPosition[1] - 1};

        if(startPosition[0] == endPosition[0] && startPosition[1] == endPosition[1]) {
            this.maze.updateMaze();
            this.updateLabel();
        }

        Color playerColor = new Color(0xFF2CD2);
        Color targetColor = new Color(0xFF5454);

        int xStart = startPosition[0];
        int yStart = startPosition[1];

        int xEnd = endPosition[0];
        int yEnd = endPosition[1];

        double xPixStart = this.insideStart + xStart * littleSide + littleSide / 4.0;
        double yPixStart = this.insideStart + yStart * littleSide + littleSide / 4.0;

        double xPixEnd1 = this.insideStart + xEnd * littleSide;
        double xPixEnd2 = this.insideStart + (xEnd + 1) * littleSide;
        double yPixEnd1 = this.insideStart + yEnd * littleSide;
        double yPixEnd2 = this.insideStart + (yEnd + 1) * littleSide;

        double xPixEndCenter = (xPixEnd1 + xPixEnd2) / 2.0;
        double yPixEndCenter = (yPixEnd1 + yPixEnd2) / 2.0;

        double rPixStart = littleSide / 2.0;

        double rPixEnd = littleSide / 3.0;

        //

        this.mouseMoveTile = null;
        if(this.mousePos != null) {
            double mouseX = this.mousePos[0];
            double mouseY = this.mousePos[1];

            int xSquare = (int) ((mouseX - this.insideStart) / littleSide);
            int ySquare = (int) ((mouseY - this.insideStart) / littleSide);

            if(!(xSquare < 0 || xSquare >= Maze.DIMENSION
                    || ySquare < 0 || ySquare >= Maze.DIMENSION)) {


                Color highlight = new Color(255, 200, 238, 255);
                g2.setColor(highlight);

                double lRect = littleSide - 2;
                if(xStart == xSquare) {
                    int yStartCopy = yStart;
                    double xRect = this.insideStart + xSquare * littleSide + 1;
                    double yRect = this.insideStart + yStartCopy * littleSide + 1;
                    g2.fill(new Rectangle2D.Double(xRect, yRect, lRect, lRect));

                    int coeff = 0;
                    boolean[][] walls = new boolean[0][];
                    boolean same = false;
                    if(ySquare < yStartCopy) {
                        coeff = -1;
                        walls = maze.getSouth();
                    }
                    else if(ySquare > yStartCopy){
                        coeff = 1;
                        walls = maze.getNorth();
                    }
                    else {
                        same = true;
                    }
                    if(!same) {
                        while(yStartCopy != ySquare && !walls[xSquare + 1][yStartCopy + 1]) {
                            xRect = this.insideStart + xSquare * littleSide + 1;
                            yRect = this.insideStart + yStartCopy * littleSide + 1;
                            g2.fill(new Rectangle2D.Double(xRect, yRect, lRect, lRect));
                            yStartCopy += coeff;
                            this.mouseMoveTile = new int[]{xSquare, yStartCopy};
                        }
                        xRect = this.insideStart + xSquare * littleSide + 1;
                        yRect = this.insideStart + yStartCopy * littleSide + 1;
                        g2.fill(new Rectangle2D.Double(xRect, yRect, lRect, lRect));
                    }
                }
                else if(yStart == ySquare){
                    int xStartCopy = xStart;

                    double xRect = this.insideStart + xStartCopy * littleSide + 1;
                    double yRect = this.insideStart + ySquare * littleSide + 1;

                    g2.fill(new Rectangle2D.Double(xRect, yRect, lRect, lRect));

                    int coeff;
                    boolean[][] walls;
                    if(xSquare < xStartCopy) {
                        coeff = -1;
                        walls = maze.getWest();
                    }
                    else {
                        coeff = 1;
                        walls = maze.getEast();
                    }

                    while(xStartCopy != xSquare && !walls[xStartCopy + 1][ySquare + 1]) {
                        xRect = this.insideStart + xStartCopy * littleSide + 1;
                        yRect = this.insideStart + ySquare * littleSide + 1;
                        lRect = littleSide - 2;
                        g2.fill(new Rectangle2D.Double(xRect, yRect, lRect, lRect));
                        xStartCopy += coeff;
                    }
                    this.mouseMoveTile = new int[]{xStartCopy, ySquare};
                    xRect = this.insideStart + xStartCopy * littleSide + 1;
                    yRect = this.insideStart + ySquare * littleSide + 1;
                    g2.fill(new Rectangle2D.Double(xRect, yRect, lRect, lRect));
                }

            }
        }


        //

        g2.setColor(playerColor);
        g2.fill(new Ellipse2D.Double(xPixStart, yPixStart, rPixStart, rPixStart));

        g2.setColor(targetColor);

        double[] a1 = new double[]{xPixEndCenter, yPixEndCenter - rPixEnd + littleSide * 0.05};
        double[] a2 = new double[]{xPixEndCenter - Math.sqrt(3) * rPixEnd / 2.0, a1[1] + 3.0 * rPixEnd / 2.0};
        double[] a3 = new double[]{xPixEndCenter + Math.sqrt(3) * rPixEnd / 2.0, a1[1] + 3.0 * rPixEnd / 2.0};

        Path2D triangle = new Path2D.Double();
        triangle.moveTo(a1[0], a1[1]);
        triangle.lineTo(a2[0], a2[1]);
        triangle.lineTo(a3[0], a3[1]);
        triangle.lineTo(a1[0], a1[1]);

        g2.fill(triangle);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        Color path = new Color(0xeeeeee);

        g2.setStroke(new BasicStroke(containerStrokeWidth));
        g2.draw(new Rectangle2D.Double(this.start, this.start, this.canvasSide, this.canvasSide));

        g2.setStroke(new BasicStroke(2));

        Color basicGridColor = new Color(219, 219, 219);
        g2.setColor(basicGridColor);
        for(int a = 1; a < Maze.DIMENSION + 1; a++) {
            double aPix = insideStart + a * littleSide;
            g2.draw(new Line2D.Double(insideStart, aPix, insideStart + innerLength, aPix));
            g2.draw(new Line2D.Double(aPix, insideStart, aPix, insideStart + innerLength));
        }

        this.drawMaze(g2);

//        g2.setColor(new Color(0xFFEEEEEE, true));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
