package com.chat;

/*=============服务端================*/

/**
 * 服务器程序 在 9988 端口监听
 * 可以通过控制台输入来回应客户端
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class MyServer1 extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;
    JTextArea jta = null;
    JTextField jtf = null;
    JButton jb = null;
    JPanel jpl= null;
    JScrollPane jsp = null;
    //把信息发给客户端的对象
    PrintWriter pw =null;

    public static void main(String [] args){
        MyServer1 ms = new MyServer1();
        ms.createChat();
    }
    public MyServer1(){

    }

    public void createChat(){
        jta = new JTextArea();
        jtf = new JTextField(20);
        jb= new JButton("发送");

        jb.addActionListener(this);

        jpl = new JPanel();
        jsp = new JScrollPane(jta);

        jpl.add(jtf);
        //jpl.add(jta);
        jpl.add(jb);
        this.add(jsp,"Center");
        this.add(jpl,"South");
        this.setTitle("服务端");
        this.setSize(400,300);
        this.setVisible(true);
        //服务器监听
        try {
            ServerSocket ss= new ServerSocket(9988);
            //等待客户端连接
            Socket s = ss.accept();
            //读取客户端发来的信息
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            BufferedReader brd = new BufferedReader(isr);
            pw = new PrintWriter(s.getOutputStream(),true);
            while(true){
                //读取客户端信息
                String info = brd.readLine();
                //把客户端信息写到信息栏
                jta.append("客户端："+info+"\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        //如果用户按下发送信息按钮
        if(e.getSource()==jb){
            //把服务器在框里写内容发送给客户端
            String info = jtf.getText();
            jta.append("服务端："+info+"\r\n");
            //发送
            pw.println(info);
            //清空输入框
            jtf.setText("");
        }
    }

}
