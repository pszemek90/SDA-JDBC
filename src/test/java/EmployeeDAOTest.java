import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDAOTest {
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_company?serverTimezone=Europe/Warsaw";
    private static final String USER = "root";
    private static final String PASSWORD = "qwerty";
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void shouldCreateEmployee() {
        //given
        try (EmployeeDAO employeeDAO = new EmployeeDAO(connection)) {
            Employee employee = new Employee(12, "Andrzej", "Duda", 12500,
                    Date.valueOf("2020-08-09"), "President");
            //when
            int result = employeeDAO.create(employee);
            //then
            assertEquals(1, result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldDeleteEmployee() {
        try (EmployeeDAO employeeDAO = new EmployeeDAO(connection)) {
            //when
            int result = employeeDAO.delete(12);
            //then
            assertEquals(1, result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldReadEmployee() {
        try (EmployeeDAO employeeDAO = new EmployeeDAO(connection)) {
            //when
            Employee resultEmployee = employeeDAO.read(12);
            //then
            assertEquals(new Employee(12, "Andrzej", "Duda", 12500,
                    Date.valueOf("2020-08-09"), "President"), resultEmployee);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldUpdateEmployees() {
        try (EmployeeDAO employeeDAO = new EmployeeDAO(connection)) {
            //when
            String column = "first_name";
            String newName = "Bogdan";
            int updatedEmployee = employeeDAO.update(12, column, newName);
            //then
            assertEquals(1, updatedEmployee);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}