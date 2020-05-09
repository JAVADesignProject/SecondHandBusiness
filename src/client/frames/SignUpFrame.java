package client.frames;

import client.components.*;
import client.listener.AbstractMouseListener;
import client.utils.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class SignUpFrame extends JFrame {
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;
    JPanel leftPanel;
    JPanel rightPanel;
    JLabel usernameLabel;
    JLabel idLabel;
    JLabel passwordLabel;
    JLabel confirmPasswordLabel;
    MKTextField usernameField;
    MKTextField idField;
    MKPasswordField passwordField;
    MKPasswordField confirmPasswordField;
    MKButton confirmButton;

    public SignUpFrame() {
        initComponents();
        initView();
        centerScreen();
        setListener();
    }

    public void initComponents() {
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(100, WINDOW_HEIGHT));

        usernameLabel = new JLabel("真实姓名：");
        idLabel = new JLabel("学号：");
        passwordLabel = new JLabel("密码：");
        confirmPasswordLabel = new JLabel("确认密码：");

        usernameLabel.setFont(FontUtil.getDefaultFont(18));
        idLabel.setFont(FontUtil.getDefaultFont(18));
        passwordLabel.setFont(FontUtil.getDefaultFont(18));
        confirmPasswordLabel.setFont(FontUtil.getDefaultFont(18));

        usernameField = new MKTextField();
        idField = new MKTextField();
        passwordField = new MKPasswordField();
        confirmPasswordField = new MKPasswordField();

        usernameField.setPlaceholder("请输入真实姓名");
        idField.setPlaceholder("请输入学号");
        passwordField.setPlaceholder("请输入密码");
        confirmPasswordField.setPlaceholder("请再次确认密码");

        Dimension fieldDimension = new Dimension(200, 30);
        usernameField.setPreferredSize(fieldDimension);
        idField.setPreferredSize(fieldDimension);
        passwordField.setPreferredSize(fieldDimension);
        confirmPasswordField.setPreferredSize(fieldDimension);

        usernameField.setFont(FontUtil.getDefaultFont(18));
        idField.setFont(FontUtil.getDefaultFont(18));
        passwordField.setFont(FontUtil.getDefaultFont(18));
        confirmPasswordField.setFont(FontUtil.getDefaultFont(18));

        confirmButton = new MKButton("注册",  Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
    }

    public void initView() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setTitle("注册");
        setResizable(false);
        setVisible(true);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel,BorderLayout.CENTER);
        leftPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 15, 30, false, false));
        leftPanel.add(usernameLabel);
        leftPanel.add(idLabel);
        leftPanel.add(passwordLabel);
        leftPanel.add(confirmPasswordLabel);
        rightPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 15, 23, true, false));
        rightPanel.add(usernameField);
        rightPanel.add(idField);
        rightPanel.add(passwordField);
        rightPanel.add(confirmPasswordField);
        rightPanel.add(confirmButton);


    }

    private void centerScreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - WINDOW_WIDTH) / 2,
                (tk.getScreenSize().height - WINDOW_HEIGHT) / 2);
    }

    private void setListener() {
        confirmButton.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO 完成注册逻辑
                dispose();
            }
        });
    }
}
