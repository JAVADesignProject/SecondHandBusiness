package client.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChatBar {
    private JPanel myGroup;
    private MKButton myGroupButton;
    private String title;
    private String id;
    private int type = 0;
    private boolean showCount = true;

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void addActionListener(ActionListener listener) {
        myGroupButton.addActionListener(listener);
    }


    public String getTitle() {
        return title;
    }

    public ChatBar(String id, String title, int type, int asize, int gwidth, int gheight) {
        this.id = id;
        this.title = title;
        myGroup = new JPanel();
        myGroup.setPreferredSize(new Dimension(gwidth, gheight));
        myGroup.setMaximumSize(new Dimension(gwidth, gheight));
        myGroupButton = new MKButton(title, new Color(0xFBFCFC), new Color(0xFDFEFE), new Color(0xF4F6F7));
        myGroupButton.setHorizontalAlignment(SwingConstants.LEFT);
        myGroupButton.setForeground(new Color(0x424949));
        this.type = type;
        myGroupButton.setPreferredSize(new Dimension(gwidth, gheight));
        myGroup.add(myGroupButton);
    }

    public JPanel getPane() {
        return  myGroup;
    }

    public MKButton getArea() {
        return myGroupButton;
    }

    public void showCount(boolean show) {
        myGroupButton.setShowCount(show);
    }

    public boolean isShowCount() {
        return myGroupButton.isShowCount();
    }

}
