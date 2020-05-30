package client.frames;

import base.json.CommentJson;
import base.json.MessageJson;
import base.json.ProductionJson;
import client.components.*;
import client.listener.AbstractMouseListener;
import client.tasks.MKChatClient;
import client.tasks.MKPost;
import client.utils.CurrentUser;
import client.utils.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

public class AuctionProductionFrame extends JFrame {
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 1150;

    public static AuctionProductionFrame context;
    public ProductionJson production;
    public File picture;
    public List<CommentJson> comments;

    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel namePanel;
    private JPanel posterPanel;
    private JPanel auctionPanel;
    private JPanel commentsPanel;
    private JScrollPane scrollPane;

    private JLabel productionName;
    private JLabel pictureLabel;
    private JLabel introductionLabel;
    private JLabel priceLabel;
    private JLabel posterLabel;
    private JLabel commentLabel;
    private MKTextField priceField;
    private MKTextArea commentArea;
    private MKButton buy;
    private MKButton addToCollection;
    private MKButton follow;
    private MKButton comment;

    public AuctionProductionFrame(ProductionJson production, File picture) {
        context = this;
        this.production = production;
        this.picture = picture;
        initComponents();
        centerScreen();
        initView();
        setListener();
    }

    private void initComponents() {
        leftPanel = new JPanel();
        centerPanel = new JPanel();

        leftPanel.setPreferredSize(new Dimension(10, WINDOW_HEIGHT));
        centerPanel.setPreferredSize(new Dimension(WINDOW_WIDTH - 20, WINDOW_HEIGHT + 500));
        leftPanel.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        centerPanel.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);

