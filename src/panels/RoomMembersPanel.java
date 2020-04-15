package panels;

import components.Colors;
import frames.MainFrame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RoomMembersPanel extends JPanel {
    public static final int ROOM_MEMBER_PANEL_WIDTH = 200;
    private static RoomMembersPanel roomMembersPanel;

    private RoomMembersPanel() {
        roomMembersPanel = this;

        initComponents();
        initView();
        setListeners();

        //currentUser = currentUserService.findAll().get(0);
    }

    public static RoomMembersPanel getInstance() {
        if(roomMembersPanel == null) {
            roomMembersPanel = new RoomMembersPanel();
            System.out.println("创建成功");
        }
        return roomMembersPanel;
    }

    private void initComponents() {
        setBorder(new LineBorder(Colors.LIGHT_GRAY));
        setBackground(Colors.FONT_WHITE);

        setPreferredSize(new Dimension(ROOM_MEMBER_PANEL_WIDTH, 650));

    }

    private void initView() {

    }

    private void setListeners() {

    }

}
