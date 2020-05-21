package client.panels;

import base.KClass;
import base.json.MessageJson;
import base.json.UserJson;
import client.components.ChatBar;
import client.components.Colors;
import client.components.MKButton;
import client.components.MKChatBubble;
import client.listener.MKListener;
import client.tasks.CurrentUser;
import client.tasks.MKChatClient;
import client.tasks.MKFileClient;
import client.tasks.MKPost;
import client.utils.FontUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

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
    private JPanel chatTool;
    private JPanel chatPane;
    private JTextPane msgInput;
    private JPanel centerPane;
    private JPanel groups;
    private JPanel chatTitleBar;
    private List<ChatBar> chatBars = new ArrayList<>();

     public ChatPanel(/*String userId, int token*/) {
//        mainStatus = new MainStatus();
//        mainStatus.setUserId(userId);
//        mainStatus.setToken(token);

        //setSize(width, height);

        // 绘制基本框架
        initView();

        // 获取我的班级
        //getMyClass();

        // 获取有新消息的列表
        //getNewChatBar();

        // 读取本地聊天记录
        //addOldChat();

        //MKChatClient.getInstance().register(mainStatus.getToken());
        MKChatClient.getInstance().addMsgListener(this);

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

    private void addChatBubble(MKChatBubble bubble, String sender) {
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
            sendMessage(msgInput.getText());
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






    /**
     * 得到应该被加入到聊天栏中的聊天条
     * @param user 用户信息
     * @return 创建的聊天条
     */
    private ChatBar addAndGetChatBar(UserJson user) {
        // 判断是否已经被添加
        for (var i : chatBars) {
            if (i.getId().equals(user.userid)) {
                return i;
            }
        }
        user = MKPost.getInstance().getUserInfo(user);
        var chatBar = new ChatBar(user.userid, user.username, 0, 40, groupWidth, 70);
        UserJson finalUser = user;
        chatBar.addActionListener(e-> {
            setChatPane(finalUser);
            chatBar.showCount(false);
        });
        chatBars.add(chatBar);
        groups.add(chatBar.getPane());
        return chatBar;
    }

    void chatTo(UserJson user) {
        if (user.userid.equals(CurrentUser.userId)) {
            // 啥玩意啊，你还想整个跟自己的聊天啊
            //Debug.log("Edge最帅");
            System.out.println("不能跟自己聊天哦");
        } else {
            //toFront();
            addAndGetChatBar(user).showCount(false);
            setChatPane(user);
            //switchCardPane(CHAT_CARD);
        }
    }

    private void repaintChatBar() {
        chatBars.sort((a, b) -> {
            if (a.getType() == b.getType()) {
                if (a.isShowCount() && b.isShowCount()) return 0;
                else if (a.isShowCount()) return -1;
                else return 1;
            } else if (a.getType() > b.getType()) {
                return -1;
            } else {
                return 1;
            }
        });
        groups.removeAll();
        for (var i : chatBars) {
            groups.add(i.getPane());
        }
        groups.revalidate();
        groups.repaint();
    }

    /**
     * 在窗体加载时的新消息列表
     */
    private void getNewChatBar() {
        var list = MKPost.getInstance().getChatUser();
        if (list == null) {
            return;
        }
        for (var userJson : list) {
            String title = userJson.username;
            final var chatBar = new ChatBar(userJson.userid, title, 0, 40, groupWidth, 70);
            chatBar.addActionListener(e -> {
                setChatPane(userJson);
                chatBar.showCount(false);
            });
            chatBars.add(chatBar);
            groups.add(chatBar.getPane());
        }
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
     * @param id 用户或班级id
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

    private List<String> getRecord(String id) {
        try {
            var path = KClass.getRecordPath(CurrentUser.userId, id);
            var file = new File(path);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            var fin = new FileInputStream(file);
            var reader = new BufferedReader(new InputStreamReader(fin));
            var line = "";
            List<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null)   {
                lines.add(line);
            }
            reader.close();
            fin.close();
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    // 重新加载消息记录
    private void reloadRecord(MessageJson message) {
        // 先获取最新的消息记录
        var list = MKPost.getInstance().getMessage(message);
        var target = message.type == 0 ? message.sender : message.receiver;
        if (list != null)
            saveRecord(target, list);
        removeChatBubble();
        var lines = getRecord(target);

        // 最多显示50条消息
        List<MessageJson> msgList = new ArrayList<>();
        for (var i : lines) {
            var msg = MessageJson.parse(i);
            msgList.add(msg);
        }

        // 将消息列表加入气泡
        addChatBubble(msgList, false);
    }

    // 设置聊天面板内容
    private void setChatPane(UserJson userJson) {
        if (CurrentUser.userId.equals(userJson.userid))
            return;
//        if (!cardPane.isVisible())
//            cardPane.setVisible(true);
        chatTitle.setText(userJson.username);
        chatTool.setVisible(false);
        var msg = new MessageJson();
        CurrentUser.targetId = userJson.userid;
        //mainStatus.setClass(false);
        msg.sender = userJson.userid;
        msg.receiver = CurrentUser.userId;
        msg.type = 0;
        //switchCardPane(CHAT_CARD);
        reloadRecord(msg);
    }

    // 发送信息
    private void sendMessage(String msg) {
        new Thread(()-> {
            try {
//                var result = new StringBuffer();
//                var m = Pattern.compile("(?<!\\\\)<([^<>]*)(?<!\\\\)>").matcher(msg);
//                while (m.find()) {
//                    var img = new FileJson();
//                    img.owner = mainStatus.getUserId();
//                    img.path = m.group(1);
//                    m.appendReplacement(result, "<" + FileJson.parse(GFileClient.getInstance().upload(img).props).fileId + ">");
//                }
//                m.appendTail(result);
                var message = new MessageJson();
                //message.type = mainStatus.isClass() ? 1 : 0;
                message.receiver = "1000";
                message.sender = CurrentUser.userId;
                message.time = Calendar.getInstance().getTimeInMillis();
                message.text = msg;
                System.out.println(message.sender + "\n" + message.receiver + "\n" + message.time + "\n" + message.text);
                MKChatClient.getInstance().sendMessage(message);
            } catch (Exception e) {
                //new GMessageDialog(this, "消息发送失败（可能因为插入空图片等原因）", "Error");
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 获取旧聊天记录文件夹中的用户
     * @return .
     */
    private List<String> getOldChatUser() {
        List<String> files = new ArrayList<>();
        File file = new File(KClass.getRecordPath(CurrentUser.userId));

        File[] tempList = file.listFiles();
        if (tempList == null) return null;
        for (File aTempList : tempList) {
            if (aTempList.isFile()) {
                files.add(aTempList.getName());
            }
        }
        return files;
    }

    /**
     * 聊天记录加载到面板
     */
    private void addOldChat() {
        var list = getOldChatUser();
        if (list == null) return;
        for(var u : list) {
            var user = new UserJson();
            user.userid = u;
            user = MKPost.getInstance().getUserInfo(user);
            if (user != null) addAndGetChatBar(user).showCount(false);
        }
    }

    @Override
    public void onReceiveMessage(MessageJson message) {
        // 获取真正的接收者，如果发送者是自己那么接收者就是消息的接收者，否则是消息发送者
        var receiver = CurrentUser.userId.equals(message.sender) ? message.receiver : message.sender;
        var user = new UserJson();
        user.userid = receiver;
        var chatBar = addAndGetChatBar(MKPost.getInstance().getUserInfo(user));
        var list = new ArrayList<MessageJson>();
        list.add(message);
        // 1.保存聊天记录
        saveRecord(receiver, list);
        // 判断当前聊天窗口是否是同一个
        if (CurrentUser.targetId.equals(receiver)) {
            // 当前正是和这条消息中的人聊天
            // 把消息添加到当前聊天窗口
            addChatBubble(list, true);
            chatPane.revalidate();
            Rectangle rect = new Rectangle(0,chatPane.getPreferredSize().height,10,10);
            chatPane.scrollRectToVisible(rect);
        } else {
            // 添加新消息提示
            chatBar.showCount(true);
        }
    }

}
