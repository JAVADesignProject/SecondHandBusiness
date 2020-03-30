package panels;

import components.Colors;
import components.MKButton;
import components.MKTextField;
import components.VerticalFlowLayout;
import utils.FontUtil;

import javax.swing.*;
import java.awt.*;

public class PostPanel extends JPanel {
    private static final int TEXT_FIELD_WIDTH = 218;
    private static final int TEXT_FIELD_HEIGHT = 42;

    private JLabel nameLabal;
    private JLabel priceLabal;
    private JLabel descriptionLabal;
    private MKButton uploadPicButton;
    private MKTextField nameField;
    private MKTextField priceField;

    public PostPanel() {
        initComponents();
        initView();
    }

    private void initComponents() {
        nameLabal = new JLabel("商品名称:");
        priceLabal = new JLabel("商品价格:");
        descriptionLabal = new JLabel("商品描述:");
        nameLabal.setFont(FontUtil.getDefaultFont(18));
        priceLabal.setFont(FontUtil.getDefaultFont(18));
        descriptionLabal.setFont(FontUtil.getDefaultFont(18));

        nameField = new MKTextField();
        priceField = new MKTextField();
        nameField.setPlaceholder("");
        priceField.setPlaceholder("");
        nameField.setSize(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        priceField.setSize(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        nameField.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);
        priceField.setBackground(Colors.WINDOW_BACKGROUND_LIGHT);

    }

    private void initView() {
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 20, 30, true, false));
        setBackground(Colors.WINDOW_BACKGROUND);
        add(nameLabal);
        add(priceLabal);
        add(descriptionLabal);
        add(nameField);
        add(priceField);
    }
}
