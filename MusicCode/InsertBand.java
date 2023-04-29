
// Example of preparing a SQL statement within code to run
// Make certain to always prepare any parameters input by users of the app;
// see prepInsert object.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner; 
import java.sql.SQLException;

public class InsertBand {

    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public static void main(String[] args) {
        String connectionUrl = 
        "jdbc:sqlserver://localhost;"
                + "database=Music2;"
                + "user=dbuser;" //May need to change this for your database user; if using "sa" for example
                + "password=scsd431134dscs;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        Scanner myObj = new Scanner(System.in);
        String inpName, inpStatus, inpRecordLabel;
        //float inpBudget;

        // Enter username and press Enter
       System.out.println("Enter band's name. "); 
       inpName = myObj.nextLine();   
       System.out.println("Enter band's status (active or inactive). "); 
       inpStatus = myObj.nextLine();
       System.out.println("Enter band's record label. "); 
       inpRecordLabel = myObj.nextLine();
       ResultSet resultSet = null;

       
       myObj.close();
       

       System.out.println("Band Name: " + inpName + "  Status: " 
             + inpStatus + "   Record Label: " + inpRecordLabel);
       String insertSql = "INSERT INTO band (name, status, record_label_id) " +
              " values (?, ?, (SELECT record_label_id FROM record_label WHERE name = ?));"; 
       resultSet = null;
       try (Connection connection = DriverManager.getConnection(connectionUrl);
                PreparedStatement prepsInsert = 
                      connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);) {
            prepsInsert.setString(1, inpName);
            prepsInsert.setString(2, inpStatus);
            prepsInsert.setString(3, inpRecordLabel);
            prepsInsert.execute();
            // Retrieve the generated key from the insert. None in this example.
            resultSet = prepsInsert.getGeneratedKeys();
            // Print the ID of the inserted row. Again, will be null because no keys auto gen
            while (resultSet.next()) {
                System.out.println("Generated: " + resultSet.getString(1));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}