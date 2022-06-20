import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBManager {

    private Connection conn;

    public DBManager(String dbPath) throws SQLException {
        DriverManager.registerDriver(new JDBC());
        conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

        conn.createStatement().execute("CREATE TABLE IF NOT EXISTS students " +
                "(id INTEGER PRIMARY KEY, first_name VARCHAR(64), second_name VARCHAR(64), " +
                "patronymic VARCHAR(64), born_date DATE, `group` INTEGER)");
    }

    public List<Student> getAllStudents() {
        try (Statement statement = conn.createStatement()) {
            List<Student> students = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

            while (resultSet.next()) {
                students.add(new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("second_name"),
                        resultSet.getString("patronymic"),
                        resultSet.getDate("born_date"),
                        resultSet.getInt("group")
                ));
            }

            return students;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean addStudent(Student student) {
        try (PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO students(`first_name`, `second_name`, `patronymic`, `born_date`, `group`) " +
                        "VALUES(?, ?, ?, ?, ?)")) {

            statement.setObject(1, student.firstName);
            statement.setObject(2, student.secondName);
            statement.setObject(3, student.patronymic);
            statement.setObject(4, student.bornDate);
            statement.setObject(5, student.group);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteStudent(int id) {
        try (PreparedStatement statement = conn.prepareStatement(
                "DELETE FROM students WHERE id = ?")) {
            statement.setObject(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
