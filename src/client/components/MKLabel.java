package client.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;

public class MKLabel extends JLabel {
    private final Duration animationTime = Duration.ofMillis(200);
    //private ColorAnimatable transitionAnimatable;

    private final Color textColor = Color.WHITE;
    private final Color normalColor = new Color(0x515A5A);
    private final Color hoverColor = new Color(0x616A6B);
    private final Color pressColor = new Color(0x424949);

    private final boolean isPressed = false;
    private final boolean isRollover = false;

    protected ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

    public void addActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public MKLabel() {}

}
