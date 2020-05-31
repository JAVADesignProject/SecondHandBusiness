package client.panels;

import base.KClass;
import base.json.MessageJson;
import base.json.UserJson;
import client.components.ChatBar;
import client.utils.Colors;
import client.components.MKButton;
import client.components.MKChatBubble;
import client.listener.MKListener;
import client.utils.CurrentUser;
import client.tasks.MKChatClient;
import client.tasks.MKFileClient;
import client.tasks.MKPost;
import client.utils.FontUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatPanel extends JPanel implements MKListener {
    private final int width = 840;
    private final int paneHeight = 620;
    private final int groupWidth = 300;
    private final int chatMessageHeight = 160;
    private final int titleBarHeight = 50;
    private final int chatHeight = paneHeight - chatMessageHeight - titleBarHeight;
    private final int msgToolBarHeight = 30;
    private final int msgSendHeight = 35;
    private final int paneWidth = width - groupWidth;
    private final int chatBarHeight = 60;

    private JLabel chatTitle;
    private JPanel chatPanel;
    private JTextPane msgInput;
    private JPanel centerPanel;
    private JPanel groups;
    private JPanel chatTitleBar;
    private JPanel chatCard;
    private JScrollPane msgScrollPane;
    private JScrollPane chatScrollPane;
    private List<ChatBar> chatBars = new ArrayList<>();

     public ChatPanel() {
        // 绘制基本框架
        initView();
        MKChatClient.getInstance().addMsgListener(this);

        //绘制聊天条
         addAndGetChatBar();
    }

    private void initView() {
        setLayout(new BorderLayout());
        createChatPane();
        createGroupPane();
    }

    private void createChatPane() {
        //聊天区域面板
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(0xF2F3F4));
        centerPanel.setPreferredSize(new Dimension(paneWidth, paneHeight));

        //标题栏
        chatTitleBar = new JPanel(new BorderLayout());
        chatTitleBar.setBackground(new Color(0xFDFEFE));
        chatTitleBar.setPreferredSize(new Dimension(paneWidth, titleBarHeight + 5));
        chatTitleBar.setBorder(BorderFactory.createCompoundBorder(chatTitleBar.getBorder(), BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xD0D3D4))));

        chatTitle = new JLabel("欢迎使用");
        chatTitle.setFont(FontUtil.getDefaultFont(18));
        chatTitle.setBorder(BorderFactory.createCompoundBorder(chatTitle.getBorder(), BorderFactory.createEmptyBorder(0, 25, 0, 0)));     // Margin

        chatTitleBar.add(chatTitle, BorderLayout.WEST);
        centerPanel.add(chatTitleBar, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void addChatBubble(MKChatBubble bubble, String sender) {
        JPanel panel = new JPanel(new FlowLayout(bubble.isLeft() ? FlowLayout.LEFT : FlowLayout.RIGHT));
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));

        panel.setOpaque(false);

        if (bubble.getPreferredSize().width > paneWidth * 0.65) {
            bubble.setPreferredSize((int) (paneWidth * 0.65));
        }
        panel.add(bubble);
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, panel.getPreferredSize().height));
        chatPanel.add(panel);
    }

    /**
     * 删除所有的气泡
     */
    private void removeChatBubble() {
        chatPanel.removeAll();
        chatPanel.revalidate();
        chatPanel.repaint();
    }

    private void createChatCard() {
        chatCard = new JPanel(new BorderLayout());
        // 聊天区域
        chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBackground(new Color(0xFDFEFE));

        chatScrollPane = new JScrollPane(chatPanel);
        chatScrollPane.setBorder(null);
        chatScrollPane.setPreferredSize(new Dimension(paneWidth, chatHeight));
        chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        chatScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        chatCard.add(chatScrollPane, BorderLayout.CENTER);

        // 发送消息区域
        JPanel msgPane = new JPanel(new BorderLayout());
        msgPane.setBackground(new Color(0xFDFEFE));
        msgPane.setPreferredSize(new Dimension(paneWidth, chatMessageHeight));
        msgPane.setBorder(new LineBorder(new Color(0xD0D3D4)));
        chatCard.add(msgPane, BorderLayout.SOUTH);

        // 发送消息文本区域
        msgInput = new JTextPane();
        msgInput.setFont(FontUtil.getDefaultFont(18));
        msgInput.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 12));

        msgScrollPane = new JScrollPane(msgInput);
        msgScrollPane.setBorder(null);
        int msgTextAreaHeight = chatHeight - msgSendHeight - msgToolBarHeight;
        msgScrollPane.setPreferredSize(new Dimension(paneWidth, msgTextAreaHeight));
        msgScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        msgScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        msgScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        msgPane.add(msgScrollPane, BorderLayout.CENTER);

        // 发送按钮
        JPanel sendPane = new JPanel(new BorderLayout());
        MKButton send = new MKButton("发送", Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);

        // 发送功能
        send.addActionListener(e-> {
            sendMessage(msgInput.getText());
            msgInput.setText("");
        });

        send.setPreferredSize(new Dimension(100, 30));
        sendPane.setBackground(new Color(0xFDFEFE));
        sendPane.add(send, BorderLayout.EAST);
        sendPane.setBorder(new EmptyBorder(8, 0, 8, 8));
        msgPane.add(sendPane, BorderLayout.SOUTH);
    }

    private void createGroupPane() {
        // 群组区域面板
        JPanel westPane = new JPanel();
        westPane.setBackground(new Color(0xF7F9F9));
        westPane.setPreferredSize(new Dimension(groupWidth, paneHeight));
        add(westPane, BorderLayout.WEST);

        // 群组标题栏
        JPanel groupTitleBar = new JPanel(new BorderLayout());
        groupTitleBar.setBackground(new Color(0xFDFEFE));
        groupTitleBar.setPreferredSize(new Dimension(groupWidth, titleBarHeight));
        groupTitleBar.setBorder(BorderFactory.createCompoundBorder(groupTitleBar.getBorder(), BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(0xD0D3D4))));
        JLabel groupTitle = new JLabel("消息列表");
        groupTitle.setFont(FontUtil.getDefaultFont(18));
        groupTitle.setBorder(BorderFactory.createCompoundBorder(groupTitle.getBorder(), BorderFactory.createEmptyBorder(0, 25, 0, 0)));     // Margin
        groupTitleBar.add(groupTitle, BorderLayout.CENTER);
        westPane.add(groupTitleBar, BorderLayout.NORTH);

        // 群组列表
        groups = new JPanel();
        groups.setOpaque(false);
        groups.setBackground(new Color(0xFDFEFE));
        groups.setLayout(new BoxLayout(groups, BoxLayout.Y_AXIS));

        JScrollPane groupPane = new JScrollPane(groups);
        groupPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        groupPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        groupPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        groupPane.getVerticalScrollBar().setUnitIncrement(16);
        groupPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(0xD0D3D4)));
        groupPane.setPreferredSize(new Dimension(groupWidth, paneHeight));
        westPane.add(groupPane, BorderLayout.CENTER);
    }

    /**
     * 得到应该被加入到聊天栏中的聊天条
     *
     * @return 创建的聊天条
     */
    private ChatBar addAndGetChatBar() {
        for (var i : CurrentUser.targetUsers) {
            var chatBar = new ChatBar(i.userID, i.username, 0, 40, groupWidth, 70);
            chatBar.addActionListener(e-> {
                createChatCard();
                setChatPanel(i);
                chatBar.showCount(false);
                CurrentUser.targetId = i.userID;
                reloadRecord(CurrentUser.userId, i.userID);
                centerPanel.add(chatCard);
            });
            chatBars.add(chatBar);
            groups.add(chatBar.getPane());
            System.out.println("得到应该被加入到聊天栏中的聊天条" + i);
        }
        return null;
    }

    /**
     * 创建聊天气泡
     * @param message 聊天内容
     * @return 创建的聊天气泡
     */
    private MKChatBubble createChatBubble(MessageJson message) {
        var bubble = new MKChatBubble(!message.sender.equals(CurrentUser.userId));
        bubble.setBackground(new Color(bubble.isLeft() ? 0xECF0F1 : 0x5DADE2));
        bubble.setForeground(new Color(bubble.isLeft() ? 0x424949 : 0xFFFFFF));
        bubble.insertText(message.text);
        return bubble;
    }

    /**
     * 保存聊天记录
     * @param id 用户id
     * @param list 聊天记录列表
     */
    private void saveRecord(String id, List<MessageJson> list) {
        try {
            MKFileClient.createDirs(KClass.getRecordPath(CurrentUser.userId));
            var path = KClass.getRecordPath(CurrentUser.userId, id);
            var file = new File(path);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    System.out.println("聊天记录文件创建失败");
                }
            }
            var fout = new FileOutputStream(file, true);
            var writer = new PrintWriter(fout);
            for (var i : list) {
                writer.println(i.toString());
            }
            writer.close();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addChatBubble(List<MessageJson> list, boolean append) {
        int maxChatCount = 10;
        if (list.size() > maxChatCount && !append) {
            list = list.subList(list.size() - maxChatCount - 1, list.size());
        }
        for (var i : list) {
            addChatBubble(createChatBubble(i), MKPost.getInstance().getUserInfo(i.sender).username);
        }
    }

    // 重新加载消息记录
    private void reloadRecord(String sender, String receiver) {
        // 先获取最新的消息记录
        var message = new MessageJson();
        message.sender = sender;
        message.receiver = receiver;
        var list = MKPost.getInstance().getMessage(message);

        // 将消息列表加入气泡
        removeChatBubble();
        addChatBubble(list, false);
        chatPanel.repaint();
        chatPanel.updateUI();
    }

    // 设置聊天面板内容
    private void setChatPanel(UserJson userJson) {
        if (CurrentUser.userId.equals(userJson.userID))
            return;
        chatTitle.setText(userJson.username);
        var msg = new MessageJson();
        msg.sender = userJson.userID;
        msg.receiver = CurrentUser.userId;
        msg.type = 0;
    }

    // 发送信息
    private void sendMessage(String msg) {
        new Thread(()-> {
            try {
                var message = new MessageJson();
                message.receiver = CurrentUser.targetId;
                message.sender = CurrentUser.userId;
                message.time = Calendar.getInstance().getTimeInMillis();
                message.text = msg;
                System.out.println(message.sender + "\n" + message.receiver + "\n" + message.time + "\n" + message.text);
                addChatBubble(createChatBubble(message), CurrentUser.userId);
                MKChatClient.getInstance().sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onReceiveMessage(MessageJson message) {
        // 获取真正的接收者，如果发送者是自己那么接收者就是消息的接收者，否则是消息发送者
        var receiver = CurrentUser.userId.equals(message.sender) ? message.receiver : message.sender;
        var user = new UserJson();
        user.userID = receiver;
        var chatBar = addAndGetChatBar();
        var list = new ArrayList<MessageJson>();
        list.add(message);
        // 1.保存聊天记录
        saveRecord(receiver, list);
        // 判断当前聊天窗口是否是同一个
        if (CurrentUser.targetId.equals(receiver)) {
            // 当前正是和这条消息中的人聊天
            // 把消息添加到当前聊天窗口
            addChatBubble(list, true);
            chatPanel.revalidate();
            Rectangle rect = new Rectangle(0, chatPanel.getPreferredSize().height,10,10);
            chatPanel.scrollRectToVisible(rect);
        } else {
            // 添加新消息提示
            chatBar.showCount(true);
        }
    }
}
