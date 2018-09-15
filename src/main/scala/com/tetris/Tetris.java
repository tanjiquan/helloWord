package com.tetris;

import javax.swing.*;

/*整个游戏的思路：在MainWindowFrame进程里建立一个定时器对象，每0.5s触发一次定时器事件，然后用此事件驱动游戏进行。
 */
public class Tetris
{
    public static void main(String args[])
    {
        MainWindowFrame mainWindow=new MainWindowFrame();
        mainWindow.setTitle("Tetris");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);

    }


}
