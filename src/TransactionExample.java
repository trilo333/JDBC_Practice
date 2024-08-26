import java.sql.*;
import java.util.Scanner;

public class TransactionExample {
    private static final String url = "jdbc:mysql://localhost:3306/bank" ;
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
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            String debit_query = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            String credit_query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement debitPreparedStatement = connection.prepareStatement(debit_query);
            PreparedStatement creditPreparedStatement = connection.prepareStatement(credit_query);
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter account from which you want to debit amount");
            int debitAccount = scanner.nextInt();
            System.out.print("Enter amount you want to debit");
            double amount = scanner.nextDouble();
            System.out.print("Enter account in which you want to credit amount");
            int creditAccount = scanner.nextInt();
            debitPreparedStatement.setDouble(1,amount);
            debitPreparedStatement.setInt(2,debitAccount);
            creditPreparedStatement.setDouble(1,amount);
            creditPreparedStatement.setInt(2,creditAccount);
            int debitUpdatedAccount = debitPreparedStatement.executeUpdate();
            int creditUpdatedAccount = creditPreparedStatement.executeUpdate();
            if(isSufficient(connection,debitAccount,amount) && debitUpdatedAccount > 0 && creditUpdatedAccount >0){
                connection.commit();
                System.out.println("Transaction is successfull!!");
            }
            else{
                System.out.println("Transaction failed!!");
            }
            scanner.close();
            debitPreparedStatement.close();
            creditPreparedStatement.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    static boolean isSufficient(Connection connection,int account_number , double amount){
        try{
            String query = "SELECT balance from accounts where account_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,account_number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                double current_balance = resultSet.getDouble("balance");
                if(amount>current_balance || amount<0.00){
                    return false;
                }
                else{
                    return true;
                }
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return true;
    }
}
