package com.tetris;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class GamePanel extends JPanel
{
    public GamePanel()
    {
        for(int i=0;i<20;i++)
        {
            for(int j=0;j<30;j++)
                grid[i][j]=0;
        }
        setBackground(Color.BLACK);//黑色背景
        currentBlock=paintPanel.getBlock();
        currentLocation[0]=9;
        currentLocation[1]=0;
        locations=currentBlock.getLocation(currentLocation[0], currentLocation[1]);
        flag=false;

    }



    public void paintComponent(Graphics g)//绘制游戏区
    {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        g2.setPaint(Color.BLUE);//蓝色方块

        for(int i=0;i<20;i++)//将grid里所有为1的区域都绘制正方形
        {
            for(int j=0;j<30;j++)
            {
                if(grid[i][j]==1)
                {
                    Rectangle2D rectangle=new Rectangle2D.Double(i*POINT_DISTANCE-1,j*POINT_DISTANCE-1,BLOCK_WIDTH,BLOCK_HEIGHT);
                    g2.fill(rectangle);
                }
            }
        }
        //然后再将当前方块对象绘制在游戏区
        for(int i=0;i<4;i++)
        {
            if(locations[i][1]>=0)
            {
                Rectangle2D rectangle=new Rectangle2D.Double(locations[i][0]*POINT_DISTANCE-1,locations[i][1]*POINT_DISTANCE-1,BLOCK_WIDTH,BLOCK_HEIGHT);
                g2.fill(rectangle);
            }
        }

    }


    //在游戏中移动方块的函数，是主要函数
    public void move(int direction)
    {
        //flag==true，表示当前区域没有活动的对象，需要得到下一个对象
        if(flag==true)
        {
            currentBlock=paintPanel.getBlock();
            currentLocation[0]=9;
            currentLocation[1]=0;
            locations=currentBlock.getLocation(currentLocation[0], currentLocation[1]);
            flag=false;
        }
        //表示当前区域有活动的对象
        //判断是否到底，如果不能再落下，那么将当前对象的块坐标添加进grid里，然后设置flag为true
        if(isBottom(locations)==true)
        {
            flag=true;
            for(int i=0;i<4;i++)
            {
                if(locations[i][1]>=0)
                    grid[locations[i][0]][locations[i][1]]=1;


            }
            repaint();
            if(checkOver()==true)
            {
                MainWindowFrame.stopTimer();
                JOptionPane.showMessageDialog(null,"Game Over");
            }
            return;
        }
        //判断是否能左右移动，如果不行，那么当前点坐标的y值不再变化，如果可以，则y值加上direction，direction=1表示右移，-1表示左移，0表示没有左右移动
        if(isEdge(direction)==false)
        {
            currentLocation[0]+=direction;
        }
        if(direction==0)
            currentLocation[1]++;
        locations=currentBlock.getLocation(currentLocation[0], currentLocation[1]);
        repaint();

    }
    //在按下了向下的键后，直接移到底部的函数
    public void moveToBottom()
    {

        int i=0;
        while(isBottom(locations)==false)
        {
            i++;
            for(int j=0;j<4;j++)
            {
                locations[j][1]++;
            }
        }
        for(int j=0;j<4;j++)
        {
            if(locations[j][1]>=0)
            {
                grid[locations[j][0]][locations[j][1]]=1;
            }
        }
        flag=true;
        repaint();
    }
    //检测是否游戏结束
    public boolean checkOver()
    {
        for(int i=0;i<4;i++)
        {
            if(locations[i][1]<0)
                return true;
        }
        return false;
    }
    //检测是否到底
    private boolean isBottom(int[][] l)
    {
        for(int i=0;i<4;i++)
        {
            if(l[i][1]>=0&&l[i][0]>=0&&l[i][0]<20)
            {
                if((l[i][1]+1)>=30||grid[l[i][0]][l[i][1]+1]==1)
                {
                    return true;
                }
            }
        }
        return false;
    }
    //检测是否到达边缘
    private boolean isEdge(int direction)
    {
        for(int i=0;i<4;i++)
        {
            if(locations[i][1]>=0)
            {
                if((locations[i][0]+direction)>=20||(locations[i][0]+direction)<0||grid[locations[i][0]+direction][locations[i][1]]==1)
                {
                    return true;
                }
            }
        }
        return false;
    }

    //检测该行是否已满，并且进行消行处理
    public int checkLine()
    {
        int lines=0;

        for(int i=0;i<30;i++)
        {
            int sum=0;
            for(int j=0;j<20;j++)
            {
                sum+=grid[j][i];
            }
            if(sum==20)
            {
                lines++;
                for(int k=i;k>0;k--)
                {
                    for(int h=0;h<20;h++)
                    {
                        grid[h][k]=grid[h][k-1];
                    }
                }
                for(int k=0;k<20;k++)
                {
                    grid[k][0]=0;
                }
                i--;
                repaint();
            }


        }
        return lines;

    }


    //改变方块姿态的函数，不仅要改变姿态，还要检测改变后的方块是否越界，如果越界，就要进行调整
    public void changePosition()
    {
        currentBlock.changePosition();
        int min=0;
        int max=0;
        locations=currentBlock.getLocation(currentLocation[0], currentLocation[1]);
        for(int i=0;i<4;i++)
        {
            if(min>locations[i][0])
            {
                min=locations[i][0];
            }
            if(max<locations[i][0])
            {
                max=locations[i][0];
            }

        }
        //System.out.println("min:"+min+"  max:"+max);
        if(min<0)
        {
            currentLocation[0]+=(-min);
        }
        if(max>19)
        {
            currentLocation[0]-=(max-19);
        }
        locations=currentBlock.getLocation(currentLocation[0], currentLocation[1]);
        repaint();
    }
    //当按下reset键的时候，重置GamePanel
    public static void resetGamePanel()
    {
        for(int i=0;i<30;i++)
        {
            for(int j=0;j<20;j++)
            {
                grid[j][i]=0;
            }
        }
        flag=true;
    }

    private static int[][] grid=new int[20][30];//用来存储游戏区数据的数组，为1表示当前格有方块,前个坐标表示x，后一个表示y，与一般的数组有点不一样
    private Blocks currentBlock;//目前正在游戏区活动的方块
    private int[][] locations;//当前活动方块的各个方块的坐标
    private int[] currentLocation=new int[]{0,0};//当前参考点的坐标
    private int BLOCK_WIDTH=18;//方块的边长
    private int BLOCK_HEIGHT=18;
    private int POINT_DISTANCE=20;//坐标点的间距
    private static boolean flag=true;//为true时，表示当前没有活动的方块

}
