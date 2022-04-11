package tw.joyce.test1;

import java.awt.Component;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.HashSet;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class SelectDatasetV2 {
    private JFrame f;
    private Object lock = new Object();
    private JLabel count;
    private int sum;
    private Set<Integer> level = new HashSet<Integer>();
    private JCheckBox selectAll,junior,senior,
                        jun1,jun2,jun3,
                        sen1,sen2,sen3,sen4,sen5,sen6;
    private JButton ok;
    private MyPanel myPanel;

    public SelectDatasetV2() {
        
        //JFrame設定
        f = new JFrame("Select Dataset");

        //將外觀設定為系統外觀
        // String laf = UIManager.getCrossPlatformLookAndFeelClassName();
        // String laf = UIManager.getSystemLookAndFeelClassName();
        // try {
        //     UIManager.setLookAndFeel(laf);
        // } catch (UnsupportedLookAndFeelException exc) {
        //     System.err.println("Warning: UnsupportedLookAndFeel: " + laf);
        // } catch (Exception exc) {
        //     System.err.println("Error loading " + laf + ": " + exc);
        // }

        //初始物件
        sum = 362; //預設為選擇國一資料集
        count = new JLabel("Count : " + sum);
        selectAll = new JCheckBox("Select All");
        junior = new JCheckBox("Junior-1200");
        senior = new JCheckBox("Senior-7000");
        jun1 = new JCheckBox("Junior 1");
        jun2 = new JCheckBox("Junior 2");
        jun3 = new JCheckBox("Junior 3");
        sen1 = new JCheckBox("Senior 1");
        sen2 = new JCheckBox("Senior 2");
        sen3 = new JCheckBox("Senior 3");
        sen4 = new JCheckBox("Senior 4");
        sen5 = new JCheckBox("Senior 5");
        sen6 = new JCheckBox("Senior 6");
        ok = new JButton("OK");

        //視窗設定
        myPanel = new MyPanel();
        f.add(myPanel);

        //預設為選擇國一資料集
        jun1.setSelected(true);
        level.add(1);

        //構造監聽器，響應checkBox及button事件
        setListener();

        //JFrame設定
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocation(200, 200);
        f.pack();
        f.setVisible(true);

        //執行序開始
        t.start();

        //監聽視窗(視窗關閉後結束執行序)
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                if (sum == 0){
                    //f.setVisible(true);
                    JOptionPane.showMessageDialog(f, "No dataset is selected, the default dataset \"Junior 1\" is used.");
                    sum = 362;
                    level.add(1);
                }
                synchronized (lock) {
                    f.setVisible(false);
                    lock.notify();
                };
            }
        });

        //等待執行序關閉後(選擇完畢)繼續原始程序
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //視窗設定，繼承JPanel可以repaint
    private class MyPanel extends JPanel {

        public MyPanel(){

            GroupLayout layout = new GroupLayout(this);
            this.setLayout(layout);

            // 自動設定組件、組之間的間隙
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            //------"欄"對齊------//
            // LEADING -- 左對齊 BASELINE -- 底部對齊 CENTER -- 中心對齊
            GroupLayout.ParallelGroup hpg3a = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
            hpg3a.addComponent(sen1);
            hpg3a.addComponent(sen3);
            hpg3a.addComponent(sen5);

            GroupLayout.ParallelGroup hpg3b = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
            hpg3b.addComponent(count);
            hpg3b.addComponent(sen2);
            hpg3b.addComponent(sen4);
            hpg3b.addComponent(sen6);
            GroupLayout.SequentialGroup hpg3H = layout.createSequentialGroup();
            hpg3H.addGroup(hpg3a).addGroup(hpg3b);

            GroupLayout.ParallelGroup hpg3 = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
            hpg3.addComponent(jun1);
            hpg3.addComponent(jun2);
            hpg3.addComponent(jun3);
            hpg3.addGroup(hpg3H);

            GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
            hpg2.addComponent(junior);
            hpg2.addComponent(senior);

            //GroupLayout.ParallelGroup hpg4 = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
            //hpg4.addComponent(count);
            //hpg4.addComponent(ok);
            // 水平
            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addComponent(selectAll).addGroup(hpg2).addGroup(hpg3).addComponent(ok));
            //------"欄"對齊------//


            // 設定Button在水平方向一樣寬
            layout.linkSize(SwingConstants.HORIZONTAL, new Component[] { ok });
            // layout.linkSize(SwingConstants.HORIZONTAL,new Component[] { caseCheckBox,
            // wholeCheckBox, wrapCheckBox, backCheckBox});

            //------"列"對齊------//
            GroupLayout.ParallelGroup vpg0 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
            vpg0.addComponent(selectAll);
            vpg0.addComponent(count);
            GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
            vpg1.addComponent(sen1);
            vpg1.addComponent(sen2);
            GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
            vpg2.addComponent(sen3);
            vpg2.addComponent(sen4);
            GroupLayout.ParallelGroup vpg3 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
            vpg3.addComponent(sen5);
            vpg3.addComponent(sen6);
            vpg3.addComponent(ok);
            // 垂直
            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(vpg0)
                    .addComponent(junior)
                    .addComponent(jun1).addComponent(jun2).addComponent(jun3)
                    .addComponent(senior)
                    .addGroup(vpg1).addGroup(vpg2).addGroup(vpg3));
            //------"列"對齊------//
        }
    }


    //構造監聽器
    private void setListener() {
        selectAll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                selectAll();
            }
        });
        junior.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                junior();
            }
        });
        senior.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                senior();
            }
        });
        jun1.addActionListener(actionListener);
        jun2.addActionListener(actionListener);
        jun3.addActionListener(actionListener);
        sen1.addActionListener(actionListener);
        sen2.addActionListener(actionListener);
        sen3.addActionListener(actionListener);
        sen4.addActionListener(actionListener);
        sen5.addActionListener(actionListener);
        sen6.addActionListener(actionListener);
        ok.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ok();
            }
        });
    }
    //構造監聽器，響應checkBox事件
    ActionListener actionListener = new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            checkBoxAction();
        }
    };

    //監聽事件後執行方法
    private void selectAll() {
        if(selectAll.isSelected()) {
            junior.setSelected(true);
            senior.setSelected(true);
            jun1.setSelected(true);
            jun2.setSelected(true);
            jun3.setSelected(true);
            sen1.setSelected(true);
            sen2.setSelected(true);
            sen3.setSelected(true);
            sen4.setSelected(true);
            sen5.setSelected(true);
            sen6.setSelected(true);
            checkBoxAction();
        }
    }
    private void junior() {
        if(junior.isSelected()) {
            jun1.setSelected(true);
            jun2.setSelected(true);
            jun3.setSelected(true);
        }else{
            selectAll.setSelected(false);
        }
        checkBoxAction();
    }
    private void senior() {
        if(senior.isSelected()) {
            sen1.setSelected(true);
            sen2.setSelected(true);
            sen3.setSelected(true);
            sen4.setSelected(true);
            sen5.setSelected(true);
            sen6.setSelected(true);
        }else{
            selectAll.setSelected(false);
        }
        checkBoxAction();
    }
    private void ok() {
        if (sum == 0){
            JOptionPane.showMessageDialog(f, "Please select dataset!!!");
        }else{
            synchronized (lock) {
                f.setVisible(false);
                lock.notify();
            };
            f.dispose();
        }
    }
    private void checkBoxAction() {
        sum = 0;
        if(jun1.isSelected()) {sum+=362;level.add(1);} else {level.remove(1);junior.setSelected(false);selectAll.setSelected(false);}
        if(jun2.isSelected()) {sum+=449;level.add(2);} else {level.remove(2);junior.setSelected(false);selectAll.setSelected(false);}
        if(jun3.isSelected()) {sum+=505;level.add(3);} else {level.remove(3);junior.setSelected(false);selectAll.setSelected(false);}
        if(sen1.isSelected()) {sum+=1666;level.add(4);} else {level.remove(4);senior.setSelected(false);selectAll.setSelected(false);}
        if(sen2.isSelected()) {sum+=1468;level.add(5);} else {level.remove(5);senior.setSelected(false);selectAll.setSelected(false);}
        if(sen3.isSelected()) {sum+=1462;level.add(6);} else {level.remove(6);senior.setSelected(false);selectAll.setSelected(false);}
        if(sen4.isSelected()) {sum+=1334;level.add(7);} else {level.remove(7);senior.setSelected(false);selectAll.setSelected(false);}
        if(sen5.isSelected()) {sum+=1534;level.add(8);} else {level.remove(8);senior.setSelected(false);selectAll.setSelected(false);}
        if(sen6.isSelected()) {sum+=1335;level.add(9);} else {level.remove(9);senior.setSelected(false);selectAll.setSelected(false);}
        
        count.setText("Count : " + sum);
        myPanel.repaint();
    }

    //執行序(f.isVisible()啟動，等待選擇完畢再繼續)
    Thread t = new Thread() {
        public void run() {
            synchronized(lock) {
                while (f.isVisible())
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
    };
    
    //取得選取結果
    public Set<Integer> getlevel(){
        return level;
    }
    public int getsum(){
        return sum;
    }

    // public static void main(String[] args) {
    //     SelectDatasetV2 sd = new SelectDatasetV2();
    //     Set<Integer> level = sd.getlevel();
    //     int sum = sd.getsum();
    //     System.out.println("main");
    //     System.out.println("Set : "+level+"  sum : "+sum);
    //     System.out.println("main");
    // }
}


