import java.sql.*;
import java.util.List;
import java.util.Random;

public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_company?serverTimezone=Europe/Warsaw";
    private static final String USER = "root";
    private static final String PASSWORD = "qwerty";

    public static void main(String[] args) {
        System.out.println("Original employees: ");
        printEmployees();
        giveRaise(500, "2020-01-01");
        System.out.println("After raising salary: ");
        printEmployees();
        changeDepartment();
        System.out.println("After department change: ");
        printEmployees();
    }

    private static void changeDepartment() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employees SET title = ?" +
                     "WHERE id = ?")
        ) {
            List<String> titlesList = List.of("Customer service", "Developer", "Administration");
            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                preparedStatement.setString(1, titlesList.get(random.nextInt(titlesList.size())));
                preparedStatement.setInt(2, random.nextInt(10) + 1);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void giveRaise(int raiseAmount, String employmentDate) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employees " +
                     "SET salary = salary + ? WHERE from_date < ?")
        ) {
            preparedStatement.setInt(1, raiseAmount);
            preparedStatement.setString(2, employmentDate);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printEmployees() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                int salary = resultSet.getInt(4);
                Date fromDate = resultSet.getDate(5);
                String title = resultSet.getString(6);
                System.out.println("Id: " + id + ", first name: " + firstName
                        + ", last name: " + lastName + ", salary " + salary
                        + ", employment date: " + fromDate + ", title: " + title);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
