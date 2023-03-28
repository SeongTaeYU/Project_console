package com.javalab;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 계산기
 */
public class Calculator extends JFrame implements ActionListener {

   private JTextField inputTField;                       	  //입력 텍스트 박스
   private String num = "";                           		  //계산식의 숫자를 담을 변수
   private String prev_oper = "";                             //방금 누른 버튼을 변수 하나에 기억하도록 지정하기 위한 변수선언 (예외처리)
   private List<String> equation = new ArrayList<String>();   //ArrayList 숫자와 연산기호를 구분하여 담는다.
   
   private JPanel panel;
   
   //생성자
   public Calculator() {
      super("계산기");
      
      setLayout(null);
      
      // inputTField 텍스트 크기,정렬, 위치 정의
      inputTField = new JTextField();                     //JTextField 객체 생성         
      inputTField.setEditable(false);                     //텍스트창 수정불가
      inputTField.setBackground(Color.WHITE);               //Background Color WHITE 설정
      inputTField.setHorizontalAlignment(JTextField.RIGHT);   //오른쪽 정렬
      inputTField.setFont(new Font("맑은고딕", Font.BOLD,50));
      inputTField.setBounds(8,10,270,70);
      
      //버튼
      JPanel btnPanel = new JPanel();
      btnPanel.setLayout(new GridLayout(4,4,10,10));   //GridLayout() 격자형태 배치  : (가로칸수,세로칸수, 좌우간겨, 상하 간격)
      btnPanel.setBounds(8,90,270,235);            //x축,y축, 길이, 높이
      
      //계산기 버튼의 글자를 차례대로 배열에 저장
      String btn_names[] =  { "C", "÷", "×", "=", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "0" };
      //버튼 배열 객체 생성
      JButton btns[] = new JButton[btn_names.length];
      
      //for문을 이용한 배열 버튼 만들기
      for (int i = 0; i < btn_names.length; i++) {
         //i 번만큼 버는 객체 생성
         btns[i] = new JButton(btn_names[i]);
         // 맑은 고딕, BOLD, 24폰트크기
         btns[i].setFont(new Font("맑은고딕",Font.BOLD,24));   
         if(btn_names[i]== "C")btns[i].setBackground(Color.ORANGE);   //"C" 버튼 BackGround  ORANGE Color 설정
         else if((i>=4 && i <=6)||(i>=8 && i<=10)||(i>=12&& i <=14))btns[i].setBackground(Color.BLACK);   //숫자 패드 (1~9) BackGround BLACK Color 설정 
         else btns[i].setBackground(Color.GRAY);   //남은 버튼  BackGround Color GRAY 설정
         btns[i].setForeground(Color.WHITE);      // setForeground() : 버튼 글씨 색은 WHITE 설정
         btns[i].setBorderPainted(false);      //setBorderPainted(false) : Border 윤곽선을 없애줌
         btns[i].addActionListener(this);   //ActionListener 버튼에 추가 CaActionListener()  :
         btnPanel.add(btns[i]);
      }
      //inputTField 추가
      add(inputTField);
      //버튼들을 버튼 패널에 추가
      add(btnPanel);
      
      // 기본 프로그램 설정(제목, 크기, 위치)
      setTitle("계산기");
      setVisible(true);   //프레임에 보이기 true
      setSize(300,370);
      setLocationRelativeTo(null);   //윈도우 창을 화면의 가운데에 띄우는 기능
      setResizable(false);         //창 크기를 조절할수 없도록 하기
      //창이 닫히지만 JVM에 남아 있음. 그래서 창을 완전하게 닫아주기 위해 사용
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
   }

      // actionPerformed(ActionEvent e) : ArrayList에 숫자와 연산 기호가 담기는 기능
      public void actionPerformed(ActionEvent e) {   //actionPerformed(ActionEvent e): 이벤트 메소드로 이벤트를 처리
         String oper = e.getActionCommand();   //e.getActionCommand() : 어떤 버튼이 눌렸는지 받아오는 기능
         
         if(oper.equals("C")) {   // C : 계산식 내용을 지워주는 기능
            inputTField.setText("");
         }else if(oper.equals("=")) {   // = : 입력된 식을 계산해 계산값 표출하는 기능
            // calculate 메소드를 이용해 계산을 해주고 계산식 화면에 값을 띄어주면 된다.
            String result = Double.toString(calculate(inputTField.getText()));
            inputTField.setText("" +result);
            num = "";
         //[예외처리] : 계산식이 비어 있지 않고 연산자를 중복으로 입력하지 않을 시 입력할 수 있음
            //지금 누른 버튼이 연산자일때의 조건문
         }else if(oper.equals("+") || oper.equals("-")||oper.equals("×")||oper.equals("÷")) {
            // 첫 입력값이 음수일 때도 있으므로 계산식이 비어 있을때  - 사용가능 조건문
            if(inputTField.getText().equals("") && oper.equals("-")) {
               inputTField.setText(inputTField.getText()+e.getActionCommand());
               
               //이전에 누른 버튼이 연산자가 아니고 계산식이 비어 있지 않을때의 조건문
            }else if(!inputTField.getText().equals("")&& !prev_oper.equals("+")&& !prev_oper.equals("-")&& !prev_oper.equals("×")&& !prev_oper.equals("÷")) {
               inputTField.setText(inputTField.getText()+e.getActionCommand());
            }
            
         }else {//나머지 숫자 버튼은 눌렀을 시 계산식에  추가
            inputTField.setText(inputTField.getText()+e.getActionCommand());
         }
         //마지막으로 누른 버튼을 기억할 수 있게 prev_oper변수에 담아준다.
         prev_oper = e.getActionCommand();
      }
      

