package client.components;

import javax.swing.*;
import java.awt.*;

public class MKPanel extends JPanel {
    public MKPanel() {
        ((FlowLayout)getLayout()).setVgap(0);
        ((FlowLayout)getLayout()).setHgap(0);
    }

    public MKPanel(LayoutManager layoutManager) {
        this();
        setLayout(layoutManager);
    }
}
