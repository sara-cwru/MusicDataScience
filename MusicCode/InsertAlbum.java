
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


public class InsertAlbum {

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
        String inpName, inpArtistFirstName, inpArtistLastName, inpBand;
        int inpYear;
  
        //float inpBudget;

        // Enter username and press Enter
       System.out.println("Enter album name."); 
       inpName = myObj.nextLine();   
       System.out.println("Enter name of album's artist's first name (if it is by a band, hit enter). "); 
       inpArtistFirstName = myObj.nextLine();
       System.out.println("Enter name of album's artist's last name (if it is by a band, hit enter). "); 
       inpArtistLastName = myObj.nextLine();
       System.out.println("Enter band name (if already inputed artist name, hit enter). "); 
       inpBand = myObj.nextLine();
       System.out.println("Enter album year."); 
       inpYear = Integer.parseInt(myObj.nextLine());
       ResultSet resultSet = null;
       
       myObj.close();
       

       System.out.println("Name: " + inpName + "  Artist: " 
             + inpArtistFirstName + " " + inpArtistLastName + "  Band: " + inpBand + "  Year: " + inpYear);
       String insertSql = "INSERT INTO album (name, artist_id, band_id, year) " +
              " values (?, (SELECT artist_id FROM artist WHERE first_name = ? and last_name = ?), (SELECT band_id FROM band WHERE name = ?), ?);"; 
       resultSet = null;
       try (Connection connection = DriverManager.getConnection(connectionUrl);
                PreparedStatement prepsInsert = 
                      connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                
            prepsInsert.setString(1, inpName);
            prepsInsert.setString(2, inpArtistFirstName);
            prepsInsert.setString(3, inpArtistLastName);
            prepsInsert.setString(4, inpBand);
            prepsInsert.setInt(5, inpYear);
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