      //계산기능
      private double calculate(String inputText) {
         fullTextParsing(inputText);   //fullTextParsing() : 
         
         double prev = 0.0;      //계산된 값이 갱신되어 저장
         double current = 0.0;   
         String mode = "";      //연산기호 처리를 위한 변수 선언
         
         //사칙연산(곱셈, 나눗셈 )연산 먼저 한 뒤 계산한 식은 ArrayList에서 삭제
         for (int i = 0; i < equation.size(); i++) {
            String s = equation.get(i);
            
            if(s.equals("+")) {
               mode = "add";
            }else if(s.equals("-")) {
               mode = "minus";
            }else if(s.equals("×")) {
               mode = "mul";
            }else if(s.equals("÷")) {
               mode = "div";
            }else {
               // 연산자가 곱셈 혹은 나눗셈이고 현재 인덱스의 값이 숫자일때 연산
               if((mode.equals("mul")|| mode.equals("div"))&& !s.equals("+")&&!s.equals("-")&& !s.equals("×")&&!s.equals("÷")) {
                  Double one = Double.parseDouble(equation.get(i-2));   //Ex) 4+3*7에서 인덱스 2번째인 7을 one에 넣어준다.  
                  Double two = Double.parseDouble(equation.get(i));
                  Double result = 0.0;
                  
                  //mode에 따라 계산
                  if(mode.equals("mul")) {
                     result = one * two;
                  }else if(mode.equals("div")) {
                     result = one / two;
                  }
                  // 계산한 result 값을 ArrayList에 추가
                  equation.add(i+1,Double.toString(result));
                  
                  // 곱셈 혹은 나눗셈 계산이 끝났으니 ArrayList에서 삭제
                  for (int j = 0; j < 3; j++) {
                     equation.remove(i-2);
                  }
                  i -= 2;   //결과값이 생긴 인덱스로 이동   Ex) 4+3*7에서  4+21이 되었으므로 인덱스도 앞으로 2만큼 되돌아감
               }
            }
         }   //곱셈 나눗셈을 먼저 계산
         
         for(String s : equation) {
            if(s.equals("+")) {
               mode = "add";
            }else if(s.equals("-")) {
               mode = "sub";
            }else {
               current = Double.parseDouble(s);   //나머지(숫자)의 경우 문자열을 Double로 형변환
               if(mode.equals("add")) {
                  prev += current;
               }else if(mode.equals("sub")) {
                  prev -= current;
               }else {
                  prev = current;
               }
            }
            //소수점 여섯째자리에서 반올림
            prev = Math.round(prev*100000) /100000.0;
         }
         //계산값 반환
         return prev;
      }//end calculate
      
      //계산식의 글자를 하나하나 거쳐가게 하는 기능 메소드
      private void fullTextParsing(String inputText) {
         equation.clear();   //.clear() :  내부의 배열을 모두 null로 초기화 하고 size를 0으로 설정
         
         //for문으로 계산식의 글자를 하나하나  거쳐가게 한다.
         for (int i = 0; i < inputText.length(); i++) {
            char ch = inputText.charAt(i);
            
            if(ch == '-' || ch == '+' || ch== '×' || ch == '÷') {
               equation.add(num);      //숫자를 먼저 ArrayList에 추가
               num = "";            //num 초기화
               equation.add(ch+"");      //연산기호를 ArrayList에 추가
            }else{
               num = num+ch;         //나머지(숫자)의 경우 num 문자에 더해준다.
            }
         }
         equation.add(num);      //반복문이 끝난 최종으로 있는 num은 ArrayList에 추가된다.
         equation.remove("");   //처음 -가 있으면 ""가 추가되어 에러 발생하기 때문에 ArrayList ""제거
      
   }//end fullTextParsing
   

   // main(실행) 메소드
   public static void main(String[] args) {
      
      new Calculator();
      

   } // main 

} // class e