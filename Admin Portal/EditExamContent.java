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

import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditExamContent extends JFrame {

	private JPanel contentPane;
	public static String examCode = null;
	public static int numberOfQuestions = 0;
	public static int numberOfMulti = 0;
	private int qNumber = 1;
	private JRadioButton[] radioButtons;
	private JTextArea[] multiFields;
	private boolean isQ = false;

	/**
	 * Create the frame.
	 */
	public EditExamContent() {
		setResizable(false);
		setTitle("Edit Exam Content");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Question");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(771, 10, 134, 20);
		panel.add(lblNewLabel);

		JTextArea textArea = new JTextArea();
		textArea.setBorder(new LineBorder(Color.RED, 2));
		textArea.setBounds(61, 64, 582, 194);
		panel.add(textArea);

		JButton btnNewButton_2 = new JButton("Next >");
		btnNewButton_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_2.setForeground(Color.RED);
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_2.setFocusable(false);
		btnNewButton_2.setBackground(Color.YELLOW);
		btnNewButton_2.setBorder(null);
		btnNewButton_2.setBounds(849, 553, 127, 50);
		panel.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("< Previous");
		btnNewButton_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_3.setEnabled(false);
		btnNewButton_3.setBorder(null);
		btnNewButton_3.setBackground(Color.YELLOW);
		btnNewButton_3.setForeground(Color.RED);
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_3.setFocusable(false);
		btnNewButton_3.setBounds(711, 553, 127, 50);
		panel.add(btnNewButton_3);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.CYAN);
		scrollPane.setBounds(61, 280, 582, 313);
		panel.add(scrollPane);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.CYAN);
		scrollPane.setViewportView(panel_1);

		lblNewLabel.setText("Question " + qNumber + "/" + numberOfQuestions);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean selected = false;
				int whichSelected = 0;
				boolean checkMutli = true;
				boolean checkQuestion = true;
				String res = "";

				String question = textArea.getText().trim();
				if (question.equals("")) {
					checkQuestion = false;
				}

				for (int i = 0; i < numberOfMulti; i++) {
					if (selected == false && radioButtons[i].isSelected()) {
						selected = true;
						whichSelected = i + 1;
					}
					if (multiFields[i].getText().trim().equals("")) {
						checkMutli = false;
						break;
					}
					if (isQ) {
						res += "m" + (i + 1) + "='" + multiFields[i].getText() + "'";
						if (i < numberOfMulti - 1) {
							res += ",";
						}
					} else {
						res += "'" + multiFields[i].getText() + "'";
						if (i < numberOfMulti - 1) {
							res += ",";
						}
					}
				}
				if (selected && checkMutli && checkQuestion) {
					try {
						Connection conn = DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
						Statement stmt = conn.createStatement();
						if (isQ) {
							String query = "UPDATE e" + examCode + " SET question='" + question + "'," + res
									+ ",correctanswer='" + whichSelected + "' WHERE n='" + qNumber + "'";
							stmt.executeUpdate(query);
						} else {
							String query = "INSERT INTO e" + examCode + " VALUES ('" + qNumber + "','" + question + "',"
									+ res + ",'" + whichSelected + "')";
							stmt.executeUpdate(query);
						}
						conn.close();
						stmt.close();
					} catch (Exception exc) {
						JOptionPane.showMessageDialog(null, "Error : " + exc.getMessage(), "Error",
								JOptionPane.ERROR_MESSAGE);
					}

					goToNext(lblNewLabel,textArea, panel_1, btnNewButton_3, btnNewButton_2);
					if (qNumber > numberOfQuestions) {
						dispose();
						new AdminMain().setVisible(true);;
					}

				} else {
					JOptionPane.showMessageDialog(null, "Check all inputs!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnNewButton_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				goBack(lblNewLabel,textArea, panel_1, btnNewButton_3, btnNewButton_2);
			}
		});

		addData(textArea, panel_1);
	}

	private void addData(JTextArea question, JPanel mainMulti) {
		mainMulti.removeAll();
		question.setText("");
		try {
			Connection conn = DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
			Statement stmt = conn.createStatement();

			String query = "SELECT * FROM e" + examCode + " WHERE n='" + qNumber + "'";
			ResultSet result = stmt.executeQuery(query);

			radioButtons = new JRadioButton[numberOfMulti];
			multiFields = new JTextArea[numberOfMulti];

			ButtonGroup group = new ButtonGroup();

			mainMulti.setLayout(new GridLayout(numberOfMulti, 1));

			for (int i = 0; i < numberOfMulti; i++) {

				JPanel temp = new JPanel(new GridLayout(1, 2));
				temp.setPreferredSize(new Dimension(0, 100));

				radioButtons[i] = new JRadioButton();
				group.add(radioButtons[i]);
				multiFields[i] = new JTextArea();
				multiFields[i].setBorder(new LineBorder(Color.red, 2));

				radioButtons[i].setBackground(Color.CYAN);

				temp.add(radioButtons[i]);
				temp.add(multiFields[i]);

				mainMulti.add(temp);
			}

			if (result.next()) {
				isQ = true;
				question.setText(result.getString(2));
				String correctAnswer = result.getString(3 + numberOfMulti);
				for (int i = 0; i < numberOfMulti; i++) {
					if ((i + 1) == Integer.parseInt(correctAnswer)) {
						radioButtons[i].setSelected(true);
					}
					multiFields[i].setText(result.getString(3 + i));
				}
			}
			conn.close();
			stmt.close();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(null, "Error : " + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void goBack(JLabel index,JTextArea textArea, JPanel panel_1,JButton prev, JButton next) {
		if(qNumber>1) {
			qNumber--;
			index.setText("Question "+qNumber+"/"+numberOfQuestions);
		}
		isQ=false;
		if(qNumber==1) {
			prev.setEnabled(false);
		}
		addData(textArea, panel_1);
	}
	private void goToNext(JLabel index,JTextArea textArea, JPanel panel_1, JButton prev, JButton next) {
		if(qNumber<=numberOfQuestions) {
			qNumber++;
			index.setText("Question "+qNumber+"/"+numberOfQuestions);
		}
		isQ = false;
		if (qNumber > 1 && qNumber <= numberOfQuestions) {
			prev.setEnabled(true);
			if (qNumber == numberOfQuestions) {
				next.setText("Finish");
			}
		}
		addData(textArea, panel_1);
	}
}
