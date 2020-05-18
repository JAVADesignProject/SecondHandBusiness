package client.panels;

import client.components.Colors;
import client.components.MKButton;
import client.components.VerticalFlowLayout;
import client.frames.PostFrame;
import client.listener.AbstractMouseListener;
import client.utils.FontUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;

public class PostLeftPanel extends JPanel {

    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel descriptionLabel;
    private JLabel cautionLabel;
    private MKButton uploadPicButton;
    private MKButton finishButton;
    private JFileChooser chooser;
    private File picture;

    public PostLeftPanel() {
        initComponents();
        initView();
        setListener();
    }

    private void initComponents() {
        nameLabel = new JLabel("商品名称:");
        priceLabel = new JLabel("商品价格:");
        descriptionLabel = new JLabel("商品描述:");
        cautionLabel = new JLabel();

        nameLabel.setFont(FontUtil.getDefaultFont(18));
        priceLabel.setFont(FontUtil.getDefaultFont(18));
        descriptionLabel.setFont(FontUtil.getDefaultFont(18));

        cautionLabel.setFont(FontUtil.getDefaultFont(16));
        cautionLabel.setForeground(Colors.FONT_GRAY_DARKER);
        cautionLabel.setPreferredSize(new Dimension(120, 150));
        cautionLabel.setText("<html>最多上传1张照片，照片大小不大于5MB，尺寸为aaa x bbb<html>");
        //cautionLabel.setBorder(new LineBorder(Colors.MAIN_COLOR));

        uploadPicButton = new MKButton("上传图片", Colors.MAIN_COLOR,Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
        uploadPicButton.setFont(FontUtil.getDefaultFont(18));
        uploadPicButton.setPreferredSize(new Dimension(120, 35));

        finishButton = new MKButton("发 布", Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
        finishButton.setFont(FontUtil.getDefaultFont(18));
        finishButton.setPreferredSize(new Dimension(120, 35));

    }

    private void initView() {
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP, 15, 30, false, false));
        setBackground(Colors.WINDOW_BACKGROUND);

        add(nameLabel);
        add(priceLabel);
        add(descriptionLabel);
        add(uploadPicButton);
        add(cautionLabel);
        add(finishButton);
    }

    private void setListener() {
        uploadPicButton.addMouseListener(new AbstractMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setMultiSelectionEnabled(false);
                chooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png)", "jpg", "png"));
                int result = chooser.showOpenDialog(PostFrame.context);
                if (result == JFileChooser.APPROVE_OPTION) {
                    picture = chooser.getSelectedFile();
                }
            }
        });


    }

}