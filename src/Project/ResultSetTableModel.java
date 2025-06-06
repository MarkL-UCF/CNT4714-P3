package Project;// A TableModel that supplies ResultSet data to a JTable.
import java.sql.*;

import javax.swing.table.AbstractTableModel;


// ResultSet rows and columns are counted from 1 and JTable 
// rows and columns are counted from 0. When processing 
// ResultSet rows or columns for use in a JTable, it is 
// necessary to add 1 to the row or column number to manipulate
// the appropriate ResultSet column (i.e., JTable column 0 is 
// ResultSet column 1 and JTable row 0 is ResultSet row 1).
@SuppressWarnings("ALL")
public class ResultSetTableModel extends AbstractTableModel
{
   private Connection connection;
   private Connection opLogConnection;
   private Statement statement;
   private ResultSet resultSet;
   private ResultSetMetaData resultSetMetaData;
   private int numberOfRows;

   // keep track of database connection status
   private boolean connectedToDatabase = false;
   
   // constructor initializes resultSet and obtains its meta data object;
   // determines number of rows
   public ResultSetTableModel(Connection passedConnection, Connection passedOpLogConnection)
      throws SQLException, ClassNotFoundException
   {
      //The constructor method here needs modification for project 3
      //The connection is not made here, but is passed into this method by the user app
      //Don't execute a default query - if user has no connection - no query can be run, if user has a connection
      //but no command is present, the SQL Error should be reported

      connection = passedConnection;
      opLogConnection = passedOpLogConnection;
      connectedToDatabase = true;

      /*
      try{

      }
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
         System.exit( 1 );
      } // end catch
      catch (IOException e) {
   	     e.printStackTrace();
      }

       */
   } // end constructor ResultSetTableModel

   // get class that represents column type
   public Class getColumnClass( int column ) throws IllegalStateException
   {
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // determine Java class of column
      try 
      {
         String className = resultSetMetaData.getColumnClassName( column + 1 );
         
         // return Class object that represents className
         return Class.forName( className );
      } // end try
      catch ( Exception exception ) 
      {
         exception.printStackTrace();
      } // end catch
      
      return Object.class; // if problems occur above, assume type Object
   } // end method getColumnClass

