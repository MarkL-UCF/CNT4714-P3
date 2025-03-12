/*
Name: Mark Lee
Course: CNT 4714 Spring 2025
Assignment title: Project 3 – A Specialized Accountant Application
Date: March 14, 2025
Class: Project3AccountantApp
*/
package Project;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@SuppressWarnings("ALL")
public class Project3AccountantApp extends JFrame {
    /*
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
    */
    private JPanel mainBackground;
    private JPanel executionArea;
    private JPanel connectionArea;
    private JPanel commandArea;
    private JLabel connectionHeader;
    private JPasswordField passwordText;
    private JLabel passwordLabel;
    private JLabel dbPropLabel;
    private JLabel userPropLabel;
    private JLabel userLabel;
    private JTextField userText;
    //private JComboBox<String> dbPropertiesComboBox;
    //private JComboBox<String> userPropertiesComboBox;
    private JButton ConnectButton;
    private JButton DisconnectButton;
    private JLabel commandHeader;
    private JTextArea textCommand;
    private JButton ClearCommand;
    private JButton ExecuteButton;
    private JLabel statusLabel;
    private JLabel windowHeader;
    private JTable resultTable;
    private JButton ClearWindow;
    private JButton CloseApp;
    private JScrollPane scrollPane;
    private JLabel dbPropForced;
    private JLabel userPropForce;
    private TableModel Empty;

    private Connection connect;
    private Connection opLogConnect;
    private Properties loginProperties = new Properties();
    private Properties dbProperties = new Properties();
    private Properties opLogProperties = new Properties();

