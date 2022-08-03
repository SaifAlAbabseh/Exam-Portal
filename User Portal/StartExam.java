package examportal;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.Dimension;

public class StartExam extends JFrame {

	private JPanel contentPane;
	public static String examCode="";
	public static int duration=0, hour=0, minute=0, numberOfQuestions=0, numberOfMulti=0, maxMark=0, pos=0;
	private double currentMark=0;
	private int qNumber=1, correctAnswer=0;

	/**
	 * Create the frame.
	 */
	public StartExam() {
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1536, 861);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 130, 1107, 158);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Question  /");
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(594, 10, 208, 31);
		panel.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.CYAN);
		panel_1.setBounds(10, 298, 1107, 460);
		panel.add(panel_1);
		
		JButton btnNewButton = new JButton("Next");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setForeground(Color.CYAN);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setFocusable(false);
		btnNewButton.setBounds(639, 810, 217, 41);
		panel.add(btnNewButton);
		
		JPanel panel_2 = new JPanel();
		panel_2.setForeground(Color.CYAN);
		panel_2.setBackground(Color.CYAN);
		panel_2.setBounds(0, 0, 291, 120);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("00");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(Color.BLACK);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_2.setBounds(31, 28, 66, 49);
		panel_2.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("00");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_3.setForeground(Color.BLACK);
		lblNewLabel_3.setBounds(107, 28, 66, 49);
		panel_2.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("00");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_4.setForeground(Color.BLACK);
		lblNewLabel_4.setBounds(183, 28, 66, 49);
		panel_2.add(lblNewLabel_4);
		
		
		JRadioButton[] radio=new JRadioButton[numberOfMulti];
		JLabel[] multis=new JLabel[numberOfMulti];
		ButtonGroup group=new ButtonGroup();
		
		
		panel_1.setLayout(new GridLayout(numberOfMulti,1));
		for(int i=0;i<=numberOfMulti-1;i++) {
			radio[i]=new JRadioButton();
			radio[i].setBackground(Color.CYAN);
			group.add(radio[i]);
			multis[i]=new JLabel("");
			multis[i].setOpaque(true);
			multis[i].setBackground(Color.CYAN);
			JPanel temp=new JPanel(new GridLayout(1,2));
			temp.setPreferredSize(new Dimension(0,20));
			temp.add(radio[i]);
			temp.add(multis[i]);
			panel_1.add(temp);
		}
		
		
		new Timer(lblNewLabel_2, lblNewLabel_3, lblNewLabel_4).start();
		setData(lblNewLabel, radio, multis, lblNewLabel_1);
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean tt=false;
				for(int i=0;i<radio.length;i++) {
					if(radio[i].isSelected()) {
						tt=true;
					}
				}
				if(tt) {
					sendAnswer(radio);
					
					qNumber++;
					setData(lblNewLabel, radio, multis, lblNewLabel_1);
					
					group.clearSelection();
					
					if(qNumber>numberOfQuestions) {
						System.exit(0);
					}
					else if(qNumber==numberOfQuestions) {
						btnNewButton.setText("Finish");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Pick an answer!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	}
	private void sendAnswer(JRadioButton[] radio) {
		int temp=0;
		double tempMaxMark=maxMark;
		double mark=tempMaxMark/numberOfQuestions;
		for(int i=0;i<radio.length;i++) {
			if(radio[i].isSelected()) {
				temp=(i+1);
				break;
			}
		}
		try {
			Connection conn=DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
			Statement stmt=conn.createStatement();
			
			String query="INSERT INTO e"+examCode+"a"+pos+" VALUES ('"+qNumber+"','"+temp+"')";
			stmt.executeUpdate(query);
			
			
			if(temp==correctAnswer) {
				currentMark+=mark;
				String updateMarkQuery="UPDATE e"+examCode+"a SET mark='"+currentMark+"' WHERE number='"+pos+"'";
				stmt.executeUpdate(updateMarkQuery);
			}
			
			
			
			conn.close();
			stmt.close();
		}
		catch(Exception exc) {
			JOptionPane.showMessageDialog(null, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void setData(JLabel question, JRadioButton[] radio, JLabel[] multis, JLabel index) {
		index.setText("Question "+qNumber+"/"+numberOfQuestions);
		
		try {
			Connection conn=DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
			Statement stmt=conn.createStatement();
			
			String query="SELECT * FROM e"+examCode+" WHERE n='"+qNumber+"'";
			ResultSet result=stmt.executeQuery(query);
			
			if(result.next()) {
				correctAnswer=Integer.parseInt(result.getString(3+numberOfMulti));
				question.setText(result.getString(2));
				
				for(int i=0;i<=numberOfMulti-1;i++) {
					multis[i].setText(result.getString(i+3));
				}
			}
			
			conn.close();
			stmt.close();
			
		}
		catch(Exception exc) {
			JOptionPane.showMessageDialog(null, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
