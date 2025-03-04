package Project;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.sql.Connection;

public class SQLClientApp {
    private JButton ConnectButton, DisconnectButton, ClearCommand, ExecuteButton, ClearWindow, CloseApp;
    private JLabel CommandLabel, dbInfoLabel, JdbcLabel1, JdbcLabel2, UserLabel, PasswordLabel;
    private JTextArea textCommand;
    private JComboBox dbPropertiesComboBox, userPropertiesComboBox;
    private JTextField userText;
    private JPasswordField passwordText;
    private JLabel StatusLabel, WindowLabel;
    private ResultSetTableModel tableModel;
    private Connection connect;
    private TableModel Empty;
    private JTable resultTable;

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}