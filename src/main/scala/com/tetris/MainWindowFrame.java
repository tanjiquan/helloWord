package com.tetris;

/*
整个游戏界面分为两部分，左边显示信息，右边是游戏区，是两个JPanel，它们的父窗口是MainWindowFrame。
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainWindowFrame extends JFrame
{
    public MainWindowFrame()
    {
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        setResizable(false);//不可设置大小

        infoPanel=new InfoPanel();
        gamePanel=new GamePanel();
        timer=new Timer(500,new timeActionListener());//新建一个定时器对象，每0.5s触发一次

        Container contentPane=getContentPane();

        contentPane.add(infoPanel,BorderLayout.WEST);
        contentPane.add(gamePanel,BorderLayout.CENTER);

        //全局键盘监控事件
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        toolkit.addAWTEventListener(new ImplAWTEventListener(), AWTEvent.KEY_EVENT_MASK);

    }

    public static void startTimer()
    {
        timer.start();
    }
    public static void stopTimer()
    {
        timer.stop();
    }
    private static Timer timer;
    //每触发一次，方块便向下移动一格
    private class timeActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            gamePanel.move(DIRECTION_NONE);
            infoPanel.setScore(gamePanel.checkLine());
            infoPanel.repaint();
        }
    }
    //全局事件，用来监控键盘输入
    class ImplAWTEventListener implements AWTEventListener
    {
        public void eventDispatched(AWTEvent event) {
            if (event.getClass() == KeyEvent.class) {
                // 被处理的事件是键盘事件.
                KeyEvent keyEvent = (KeyEvent) event;
                if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
                    //按下时你要做的事情
                    keyPressed(keyEvent);
                } else if (keyEvent.getID() == KeyEvent.KEY_RELEASED) {
                    //放开时你要做的事情
                    keyReleased(keyEvent);
                }
            }
        }

        private void keyPressed(KeyEvent e)
        {
            int keyCode=e.getKeyCode();
            if(keyCode==KeyEvent.VK_LEFT)
            {
                gamePanel.move(DIRECTION_LEFT);
                infoPanel.setScore(gamePanel.checkLine());
            }
            else if(keyCode==KeyEvent.VK_RIGHT)
            {
                gamePanel.move(DIRECTION_RIGHT);
                infoPanel.setScore(gamePanel.checkLine());
            }
            else if(keyCode==KeyEvent.VK_UP)
            {
                gamePanel.changePosition();
                infoPanel.setScore(gamePanel.checkLine());
            }
            else if(keyCode==KeyEvent.VK_DOWN)
            {
                gamePanel.moveToBottom();
                infoPanel.setScore(gamePanel.checkLine());
            }
        }
        private void keyReleased(KeyEvent event) {}
    }


    InfoPanel infoPanel;
    GamePanel gamePanel;
    private final int DEFAULT_WIDTH=550;
    private final int DEFAULT_HEIGHT=633;
    private int DIRECTION_LEFT=-1;
    private int DIRECTION_RIGHT=1;
    private int DIRECTION_NONE=0;
}