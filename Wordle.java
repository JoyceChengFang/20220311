package tw.joyce.test2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import tw.joyce.test1.SelectDatasetV2;

public class Wordle {
    private MyFrame f;
    private JPanel panel,top;
    private JLabel label;
    private JTextField input;
    private JButton guess;
    private JTable table;
    private MyModel myModel;

    public static Connection conn;
    private static String[] retuStrings;
    private int sum;
    private String getlevel;
    private int counter;
    private String Answer;
    private int number;
    private int[][] tablecolor;


    public Wordle() {

        connMysql(); //連接資料庫
        selectDataset(); //選取資料庫
        newGame();
        
    }

    private class MyFrame extends JFrame {
        private MyFrame() {
            super("Wordle");
            setLayout(new BorderLayout());


            //視窗設計
            label = new JLabel("Please enter "+ number +" characters (lowercase):");
            label.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
            label.setForeground(Color.WHITE);
            input = new JTextField();
            input.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
            guess = new JButton("Guess");
            guess.setFont(new Font("SAN_SERIF", Font.BOLD, 20));
            top = new JPanel(new BorderLayout());
            top.setBackground(Color.BLACK);
            top.add(label, BorderLayout.NORTH);
            top.add(input, BorderLayout.CENTER);
            top.add(guess, BorderLayout.EAST);
            add(top, BorderLayout.NORTH);
            
            myModel = new MyModel();
            table = new JTable(myModel);
            table.setGridColor(Color.WHITE); //設定表格框線顏色
            table.setBackground(new Color(116,120,122)); //設定表格背景顏色
            table.setForeground(Color.WHITE); //設定表格內容顏色
            table.setFont(new Font("SAN_SERIF", Font.BOLD, 30)); //設定字型
            table.setRowHeight(50); //設定表格欄高
            //設定表格列高
            for (int i = 0; i < number; i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(50);
            }
            panel = new JPanel();
            panel.setBackground(Color.BLACK);
            panel.add(table);
            add(panel, BorderLayout.CENTER);
            
            //設定按Enter後執行
            Action sendAction = new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    doGuess();
                }
            };
            input.setAction(sendAction);

            guess.addActionListener(new ActionListener(){ //寫成內部類別(直接定義)=>點選按鈕後(事件)執行doGuess()
                @Override
                public void actionPerformed(ActionEvent e){
                    doGuess();
                }
            });
            //table.setDefaultRenderer(Object.class, new ColorTable());


            setSize((number+3)*50+15,(number+3)*50+15);
            setLocationRelativeTo(null);
            setVisible(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
    }

    private class MyModel extends DefaultTableModel {
        @Override
        public int getColumnCount() {
            return number;
        }

        @Override
        public int getRowCount() {
            return number + 1;
        }

        // @Override
        // public Object getValueAt(int row, int column) {
        //     return data[row][column];
        // }
        
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    //更改cell顏色
    public class ColorTable extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int col) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            
            if (tablecolor[row][col] == 1) {
                setOpaque(true);
                setBackground(new Color(103,165,97));
            } else if(tablecolor[row][col] == 2){
                setOpaque(true);
                setBackground(new Color(195,174,85));
            } else {
                setOpaque(false);
            }

            setHorizontalAlignment(SwingConstants.CENTER);

            return this;
        }
    }

    //方法=>點選"猜"後執行
    private void doGuess() {
        // get input String
        String str = input.getText();
        // 判斷輸入是否正確
        String n = "[a-z]{"+number+"}";
        if(str.matches(n)){
            // counter++
            counter++;
            // checkcolor
            int a = checkColor(str, counter);
            table.setDefaultRenderer(Object.class, new ColorTable());
            // display => table
            Object[] data = new Object[number];
            for (int i = 0; i < number ; i++) {
                data[i] = str.charAt(i);
                table.setValueAt(data[i], counter-1, i);
            }
            // if (猜對) => showResultDialog(true);
            if(a == number){ showResultDialog(true);f.dispose();newGame();}
            // if (超過猜測次數) => showResultDialog(false);
            if(counter == number+1){showResultDialog(false);f.dispose();newGame();}
        }else{
            JOptionPane.showMessageDialog(null, "Please enter "+ number +" characters (lowercase):");
        }
        String t = "";
        input.setText(t);
    }

    //方法=>連接資料庫
	private static void connMysql() {
		try {
			Properties prop = new Properties();
			prop.put("user", "root");
			prop.put("password", "");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/iii", prop);
		}catch(Exception e) {
			System.out.println(e.toString());
		}
	}

    //方法=>選取資料庫
    private void selectDataset() {
        SelectDatasetV2 sd = new SelectDatasetV2();
        Set<Integer> level = sd.getlevel();
        sum = sd.getsum();
        getlevel = "(";
        int a = 1;
        for (Integer v : level) {
            if( a == level.size() ){ getlevel = getlevel + v.toString() + ")";}
            else{getlevel = getlevel + v.toString() + ",";}
            a++;
        }
    }

    //方法=>從資料庫取得vocabulary並分割
    private void selectData(String getlevel,int rand) {
        String ret = new String();
        String sql = "SELECT vocabulary FROM wordle WHERE level IN " + getlevel + " ORDER BY id LIMIT " + rand + " ,1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(
                    sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
			
			
			// 4. execute SQL statement
            ResultSet rs = pstmt.executeQuery();
            rs.beforeFirst();
            while(rs.next()){
                ret = rs.getString("vocabulary");
            }
            System.out.println("getlevel = "+getlevel+" rand = "+rand+" ret = "+ret);
		}catch(Exception e) {
			System.out.println(e.toString());
		}
        retuStrings = ret.split("[@]{1}[0-9]*+");
	}

    //方法=>開啟新局
    private void newGame() {
        // counter = 0
        counter = 0;
        Answer = " ";
        // createAnswer
        createAnswer();
        tablecolor = new int[number+1][number];
        f = new MyFrame();
        System.out.println(Answer);
    }

    //方法=>產生新的答案
    public void createAnswer() {

        //選擇level後在資料庫隨機取得結果
        while (!Answer.matches("[a-z]*+")) {
            int rand = (int)(Math.random()*sum);
            selectData(getlevel,rand);
            Answer = retuStrings[0];
        }
        number = Answer.length();
    }

    private int checkColor(String str, int counter) {
        int aNumber = 0;
        // for (int i = 0; i < str.length(); i++) {
        //     if(str.charAt(i)==Answer.charAt(i)) {
        //         tablecolor[counter-1][i] = 1;
        //         aNumber++;
        //     } else if (Answer.indexOf(str.charAt(i)) != -1) {
        //         tablecolor[counter-1][i] = 2;
        //     }
        // }
        int[] a = new int[number];
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < Answer.length(); j++) {
                if (i == j && str.charAt(i)==Answer.charAt(j)) {
                    tablecolor[counter-1][i] = 1;
                    a[i] = 1;
                    aNumber++;
                    break;
                }
            }
        }
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < Answer.length(); j++) {
                if ( a[i]!=1 && a[j]!=1 && i != j && str.charAt(i)==Answer.charAt(j)) {
                    tablecolor[counter-1][i] = 2;
                    break;
                } 
            }
        }
        return aNumber;
    }

    //方法=>跳出訊息視窗顯示WINNER或Loser
    private void showResultDialog(boolean isWin) {
        JOptionPane.showMessageDialog(null, isWin?"WINNER\n\n"+Answer+"\n"+retuStrings[1]:"Loser\n\n正確答案是 : "+Answer+"\n"+retuStrings[1]);
    }

    public static void main(String[] args) {
        new Wordle();
    }
}
