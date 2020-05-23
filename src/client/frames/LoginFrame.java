package client.frames;

import base.Parser;
import client.components.*;
import client.listener.AbstractMouseListener;
import client.tasks.CurrentUser;
import client.tasks.MKPost;
import client.utils.FontUtil;
import base.json.UserJson;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 400;

    private JPanel editPanel;
    private MKTextField usernameField;
    private MKPasswordField passwordField;
    private MKButton loginButton;
    private MKButton signUpButton;
    private JLabel statusLabel;
    private JLabel titleLabel;

    private String username;
    private String password;

    public LoginFrame() {
//        initService();
        initComponents();                                                                                               //组件
        initView();                                                                                                     //视图
        centerScreen();
        setListeners();
    }

    public LoginFrame(String username) {
        this();
        this.username = username;
        if (username != null && !username.isEmpty())
        {
            usernameField.setText(username);
        }
    }

    private void initComponents() {
        setResizable(false);                                                                                            //禁止窗体调整大小
        setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));                                                                                            //设置窗体尺寸

        titleLabel = new JLabel();
        titleLabel.setText("欢迎使用氪氪二手");
        titleLabel.setFont(FontUtil.getDefaultFont(18));

        editPanel = new JPanel();                                                                                       //新建编辑区域面板
        editPanel.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        editPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 0, 8, true, false));  //设置面板为自定义的垂直流布局

        Dimension textFieldDimension = new Dimension(218, 42);
        usernameField = new MKTextField();                                                                              //新建一个自定义的文本框
        usernameField.setPlaceholder("用户名");                                                                          //设置文本框内提示内容
        usernameField.setPreferredSize(textFieldDimension);                                                             //设置文本框大小
        usernameField.setFont(FontUtil.getDefaultFont(16));                                                        //设置输入文字的字体大小
        usernameField.setForeground(Colors.FONT_BLACK);                                                                 //设置输入文字的字体颜色
        usernameField.setMargin(new Insets(0, 15, 0, 0));                                      //设置文本与边框的距离
        usernameField.setBorder(new MatteBorder(0, 0, 1, 0, Colors.DIALOG_BORDER));

        passwordField = new MKPasswordField();                                                                          //新建一个自定义的密码框
        passwordField.setPlaceholder("密码");                                                                            //设置密码框内提示内容
        passwordField.setPreferredSize(textFieldDimension);                                                             //设置密码框大小
        passwordField.setFont(FontUtil.getDefaultFont(16));                                                        //设置输入文字的字体大小
        passwordField.setForeground(Colors.FONT_BLACK);                                                                 //设置输入文字的字体颜色
        passwordField.setMargin(new Insets(0, 15, 0, 0));                                       //设置文本与边框的距离
        passwordField.setBorder(new MatteBorder(0, 0, 1, 0, Colors.DIALOG_BORDER));

        loginButton = new MKButton("登 录", Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER); //新建一个自定义的按钮
        loginButton.setFont(FontUtil.getDefaultFont(18));                                                          //设置按钮中的字体大小
        loginButton.setPreferredSize(new Dimension(218, 47));                                             //设置按钮尺寸

        signUpButton = new MKButton("注 册", Colors.WINDOW_BACKGROUND, Colors.SCROLL_BAR_TRACK_LIGHT, Colors.SCROLL_BAR_TRACK_LIGHT);
        signUpButton.setFont(FontUtil.getDefaultFont(18));
        signUpButton.setPreferredSize(new Dimension(218, 47));
        signUpButton.setForeground(Colors.DARKER);

        statusLabel = new JLabel();                                                                                     //新建密码输入错误的提示标签
        statusLabel.setForeground(Colors.RED);
        statusLabel.setText("密码不正确");
        statusLabel.setVisible(false);
    }

    private void initView() {
        JPanel contentPanel = new JPanel();                                                                             //新建内容面板
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);

        JPanel titlePanel = new JPanel();                                                                               //新建标题区域面板
        titlePanel.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        titlePanel.add(titleLabel);

        editPanel.add(usernameField);
        editPanel.add(passwordField);
        editPanel.add(loginButton);
        editPanel.add(signUpButton);
        editPanel.add(statusLabel);

        add(contentPanel);
        contentPanel.add(titlePanel,
                new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 1).setInsets(10, 10, 0, 10));
        contentPanel.add(editPanel,
                new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 10).setInsets(10, 10, 0, 10));
    }

    private void centerScreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - WINDOW_WIDTH) / 2,
                (tk.getScreenSize().height - WINDOW_HEIGHT) / 2);
    }

    private void setListeners() {
        loginButton.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (loginButton.isEnabled()) {
                    doLogin();
                }
                super.mouseClicked(e);
            }
        });

        signUpButton.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO 注册
                SignUpFrame frame = new SignUpFrame();
                super.mouseClicked(e);
            }
        });

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    doLogin();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        usernameField.addKeyListener(keyListener);
        passwordField.addKeyListener(keyListener);
    }

    private void doLogin() {
        // TODO: 登录逻辑
        username = usernameField.getText();
        password = new String(passwordField.getPassword());
        if (username == null || username.isEmpty() || username.isBlank() || username.length() != 12) {
            showMessage("请输入学号");
        } else if (password == null || password.isEmpty() || password.isBlank()) {
            showMessage("请输入密码");
        } else {
            UserJson user = new UserJson();
            if (Character.isDigit(username.charAt(0))) {
                user.userID = username;
            } else {
                user.username = username;
            }
            user.password = Parser.md5(password);
            var result = MKPost.getInstance().login(user);
            if (result.code == 0) {
                showMessage("登录成功，加载主界面");
                user = MKPost.getInstance().getUserInfo(username);
                CurrentUser.userId = user.userID;
                CurrentUser.username = user.username;
                CurrentUser.password = user.password;
                CurrentUser.status = user.status;
                CurrentUser.targetUsers = MKPost.getInstance().getChatUser(user);
                System.out.println(CurrentUser.targetUsers);
                MainFrame frame = new MainFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                dispose();
            } else {
                showMessage("登录失败" + result.code);
            }
        }
    }

    private void showMessage(String message) {
        if (!statusLabel.isVisible()) {
            statusLabel.setVisible(true);
        }
        statusLabel.setText(message);
    }

}