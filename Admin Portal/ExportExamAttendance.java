package examportal;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.JobAttributes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.LineBorder;

public class ExportExamAttendance extends JFrame {

	private JPanel contentPane;
	private JTable table;
	public static String examCode=null, maxMark=null;
	

	/**
	 * Create the frame.
	 */
	public ExportExamAttendance() {
		setResizable(false);
		setTitle("Export Exam Attendance");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 944, 556);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.CYAN);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.LIGHT_GRAY);
		scrollPane.setBounds(10, 10, 900, 359);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setAutoCreateRowSorter(true);
		table.setForeground(Color.WHITE);
		table.setFont(new Font("Tahoma", Font.BOLD, 14));
		table.setBackground(Color.BLACK);
		scrollPane.setViewportView(table);
		table.setRowSelectionAllowed(false);
		
		DefaultTableModel model=new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"#", "Name", "Mark/"+maxMark
				}
			) {
				Class[] columnTypes = new Class[] {
					String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
		
		table.setModel(model);
		
		JButton btnNewButton = new JButton("<< Go Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new AdminMain().setVisible(true);
			}
		});
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBackground(Color.YELLOW);
		btnNewButton.setForeground(Color.RED);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setFocusable(false);
		btnNewButton.setBorder(null);
		btnNewButton.setBounds(10, 448, 145, 51);
		panel.add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setForeground(Color.BLACK);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBox.setBorder(new LineBorder(Color.RED, 2));
		comboBox.setBackground(Color.YELLOW);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {".CSV"}));
		comboBox.setBounds(782, 428, 128, 32);
		panel.add(comboBox);
		
		JButton btnNewButton_1 = new JButton("Export As");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex()==0) {
					exportFile(model);
				}
				else {
					JOptionPane.showMessageDialog(null, "Select a file type.");
				}
			}
		});
		btnNewButton_1.setFocusable(false);
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setBorder(null);
		btnNewButton_1.setBackground(Color.RED);
		btnNewButton_1.setForeground(Color.CYAN);
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_1.setBounds(633, 428, 128, 32);
		panel.add(btnNewButton_1);
		
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		setLocationRelativeTo(null);
		
		setData(model);
	}
	private void exportFile(DefaultTableModel model) {
		try {
			BufferedWriter input = new BufferedWriter(new FileWriter(""+examCode+" exam results.csv"));
			
			String firstLine=" # , Name , Mark/"+maxMark+" ";
			input.write(firstLine+"\n");
			
			
			for(int i=0;i<model.getRowCount();i++) {
				String temp="";
				for(int j=0;j<model.getColumnCount();j++) {
					temp+=model.getValueAt(i, j);
					if(j<model.getColumnCount()-1) {
						temp+=",";
					}
				}
				input.write(temp+"\n");
			}
			
			input.close();
			
			JOptionPane.showMessageDialog(null, "Successfully exported file.");
			
		}
		catch(Exception exc) {
			JOptionPane.showMessageDialog(null, exc.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void setData(DefaultTableModel model) {
		try {
			Connection conn = DriverManager.getConnection(DBInfo.host, DBInfo.username, DBInfo.password);
			Statement stmt = conn.createStatement();
			
			String query="SELECT * FROM e"+examCode+"a ";
			ResultSet result=stmt.executeQuery(query);
			
			while(result.next()) {
				String[] arr=new String[3];
				
				for(int i=0;i<arr.length;i++) {
					arr[i]=result.getString(i+1);
				}
				
				model.addRow(arr);
			}
			
			conn.close();
			stmt.close();
			
			
		}
		catch(Exception exc) {
			JOptionPane.showMessageDialog(null, exc.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
}
