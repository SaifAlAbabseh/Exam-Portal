package examportal;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class ExportExam extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ExportExam() {
		setTitle("Export Exam");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 848, 576);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 804, 407);
		panel.add(scrollPane);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaption);
		scrollPane.setViewportView(panel_1);

		JButton btnNewButton = new JButton("<< Go Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new AdminMain().setVisible(true);
			}
		});
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setForeground(Color.CYAN);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setFocusable(false);
		btnNewButton.setBounds(10, 461, 145, 58);
		panel.add(btnNewButton);
		setLocationRelativeTo(null);

		loadExams(panel_1);

	}

	private void styleButton(JButton button) {
		button.setFont(new Font("Calibari", Font.BOLD, 15));
		button.setForeground(Color.white);
		button.setBackground(Color.red);
		button.setFocusable(false);
		button.setBorder(null);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	private void loadExams(JPanel panel) {
		try {
			Connection conn = DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
			Statement stmt = conn.createStatement();

			String getSize = "SELECT COUNT(*) FROM exams";
			ResultSet sizeRes = stmt.executeQuery(getSize);
			if (sizeRes.next()) {
				int size = sizeRes.getInt(1);
				panel.setLayout(new GridLayout(size, 1));
			}

			String query = "SELECT * FROM exams";
			ResultSet result = stmt.executeQuery(query);
			while (result.next()) {
				String examCode = result.getString(1);
				String examName = result.getString(2);
				String examMaxMark= result.getString(3);

				int numberOfmulti = Integer.parseInt(result.getString(5));

				JPanel resultPanel = new JPanel(new GridLayout(1, 5));
				resultPanel.setPreferredSize(new Dimension(0, 100));

				JLabel l1 = new JLabel("Exam Code : " + examCode);
				JLabel l2 = new JLabel("Exam Name : " + examName);

				l1.setBackground(Color.WHITE);
				l2.setBackground(Color.WHITE);

				resultPanel.add(l1);
				resultPanel.add(l2);

				resultPanel.setBackground(Color.WHITE);

				JButton exportExam = new JButton("Export As");
				JButton exportAttendance=new JButton("Export Attendance");				

				JComboBox<String> selectExportType = new JComboBox<String>(new String[] { ".txt", ".csv" });
				
				exportAttendance.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						ExportExamAttendance.examCode=examCode;
						ExportExamAttendance.maxMark=examMaxMark;
						dispose();
						new ExportExamAttendance().setVisible(true);
					}
				});

				exportExam.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							if (selectExportType.getSelectedIndex() == 0) {
								BufferedWriter input = new BufferedWriter(new FileWriter("" + examCode + ".txt"));
								createTxtFile(input, examCode, numberOfmulti);
								JOptionPane.showMessageDialog(null, "Successfully exported to .txt file.");
							} else if (selectExportType.getSelectedIndex() == 1) {
								BufferedWriter input = new BufferedWriter(new FileWriter("" + examCode + ".csv"));
								createCsvFile(input, examCode, numberOfmulti);
								JOptionPane.showMessageDialog(null, "Successfully exported to .csv file.");
							}
						} catch (Exception exc) {
							JOptionPane.showMessageDialog(null, "" + exc.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}

					}
				});

				styleButton(exportAttendance);
				styleButton(exportExam);

				resultPanel.add(exportAttendance);
				resultPanel.add(exportExam);
				resultPanel.add(selectExportType);

				panel.add(resultPanel);

			}

			stmt.close();
			conn.close();

		} catch (Exception exc) {
			JOptionPane.showMessageDialog(null, "Error : " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void createTxtFile(BufferedWriter input, String examCode, int numberOfmulti) {
		try {
			Connection conn = DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
			Statement stmt = conn.createStatement();

			String query = "SELECT * FROM e" + examCode;
			ResultSet result = stmt.executeQuery(query);

			while (result.next()) {
				int index = Integer.parseInt(result.getString(1));
				String question = result.getString(2);
				input.write("Question " + index + "\n" + question + "\n");
				int ii = 1;
				for (int i = 3; i < 3 + numberOfmulti; i++) {
					input.write(ii + ") " + result.getString(i) + "\n");
					ii++;
				}
				input.write("Correct Answer : " + result.getString(numberOfmulti + 3)+ "\n--------------------------------------------------\n");
			}

			input.close();

		} catch (Exception exc) {
			JOptionPane.showMessageDialog(null, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void createCsvFile(BufferedWriter input, String examCode, int numberOfmulti) {
		try {
			Connection conn = DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
			Statement stmt = conn.createStatement();

			String query = "SELECT * FROM e" + examCode;
			ResultSet result = stmt.executeQuery(query);

			if (result.next()) {
				String multis = "";
				int ii = 1;
				for (int i = 3; i < 3 + numberOfmulti; i++) {

					multis += "Multi #" + ii + ",";

					ii++;
				}
				String firstLine = " Question # , Question Content , " + multis + " Correct Answer ";
				input.write(firstLine + "\n");
			}
			while (result.next()) {
				int index = Integer.parseInt(result.getString(1));
				String question = result.getString(2);
				String correctAnswer = result.getString(numberOfmulti + 3);

				int ii = 1;
				String resRow = "" + index + "," + question + ",";
				for (int i = 3; i < 3 + numberOfmulti; i++) {


					resRow += result.getString(i) + ",";

					ii++;
				}

				resRow += correctAnswer;

				input.write(resRow+"\n");

			}

			input.close();
			
			conn.close();
			stmt.close();

		} catch (Exception exc) {
			JOptionPane.showMessageDialog(null, exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
