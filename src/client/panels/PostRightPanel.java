package client.panels;

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

    public PostRightPanel() {
        initComponents();
        initView();
    }

    private void initComponents(){
        nameField = new MKTextField();
        priceField = new MKTextField();
        descriptionArea = new MKTextArea();
        setTextField(nameField);
        setTextField(priceField);
        descriptionArea.setPlaceholder("品牌型号，新旧程度，入手渠道，转手原因...");
        descriptionArea.setPreferredSize(new Dimension(100,100));
        descriptionArea.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        descriptionArea.setFont(FontUtil.getDefaultFont(16));
        descriptionArea.setForeground(Colors.FONT_BLACK);
        descriptionArea.setMargin(new Insets(0, 15, 0, 0));
    }

    private void initView() {
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 10, 25, true, false));
        setBackground(Colors.WINDOW_BACKGROUND);
        add(nameField);
        add(priceField);
        add(descriptionArea);
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
}
