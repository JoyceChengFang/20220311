package tw.Joyce.tutor;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GuessNumber extends JFrame { //implements ActionListener
    private JTextField input;
    private JButton guess;
    private JTextArea log;
    private String Answer;
    private int counter;
    private int number = 3;

    //建構式=>視窗設計
    public GuessNumber() {
        super("猜數字遊戲");
        setLayout(new BorderLayout());

        input = new JTextField();
        guess = new JButton("猜");
        log = new JTextArea();

        add(log, BorderLayout.CENTER);

        JPanel top = new JPanel(new BorderLayout());
        add(top, BorderLayout.NORTH);

        top.add(guess, BorderLayout.EAST);
        top.add(input, BorderLayout.CENTER);

        newGame();

        // guess.addActionListener(new MyListener()); //另外定義=>搭配最後class MyListener
        // guess.addActionListener(this); //自己實作=>搭配開頭class GuessNumber後implements ActionListener及下方@Override
        // guess.addActionListener(new MyListenerV2()); //寫成內部類別=>搭配下方class MyListenerV2
        guess.addActionListener(new ActionListener(){ //寫成內部類別(直接定義)=>點選按鈕後(事件)執行doGuess()
            @Override
            public void actionPerformed(ActionEvent e){
                //System.out.println(createAnswer(4));
                doGuess();
            }
        });

        setSize(640,480);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // 點選按鈕後(事件)印出3及執行createAnswer(int dig)
    // class MyListenerV2 implements ActionListener {
    //     public void actionPerformed(ActionEvent e) {
    //         System.out.println("3");
    //         System.out.println(createAnswer(4));
    //     }
    // }

    //方法=>點選"猜"後執行
    private void doGuess() {
        // counter++
        counter++;
        // get input String
        String str = input.getText();
        // 判斷輸入是否正確
        String n = "[0-9]{"+number+"}";
        if(str.matches(n)){
            // checkAB() => ?A?B
            String checkab = checkAB(str);
            // display => log
            log.append(counter+".  "+str+" => "+checkab+"\n");
            // if (?A?B .equals("3A0B")) => showResultDialog(true);
            if(checkab.equals(number+"A0B")){ showResultDialog(true);newGame();}
            // if (counter == 10) => showResultDialog(false);
            if(counter == 10){showResultDialog(false);newGame();}
        }else{
            JOptionPane.showMessageDialog(null, "請重新輸入");
        }
        String t = "";
        input.setText(t);
    }

    //方法=>無輸入猜測幾個數字執行猜測3個數字
    // private String createAnswer() {
    //     return createAnswer(3);
    // }
    //方法=>產生答案(隨機產生n個0-9不重複的數字)
    public static String createAnswer(int dig) {
        int[] poker = new int[10];
        boolean isRepeat;
        int temp;
        for (int i=0; i<poker.length; i++){
            do{
                temp = (int)(Math.random()*10);

                //檢查機制
                isRepeat = false;
                for (int j=0; j<i; j++){
                    if(temp == poker[j]){
                        isRepeat = true;
                        break;
                    }
                }
            }while(isRepeat);


            poker[i] = temp;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < dig; i++) {
            sb.append(poker[i]);
        }
        return sb.toString();
    }

    //方法=>判斷並回傳幾A幾B
    private String checkAB(String str) {
        int aNumber = 0, bNumber = 0;
        for (int i = 0; i < str.length(); i++) {
            for (int j = 0; j < Answer.length(); j++) {
                if (i == j && str.charAt(i)==Answer.charAt(j)) {
                    aNumber++;
                    break;
                } else if (str.charAt(i)==Answer.charAt(j)){
                    bNumber++;
                    break;
                }
            }
        }
        return aNumber+"A"+bNumber+"B";
    }

    //方法=>跳出訊息視窗顯示WINNER或Loser
    private void showResultDialog(boolean isWin) {
        JOptionPane.showMessageDialog(null, isWin?"WINNER":"Loser\n正確答案是 : "+Answer);
    }

    //方法=>開啟新局
    private void newGame() {
        // counter = 0
        counter = 0;
        // input, log => clear
        String t = "";
        input.setText(t);
        log.setText(t);
        // createAnswer
        Answer = createAnswer(number);
        System.out.println(Answer);
        log.append("請輸入"+number+"個數字\n");
    }

    //方法=>程式執行進入點
    public static void main(String[] args) {
        new GuessNumber();
    }

    // 點選按鈕後(事件)印出2及執行createAnswer(int dig)
    // @Override
    // public void actionPerformed(ActionEvent e) {
    //     // TODO Auto-generated method stub
    //     System.out.println("2");
    //     System.out.println(createAnswer(4));
    // }
}

// 點選按鈕後(事件)印出1及執行createAnswer(int dig)
// class MyListener implements ActionListener {
//     public void actionPerformed(ActionEvent e) {
//         System.out.println("1");
//         System.out.println(GuessNumber.createAnswer(4));
//     }
// }