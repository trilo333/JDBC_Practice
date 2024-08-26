import java.sql.*;
import java.util.Scanner;

//import static java.lang.String.format;

public class Main {
    private static final String url = "jdbc:mysql://localhost:3306/mydb" ;
    private static final String  username = "root";
    private static final String password = "Trilochan@123";
    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
          Connection connection = DriverManager.getConnection(url,username,password);
//          Statement statement = connection.createStatement();
          String query = "INSERT INTO students(name,age,marks) VALUES(?,?,?)";
          PreparedStatement preparedStatement = connection.prepareStatement(query);
          Scanner scanner = new Scanner(System.in);
          while(true){
              System.out.print("Enter name: ");
              String name = scanner.next();
              System.out.print("Enter age: ");
              Integer age = scanner.nextInt();
              System.out.print("Enter marks: ");
              Double marks = scanner.nextDouble();
              System.out.print("Enter more data(Y/N): ");
              String choice = scanner.next();
//              String query = String.format("INSERT INTO students(name,age,marks) VALUES('%s',%d,%f) ", name,age,marks);
//              statement.addBatch(query);
              preparedStatement.setString(1,name);
              preparedStatement.setInt(2,age);
              preparedStatement.setDouble(3,marks);
              preparedStatement.addBatch();
              if(choice.toUpperCase().equals("N")){
                  break;
              }
          }



//          String query = format("INSERT INTO students(name,age,marks) VALUES('%s',%d,%f) ", "Trilochan" , 25, 92.3);
//            String query = format("DELETE FROM students  WHERE id =%d" ,2);
//            String query = "DELETE FROM students WHERE ID = ?";
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setDouble(1,4);
////            preparedStatement.setInt(2,30);
////            preparedStatement.setDouble(3,78.9);
            int[] rowsAffected  = preparedStatement.executeBatch();
//            if (rowsAffected > 0){
//              System.out.println("Data Updated successfully!!");
//            }
//        else{
//          System.out.println("Data not updated successfully");
//        }
            for(int i=0;i< rowsAffected.length;i++){
                if(rowsAffected[i]==1){
                    int next = i+1;
                    System.out.println("Query Number " + next  + " executed successfully!!");
                }
            }

//          String query = "select * from students";
//          ResultSet resultSet = preparedStatement.executeQuery();
//          while(resultSet.next()){
//              int id = resultSet.getInt("id");
//              String name = resultSet.getString("name");
//              int age = resultSet.getInt("age");
//              double marks = resultSet.getDouble("marks");
//                  System.out.println("ID: "+ id);
//                  System.out.println("Name: "+ name);
//                  System.out.println("Age: "+ age);
//                  System.out.println("Marks: "+ marks + "\n");
//          }
//            if (resultSet.next()){
//                double marks = resultSet.getDouble("marks");
//                System.out.println("Marks: "+ marks );
//            }
//            else{
//                System.out.println("Marks not found!!");
//            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    }
