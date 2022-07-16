package sk.stuba.fei.uim.oop;

public class MoveBallDriver {
    public void moveBall(Direction direction, Maze maze) {
        Ball ball = maze.getBall();
        int[] position = ball.getPosition();
        int posX = position[0];
        int posY = position[1];

        switch (direction) {
            case DOWN:
                if(!maze.getNorth()[posX][posY]) {
                    ball.setPosition(posX, posY + 1);
                }
                break;

            case UP:
                if(!maze.getSouth()[posX][posY]) {
                    ball.setPosition(posX, posY - 1);
                }
                break;

            case LEFT:
                if(!maze.getWest()[posX][posY]) {
                    ball.setPosition(posX - 1, posY);
                }
                break;

            case RIGHT:
                if(!maze.getEast()[posX][posY]) {
                    ball.setPosition(posX + 1, posY);
                }
                break;

            case DOWN_CTRL:
                while(!maze.getNorth()[posX][posY]) {
                    ball.setPosition(posX, posY + 1);
                    position = ball.getPosition();
                    posX = position[0];
                    posY = position[1];
                }
                break;

            case UP_CTRL:
                while(!maze.getSouth()[posX][posY]) {
                    ball.setPosition(posX, posY - 1);
                    position = ball.getPosition();
                    posX = position[0];
                    posY = position[1];
                }
                break;

            case LEFT_CTRL:
                while(!maze.getWest()[posX][posY]) {
                    ball.setPosition(posX - 1, posY);
                    position = ball.getPosition();
                    posX = position[0];
                    posY = position[1];
                }
                break;

            case RIGHT_CTRL:
                while(!maze.getEast()[posX][posY]) {
                    ball.setPosition(posX + 1, posY);
                    position = ball.getPosition();
                    posX = position[0];
                    posY = position[1];
                }
                break;
        }
    }
}
