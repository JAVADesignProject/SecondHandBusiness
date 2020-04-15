package frames;

import components.MKButton;

import javax.swing.*;

public class ChangePasswordFrame extends JDialog {
    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 300;

    JPanel panel;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    MKButton confirmBottom;

    public ChangePasswordFrame() {
        super(MainFrame.context, "修改密码",true);
        initComponents();
        initView();
        setVisible(true);
    }

    private void initComponents() {

    }

    private void initView(){
        
    }
}
