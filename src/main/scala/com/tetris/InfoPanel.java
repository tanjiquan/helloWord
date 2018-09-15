package com.tetris;



/*
用来显示游戏信息的，还包含一个next显示面板
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

public class InfoPanel extends JPanel
{
    public InfoPanel()
    {
        setBounds(POSITION_X,POSITION_Y,WIDTH,HEIGHT);
        setLayout(new GridLayout(6,1));


        JLabel nextLabel=new JLabel("Next");
        nextLabel.setFont(new Font("Serif",Font.BOLD,30));

        JLabel scoreLabel=new JLabel("Score");
        scoreLabel.setFont(new Font("Serif",Font.BOLD,30));
        //显示分数的面板
        displayScoreLabel.setFont(new Font("Serif",Font.BOLD,30));
        //开始按钮
        JButton startButton=new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainWindowFrame.startTimer();
            }
        });
        //暂停按钮
        JButton pauseButton=new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                MainWindowFrame.stopTimer();
            }
        });
        //重新开始按钮
        JButton restartButton=new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                GamePanel.resetGamePanel();
                score=0;
                setScore(0);
            }
        });

        JPanel buttonPanel1=new JPanel();
        buttonPanel1.add(startButton);
        buttonPanel1.add(pauseButton);
        JPanel buttonPanel2=new JPanel();
        buttonPanel2.add(restartButton);

        add(nextLabel);
        add(nextPaintPanel);
        add(scoreLabel);
        add(displayScoreLabel);
        add(buttonPanel1);
        add(buttonPanel2);
    }
    //设置分数
    public void setScore(int s)
    {
        score+=s*100;
        displayScoreLabel.setText(String.valueOf(score));
    }


    private final int POSITION_X=0;
    private final int POSITION_Y=0;
    private final int WIDTH=200;
    private final int HEIGHT=600;

    private JLabel displayScoreLabel=new JLabel("0");
    private int score=0;
    private paintPanel nextPaintPanel=new paintPanel();
}
//显示next的面板
class paintPanel extends JPanel
{
    public paintPanel()
    {
        nextBlock=getNextBlock();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        int[][] locations=nextBlock.getLocation(4,4);
        g2.setPaint(Color.BLUE);

        for(int i=0;i<4;i++)
        {
            Rectangle2D rectangle=new Rectangle2D.Double(locations[i][0]*POINT_DISTANCE-1,locations[i][1]*POINT_DISTANCE-1,BLOCK_WIDTH,BLOCK_HEIGHT);
            g2.fill(rectangle);
        }


    }
    //获得下一个block的方法
    private static Blocks getNextBlock()
    {
        int i=(int)(Math.random()*7);
        if(i==0)
            nextBlock=new OneBlock();
        else if(i==1)
            nextBlock=new LeftTwoBlock();
        else if(i==2)
            nextBlock=new RightTwoBlock();
        else if(i==3)
            nextBlock=new LeftThreeBlock();
        else if(i==4)
            nextBlock=new RightThreeBlock();
        else if(i==5)
            nextBlock=new FourBlock();
        else if(i==6)
            nextBlock=new FiveBlock();
        return nextBlock;
    }
    //向GamePanel传递block的方法
    public static Blocks getBlock()
    {
        Blocks temp=nextBlock;
        nextBlock=getNextBlock();

        return temp;

    }
    private static Blocks nextBlock;
    private final double BLOCK_WIDTH=10;
    private final double BLOCK_HEIGHT=10;
    private final double POINT_DISTANCE=12;
}