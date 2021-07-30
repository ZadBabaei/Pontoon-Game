import javax.swing.*;
import java.awt.*;

public class GamePlay extends JButton {
    private int xCoord, yCoord;

    public GamePlay(int xCoord, int yCoord) {
        super();
        this.setSize(50, 50);
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public void setColor() {

        Color color = Color.white;
        this.setBackground(color);
    }

    // this function take the state as the parameter and base on the state it will switch the color for the players
    public void switchColor(String state) {
        Color color;
        if (state.equals("player1")) {
            color = Color.blue;
        } else if (state.equals("player2")) {
            color = Color.orange;
        } else {
            color = Color.white;
        }
        this.setBackground(color);
    }
}
