package panels;

import components.Colors;
import components.MKButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class HomePanel extends JPanel {
    JPanel upPanel;
    JPanel downPanel;
    JPanel leftPanel;
    JPanel rightPanel;
    JPanel centerPanel;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JLabel label5;
    JLabel label6;
    MKButton nextPage;
    MKButton previousPage;

    public HomePanel(){
        initComponents();
        initView();
        //setListener();
    }

    private void initComponents() {
        upPanel = new JPanel();
        downPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        centerPanel = new JPanel();

        label1 = new JLabel("1");
        label2 = new JLabel("2");
        label3 = new JLabel("3");
        label4 = new JLabel("4");
        label5 = new JLabel("5");
        label6 = new JLabel("6");

        nextPage = new MKButton(null, Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER, new ImageIcon("res/image/nextPage.png"));
        previousPage = new MKButton(null, Colors.MAIN_COLOR, Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER, new ImageIcon("res/image/previousPage.png"));
        nextPage.setPreferredSize(new Dimension(30,20));
        previousPage.setPreferredSize(new Dimension(30,20));


        upPanel.setPreferredSize(new Dimension(840,40));
        downPanel.setPreferredSize(new Dimension(840,40));
        leftPanel.setPreferredSize(new Dimension(30,25));
        rightPanel.setPreferredSize(new Dimension(30,25));
        centerPanel.setPreferredSize(new Dimension(750,550));

//        upPanel.setBorder(new LineBorder(Colors.MAIN_COLOR));
//        downPanel.setBorder(new LineBorder(Colors.MAIN_COLOR));
//        leftPanel.setBorder(new LineBorder(Colors.MAIN_COLOR));
//        rightPanel.setBorder(new LineBorder(Colors.MAIN_COLOR));
//        centerPanel.setBorder(new LineBorder(Colors.MAIN_COLOR));

        label1.setBorder(new LineBorder(Colors.MAIN_COLOR));
        label2.setBorder(new LineBorder(Colors.MAIN_COLOR));
        label3.setBorder(new LineBorder(Colors.MAIN_COLOR));
        label4.setBorder(new LineBorder(Colors.MAIN_COLOR));
        label5.setBorder(new LineBorder(Colors.MAIN_COLOR));
        label6.setBorder(new LineBorder(Colors.MAIN_COLOR));
    }

    private void initView() {
        centerPanel.add(label1);
        centerPanel.add(label2);
        centerPanel.add(label3);
        centerPanel.add(label4);
        centerPanel.add(label5);
        centerPanel.add(label6);
        centerPanel.setLayout(new GridLayout(2, 3, 20, 20));

        downPanel.add(previousPage);
        downPanel.add(nextPage);

        setLayout(new BorderLayout());
        add(upPanel, BorderLayout.NORTH);
        add(downPanel, BorderLayout.SOUTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
    }
}
