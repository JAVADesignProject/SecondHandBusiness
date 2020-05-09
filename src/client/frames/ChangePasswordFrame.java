package client.frames;

import client.components.Colors;
import client.components.MKButton;
import client.components.MKPasswordField;
import client.components.VerticalFlowLayout;
import client.listener.AbstractMouseListener;
import client.utils.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ChangePasswordFrame extends JDialog {
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;

    JPanel leftPanel;
    JPanel rightPanel;
    JLabel oldPasswordLabel;
    JLabel newPasswordLabel;
    JLabel confirmNewPasswordLabel;
    MKPasswordField oldPasswordField;
    MKPasswordField newPasswordField;
    MKPasswordField confirmNewPasswordField;
    MKButton confirmButton;

    public ChangePasswordFrame() {
        super(MainFrame.context, "修改密码",true);
        initComponents();
        initView();
        setListener();
        setVisible(true);
    }

    private void initComponents() {
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        oldPasswordLabel = new JLabel("原密码:");
        newPasswordLabel = new JLabel("新密码:");
        confirmNewPasswordLabel = new JLabel("确认新密码:");
        oldPasswordField = new MKPasswordField();
        newPasswordField = new MKPasswordField();
        confirmNewPasswordField = new MKPasswordField();
        confirmButton = new MKButton("确认修改",  Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
//        leftPanel.setBorder(new LineBorder(Colors.MAIN_COLOR));
//        rightPanel.setBorder(new LineBorder(Colors.MAIN_COLOR));

        oldPasswordLabel.setFont(FontUtil.getDefaultFont(18));
        newPasswordLabel.setFont(FontUtil.getDefaultFont(18));
        confirmNewPasswordLabel.setFont(FontUtil.getDefaultFont(18));

        setField(oldPasswordField);
        setField(newPasswordField);
        setField(confirmNewPasswordField);
    }

    private void initView(){
        leftPanel.add(oldPasswordLabel);
        leftPanel.add(newPasswordLabel);
        leftPanel.add(confirmNewPasswordLabel);
        leftPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,10,40,false,false));
        rightPanel.add(oldPasswordField);
        rightPanel.add(newPasswordField);
        rightPanel.add(confirmNewPasswordField);
        rightPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,10,35,true,false));
        rightPanel.add(confirmButton);

        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(MainFrame.context);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel,BorderLayout.CENTER);
    }

    private void setField(MKPasswordField field) {
        field.setPlaceholder("");
        field.setPreferredSize(new Dimension(200,30));
        field.setFont(FontUtil.getDefaultFont(18));
    }

    private void setListener() {
        confirmButton.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO 完成修改密码逻辑
                dispose();
            }
        });
    }
}
