package client.panels;

import base.json.ProductionJson;
import client.components.Colors;
import client.components.MKTextArea;
import client.components.MKTextField;
import client.components.VerticalFlowLayout;
import client.utils.FontUtil;

import javax.swing.*;
import java.awt.*;

public class PostRightPanel extends JPanel {
    private static final int TEXT_FIELD_WIDTH = 200;
    private static final int TEXT_FIELD_HEIGHT = 30;

    private MKTextField nameField;
    private MKTextField priceField;
    private MKTextArea descriptionArea;
    private JScrollPane pane;
    private JCheckBox auctionCheckBox;
    private JLabel previewPicLabel;
    private ImageIcon previewPicIcon;

    public static PostRightPanel context;

    public PostRightPanel() {
        context = this;
        initComponents();
        initView();
    }

    private void initComponents(){
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

//        previewPicIcon = new ImageIcon(PostLeftPanel.context.picture.getPath());
//        previewPicLabel = new JLabel(previewPicIcon);

    }

    private void initView() {
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 10, 25, true, false));
        setBackground(Colors.WINDOW_BACKGROUND);
        add(nameField);
        add(priceField);
        add(pane);
        add(auctionCheckBox);
        //add(previewPicLabel);
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

    public ProductionJson getProductionInfo() {
        ProductionJson productionInfo = new ProductionJson();

        productionInfo.name = nameField.getText();
        productionInfo.price = Integer.parseInt(priceField.getText());
        productionInfo.introduction = descriptionArea.getText();
        productionInfo.auction = auctionCheckBox.isSelected();

        return productionInfo;
    }
}
