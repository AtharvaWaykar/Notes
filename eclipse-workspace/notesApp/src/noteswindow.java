import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
public class noteswindow {

	private JFrame frame;
	private JTextField titleField;
	private JTextField subjectField;
	
	private JTextArea bodyField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					noteswindow window = new noteswindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public noteswindow() {
		initialize();
		Connect();
		table_load();
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;



	private JTextField titleFinderField;


	public void Connect() 
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/project",  "root", "");
		} 
		catch (ClassNotFoundException ex)
		{


		}
		catch (SQLException ex)
		{

		}
	}

	void table_load()
	{
		try
		{
			pst = con.prepareStatement("select Title from notes");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));

		}
		catch(SQLException e) 
		{
			e.printStackTrace();

		}



	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel Notes = new JLabel("Notes App");
		Notes.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		Notes.setBounds(218, 6, 117, 33);
		frame.getContentPane().add(Notes);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel.setBounds(20, 51, 454, 347);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Title ");
		lblNewLabel.setBounds(38, 37, 61, 16);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Subject");
		lblNewLabel_1.setBounds(38, 70, 61, 16);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Body");
		lblNewLabel_2.setBounds(38, 106, 61, 16);
		panel.add(lblNewLabel_2);

		titleField = new JTextField();
		titleField.setBounds(111, 32, 312, 26);
		panel.add(titleField);
		titleField.setColumns(10);

		subjectField = new JTextField();
		subjectField.setBounds(111, 65, 312, 26);
		panel.add(subjectField);
		subjectField.setColumns(10);

		
		
		bodyField = new JTextArea();
		bodyField.setLineWrap(true);
		bodyField.setBounds(111, 106, 312, 230);
		panel.add(bodyField);

		JButton btnNewButton = new JButton("Save New");
		btnNewButton.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {

				String title , subject , body;

				title = titleField.getText();
				subject = subjectField.getText();
				body = bodyField.getText();

				if(!title.equals("")) {

					try {
						pst = con.prepareStatement("insert into notes(title, subject, body)values(?,?,?)");
						pst.setString(1, title);
						pst.setString(2, subject);
						pst.setString(3, body);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Note Saved");
						table_load();
						titleField.setText("");
						subjectField.setText("");
						bodyField.setText("");
						titleField.requestFocus();
					}

					catch(SQLException el) {

						el.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "Please add Subject");


				}


			}


		});


		btnNewButton.setBounds(36, 424, 117, 29);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Clear");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				titleField.setText("");
				subjectField.setText("");
				bodyField.setText("");
				titleField.requestFocus();
			}
		});
		btnNewButton_1.setBounds(423, 424, 117, 29);
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_1_1 = new JButton("Exit");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {



				String title , subject , body;

				title = titleField.getText();
				subject = subjectField.getText();
				body = bodyField.getText();

				try {
					pst = con.prepareStatement("insert into notes(title, subject, body)values(?,?,?)");
					pst.setString(1, title);
					pst.setString(2, subject);
					pst.setString(3, body);
					pst.executeUpdate();
					
					table_load();
					titleField.setText("");
					subjectField.setText("");
					bodyField.setText("");
					titleField.requestFocus();
				}

				catch(SQLException el) {

					el.printStackTrace();

				}




				System.exit(0);
			}
		});
		btnNewButton_1_1.setBounds(561, 424, 117, 29);
		frame.getContentPane().add(btnNewButton_1_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(510, 60, 165, 253);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(485, 325, 209, 87);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel searchField = new JLabel("Title");
		searchField.setBounds(80, 18, 61, 16);
		panel_1.add(searchField);

		titleFinderField = new JTextField();
		titleFinderField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {

				try {
					String id = titleFinderField.getText();

					pst = con.prepareStatement("select Title, Subject, Body from Notes where Title = ?");
					pst.setString(1,id);
					ResultSet rs = pst.executeQuery();
					if(rs.next() == true) {
						String Title = rs.getString(1);
						String Subject = rs.getString(2);
						String Body = rs.getString(3);

						titleField.setText(Title);
						subjectField.setText(Subject);
						bodyField.setText(Body);

					} else {

						titleField.setText("");
						subjectField.setText("");
						bodyField.setText("");

					}
				}	catch(SQLException ex){


				}


			}
		});
		titleFinderField.setBounds(6, 43, 197, 26);
		panel_1.add(titleFinderField);
		titleFinderField.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("My Notes");
		lblNewLabel_4.setBounds(561, 32, 61, 16);
		frame.getContentPane().add(lblNewLabel_4);

		JButton btnNewButton_2 = new JButton("Update");
		btnNewButton_2.setBounds(165, 424, 117, 29);
		frame.getContentPane().add(btnNewButton_2);

		JButton btnNewButton_2_1 = new JButton("Delete");
		btnNewButton_2_1.setBounds(294, 424, 117, 29);
		frame.getContentPane().add(btnNewButton_2_1);
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String  titleSearch;


				titleSearch = titleFinderField.getText();

				try {

					pst = con.prepareStatement("select Title from Notes where Title = ?");
					pst.setString(1, titleSearch);
					ResultSet rs = pst.executeQuery();

					
					boolean hasResult = rs.next();
					if(hasResult == true) {
						pst = con.prepareStatement("delete from notes where title = ?");
						pst.setString(1,titleSearch);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Note Deleted");
						table_load();
						titleField.setText("");
						subjectField.setText("");
						bodyField.setText("");
						titleField.requestFocus();
					} else {
						JOptionPane.showMessageDialog(null, "Plese enter valid Note Title in Search");
					}
				}

				catch(SQLException el) {

					el.printStackTrace();

				}

			}
		});


		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				String title , subject , body, titleSearch;

				title = titleField.getText();
				subject = subjectField.getText();
				body = bodyField.getText();
				titleSearch = titleFinderField.getText();
				


				
					try 
					{
						pst = con.prepareStatement("select Title from Notes where Title = ?");
						pst.setString(1, titleSearch);
						ResultSet rs = pst.executeQuery();

						
						boolean hasResult = rs.next();
						if(hasResult == true) {
							if(!title.equals("")) {



						pst = con.prepareStatement("update notes set title = ?, subject = ?, body = ? where title = ?");
						pst.setString(1, title);
						pst.setString(2, subject);
						pst.setString(3, body);
						pst.setString(4, titleSearch);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Note Updated");
						table_load();
						titleField.setText("");
						subjectField.setText("");
						bodyField.setText("");
						titleField.requestFocus();
							} else {
								JOptionPane.showMessageDialog(null, "Please add Subject");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Please Enter Valid Title Name in seach");
						}

					}
					catch(SQLException el) 
					{

						el.printStackTrace();

					}


				
				


			}

		});


	}
}