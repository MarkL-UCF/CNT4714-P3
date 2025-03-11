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

@SuppressWarnings("FieldMayBeFinal")
public class PrimaryApp extends JFrame {
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
    private JComboBox<String> dbPropertiesComboBox;
    private JComboBox<String> userPropertiesComboBox;
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
    private TableModel Empty;

    private Connection connect;
    private Properties loginProperties = new Properties();
    private Properties dbProperties = new Properties();

    public PrimaryApp() {
        //Construct GUI instance
        setTitle("SQL CLIENT APPLICATION - (MJL - CNT 4714 - SPRING 2025 - PROJECT 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainBackground);
        pack();

        String[] dbPropertiesItems = {"src/project3.properties", "src/bikedb.properties", "src/newdb.properties", "src/modeldb.properties"};
        String[] userPropertiesItems = {"src/root.properties", "src/client1.properties", "src/client2.properties", "src/newuser.properties", "src/mysteryuser.properties"};

        //populate combo boxes
        for (String dbPropertiesItem : dbPropertiesItems)
            dbPropertiesComboBox.addItem(dbPropertiesItem);

        for (String userPropertiesItem : userPropertiesItems)
            userPropertiesComboBox.addItem(userPropertiesItem);

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
                        @SuppressWarnings("DataFlowIssue") FileInputStream loginPropertiesStream = new FileInputStream((String)userPropertiesComboBox.getSelectedItem());
                        loginProperties.load(loginPropertiesStream);
                        loginPropertiesStream.close();

                        @SuppressWarnings("DataFlowIssue") FileInputStream dbPropertiesStream = new FileInputStream((String)dbPropertiesComboBox.getSelectedItem());
                        dbProperties.load(dbPropertiesStream);
                        dbPropertiesStream.close();

                        //set the DataSource object
                        MysqlDataSource dataSource = new MysqlDataSource();

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

                            //get connection
                            connect = dataSource.getConnection();

                            //update connection status
                            statusLabel.setText("CONNECTED TO: " + dbProperties.getProperty("MYSQL_DB_URL"));
                            statusLabel.setForeground(Color.BLACK);
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

                //clear the input command area

                /*
                try{
                    connect.close();
                }
                catch(SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
                }
                */

            }
        });

        ClearWindow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //clears the results displayed in the window
            }
        });


        ClearCommand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //clears the text displayed in the query window
            }
        });

        CloseApp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               System.exit(0);
            }
        });

        ExecuteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                /*
                try{
                    //activate result table

                    //set scrolling

                    //create TableModel object for results


                    //If select statement is used (a query), use executeQuery()
                    //All other command types will use executeUpdate() from the ResultSetTableModelFall2023 class
                    //New window will pop up with message for user that does not have permission to issue the command

                    if(textCommand.getText().toUpperCase().contains("SELECT")){
                        //helper class executes query statement
                        //returns ResultSet object
                        //convert ResultSet object into JTable object
                    }
                    else{
                        //helper class executes update statement
                        //returns int
                        //if return value is >= 0, then update was successful, oops otherwise (SQLException)
                    }
                }
                catch(SQLException e){
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
                }
                catch(ClassNotFoundException NotFound){
                    JOptionPane.showMessageDialog(null, "MySQL driver not found", "Driver not found", JOptionPane.ERROR_MESSAGE);
                }
                */

            }
        });



       setVisible(true);
    }
}
