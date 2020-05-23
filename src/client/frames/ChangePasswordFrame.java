package client.frames;

import base.Parser;
import base.json.UserJson;
import client.components.Colors;
import client.components.MKButton;
import client.components.MKPasswordField;
import client.components.VerticalFlowLayout;
import client.listener.AbstractMouseListener;
import client.tasks.CurrentUser;
import client.tasks.MKPost;
import client.utils.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ChangePasswordFrame extends JDialog {
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 300;

    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel oldPasswordLabel;
    private JLabel newPasswordLabel;
    private JLabel confirmNewPasswordLabel;
    private JLabel statusLabel;
    private MKPasswordField oldPasswordField;
    private MKPasswordField newPasswordField;
    private MKPasswordField confirmNewPasswordField;
    private MKButton confirmButton;

    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

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
        statusLabel = new JLabel();

        oldPasswordField = new MKPasswordField();
        newPasswordField = new MKPasswordField();
        confirmNewPasswordField = new MKPasswordField();

        confirmButton = new MKButton("确认修改",  Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);

        oldPasswordLabel.setFont(FontUtil.getDefaultFont(18));
        newPasswordLabel.setFont(FontUtil.getDefaultFont(18));
        confirmNewPasswordLabel.setFont(FontUtil.getDefaultFont(18));
        statusLabel.setFont(FontUtil.getDefaultFont(14));
        statusLabel.setForeground(Colors.RED);
        //statusLabel.setVisible(false);

        setField(oldPasswordField);
        setField(newPasswordField);
        setField(confirmNewPasswordField);
    }

    private void initView(){
        leftPanel.add(oldPasswordLabel);
        leftPanel.add(newPasswordLabel);
        leftPanel.add(confirmNewPasswordLabel);
        leftPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP,10,40,false,false));
        leftPanel.add(statusLabel);

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

    private void changePassword() {
        oldPassword = String.valueOf(oldPasswordField.getPassword());
        newPassword = String.valueOf(newPasswordField.getPassword());
        confirmNewPassword = String.valueOf(confirmNewPasswordField.getPassword());
        if (oldPassword == null || oldPassword.isEmpty() || oldPassword.isBlank()) {
            showMessage("请输入原密码");
        } else if (newPassword == null || newPassword.isEmpty() || newPassword.isBlank()){
            showMessage("请输入新密码");
        } else if (confirmNewPassword == null || confirmNewPassword.isEmpty() || confirmNewPassword.isBlank()) {
            showMessage("请再次输入新密码");
        } else if (!newPassword.equals(confirmNewPassword)) {
            showMessage("两次密码不匹配");
        } else if (!Parser.md5(oldPassword).equals(CurrentUser.password)) {
            showMessage("原密码输入错误");
        } else {
            new Thread(() -> {
                confirmButton.setText("修改中...");
                var user = new UserJson();
                user.userID = CurrentUser.userId;
                CurrentUser.password = Parser.md5(newPassword);
                user.password = Parser.md5(newPassword);
                user.username = CurrentUser.username;
                var result = MKPost.getInstance().updatePassword(user);
                if (result.code == 0) {
                    showMessage("修改成功！");
                    dispose();
                } else {
                    showMessage("修改失败，错误：" + result.code);
                }
            }).start();
        }
    }

    private void setListener() {
        confirmButton.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changePassword();
            }
        });
    }

    private void showMessage(String message) {
        if (!statusLabel.isVisible()) {
            statusLabel.setVisible(true);
        }
        statusLabel.setText(message);
    }
}