   // get number of columns in ResultSet
   public int getColumnCount() throws IllegalStateException
   {   
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // determine number of columns
      try 
      {
         return resultSetMetaData.getColumnCount();
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch
      
      return 0; // if problems occur above, return 0 for number of columns
   } // end method getColumnCount

   // get name of a particular column in ResultSet
   public String getColumnName( int column ) throws IllegalStateException
   {    
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // determine column name
      try 
      {
         return resultSetMetaData.getColumnName( column + 1 );
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch
      
      return ""; // if problems, return empty string for column name
   } // end method getColumnName

   // return number of rows in ResultSet
   public int getRowCount() throws IllegalStateException
   {      
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );
 
      return numberOfRows;
   } // end method getRowCount

   // obtain value in particular row and column
   public Object getValueAt( int row, int column ) 
      throws IllegalStateException
   {
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // obtain a value at specified ResultSet row and column
      try 
      {
		   resultSet.next();  /* fixes a bug in MySQL/Java with date format */
         resultSet.absolute( row + 1 );
         return resultSet.getObject( column + 1 );
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch
      
      return ""; // if problems, return empty string object
   } // end method getValueAt
   
   // set new database query string
   public void setQuery( String query ) 
      throws SQLException, IllegalStateException 
   {
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // specify query and execute it
      statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
      resultSet = statement.executeQuery(query);

      // obtain meta data for ResultSet
      resultSetMetaData = resultSet.getMetaData();

      // determine number of rows in ResultSet
      resultSet.last();                   // move to last row
      numberOfRows = resultSet.getRow();  // get row number

      //additional code will go here to handle operationsLog processing
      //1. get a connection as a project3app user to the operationsLog db
      //2. identify the user issuing query
      //3. using the connection from step 1, send the update command
      //4. close connection to operationsLog db

      //additional details of the steps above

      //get the metaData for the connection object
      //extract the username from the connection object - need to know who "owns" the connection
      //note: if it is theaccountant user - ignore this section of code


      //use the project3app.properties file to get a project3app "user" connection to operationsLog DB
         //Establish a connection to the operationsLog db
      //use this connection for the updating of the operationscount table

      //create prepareStatement command Strings
      //need one to find the user in operationscount table
      // "select * from operationscount where login_username = ?;";
      //need one for inserting a new user into the operationscount table //loginname, 1, 0
      //need one for updating the operationscount table to increment number of queries

      //create the PreparedStatement objects - one for each command string above
      //PreparedStatement "name of PreparedStatement object" = connection.prepareStatement(command String);

      //find the row in operationsCount table that belongs to the user issuing the command
      //if there is currently no row for this user, then this is their first command issued - add a new row
      //    to the operationsCount table for this user with the values: ("login-username", 1, 0).
      //if a row already exists in the operationsCount table for this user, then they have issued previous commands -
      //    increment by 1 the number of query commands issued by this user
      //set the username parameter for the PreparedStatement object
      //    operationsCountStatement1.setString(1, user_name);

      //run the prepared statement to see if the user is in the operationsCount table
      //    If ResultSet is empty on return - then you have a new user and need to enter a new row into the operationsCount table
      //    parameter values would be (username, 1, 0)
      //else ResultSet contained this username - so user has issued commands before - need to update their row in the operationsCount table
      //    this user has already issued commands and is already represented in the operationsCount table
      //    need to update their existing row - parameter values should be (username, numqueries + 1);

      String hostlessUsername = connection.getMetaData().getUserName().split("@")[0];

      if(!hostlessUsername.equals("theaccountant")){
         String findUserString = "select * from operationscount where login_username = ?;";
         String insertUserString = "insert into operationscount (login_username, num_queries, num_updates) values (?, ?, ?);";
         String updateQueryString = "update operationscount set num_queries = num_queries + ? where login_username = ?";

         PreparedStatement findState = opLogConnection.prepareStatement(findUserString);
         findState.setString(1, connection.getMetaData().getUserName());

         PreparedStatement insertState = opLogConnection.prepareStatement(insertUserString);
         insertState.setString(1, connection.getMetaData().getUserName());
         insertState.setInt(2, 1);
         insertState.setInt(3, 0);

         PreparedStatement updateState = opLogConnection.prepareStatement(updateQueryString);
         updateState.setInt(1, 1);
         updateState.setString(2, connection.getMetaData().getUserName());

         //execute statement
         ResultSet opLogSet;

         opLogSet = findState.executeQuery();

         if(!opLogSet.next()) {
            //user does not exist, make one
            insertState.execute();
         }
         else {
            //user exists, update
            updateState.execute();
         }

      }

      //Close connection to operationsLog db
      //operationslogDBconnection.close();
      //end code to update operationslog database
      
      // notify JTable that model has changed
      fireTableStructureChanged();
   } // end method setQuery


// set new database update-query string
   public int setUpdate( String query )
      throws SQLException, IllegalStateException 
   {
	  int res;
      // ensure database connection is available
      if ( !connectedToDatabase ) 
         throw new IllegalStateException( "Not Connected to Database" );

      // specify query and execute it
      statement = connection.createStatement();
      res = statement.executeUpdate( query );

      //add code here to update the operations log db as the project3app user; +1 to update count

      //Create prepareStatement command strings
         //need one to find the user in the operationsCount table
            //"select * from operationsCount where login_username = ?;";
      //Need one for inserting a new user into operationscount table //loginname, 0, 1
      //Need one for updating the operationscount table to increment the number of updates
      String hostlessUsername = connection.getMetaData().getUserName().split("@")[0];

      if(!hostlessUsername.equals("theaccountant@localhost")){
         String findUserString = "select * from operationscount where login_username = ?;";
         String insertUserString = "insert into operationscount (login_username, num_queries, num_updates) values (?, ?, ?);";
         String updateQueryString = "update operationscount set num_updates = num_updates + ? where login_username = ?";

         PreparedStatement findState = opLogConnection.prepareStatement(findUserString);
         findState.setString(1, connection.getMetaData().getUserName());

         PreparedStatement insertState = opLogConnection.prepareStatement(insertUserString);
         insertState.setString(1, connection.getMetaData().getUserName());
         insertState.setInt(2, 0);
         insertState.setInt(3, 1);

         PreparedStatement updateState = opLogConnection.prepareStatement(updateQueryString);
         updateState.setInt(1, 1);
         updateState.setString(2, connection.getMetaData().getUserName());

         //execute statement
         ResultSet opLogSet;

         opLogSet = findState.executeQuery();

         if(!opLogSet.next()) {
            //user does not exist, make one
            insertState.execute();
         }
         else {
            //user exists, update
            updateState.execute();
         }

      }

      //end code to update operationsLog database
      return res;

   } // end method setUpdate

   // close Statement and Connection               
   public void disconnectFromDatabase()            
   {              
      if ( !connectedToDatabase )                  
         return;
      // close Statement and Connection            
      else try                                          
      {                                            
         statement.close();                        
         connection.close();                       
      } // end try                                 
      catch ( SQLException sqlException )          
      {                                            
         sqlException.printStackTrace();           
      } // end catch                               
      finally  // update database connection status
      {                                            
         connectedToDatabase = false;              
      } // end finally                             
   } // end method disconnectFromDatabase          
}  // end class ResultSetTableModel





