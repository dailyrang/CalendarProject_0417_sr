import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class WinCalendar extends JDialog {

	private JPanel panelCalendar;
	private JComboBox cbMonth;
	private JComboBox cbYear;
	private JButton btnNextMonth;
	private JButton btnPrevMonth;
	private JButton btnNextYear;
	private JButton btnPrevYear;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinCalendar dialog = new WinCalendar();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the dialog.
	 */
	public WinCalendar() {
		setTitle("달력(1923년~2123년)");
		setBounds(100, 100, 450, 392);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		
		panelCalendar = new JPanel();
		getContentPane().add(panelCalendar, BorderLayout.CENTER);
		panelCalendar.setLayout(new GridLayout(0, 7, 2, 2));
		
		
		JButton btnRun = new JButton("달력보기");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCalendar();				
			}
		});
		
		cbMonth = new JComboBox();
		cbMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showCalendar();
			}
		});
		
		btnPrevMonth = new JButton("<");
		btnPrevMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int month = Integer.parseInt(cbMonth.getSelectedItem().toString());
				month--;		
				if(month==0) {
					month=12;
				}
				cbMonth.setSelectedIndex(month-1);
				showCalendar();
			}
		});
		
		btnPrevYear = new JButton("<<");
		panel.add(btnPrevYear);
		panel.add(btnPrevMonth);
		
		cbYear = new JComboBox();
		panel.add(cbYear);
		cbMonth.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		panel.add(cbMonth);
		panel.add(btnRun);
		
		btnNextMonth = new JButton(">");
		btnNextMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int month = Integer.parseInt(cbMonth.getSelectedItem().toString());
				month++;		
				if(month==13) {
					month=1;
				}
				cbMonth.setSelectedIndex(month-1);
				showCalendar();
			}
		});
		panel.add(btnNextMonth);
		
		btnNextYear = new JButton(">>");
		panel.add(btnNextYear);

		for(int year=1923;year<=2123;year++)
			cbYear.addItem(year);
	}

	protected void showCalendar() {
		// 버튼 싹 지우고
		Component []componentList = panelCalendar.getComponents();
		for(Component c: componentList) {
			if(c instanceof JButton )
				panelCalendar.remove(c);
		}
		panelCalendar.revalidate();
		panelCalendar.repaint();
		
		// 일요일~토요일 버튼 생성한다.
//		JButton btn1 = new JButton("일요일");	panelCalendar.add(btn1);	
//		JButton btn2 = new JButton("월요일");	panelCalendar.add(btn2);
//		JButton btn3 = new JButton("화요일");	panelCalendar.add(btn3);
//		JButton btn4 = new JButton("수요일");	panelCalendar.add(btn4);
//		JButton btn5 = new JButton("목요일");	panelCalendar.add(btn5);
//		JButton btn6 = new JButton("금요일");	panelCalendar.add(btn6);
//		JButton btn7 = new JButton("토요일");	panelCalendar.add(btn7);
		
//		String yoil[] = {"일","월","화","수","목","금","토"};
//		for(int i=0;i<yoil.length;i++) {
//			JButton btn = new JButton(yoil[i] + "요일");	
//			panelCalendar.add(btn);
//		}
		
		 String yoil = "일월화수목금토";
	      for(int i=0;i<yoil.length();i++) {
	         JButton btn = new JButton(yoil.substring(i,i+1));   
	         panelCalendar.add(btn);
	      }

	    int Month[] = {31,28,31,30,31,30,31,31,30,31,30,31};
	    int year = Integer.parseInt(cbYear.getSelectedItem().toString());
		int month = Integer.parseInt(cbMonth.getSelectedItem().toString());
		int sum = 0;
		
		// 해당하는 전연도까지의 합을 구하시오.(1923.1.1~2022.12.31)
		for(int i=1923;i<year;i++) {
			if(i%4==0 && i%100 !=0 || i%400==0)
				sum = sum + 366;
			else
				sum = sum + 365;
		}
		// 해당하는 전월까지의 날짜 수의 합을 구하시오. 
		for(int i=0;i<month-1;i++) {
			if(i==1 && (year%4==0 && year%100!=0 || year%400==0))
				sum = sum + ++Month[i];
			else
				sum = sum + Month[i];
		}		
	    
	    // 1923년도 1월 1일의 시작은 월요일(1)부터 시작이다.
	      
	    int start = 1;
	    start = (start + sum ) % 7;
	    
	    for(int i=1;i<=start;i++) {
			JButton btn = new JButton("");
			panelCalendar.add(btn);			
			btn.setVisible(false);
		}
	    
	      
		// 해당하는 달의 마지막 날짜까지 버튼을 생성한다.
		
		int last = Month[month-1];
		if(month==2 &&  (year%4==0 && year%100!=0 || year%400==0) )
			last++;
		for(int i=1;i<=last;i++) {
			JButton btn = new JButton(i + "");
			panelCalendar.add(btn);			
			panelCalendar.revalidate();
		}
	}

}
