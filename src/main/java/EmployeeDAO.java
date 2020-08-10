import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO implements AutoCloseable {
    private Connection connection;

    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }

    public int create(Employee employee) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employees " +
                "VALUES (?,?,?,?,?,?)");
        preparedStatement.setInt(1, employee.getId());
        preparedStatement.setString(2, employee.getFirstName());
        preparedStatement.setString(3, employee.getLastName());
        preparedStatement.setInt(4, employee.getSalary());
        preparedStatement.setDate(5, employee.getFromDate());
        preparedStatement.setString(6, employee.getTitle());
        return preparedStatement.executeUpdate();

    }

    public Employee read(int id) throws SQLException {
        Employee employee = new Employee();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employees WHERE " +
                " id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            employee.setId(resultSet.getInt(1));
            employee.setFirstName(resultSet.getString(2));
            employee.setLastName(resultSet.getString(3));
            employee.setSalary(resultSet.getInt(4));
            employee.setFromDate(resultSet.getDate(5));
            employee.setTitle(resultSet.getString(6));
        }
        return employee;
    }

    public int update(int id, String column, String value) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employees SET ? = ? WHERE " +
                "id = ?");
        preparedStatement.setString(1, column);
        preparedStatement.setString(2, value);
        preparedStatement.setInt(3, id);
        return preparedStatement.executeUpdate();
    }

    public int delete(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employees WHERE " +
                "id = ?");
        preparedStatement.setInt(1, id);
        return preparedStatement.executeUpdate();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
