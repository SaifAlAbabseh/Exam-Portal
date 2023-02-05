package examportal;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.Cursor;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class AttendMain extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private String duration, day, month, year, hour, minute;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AttendMain frame = new AttendMain();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AttendMain() {
		setResizable(false);
		setTitle("Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 767, 424);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBorder(new LineBorder(Color.BLUE, 2));
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setForeground(Color.BLUE);
		textField.setBounds(293, 143, 216, 46);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Exam Code : ");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(151, 157, 132, 13);
		panel.add(lblNewLabel);

		JButton btnNewButton = new JButton("Attend");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String examCode = textField.getText().trim();
				if (!examCode.equals("")) {
					if (checkIfExists(examCode)) {
						if (checkIfStarted(examCode)) {
							AttendInfo.examCode = examCode;
							AttendInfo.duration = Integer.parseInt(duration);
							AttendInfo.hour = Integer.parseInt(hour);
							AttendInfo.minute = Integer.parseInt(minute);
							dispose();
							new AttendInfo().setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null,
									"Exam has not been started yet, or it has been finished.", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Exam doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Empty field!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBorder(null);
		btnNewButton.setForeground(Color.CYAN);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setFocusable(false);
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setBounds(257, 214, 156, 46);
		panel.add(btnNewButton);
		setLocationRelativeTo(null);
	}

	private boolean checkIfStarted(String examCode) {
		try {
			Connection conn = DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
			Statement stmt = conn.createStatement();

			String query = "SELECT day,month,year,hour,mins,duration FROM exams WHERE id='" + examCode + "'";
			ResultSet result = stmt.executeQuery(query);
			if (result.next()) {
				day = result.getString(1);
				month = result.getString(2);
				year = result.getString(3);
				hour = result.getString(4);
				minute = result.getString(5);
				duration = result.getString(6);
				
				GeoMain geo = new GeoMain();
				
				int[] date = geo.getDate();

				int dayInt = date[2];
				int monthInt = date[1];
				int yearInt = date[0];
				
				int[] time = geo.getTime();
				
				int hourInt = time[0];
				if (hourInt == 0) {
					hourInt = 24;
				}
				int minuteInt = time[1];
				int durationInt = Integer.parseInt(duration);

				if (Integer.parseInt(year) == yearInt && Integer.parseInt(month) == monthInt && Integer.parseInt(day) == dayInt) {
					int temp = durationInt + Integer.parseInt(minute);
					int hoursMore = temp / 60;
					int minutesMore = temp % 60;
					int h = hoursMore + Integer.parseInt(hour);
					int m = minutesMore;

					if(h>24) {
						hourInt = hourInt + Integer.parseInt(hour);
					}
					
					if(hourInt>Integer.parseInt(hour) && hourInt<h) {
						return true;
					}
					
					else if(hourInt==Integer.parseInt(hour) && hourInt<h) {
						if(minuteInt>=Integer.parseInt(minute)) {
							return true;
						}
					}
					
					else if(hourInt>Integer.parseInt(hour) && hourInt==h) {
						if(minuteInt<m) {
							return true;
						}
					}
					else if(hourInt==Integer.parseInt(hour) && hourInt==h) {
						if(minuteInt>=Integer.parseInt(minute) && minuteInt<m) {
							return true;
						}
					}
				}

			}

			conn.close();
			stmt.close();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(null, "" + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	private boolean checkIfExists(String examCode) {
		try {
			Connection conn = DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
			Statement stmt = conn.createStatement();

			String query = "SELECT * FROM exams WHERE id='" + examCode + "'";
			ResultSet result = stmt.executeQuery(query);
			if (result.next()) {
				StartExam.numberOfMulti = Integer.parseInt(result.getString(5));
				StartExam.numberOfQuestions = Integer.parseInt(result.getString(6));
				StartExam.maxMark = Integer.parseInt(result.getString(3));
				return true;
			}

			conn.close();
			stmt.close();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(null, "" + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
}
