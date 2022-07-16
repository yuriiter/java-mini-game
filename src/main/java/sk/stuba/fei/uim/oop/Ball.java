package sk.stuba.fei.uim.oop;

public class Ball {
    private int positionX;
    private int positionY;


    public Ball(int x, int y) {
        positionX = x;
        positionY = y;
    }

    public int[] getPosition() {
        return new int[]{positionX, positionY};
    }
    public void setPosition(int x, int y) {
        positionX = x;
        positionY = y;
    }
}