        scrollPane = new JScrollPane(centerPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        namePanel = new JPanel(new BorderLayout());
        productionName = new JLabel(production.name);
        addToCollection = new MKButton("收藏", Colors.MAIN_COLOR,Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
        namePanel.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        productionName.setFont(FontUtil.getDefaultFont(32));
        addToCollection.setFont(FontUtil.getDefaultFont(20));
        addToCollection.setPreferredSize(new Dimension(100, 25));

        ImageIcon pictureIcon = new ImageIcon(picture.getPath());
        pictureLabel = new JLabel(pictureIcon);

        introductionLabel = new JLabel(production.introduction);
        introductionLabel.setFont(FontUtil.getDefaultFont(22));

        auctionPanel = new JPanel(new BorderLayout());
        priceLabel = new JLabel("拍卖价：" + production.auction_max_price + " 元               ");
        buy = new MKButton("出价", Colors.MAIN_COLOR,Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
        buy.setFont(FontUtil.getDefaultFont(20));
        buy.setPreferredSize(new Dimension(100, 35));
        priceLabel.setFont(FontUtil.getDefaultFont(22));
        priceLabel.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        priceField = new MKTextField();
        priceField.setBackground(Colors.FONT_WHITE);
        priceField.setPlaceholder("输入你的出价");
        priceField.setFont(FontUtil.getDefaultFont(18));
        auctionPanel.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        auctionPanel.add(priceLabel, BorderLayout.WEST);
        auctionPanel.add(priceField, BorderLayout.CENTER);
        auctionPanel.add(buy, BorderLayout.EAST);

        posterPanel = new JPanel(new BorderLayout());
        posterLabel = new JLabel("<html>发布者：" + MKPost.getInstance().getUserInfo(production.producer_id).username
                + "&nbsp<font style=\"color:gray; font-size:14px\">(关注后才可以聊天哦~)</font></html>");
        follow = new MKButton("关注ta", Colors.MAIN_COLOR,Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
        posterPanel.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        posterLabel.setFont(FontUtil.getDefaultFont(22));
        follow.setFont(FontUtil.getDefaultFont(16));
        follow.setPreferredSize(new Dimension(100, 25));
        if (CurrentUser.targetUsers.contains(MKPost.getInstance().getUserInfo(production.producer_id))) {
            follow.setEnabled(false);
            follow.setText("已关注！");
        }

        commentArea = new MKTextArea();
        commentArea.setPlaceholder("写下你的评论吧！");
        commentArea.setPreferredSize(new Dimension(100,100));
        commentArea.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        commentArea.setFont(FontUtil.getDefaultFont(18));
        commentArea.setForeground(Colors.FONT_BLACK);
        commentArea.setBackground(Colors.FONT_WHITE);
        commentArea.setMargin(new Insets(0, 15, 0, 0));
        commentArea.setLineWrap(true);

        comment = new MKButton("发表评论", Colors.MAIN_COLOR,Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
        comment.setFont(FontUtil.getDefaultFont(20));
        comment.setPreferredSize(new Dimension(120, 35));

        commentLabel = new JLabel("评 论");
        commentsPanel = new JPanel(new VerticalFlowLayout());
        commentsPanel.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        commentLabel.setFont(FontUtil.getDefaultFont(26));
        loadComment();
    }

    private void initView() {
        setTitle("浏览拍卖商品");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setVisible(true);

        namePanel.add(productionName, BorderLayout.WEST);
        namePanel.add(addToCollection, BorderLayout.EAST);

        posterPanel.add(posterLabel, BorderLayout.WEST);
        posterPanel.add(follow, BorderLayout.EAST);

        centerPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 10, 25, true,false));
        centerPanel.add(namePanel);
        centerPanel.add(pictureLabel);
        centerPanel.add(introductionLabel);
        centerPanel.add(auctionPanel);
        centerPanel.add(posterPanel);
        centerPanel.add(commentArea);
        centerPanel.add(comment);
        centerPanel.add(commentLabel);
        centerPanel.add(commentsPanel);

        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadComment() {
        comments = MKPost.getInstance().getComment(production.production_id);
        for (var i : comments) {
            JLabel comment = new JLabel("<html>" + MKPost.getInstance().getUserInfo(i.reviewer_id).username
                    + ": <br>" + i.text + "&nbsp<font style=\"color:gray; font-size:10px\">"
                    + new SimpleDateFormat("yyyy-MM-dd").format(i.time) + "</font></html>");
            comment.setFont(FontUtil.getDefaultFont(20));
            commentsPanel.add(comment);
        }
    }

    private void doComment() {
        if (commentArea.getText() != null && !commentArea.getText().isEmpty() && !commentArea.getText().isBlank()) {
            CommentJson commentJson = new CommentJson();
            commentJson.reviewer_id = CurrentUser.userId;
            commentJson.production_id = production.production_id;
            commentJson.text = commentArea.getText();
            commentJson.time = System.currentTimeMillis();
            MKPost.getInstance().addComment(commentJson);

        }
    }

    private void setListener() {
        buy.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (priceField.getText() != null && !priceField.getText().isBlank() && !priceField.getText().isEmpty()
                        && Integer.parseInt(priceField.getText()) > production.auction_max_price) {
                    production.auction_max_price = Integer.parseInt(priceField.getText());
                    production.max_price_user_id = CurrentUser.userId;
                    MKPost.getInstance().buyAuctionProduction(production);
                    priceField.setText("");
                    priceLabel.setText("拍卖价：" + production.auction_max_price + " 元               ");
                    priceLabel.updateUI();
                }
            }
        });

        addToCollection.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CurrentUser.collection++;
                addToCollection.setEnabled(false);
                addToCollection.setText("已收藏");
            }
        });

        follow.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                var message = new MessageJson();
                message.sender = CurrentUser.userId;
                message.receiver = production.producer_id;
                message.text = "Hi, 我关注你啦~";
                message.time = System.currentTimeMillis();

                MKChatClient.getInstance().sendMessage(message);

                follow.setEnabled(false);
                follow.setText("已关注");
            }
        });

        comment.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                doComment();
                commentsPanel.removeAll();
                loadComment();
                commentsPanel.repaint();
                commentsPanel.updateUI();
                commentArea.setText("");
            }
        });
    }

    private void centerScreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - WINDOW_WIDTH) / 2,
                (tk.getScreenSize().height - WINDOW_HEIGHT) / 2);
    }
}
