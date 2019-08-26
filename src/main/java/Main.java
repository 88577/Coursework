import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    public static Connection db = null;
    // Behaves like a global variable
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        openDatabase("courseworkDatabase.db");
        listUsers();

        closeDatabase();
    }

    public static void listUsers(){
        try{
            PreparedStatement ps = db.prepareStatement("SELECT UserID, firstName, lastName, password, email, admin FROM Users");
            ResultSet results = ps.executeQuery();
            while(results.next()){
                int UserID = results.getInt(1);
                String firstName = results.getString(2);
                String lastName = results.getString(3);
                String password = results.getString(4);
                String email = results.getString(5);
                String admin = results.getString(6);
                System.out.println(UserID + " " + firstName + " " + lastName + " " + password + " " + email + " " + admin);
            }
        }   catch (Exception exception){
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void insertUsers(int UserID, String Username, String DateOfBirth){
        try{
            PreparedStatement ps = db.prepareStatement("INSERT INTO Users (UserID, Username, DateOfBirth) values (?, ?, ?)");
            ps.setInt(1, UserID);
            ps.setString(2, Username);
            ps.setString(3, DateOfBirth);
            ps.executeUpdate();
            System.out.println("Records successfully added");
        }   catch (Exception exception){
            System.out.println("Error: " + exception.getMessage());
        }
    }

    private static  void openDatabase(String dbFile){
        try{
            Class.forName("org.sqlite.JDBC");
            // Loads the database driver
            SQLiteConfig config = new SQLiteConfig();
            // Does the database settings
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties());
            // Opens database file
            System.out.println("Database connection successfully established.");
        }   catch (Exception exception){
            System.out.println("Database connection error: " + exception.getMessage());
            // Catches any errors and returns error statement
        }
    }

    private static void closeDatabase(){
        try{
            db.close();
            // Closes database
            System.out.println("Disconnected from database successfully.");
        }   catch (Exception exception){
            System.out.println("Database disconnection error:" + exception.getMessage());
        }
    }
}
