package sk.stuba.fei.uim.oop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestartAction implements ActionListener {
    private Game game;
    public RestartAction(Game game) {
        this.game = game;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        this.game.setSuccessfulMazes(0);
        this.game.getLabel().setText("Count of wins: 0");
        this.game.getMaze().updateMaze();
    }
}
