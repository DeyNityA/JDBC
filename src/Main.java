import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String Url = "jdbc:oracle:thin:@localhost:1521/orcl";
    private static final String username= "bubun";
    private static final String password="sys123";
    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try {
            Connection con= DriverManager.getConnection(Url,username,password);
            Statement statement= con.createStatement();
            /*
            String query="select * from employee";
            ResultSet resultSet= statement.executeQuery(query);
            while (resultSet.next()){
                System.out.println(resultSet.getString("name"));
                System.out.println(resultSet.getInt("id"));
                System.out.println(resultSet.getDate("join_date"));
            }
            String query2= String.format("insert into employee(name,id) values('%s',%d)","kuku",897654);
            int rowcount= statement.executeUpdate(query2);
            if(rowcount>0) System.out.println("row inserted");
            else System.out.println("noo nooo");
            */

            //preparedStatement---In JDBC, PreparedStatements are generally preferred over Statements because
            // they offer improved performance, security, and portability. PreparedStatements can handle unusual
            // data types like Timestamp and InputStream.

            /*
            String query3= String.format("insert into employee values(?,?,?)");
            PreparedStatement preparedStatement=con.prepareStatement(query3);
            preparedStatement.setString(1,"Ram");
            preparedStatement.setInt(2,986756);
            preparedStatement.setDate(3,new Date(2024,7,23));

            int row_inserted= preparedStatement.executeUpdate();
            if(row_inserted>0) System.out.println("row inserted");
            else System.out.println("noo nooo");

            */

            //batch inserting
            boolean check=true;
            Scanner sc= new Scanner(System.in);
            while (check){
                String name= sc.next();
                int id= sc.nextInt();
                String Q= String.format("insert into employee(name,id) values('%s',%d)",name,id);
                statement.addBatch(Q);
                System.out.println("Do you want to insert one more row ?....please enter Y/N");
                String s= sc.next();
                if(s.equalsIgnoreCase("N")) check=false;
            }
            sc.close();

            int [] arr= statement.executeBatch();
            System.out.println(Arrays.toString(arr));

            String query4= String.format("select * from employee");
            PreparedStatement preparedStatement2=con.prepareStatement(query4);
            ResultSet resultSet1= preparedStatement2.executeQuery();

            while (resultSet1.next()){
                System.out.println(resultSet1.getString("name"));
                System.out.println(resultSet1.getInt("id"));
                System.out.println(resultSet1.getDate("join_date"));
            }

        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}