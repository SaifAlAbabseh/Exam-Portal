package examportal;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class AttendInfo extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnNewButton;
	public static String examCode="";
	public static int duration=0, hour=0, minute=0;
	

	/**
	 * Create the frame.
	 */
	public AttendInfo() {
		setTitle("Attend Information");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 758, 532);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		panel.setForeground(Color.BLACK);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Your Full Name : ");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(118, 161, 178, 13);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBorder(new LineBorder(Color.RED, 2));
		textField.setForeground(Color.RED);
		textField.setFont(new Font("Tahoma", Font.BOLD, 12));
		textField.setBounds(294, 147, 237, 42);
		panel.add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Start Exam");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField.getText().trim();
				if(!name.equals("")) {
					addAttendance(name);
					StartExam.examCode=examCode;
					StartExam.duration=duration;
					StartExam.hour=hour;
					StartExam.minute=minute;
					dispose();
					new StartExam().setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "Name field is empty!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setFocusable(false);
		btnNewButton.setForeground(Color.CYAN);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(Color.BLUE);
		btnNewButton.setBounds(224, 229, 207, 52);
		panel.add(btnNewButton);
		setLocationRelativeTo(null);
	}
	private int getHowMuchBefore() {
		int res=0;
		try {
			Connection conn=DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
			Statement stmt=conn.createStatement();
			
			String query="SELECT COUNT(*) FROM e"+examCode+"a";
			ResultSet result=stmt.executeQuery(query);
			
			
			
			if(result.next()) {
				res=result.getInt(1);
			}
			
			conn.close();
			stmt.close();
			
		}
		catch(Exception exc) {
			JOptionPane.showMessageDialog(null, ""+exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return res;
	}
	private void addAttendance(String name) {
		int temp=getHowMuchBefore();
		int pos=temp+1;
		StartExam.pos=pos;
		try {
			Connection conn=DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
			Statement stmt=conn.createStatement();

			
			String query="INSERT INTO e"+examCode+"a VALUES ('"+pos+"','"+name+"','0')";
			stmt.executeUpdate(query);
			
			String CreateTableQuery="CREATE TABLE e"+examCode+"a"+pos+" ( qnumber VARCHAR(1000), answer VARCHAR(1000) )";
			stmt.executeUpdate(CreateTableQuery);
			
			conn.close();
			stmt.close();
			
		}
		catch(Exception exc) {
			JOptionPane.showMessageDialog(null, ""+exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
}
