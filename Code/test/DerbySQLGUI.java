/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * DerbySQLGUI is a simple graphical user interface (GUI) for executing SQL queries
 * on an Apache Derby database. It allows users to input SQL commands and view the results.
 */

import java.sql.*;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class DerbySQLGUI extends JFrame {

    private static final String DB_URL = "jdbc:derby:..\\db\\QAsystemDB;create=true";
    private JTextArea resultArea;
    private JTextArea sqlInputArea;
    private Connection conn;

    public DerbySQLGUI() {
        setTitle("Derby SQL Executor GUI");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        JLabel sqlLabel = new JLabel("Enter SQL Query:");
        inputPanel.add(sqlLabel, BorderLayout.NORTH);

        sqlInputArea = new JTextArea(4, 50);
        inputPanel.add(new JScrollPane(sqlInputArea), BorderLayout.CENTER);

        JButton executeBtn = new JButton("Execute SQL");
        inputPanel.add(executeBtn, BorderLayout.SOUTH);
        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        executeBtn.addActionListener(e -> executeSQL());

        // Initialize database connection
        try {
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false);  // Disabling auto-commit for better control
        } catch (SQLException e) {
            resultArea.append("Database Connection Error: " + e.getMessage() + "\n");
        }

        setVisible(true);
    }

    private void executeSQL() {
        String sql = sqlInputArea.getText().trim();

        if (sql.isEmpty()) {
            resultArea.append("SQL query cannot be empty.\n");
            return;
        }

        try (Statement stmt = conn.createStatement()) {
            boolean isResultSet = stmt.execute(sql);

            if (isResultSet) {
                ResultSet rs = stmt.getResultSet();
                displayResultSet(rs);
            } else {
                int updateCount = stmt.getUpdateCount();
                resultArea.append("Query executed, " + updateCount + " row(s) affected.\n");
            }

        } catch (SQLException e) {
            resultArea.append("SQL Execution Error: " + e.getMessage() + "\n");
        }
    }

    private void displayResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        resultArea.setText("");

        for (int i = 1; i <= columnCount; i++) {
            resultArea.append(metaData.getColumnName(i) + "\t");
        }
        resultArea.append("\n");

        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                resultArea.append(rs.getString(i) + "\t");
            }
            resultArea.append("\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DerbySQLGUI::new);
    }
}
