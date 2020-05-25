package client.frames;

import base.json.ProductionJson;
import client.components.*;
import client.listener.AbstractMouseListener;
import client.tasks.CurrentUser;
import client.tasks.MKPost;
import client.utils.FontUtil;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Arrays;
import java.util.Base64;

public class PostFrame extends JFrame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 650;
    private static final int TEXT_FIELD_WIDTH = 200;
    private static final int TEXT_FIELD_HEIGHT = 30;

    private JPanel postLeftPanel;
    private JPanel postRightPanel;

    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel descriptionLabel;
    private JLabel cautionLabel;
    private MKButton uploadPicButton;
    private MKButton finishButton;
    private JFileChooser chooser;
    private File picture;

    private MKTextField nameField;
    private MKTextField priceField;
    private MKTextArea descriptionArea;
    private JScrollPane pane;
    private JCheckBox auctionCheckBox;
    private JLabel previewPicLabel;
    private ImageIcon previewPicIcon;

    public static PostFrame context;

    public PostFrame() {
        context = this;
        initComponents();
        centerScreen();
        initView();
        setListener();
    }

    private void initComponents() {
        postLeftPanel = new JPanel();
        postLeftPanel.setPreferredSize(new Dimension(150, WINDOW_HEIGHT));
        postRightPanel = new JPanel();

        nameLabel = new JLabel("商品名称:");
        priceLabel = new JLabel("商品价格:");
        descriptionLabel = new JLabel("商品描述:");
        cautionLabel = new JLabel();

        nameLabel.setFont(FontUtil.getDefaultFont(18));
        priceLabel.setFont(FontUtil.getDefaultFont(18));
        descriptionLabel.setFont(FontUtil.getDefaultFont(18));

        cautionLabel.setFont(FontUtil.getDefaultFont(16));
        cautionLabel.setForeground(Colors.FONT_GRAY_DARKER);
        cautionLabel.setPreferredSize(new Dimension(120, 130));
        cautionLabel.setText("<html>最多上传1张照片，照片大小不大于5MB，尺寸为aaa x bbb<html>");

        uploadPicButton = new MKButton("上传图片", Colors.MAIN_COLOR,Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
        uploadPicButton.setFont(FontUtil.getDefaultFont(18));
        uploadPicButton.setPreferredSize(new Dimension(120, 35));

        finishButton = new MKButton("发 布", Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
        finishButton.setFont(FontUtil.getDefaultFont(18));
        finishButton.setPreferredSize(new Dimension(120, 35));

        nameField = new MKTextField();
        priceField = new MKTextField();
        descriptionArea = new MKTextArea();
        auctionCheckBox = new JCheckBox("拍卖");
        auctionCheckBox.setFont(FontUtil.getDefaultFont(18));

        setTextField(nameField);
        setTextField(priceField);

        descriptionArea.setPlaceholder("品牌型号，新旧程度，入手渠道，转手原因...");
        descriptionArea.setPreferredSize(new Dimension(100,100));
        descriptionArea.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        descriptionArea.setFont(FontUtil.getDefaultFont(16));
        descriptionArea.setForeground(Colors.FONT_BLACK);
        descriptionArea.setMargin(new Insets(0, 15, 0, 0));
        descriptionArea.setLineWrap(true);

        pane = new JScrollPane(descriptionArea);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        pane.getVerticalScrollBar().setUnitIncrement(16);
        pane.setPreferredSize(new Dimension(100,100));
    }

    private void initView() {
        postLeftPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 15, 30, false, false));
        postLeftPanel.setBackground(Colors.WINDOW_BACKGROUND);
        postLeftPanel.add(nameLabel);
        postLeftPanel.add(priceLabel);
        postLeftPanel.add(descriptionLabel);
        postLeftPanel.add(uploadPicButton);
        postLeftPanel.add(cautionLabel);
        postLeftPanel.add(finishButton);

        postRightPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 10, 25, true, false));
        postRightPanel.setBackground(Colors.WINDOW_BACKGROUND);
        postRightPanel.add(nameField);
        postRightPanel.add(priceField);
        postRightPanel.add(pane);
        postRightPanel.add(auctionCheckBox);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("发布二手商品");
        setResizable(false);
        setVisible(true);
        add(postLeftPanel, BorderLayout.WEST);
        add(postRightPanel,BorderLayout.CENTER);
    }

    private void centerScreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - WINDOW_WIDTH) / 2,
                (tk.getScreenSize().height - WINDOW_HEIGHT) / 2);
    }

    private void setTextField(MKTextField field) {
        Dimension textFieldDimension = new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        field.setPlaceholder("");
        field.setPreferredSize(textFieldDimension);
        field.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        field.setFont(FontUtil.getDefaultFont(18));
        field.setForeground(Colors.FONT_BLACK);
        field.setMargin(new Insets(0, 15, 0, 0));
    }

    private void setListener() {
        uploadPicButton.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setMultiSelectionEnabled(false);
                chooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png)", "jpg", "png", "jpeg"));
                int result = chooser.showOpenDialog(PostFrame.context);
                if (result == JFileChooser.APPROVE_OPTION) {
                    picture = chooser.getSelectedFile();
                    previewPicIcon = new ImageIcon(picture.getPath());
                    previewPicIcon.setImage(previewPicIcon.getImage().getScaledInstance(384, 216, Image.SCALE_DEFAULT));
                    previewPicLabel = new JLabel(previewPicIcon);
                    postRightPanel.add(previewPicLabel);
                    postRightPanel.updateUI();
                }
            }
        });

        finishButton.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                post();
            }
        });
    }

    private void post() {
        ProductionJson production = new ProductionJson();
        byte[] buffer = new byte[(int)picture.length()];
        try {
            FileInputStream inputStream = new FileInputStream(picture);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            while (inputStream.read(buffer) != -1) {
                outputStream.write(buffer, 0, (int)picture.length());
            }
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        production.price = Integer.parseInt(priceField.getText());
        production.name = nameField.getText();
        production.introduction = descriptionArea.getText();
        production.producer_id = CurrentUser.userId;
        production.auction = auctionCheckBox.isSelected();
        production.post_time = System.currentTimeMillis();
        production.pic = buffer;
        MKPost.getInstance().addProduction(production);
    }
}
