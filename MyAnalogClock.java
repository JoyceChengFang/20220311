package tw.joyce.test2;

import javax.swing.*;

import java.awt.*;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MyAnalogClock extends JFrame { //implements Runnable {

    private Timer timer;
    private int width = 400, height = 400;
    // private Image iBuffer = null;
    // private Graphics gBuffer;
    //Thread clock;

    public MyAnalogClock(){
        setTitle("My Analog Clock");
        setSize(width, height);
        //setBackground(Color.BLACK);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 0, 1000);
        //start();
    }

    // public void start() { // 開始程序
    //     if (clock == null)// 如果程序為空值
    //     {
    //         clock = new Thread (this); // 例項化程序
    //         clock.start(); // 開始程序
    //     }
    // }

    // public void run()// 執行程序
    // {
    //     while (clock != null) {
    //         repaint(); // 呼叫paint方法重繪介面
    //         try {
    //             Thread.sleep(1000); // 執行緒暫停一秒(1000毫秒)
    //         } catch (InterruptedException ex) {
    //             ex.printStackTrace(); // 輸出出錯資訊
    //         }
    //     }
    // }

    // public void stop()// 停止程序
    // {
    //     clock = null;
    // }

    

    @Override
    public void paint(Graphics g) {
        int x0=width/2,y0=height/2-10; //圓心座標
        int r = width/2-80; //半徑
        int ah, am, as; //時針,分針,秒針角度
        int dot = r-15; //各點與圓心距離
        int mmssline = r-30; //分針,秒針長度
        int hhline = r-60; //時針長度
        Graphics2D g2d = (Graphics2D) g;
        //g2d.setBackground(Color.BLACK);

        int[] time = new int[3];
        //int ms;
        Calendar now = Calendar.getInstance();
        time[0] = now.get(Calendar.HOUR);
        time[1] = now.get(Calendar.MINUTE);
        time[2] = now.get(Calendar.SECOND);
        //ms = now.get(Calendar.MILLISECOND);

        g2d.setColor(Color.BLACK); // 設定當前顏色為黑色
        Dimension dim = getSize(); // 得到視窗尺寸
        g2d.fillRect(0, 0, dim.width, dim.height); // 填充背景色為黑色的矩形

        
        // 顯示時間字串
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
        g2d.drawString(String.format("%02d:%02d:%02d", time[0], time[1], time[2]), width/2-40, height-40); 

        //畫出時鐘外圓
        g2d.setColor(Color.YELLOW);
        g2d.drawOval(x0-r, y0-r, r*2, r*2); 

        //畫出各點
        g2d.setColor(Color.WHITE);
        int an = 0;
        for (int i = 0; i < 12; i++) {
            g2d.fillOval(x0-1-3 + (int)(dot * Math.cos(an * Math.PI /180)),
                         y0-1-3 + (int)(dot * Math.sin(an * Math.PI /180)), 6, 6);
            an += 30;
        }

        //畫出時針
        g2d.setColor(Color.PINK);
        g2d.setStroke(new BasicStroke(3));
        //ah = time[0]<3 ? 360+(time[0]*30-90) : time[0]*30-90; //因座標向右向下增加，及0度在3點方向，換算角度。
        //ah = ah + time[1]/60 + time[2]/60/60;
        //g2d.drawLine(x0-1, y0-1, x0-1 + (int)(hhline * Math.cos(ah * Math.PI /180)) , y0-1 + (int)(hhline * Math.sin(ah * Math.PI /180)));
        ah = time[0]*30 + time[1]*30/60 + time[2]*30/60/60;
        g2d.drawLine(x0-1, y0-1, x0-1 + (int)(hhline * Math.sin(ah * Math.PI /180)) , 
                                 y0-1 - (int)(hhline * Math.cos(ah * Math.PI /180)));
        

        //畫出分針
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2));
        // am = time[1]<15 ? 360+(time[1]*6-90) : time[1]*6-90; //因座標向右向下增加，及0度在3點方向，換算角度。
        // am = am + time[2]/60;
        // g2d.drawLine(x0-1, y0-1, x0-1 + (int)(mmssline * Math.cos(am * Math.PI /180)) , y0-1 + (int)(mmssline * Math.sin(am * Math.PI /180)));
        am = time[1]*6 + time[2]*6/60;
        g2d.drawLine(x0-1, y0-1, x0-1 + (int)Math.round(mmssline * Math.sin(am * Math.PI /180)) , 
                                 y0-1 - (int)Math.round(mmssline * Math.cos(am * Math.PI /180)));
        
        
        //畫出秒針
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(1));
        // as = time[2]<15 ? 360+(time[2]*6-90) : time[2]*6-90; //因座標向右向下增加，及0度在3點方向，換算角度。
        // as = as + time[2]/60;
        // g2d.drawLine(x0-1, y0-1, x0-1 + (int)(mmssline * Math.cos(as * Math.PI /180)) , y0-1 + (int)(mmssline * Math.sin(as * Math.PI /180)));
        as = time[2]*6; //+ ms*6/1000;
        g2d.drawLine(x0-1, y0-1, x0-1 + (int)(mmssline * Math.sin(as * Math.PI /180)) , 
                                 y0-1 - (int)(mmssline * Math.cos(as * Math.PI /180)));
        

        //畫出圓心
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(x0-5, y0-5, 10, 10);  

        //update(g2d);
    }

    // @Override
    // public void update(Graphics g)
    // {
    //     if(iBuffer==null)
    //     {
    //         iBuffer=createImage(this.getSize().width,this.getSize().height);
    //         gBuffer=iBuffer.getGraphics();
    //     }
    //     gBuffer.setColor(getBackground());
    //     gBuffer.fillRect(0,0,this.getSize().width,this.getSize().height);
    //     paint(gBuffer);
    //     g.drawImage(iBuffer,0,0,this);
    // }


    public static void main(String[] args) {

        new MyAnalogClock();
        
    }
}
