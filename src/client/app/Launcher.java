package client.app;

import client.frames.LoginFrame;
import client.frames.MainFrame;
import client.tasks.MKPost;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class Launcher {
    private static Launcher context;

    public static String userHome;
    public static String appFilesBasePath;

    private JFrame currentFrame;

    static {
        try {
            new MKPost();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Launcher() {
        context = this;
    }

    public void launch() {
        //获取系统环境变量
        config();
        //判断程序是否正在运行
        if (!isApplicationRunning()) {
            openFrame();
        }
        else {
            System.exit(-1);
        }
    }

    private void openFrame() {
        // 原来登录过
        if (checkLoginInfo()) {
            currentFrame = new MainFrame();
        }
        // 从未登录过
        else {
            currentFrame = new LoginFrame();
            currentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        currentFrame.setVisible(true);
    }

    private void config() {
        userHome = System.getProperty("user.home");
        appFilesBasePath = userHome + System.getProperty("file.separator") + "wechat";
    }

    private boolean checkLoginInfo() {
        // TODO 判断是否已登录的逻辑
        return false;
    }

    private static boolean isApplicationRunning() {
        boolean rv = false;
        try {
            String path = appFilesBasePath + System.getProperty("file.separator") + "appLock";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File lockFile = new File(path + System.getProperty("file.separator") + "appLaunch.lock");
            if (!lockFile.exists()) {
                lockFile.createNewFile();
            }
            //程序名称
            RandomAccessFile fis = new RandomAccessFile(lockFile.getAbsolutePath(), "rw");
            FileChannel fileChannel = fis.getChannel();
            FileLock fileLock = fileChannel.tryLock();
            if (fileLock == null) {
                System.out.println("程序已在运行.");
                rv = true;
            }
        }
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return rv;
    }


















}
