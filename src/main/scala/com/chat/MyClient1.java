package com.chat;

/*===============客户端====================*/
/**
 * 客户端
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;



public class MyClient1 extends JFrame implements ActionListener {

    JTextArea jta = null;
    JTextField jtf = null;
    JButton jb = null;
    JPanel jpl= null;
    JScrollPane jsp = null;
    //把信息发给客户端的对象
    PrintWriter pw =null;


    public static void main(String [] args){
        MyClient1 mc = new MyClient1();
        mc.createChat();
    }

    public MyClient1(){

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
        this.setTitle("客户端");
        this.setSize(400,300);
        this.setVisible(true);

        try {
            Socket s = new Socket("127.0.0.1",9988);
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            BufferedReader br = new BufferedReader(isr);

            pw = new PrintWriter(s.getOutputStream(),true);

            while(true){
                //不停地读取从服务器端发来的信息
                String info = br.readLine();
                jta.append("服务端："+info+"\r\n");
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        //如果用户按下发送信息按钮
        if(e.getSource()==jb){
        //把服务器在框里写内容发送给客户端
            String info = jtf.getText();
            jta.append("客户端："+info+"\r\n");
            //发送
            pw.println(info);
            //清空输入框
            jtf.setText("");
        }
    }
}