    public Project3AccountantApp() {
        //Construct GUI instance
        setTitle("SPECIALIZED ACCOUNTANT APPLICATION - (MJL - CNT 4714 - SPRING 2025 - PROJECT 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainBackground);
        pack();

        /*
        String[] dbPropertiesItems = {"src/project3.properties", "src/bikedb.properties", "src/newdb.properties", "src/modeldb.properties"};
        String[] userPropertiesItems = {"src/root.properties", "src/client1.properties", "src/client2.properties", "src/newuser.properties", "src/mysteryuser.properties"};

        //populate combo boxes
        for (String dbPropertiesItem : dbPropertiesItems)
            dbPropertiesComboBox.addItem(dbPropertiesItem);

        for (String userPropertiesItem : userPropertiesItems)
            userPropertiesComboBox.addItem(userPropertiesItem);

         */

        //Construct GUI components

        //Define buttons - there are 6 buttons in this GUI
        /*
        ConnectButton = new JButton("Connect to Database");
        ConnectButton.setFont(new Font("Arial", Font.BOLD, 12));
        ConnectButton.setBackground((Color.BLUE));
        ConnectButton.setBackground((Color.WHITE));
        ConnectButton.setBorderPainted(false);
        ConnectButton.setOpaque(true);
        */


        /* ... */

        //Define labels
        /*
        CommandLabel = new JLabel();
        CommandLabel.setFont(new Font("Arial", Font.BOLD, 14));
        CommandLabel.setForeground(Color.BLUE);
        CommandLabel.setText("Enter An SQL Command");
        */


        /* ... */

        //Define input and output areas
            //user command area
            //user select db properties to load
            //user selects user properties to load
            //enter user's name
            //enter user's password

        // set components, dimensions, etc

        Empty = new DefaultTableModel();
        resultTable.setModel(Empty);

        //set up componenets with bounds (x,y,w,h) x = pixels in from left edge of container, y = pixels down from top edge of container
        //w = pixel width of element, and h = pixel height of element
        //ConnectButton.setBounds(20, 180, 165, 25);

        //add components to GUI
        //add (ConnectButton);

        ConnectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                try{
                    //if already connected, close previous connection
                    if(connect != null)
                        connect.close();

                    //display status message when not connected
                    statusLabel.setText("NO CONNECTION ESTABLISHED");
                    statusLabel.setForeground(Color.RED);


                    try{
                        //open and read the properties file
                        FileInputStream loginPropertiesStream = new FileInputStream("src/theaccountant.properties");
                        loginProperties.load(loginPropertiesStream);
                        loginPropertiesStream.close();

                        FileInputStream dbPropertiesStream = new FileInputStream("src/operationslog.properties");
                        dbProperties.load(dbPropertiesStream);
                        dbPropertiesStream.close();

                        FileInputStream opLogPropertiesStream = new FileInputStream("src/project3app.properties");
                        opLogProperties.load(opLogPropertiesStream);
                        opLogPropertiesStream.close();

                        //set the DataSource object
                        MysqlDataSource dataSource = new MysqlDataSource();
                        MysqlDataSource opDataSource = new MysqlDataSource();

                        //match username and password with properties file values
                        boolean userCredentialsOK = false;

                        /*
                        System.out.println("Username given: " + userText.getText());
                        System.out.println("Username in properties: " + loginProperties.getProperty("MYSQL_DB_USERNAME"));

                        System.out.println("Password given: " + String.valueOf(passwordText.getPassword()));
                        System.out.println("Password in properties: " + loginProperties.getProperty("MYSQL_DB_PASSWORD"));
                         */

                        if(loginProperties.getProperty("MYSQL_DB_USERNAME").equals(userText.getText())
                        && loginProperties.getProperty("MYSQL_DB_PASSWORD").equals(String.valueOf(passwordText.getPassword()))){
                            userCredentialsOK = true;
                        }

                        //use valueOf() for password field to read


                        if(userCredentialsOK) {
                            //set DataSource parameter values
                            dataSource.setUrl(dbProperties.getProperty("MYSQL_DB_URL"));
                            dataSource.setUser(loginProperties.getProperty("MYSQL_DB_USERNAME"));
                            dataSource.setPassword(loginProperties.getProperty("MYSQL_DB_PASSWORD"));

                            opDataSource.setUrl(opLogProperties.getProperty("MYSQL_DB_URL"));
                            opDataSource.setUser(opLogProperties.getProperty("MYSQL_DB_USERNAME"));
                            opDataSource.setPassword(opLogProperties.getProperty("MYSQL_DB_PASSWORD"));

                            //get connection
                            connect = dataSource.getConnection();
                            opLogConnect = opDataSource.getConnection();

                            //update connection status
                            statusLabel.setText("CONNECTED TO: " + dbProperties.getProperty("MYSQL_DB_URL"));
                            statusLabel.setForeground(Color.BLACK);

                            DisconnectButton.setEnabled(true);
                            ConnectButton.setEnabled(false);
                            //dbPropertiesComboBox.setEnabled(false);
                            //userPropertiesComboBox.setEnabled(false);
                            ClearCommand.setEnabled(true);
                            ClearWindow.setEnabled(true);
                            ExecuteButton.setEnabled(true);
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Username and/or Password Incorrect", "Connection error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    catch(SQLException e){
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch(IOException e){
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Properties File error", JOptionPane.ERROR_MESSAGE);
                }
                catch(SQLException e){
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
                }



            }
        }
        );


        DisconnectButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event) {
                //clear the results displayed in the window
                resultTable.setModel(Empty);
                scrollPane.setEnabled(false);

                //clear the input command area
                textCommand.setText("");


                try{
                    connect.close();
                    statusLabel.setText("NO CONNECTION ESTABLISHED");
                    statusLabel.setForeground(Color.RED);
                    DisconnectButton.setEnabled(false);
                    ConnectButton.setEnabled(true);
                    //dbPropertiesComboBox.setEnabled(true);
                    //userPropertiesComboBox.setEnabled(true);
                    ClearCommand.setEnabled(false);
                    ClearWindow.setEnabled(false);
                    ExecuteButton.setEnabled(false);
                }
                catch(SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        ClearWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                //clears the results displayed in the window
                resultTable.setModel(Empty);
                scrollPane.setEnabled(false);
            }
        });


        ClearCommand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                //clears the text displayed in the query window
                textCommand.setText("");
            }
        });

        CloseApp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               try{
                   if(connect != null  && !connect.isClosed()) {
                       connect.close();
                   }
                   if(opLogConnect != null  && !opLogConnect.isClosed()) {
                       opLogConnect.close();
                   }
               }
               catch(SQLException e){
                   JOptionPane.showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
               }
                System.exit(0);
            }
        });

        ExecuteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try{
                    //activate result table
                    scrollPane.setEnabled(true);

                    //set scrolling


                    //create TableModel object for results
                    ResultSetTableModel resultModel = new ResultSetTableModel(connect, opLogConnect);

                    //If select statement is used (a query), use executeQuery()
                    //All other command types will use executeUpdate() from the ResultSetTableModelFall2023 class
                    //New window will pop up with message for user that does not have permission to issue the command

                    if(textCommand.getText().toUpperCase().contains("SELECT")){
                        //helper class executes query statement
                        resultModel.setQuery(textCommand.getText());

                        //returns ResultSet object
                        //convert ResultSet object into JTable object
                        resultTable.setModel(resultModel);
                    }
                    else{
                        //helper class executes update statement
                        int rowsUpdated = resultModel.setUpdate(textCommand.getText());
                        //returns int

                        //if return value is >= 0, then update was successful, oops otherwise (SQLException)
                        if(rowsUpdated >= 0)
                            JOptionPane.showMessageDialog(null, "Successful Update... " + rowsUpdated + " rows updated.", "Successful Update", JOptionPane.ERROR_MESSAGE);
                    }
                }
                catch(SQLException e){
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
                }
                catch(ClassNotFoundException NotFound){
                    JOptionPane.showMessageDialog(null, "MySQL driver not found", "Driver not found", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        scrollPane.setEnabled(false);
        DisconnectButton.setEnabled(false);
        DisconnectButton.setForeground(Color.WHITE);
        ClearCommand.setEnabled(false);
        ClearCommand.setForeground(Color.WHITE);
        ClearWindow.setEnabled(false);
        ClearWindow.setForeground(Color.WHITE);
        ExecuteButton.setEnabled(false);
        ExecuteButton.setForeground(Color.WHITE);
        CloseApp.setForeground(Color.WHITE);

        setVisible(true);
    }
}
