package client.frames;

import client.components.Colors;
import client.components.MKButton;
import client.components.VerticalFlowLayout;
import client.listener.AbstractMouseListener;
import client.tasks.MKPost;
import client.utils.CurrentUser;
import client.utils.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.sql.Blob;
import java.text.SimpleDateFormat;

public class MyProductionFrame extends JFrame {
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 500;

    public MyProductionFrame() {
        centerScreen();
        initComponents();
        initView();
    }

    private void initComponents() {
        for (var i : CurrentUser.productions) {
            if (i.producer_id.equals(CurrentUser.userId)) {
                JPanel panel = new JPanel(new BorderLayout());
                JLabel infoLabel = new JLabel(i.name + "  "
                        + "价格：" + i.price + "元  发布时间："
                        + new SimpleDateFormat("yyyy-MM-dd").format(i.post_time));
                infoLabel.setFont(FontUtil.getDefaultFont(18));
                panel.add(infoLabel, BorderLayout.WEST);
                if (!i.bought) {
                    MKButton delete = new MKButton("删除商品", Colors.MAIN_COLOR,Colors.MAIN_COLOR_DARKER, Colors.MAIN_COLOR_DARKER);
                    delete.addMouseListener(new AbstractMouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            MKPost.getInstance().deleteProduction(i);
                            delete.setText("已删除");
                            delete.setEnabled(false);
                            panel.removeAll();
                            panel.updateUI();
                        }
                    });
                    panel.add(delete, BorderLayout.EAST);
                } else {
                    JLabel label = new JLabel("商品已被" + MKPost.getInstance().getUserInfo(i.buyer_id).username + "购买");
                    panel.add(label, BorderLayout.EAST);
                }
                add(panel);
            }
        }
    }

    private void initView() {
        setTitle("我发布的");
        setLayout(new VerticalFlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setVisible(true);
    }

    private void centerScreen() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - WINDOW_WIDTH) / 2,
                (tk.getScreenSize().height - WINDOW_HEIGHT) / 2);
    }
}
