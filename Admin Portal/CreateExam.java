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
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import java.awt.Font;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Cursor;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class CreateExam extends JFrame {
	private boolean checkExamCode(String code) {
		for (int i = 0; i < code.length(); i++) {
			if (!((((int) code.charAt(i)) >= 48 && (((int) code.charAt(i)) <= 57))
					|| (((int) code.charAt(i)) >= 65 && (((int) code.charAt(i)) <= 122)))) {
				return false;
			}
		}
		return true;
	}

	public static String isForEditExamCode = null;

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the frame.
	 */
	public CreateExam() {

		setTitle("Create Exam");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 892, 643);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Set Exam Code");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(113, 34, 107, 29);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Exam Duration");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(113, 100, 116, 29);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Number of Multiple Answers");
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setBounds(113, 160, 209, 29);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Number of Questions");
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(113, 223, 159, 29);
		panel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Time and Date ");
		lblNewLabel_4.setForeground(Color.RED);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setBounds(113, 309, 116, 29);
		panel.add(lblNewLabel_4);

		textField = new JTextField();
		textField.setForeground(Color.RED);
		textField.setFont(new Font("Tahoma", Font.BOLD, 13));
		textField.setBorder(new LineBorder(Color.RED, 2));
		textField.setBounds(339, 30, 159, 38);
		panel.add(textField);
		textField.setColumns(10);

		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinner.setBounds(339, 98, 159, 38);
		panel.add(spinner);

		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(new Integer(2), new Integer(2), null, new Integer(1)));
		spinner_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinner_1.setBounds(339, 158, 159, 38);
		panel.add(spinner_1);

		JSpinner spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinner_2.setBounds(339, 221, 159, 38);
		panel.add(spinner_2);

		JLabel lblNewLabel_5 = new JLabel("Year");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5.setBounds(368, 301, 45, 13);
		panel.add(lblNewLabel_5);

		JSpinner spinner_3 = new JSpinner();
		spinner_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinner_3.setBounds(359, 319, 66, 42);
		panel.add(spinner_3);
		int year = new GregorianCalendar().get(Calendar.YEAR);
		spinner_3.setModel(new SpinnerNumberModel(new Integer(year), new Integer(year), null, new Integer(1)));

		JLabel lblNewLabel_6 = new JLabel("Month");
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_6.setBounds(464, 301, 54, 13);
		panel.add(lblNewLabel_6);

		JSpinner spinner_4 = new JSpinner();
		spinner_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinner_4.setBounds(464, 319, 66, 42);
		panel.add(spinner_4);
		spinner_4.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), new Integer(12), new Integer(1)));

		JLabel lblNewLabel_7 = new JLabel("Day");
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_7.setBounds(570, 301, 45, 13);
		panel.add(lblNewLabel_7);

		JSpinner spinner_5 = new JSpinner();
		spinner_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinner_5.setBounds(560, 319, 66, 42);
		panel.add(spinner_5);
		spinner_5.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), new Integer(31), new Integer(1)));

		JLabel lblNewLabel_8 = new JLabel("Hour");
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_8.setBounds(660, 301, 45, 13);
		panel.add(lblNewLabel_8);

		JSpinner spinner_6 = new JSpinner();
		spinner_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinner_6.setBounds(660, 319, 66, 42);
		panel.add(spinner_6);
		spinner_6.setModel(new SpinnerNumberModel(1, 1, 24, 1));

		JLabel lblNewLabel_9 = new JLabel("Minute");
		lblNewLabel_9.setForeground(Color.WHITE);
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_9.setBounds(761, 301, 66, 13);
		panel.add(lblNewLabel_9);

		JSpinner spinner_7 = new JSpinner();
		spinner_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		spinner_7.setBounds(761, 319, 66, 42);
		panel.add(spinner_7);
		spinner_7.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(59), new Integer(1)));

		JLabel lblNewLabel_10 = new JLabel("Enter the duration in minutes.");
		lblNewLabel_10.setForeground(Color.GRAY);
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_10.setBounds(508, 110, 218, 13);
		panel.add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("Hour should be from [1, 24]");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_11.setForeground(Color.GRAY);
		lblNewLabel_11.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_11.setBounds(614, 371, 200, 29);
		panel.add(lblNewLabel_11);

		JLabel lblNewLabel_12 = new JLabel("Code should be alpha-numeric only.");
		lblNewLabel_12.setForeground(Color.GRAY);
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_12.setBounds(508, 44, 264, 13);
		panel.add(lblNewLabel_12);

		JLabel lblNewLabel_13 = new JLabel("Exam Max Mark");
		lblNewLabel_13.setForeground(Color.RED);
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_13.setBounds(113, 411, 126, 13);
		panel.add(lblNewLabel_13);

		JSpinner spinner_8 = new JSpinner();
		spinner_8.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner_8.setFont(new Font("Tahoma", Font.BOLD, 14));
		spinner_8.setBounds(281, 397, 77, 42);
		panel.add(spinner_8);

		JLabel lblNewLabel_14 = new JLabel("Exam Name");
		lblNewLabel_14.setForeground(Color.RED);
		lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_14.setBounds(113, 466, 107, 13);
		panel.add(lblNewLabel_14);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		textField_1.setBounds(281, 456, 209, 38);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JButton btnNewButton_1 = new JButton("<< Go Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateExam.isForEditExamCode = null;
				dispose();
				new AdminMain().setVisible(true);
			}
		});
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_1.setFocusable(false);
		btnNewButton_1.setBorder(null);
		btnNewButton_1.setBackground(Color.YELLOW);
		btnNewButton_1.setBounds(733, 527, 135, 69);
		panel.add(btnNewButton_1);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String examCode = textField.getText().trim();
				String examDuration = ("" + spinner.getValue()).trim();
				String numberOfMulti = ("" + spinner_1.getValue()).trim();
				String numberOfQuestions = ("" + spinner_2.getValue()).trim();
				String year = ("" + spinner_3.getValue()).trim();
				String month = ("" + spinner_4.getValue()).trim();
				String day = ("" + spinner_5.getValue()).trim();
				String hour = ("" + spinner_6.getValue()).trim();
				String minute = ("" + spinner_7.getValue()).trim();
				String examMaxMark = ("" + spinner_8.getValue()).trim();
				String examName = textField_1.getText().trim();

				if (!(examCode.equals("") || examDuration.equals("") || numberOfMulti.equals("")
						|| numberOfQuestions.equals("") || year.equals("") || month.equals("") || day.equals("")
						|| hour.equals("") || minute.equals("") || examMaxMark.equals("") || examName.equals(""))) {
					if (checkExamCode(examCode)) {
						sendExamInformation(examCode, examDuration, numberOfMulti, numberOfQuestions, year, month, day,
								hour, minute, examMaxMark, examName);
					} else {
						JOptionPane.showMessageDialog(null, "Exam code is not valid!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "All fields should not be empty!");
				}

			}
		});
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setForeground(Color.CYAN);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setFocusable(false);
		btnNewButton.setBounds(113, 527, 209, 59);
		panel.add(btnNewButton);

		if (isForEditExamCode != null) {
			loadInformation(textField, spinner, spinner_1, spinner_2, spinner_3, spinner_4, spinner_5, spinner_6,
					spinner_7, spinner_8, textField_1);
		}

	}

	private void loadInformation(JTextField examCode, JSpinner examDuration, JSpinner numberOfMulti,
			JSpinner numberOfQuestions, JSpinner year, JSpinner month, JSpinner day, JSpinner hour, JSpinner minute,
			JSpinner examMaxMark, JTextField examName) {
		try {
			Connection conn = DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
			Statement stmt = conn.createStatement();

			String query = "SELECT * FROM exams WHERE id='" + isForEditExamCode + "'";
			ResultSet result = stmt.executeQuery(query);

			if (result.next()) {
				examCode.setText(result.getString(1));
				examDuration.setValue(new Integer(result.getString(4)));
				numberOfMulti.setValue(new Integer(result.getString(5)));
				numberOfQuestions.setValue(new Integer(result.getString(6)));
				year.setValue(new Integer(result.getString(9)));
				month.setValue(new Integer(result.getString(8)));
				day.setValue(new Integer(result.getString(7)));
				hour.setValue(new Integer(result.getString(10)));
				minute.setValue(new Integer(result.getString(11)));
				examMaxMark.setValue(new Integer(result.getString(3)));
				examName.setText(result.getString(2));
			}

			conn.close();
			stmt.close();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(null, "Error : " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void sendExamInformation(String examCode, String examDuration, String numberOfMulti,
			String numberOfQuestions, String year, String month, String day, String hour, String minute,
			String examMaxMark, String examName) {
		try {
			Connection conn = DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
			Statement stmt = conn.createStatement();

			if (isForEditExamCode != null) {
				if (examCode.equals(isForEditExamCode)) {
					String query = "UPDATE exams SET id='" + examCode + "',name='" + examName + "',mark='" + examMaxMark
							+ "',duration='" + examDuration + "',multiplesnumber='" + numberOfMulti
							+ "',questionsnumber='" + numberOfQuestions + "',day='" + day + "',month='" + month
							+ "',year='" + year + "',hour='" + hour + "',mins='" + minute + "' WHERE id='"
							+ isForEditExamCode + "'   ";
					stmt.executeUpdate(query);
					JOptionPane.showMessageDialog(null, "Successfully saved the exam.", "Successful",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					String checkQuery = "SELECT * FROM exams WHERE id='" + examCode + "'";
					ResultSet checkResult = stmt.executeQuery(checkQuery);
					if (!checkResult.next()) {
						String query = "UPDATE exams SET id='" + examCode + "',name='" + examName + "',mark='"
								+ examMaxMark + "',duration='" + examDuration + "',multiplesnumber='" + numberOfMulti
								+ "',questionsnumber='" + numberOfQuestions + "',day='" + day + "',month='" + month
								+ "',year='" + year + "',hour='" + hour + "',mins='" + minute + "' WHERE id='"
								+ isForEditExamCode + "'   ";
						stmt.executeUpdate(query);
						JOptionPane.showMessageDialog(null, "Successfully saved the exam.", "Successful",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Exam code already exists!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
				String dropTable="DROP TABLE e"+isForEditExamCode;
				stmt.executeUpdate(dropTable);
				createTable(stmt, numberOfMulti, examCode);
			} else {
				String checkQuery = "SELECT * FROM exams WHERE id='" + examCode + "'";
				ResultSet checkResult = stmt.executeQuery(checkQuery);

				if (!checkResult.next()) {
					String query = "INSERT INTO exams VALUES ('" + examCode + "','" + examName + "','" + examMaxMark
							+ "','" + examDuration + "','" + numberOfMulti + "','" + numberOfQuestions + "','" + day
							+ "','" + month + "','" + year + "','" + hour + "','" + minute + "')";
					stmt.executeUpdate(query);
					
					
					createTable(stmt, numberOfMulti, examCode);
					
					JOptionPane.showMessageDialog(null, "Successfully created the exam.", "Successful",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Exam code already exists, type another code!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			conn.close();
			stmt.close();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(null, "Error : " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void createTable(Statement stmt,String numberOfMulti, String examCode) throws Exception{
		String fields="";
		for(int i=1;i<=Integer.parseInt(numberOfMulti);i++) {
			fields+="m"+i+" VARCHAR(1000),";
		}
		
		String createTableQuery="CREATE TABLE e"+examCode+"( n VARCHAR(1000),question VARCHAR(1000),"+fields+" correctanswer VARCHAR(10))";
		stmt.executeUpdate(createTableQuery);
		
		String createAttendanceQuery="CREATE TABLE e"+examCode+"a ( number VARCHAR(1000), name VARCHAR(1000), mark VARCHAR(1000) )";
		stmt.executeUpdate(createAttendanceQuery);
	}
}
