package client.frames;

import base.Parser;
import client.components.*;
import client.listener.AbstractMouseListener;
import client.tasks.MKPost;
import client.utils.FontUtil;
import base.json.UserJson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class SignUpFrame extends JFrame {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 300;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel usernameLabel;
    private JLabel idLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private MKTextField usernameField;
    private MKTextField idField;
    private MKPasswordField passwordField;
    private MKPasswordField confirmPasswordField;
    private MKButton confirmButton;
    private JLabel statusLabel;

    private String userid;//用户id
    private String password;//密码
    private String confirmPassword;//确认密码
    private String username;//用户名

    public SignUpFrame() {
        initComponents();
        initView();
        centerScreen();
        setListener();
    }

    public void initComponents() {
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(150, WINDOW_HEIGHT));

        usernameLabel = new JLabel("真实姓名：");
        idLabel = new JLabel("学号：");
        passwordLabel = new JLabel("密码：");
        confirmPasswordLabel = new JLabel("确认密码：");
        statusLabel = new JLabel("密码不正确");

        usernameLabel.setFont(FontUtil.getDefaultFont(18));
        idLabel.setFont(FontUtil.getDefaultFont(18));
        passwordLabel.setFont(FontUtil.getDefaultFont(18));
        confirmPasswordLabel.setFont(FontUtil.getDefaultFont(18));
        statusLabel.setFont(FontUtil.getDefaultFont(14));
        statusLabel.setForeground(Colors.RED);
        statusLabel.setVisible(false);


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
        leftPanel.add(statusLabel);
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

    private void registerEvent() {
        userid = idField.getText();
        password = new String(passwordField.getPassword());
        confirmPassword = new String(confirmPasswordField.getPassword());
        username = usernameField.getText();
        if (userid == null || userid.isEmpty() || userid.isBlank() || userid.length() != 12) {
            showMessage("学号为12位数字");
        } else if (username == null || username.isEmpty() || username.isBlank()) {
            showMessage("请正确输入姓名");
        } else if (password == null || password.isEmpty() || password.isBlank()) {
            showMessage("请输入密码");
        } else if (confirmPassword == null || confirmPassword.isEmpty() || confirmPassword.isBlank()) {
            showMessage("请再次输入密码");
        } else if (!password.equals(confirmPassword)) {
            showMessage("两次密码不匹配");
        } else {
            new Thread(() -> {
                confirmButton.setText("注册中...");
                var user = new UserJson();
                user.userid = userid;
                user.password = Parser.md5(password);
                user.username = username;
                var result = MKPost.getInstance().register(user);
                if (result.code == 0) {
                    showMessage("注册成功！");
                    dispose();
                } else if (result.code == -1) {
                    showMessage("该用户已注册，请直接登录");
                } else {
                    showMessage("注册失败，错误：" + result.code);
                }
            }).start();
        }
    }

    private void showMessage(String message) {
        if (!statusLabel.isVisible()) {
            statusLabel.setVisible(true);
        }
        statusLabel.setText(message);
    }

    private void setListener() {
        confirmButton.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO 完成注册逻辑
                registerEvent();
            }
        });
    }
}
