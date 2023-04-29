
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


public class InsertArtist {

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
        String inpFirstName, inpLastName, inpStatus, inpRecordLabel, inpBand;
        int inpDebutYear;
  
        //float inpBudget;

        // Enter username and press Enter
       System.out.println("Enter artist's first name then enter. "); 
       inpFirstName = myObj.nextLine();   
       System.out.println("Enter artist's last name then enter. If no last name, just hit enter.");
       inpLastName = myObj.nextLine();
       System.out.println("Enter artist's status: 'alive' or 'dead' then hit enter. "); 
       inpStatus = myObj.nextLine();
       System.out.println("Enter artist's record label then hit enter. "); 
       inpRecordLabel = myObj.nextLine();
       System.out.println("Enter artist's debut year."); 
       inpDebutYear = Integer.parseInt(myObj.nextLine());
       System.out.println("Enter artist's band name. If not in a band, just hit enter. "); 
       inpBand = myObj.nextLine();
       ResultSet resultSet = null;
       
       myObj.close();
       

       System.out.println("First Name: " + inpFirstName + "  Last Name: " 
             + inpLastName + "   Status: " + inpStatus + "  Record Label: " + inpRecordLabel + " Debut Year: " + inpDebutYear + 
             " Band: " + inpBand);
       String insertSql = "INSERT INTO artist (first_name, last_name, status, record_label_id, debut_year, band_id) " +
              " values (?, ?, ?, (SELECT record_label_id FROM record_label WHERE name = ?),?, (SELECT band_id FROM band WHERE name = ?));"; 
       resultSet = null;
       try (Connection connection = DriverManager.getConnection(connectionUrl);
                PreparedStatement prepsInsert = 
                      connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                
            prepsInsert.setString(1, inpFirstName);
            prepsInsert.setString(2, inpLastName);
            prepsInsert.setString(3, inpStatus);
            prepsInsert.setString(4, inpRecordLabel);
            prepsInsert.setInt(5, inpDebutYear);
            prepsInsert.setString(6, inpBand);
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
            System.out.println ("Please put in a valid status (either 'alive' or 'dead')");
        }
        
    }
}
