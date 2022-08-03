package examportal;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.Rectangle;

public class EditExam extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public EditExam() {
		setResizable(false);
		setTitle("Edit Exam");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 575);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(2, 200));
		scrollPane.setMaximumSize(new Dimension(32767, 10));
		scrollPane.setBounds(10, 10, 936, 429);
		panel.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(new Rectangle(0, 0, 0, 429));
		panel_1.setMaximumSize(new Dimension(32767, 200));
		panel_1.setBackground(SystemColor.activeCaption);
		scrollPane.setViewportView(panel_1);
		
		setVisible(true);
		setLocationRelativeTo(null);
		
		loadExams(panel_1);
		
		JButton btnNewButton = new JButton("<< Go Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new AdminMain().setVisible(true);
			}
		});
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(Color.YELLOW);
		btnNewButton.setFocusable(false);
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(10, 465, 157, 53);
		panel.add(btnNewButton);
	}
	private void styleButton(JButton button) {
		button.setFont(new Font("Calibari",Font.BOLD,15));
		button.setForeground(Color.white);
		button.setBackground(Color.red);
		button.setFocusable(false);
		button.setBorder(null);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	private void loadExams(JPanel panel) {
		try {
			Connection conn=DriverManager.getConnection(DBInfo.host,DBInfo.username,DBInfo.password);
			Statement stmt=conn.createStatement();
				
			String getSize="SELECT COUNT(*) FROM exams";
			ResultSet sizeRes=stmt.executeQuery(getSize);
			if(sizeRes.next()) {
				int size=sizeRes.getInt(1);
				panel.setLayout(new GridLayout(size, 1));
			}
			
			
			
			String query="SELECT * FROM exams";
			ResultSet result=stmt.executeQuery(query);
			while(result.next()) {
				String examCode=result.getString(1);
				String examName=result.getString(2);
				String numberOfQuestions=result.getString(6);
				String numberOfMulti=result.getString(5);
				
				JPanel resultPanel=new JPanel(new GridLayout(1,4));
				resultPanel.setPreferredSize(new Dimension(0, 100));
				
				JLabel l1=new JLabel("Exam Code : " + examCode);
				JLabel l2=new JLabel("Exam Name : " + examName);
				
				l1.setBackground(Color.WHITE);
				l2.setBackground(Color.WHITE);
				
				resultPanel.add(l1);
				resultPanel.add(l2);
				
				resultPanel.setBackground(Color.WHITE);
				
				JButton editInfo=new JButton("Edit Info");			
				
				JButton editContent=new JButton("Edit Content");
				
				editInfo.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						CreateExam.isForEditExamCode=examCode;
						dispose();
						new CreateExam().setVisible(true);
					}
				});
				
				editContent.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						EditExamContent.examCode=examCode;
						EditExamContent.numberOfQuestions=Integer.parseInt(numberOfQuestions);
						EditExamContent.numberOfMulti=Integer.parseInt(numberOfMulti);
						new EditExamContent().setVisible(true);
					}
				});
				
				styleButton(editInfo);
				styleButton(editContent);
				
				resultPanel.add(editInfo);
				resultPanel.add(editContent);
				
				panel.add(resultPanel);
				
				
			}
			
			stmt.close();
			conn.close();
			
			
			
		}
		catch(Exception exc) {
			JOptionPane.showMessageDialog(null, "Error : "+exc.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
}
