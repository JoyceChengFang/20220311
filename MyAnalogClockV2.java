package tw.joyce.test2;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MyAnalogClockV2 extends JFrame {
    private MyPanel myPanel;
    private int width = 400, height = 400;

    public MyAnalogClockV2(){
        setLayout(new BorderLayout());
        setTitle("My Analog Clock");

        myPanel = new MyPanel();
        add(myPanel, BorderLayout.CENTER);

        setSize(width, height);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private class MyPanel extends JPanel {
        private Timer timer;
        private int[] time = new int[4];
        
        public MyPanel() {
            setBackground(Color.BLACK);

            timer = new Timer();
            timer.schedule(new RefreshTask(), 0, 30);
            timer.schedule(new ClockTask(), 0, 30);
        }

        private class ClockTask extends TimerTask {
            @Override
            public void run() {
                Calendar now = Calendar.getInstance();
                time[0] = now.get(Calendar.HOUR);
                time[1] = now.get(Calendar.MINUTE);
                time[2] = now.get(Calendar.SECOND);
                time[3] = now.get(Calendar.MILLISECOND);
            }
        }

        private class RefreshTask extends TimerTask {
            @Override
            public void run() {
                repaint();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int viewW = getWidth(); int viewH = getHeight();

            int x0=viewW/2,y0=viewH/2-10; //圓心座標
            int r = viewW/2-80; //半徑
            int ah, am, as; //時針,分針,秒針角度
            int dot = r-15; //各點與圓心距離
            int mmssline = r-30; //分針,秒針長度
            int hhline = r-60; //時針長度
            
            // 顯示時間字串
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
            g2d.drawString(String.format("%02d:%02d:%02d", time[0], time[1], time[2]), viewW/2-40, viewH-40); 

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
            ah = time[0]*30 + time[1]*30/60 + time[2]*30/60/60;
            g2d.drawLine(x0-1, y0-1, x0-1 + (int)Math.round(hhline * Math.sin(ah * Math.PI /180)) , 
                                    y0-1 - (int)Math.round(hhline * Math.cos(ah * Math.PI /180)));
            

            //畫出分針
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(2));
            am = time[1]*6 + time[2]*6/60;
            g2d.drawLine(x0-1, y0-1, x0-1 + (int)Math.round(mmssline * Math.sin(am * Math.PI /180)) , 
                                    y0-1 - (int)Math.round(mmssline * Math.cos(am * Math.PI /180)));
            
            
            //畫出秒針
            g2d.setColor(Color.GREEN);
            g2d.setStroke(new BasicStroke(1));
            as = time[2]*6 + time[3]*6/1000;
            g2d.drawLine(x0-1, y0-1, x0-1 + (int)Math.round(mmssline * Math.sin(as * Math.PI /180)) , 
                                    y0-1 - (int)Math.round(mmssline * Math.cos(as * Math.PI /180)));
            

            //畫出圓心
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(x0-5, y0-5, 10, 10);  

        }
    }

    public static void main(String[] args) {

        new MyAnalogClockV2();
        
    }
}
