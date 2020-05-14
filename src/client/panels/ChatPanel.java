package client.panels;

import client.components.Colors;
import client.components.MKButton;
import client.components.MKChatLabel;
import client.utils.FontUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.regex.Pattern;

public class ChatPanel extends JPanel {
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
    private JPanel chatTool;
    private JPanel chatPane;
    private JTextPane msgInput;
    private JPanel centerPane;
    private JPanel groups;
    private JPanel chatTitleBar;

     public ChatPanel(/*String userid, int token*/) {
//        mainStatus = new MainStatus();
//        mainStatus.setUserId(userid);
//        mainStatus.setToken(token);

        //setSize(width, height);
        //Debug.out("基本框架加载");
        // 绘制基本框架
        initView();
        //Debug.out("获取用户头像");
        //Debug.out("班级列表加载");
        // 获取我的班级
        //getMyClass();
        //Debug.out("获取新消息");
        // 获取有新消息的列表
        //getNewChatBar();
        //Debug.out("读取聊天记录");
        // 读取本地聊天记录
        //addOldChat();
        //Debug.out("聊天引擎加载");
        //GChatClient.getInstance().register(mainStatus.getToken());
        //GChatClient.getInstance().addMsgListener(this);
        //Debug.out("加载完成.");
        // 绘制聊天条
        //showFrame();
    }

    private void initView() {
        //GShadowPane contentPane = new GShadowPane(5, 12);
//        contentPane.setLayout(new BorderLayout());
//        setContentPane(contentPane);
        //createTitleBar();
        setLayout(new BorderLayout());
        createChatPane();
        createChatCard();
        createGroupPane();
    }

    private void createChatPane() {
        //聊天区域面板
        centerPane = new JPanel(new BorderLayout());
        centerPane.setBackground(new Color(0xF2F3F4));
        centerPane.setPreferredSize(new Dimension(paneWidth, paneHeight));
        //centerPane.setBorder(new LineBorder(Color.GREEN));


        //标题栏
        chatTitleBar = new JPanel(new BorderLayout());
        chatTitleBar.setBackground(new Color(0xFDFEFE));
        chatTitleBar.setPreferredSize(new Dimension(paneWidth, titleBarHeight));
        chatTitleBar.setBorder(BorderFactory.createCompoundBorder(chatTitleBar.getBorder(), BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0xD0D3D4))));

        chatTitle = new JLabel("欢迎使用");
        chatTitle.setFont(FontUtil.getDefaultFont(18));
        chatTitle.setBorder(BorderFactory.createCompoundBorder(chatTitle.getBorder(), BorderFactory.createEmptyBorder(0, 25, 0, 0)));     // Margin

        chatTitleBar.add(chatTitle, BorderLayout.WEST);
        centerPane.add(chatTitleBar, BorderLayout.NORTH);
        //centerPane.add(cardPane, BorderLayout.CENTER);
        add(centerPane, BorderLayout.CENTER);
    }

    private void addChatBubble(MKChatLabel bubble, String sender) {
        JPanel panel = new JPanel(new FlowLayout(bubble.isLeft() ? FlowLayout.LEFT : FlowLayout.RIGHT));
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));

        panel.setOpaque(false);

        if (bubble.getPreferredSize().width > paneWidth * 0.65) {
            bubble.setPreferredSize((int) (paneWidth * 0.65));
        }
        panel.add(bubble);
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, panel.getPreferredSize().height));
        chatPane.add(panel);
    }

    /**
     * 删除所有的气泡
     */
    private void removeChatBubble() {
        chatPane.removeAll();
        chatPane.revalidate();
        chatPane.repaint();
    }

    private void createChatCard() {
        JPanel chatCard = new JPanel(new BorderLayout());
        // 聊天区域
        chatPane = new JPanel();
        chatPane.setLayout(new BoxLayout(chatPane, BoxLayout.Y_AXIS));
        chatPane.setBackground(new Color(0xFDFEFE));
        //chatPane.setBorder(new LineBorder(Color.GREEN));

        var chatScrollPane = new JScrollPane(chatPane);
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
        chatCard.add(msgPane, BorderLayout.SOUTH);

        // 发送消息工具栏
        JPanel toolBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ((FlowLayout)toolBar.getLayout()).setHgap(10);
        toolBar.setPreferredSize(new Dimension(paneWidth, msgToolBarHeight));
        toolBar.setBackground(new Color(0xFDFEFE));
        toolBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        toolBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(0xD0D3D4)), toolBar.getBorder()));

        msgPane.add(toolBar, BorderLayout.NORTH);

        // 发送消息文本区域
        msgInput = new JTextPane();
        msgInput.setFont(FontUtil.getDefaultFont(18));
        msgInput.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 12));

        JScrollPane msgScrollPane = new JScrollPane(msgInput);
        msgScrollPane.setBorder(null);
        int msgTextAreaHeight = chatHeight - msgSendHeight - msgToolBarHeight;
        msgScrollPane.setPreferredSize(new Dimension(paneWidth, msgTextAreaHeight));
        msgScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        msgScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        msgScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        toolBar.setBackground(new Color(0xFDFEFE));
        msgPane.add(msgScrollPane, BorderLayout.CENTER);

        // 发送按钮
        JPanel sendPane = new JPanel(new BorderLayout());
        MKButton send = new MKButton("发送", Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);

        // 发送功能
        send.addActionListener(e-> {
            //sendMessage(msgInput.getMessage());
            msgInput.setText("");
        });

        send.setPreferredSize(new Dimension(100, 30));
        sendPane.setBackground(new Color(0xFDFEFE));
        sendPane.add(send, BorderLayout.EAST);
        sendPane.setBorder(new EmptyBorder(8, 0, 8, 8));
        msgPane.add(sendPane, BorderLayout.SOUTH);
        centerPane.add(chatCard);
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




    // 发送信息
//    private void sendMessage(String msg) {
//        new Thread(()-> {
//            try {
//                var result = new StringBuffer();
//                var m = Pattern.compile("(?<!\\\\)<([^<>]*)(?<!\\\\)>").matcher(msg);
//                while (m.find()) {
//                    var img = new FileJson();
//                    img.owner = mainStatus.getUserId();
//                    img.path = m.group(1);
//                    m.appendReplacement(result, "<" + FileJson.parse(GFileClient.getInstance().upload(img).props).fileId + ">");
//                }
//                m.appendTail(result);
//                var message = new MessageJson();
//                message.type = mainStatus.isClass() ? 1 : 0;
//                message.receiver = mainStatus.getTargetId();
//                message.sender = mainStatus.getUserId();
//                message.time = Calendar.getInstance().getTimeInMillis();
//                message.text = result.toString();
//                GChatClient.getInstance().sendMessage(message);
//            } catch (Exception e) {
//                new GMessageDialog(this, "消息发送失败（可能因为插入空图片等原因）", "Error");
//            }
//        }).start();
//    }
}
