import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Path to DB sqlite file:");
        String pathToDB = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.M.yyyy", Locale.ENGLISH);
            DBManager dbManager = new DBManager(pathToDB);

            System.out.println("""
                    Commands:
                    add first_name second_name patronymic born_date group
                    remove id
                    print all
                    """);

            while (true){
                String input = scanner.nextLine();

                if (input.startsWith("add")){
                    String[] commandArgs = input.substring(4).split(" ");
                    boolean success = false;
                    try {
                        success = dbManager.addStudent(new Student(0, commandArgs[0], commandArgs[1], commandArgs[2],
                                dateFormat.parse(commandArgs[3]), Integer.parseInt(commandArgs[4])));
                    } catch (ParseException e) {
                        System.out.println("Date format should be dd.M.yyyy");
                    }

                    System.out.println(success ? "Student added" : "Student add error");
                }
                else if (input.startsWith("remove")){
                    boolean success = dbManager.deleteStudent(Integer.parseInt(input.substring(7)));
                    System.out.println(success ? "Student removed" : "Student remove error");
                }
                else if (input.equals("print all")){
                    List<Student> students = dbManager.getAllStudents();
                    System.out.println("Students list:");

                    for (Student student : students){
                        System.out.println(student.toString());
                    }
                }
                else {
                    System.out.println("Unrecognized command");
                }
            }

        } catch (SQLException e) {
            System.out.println("Path to db is incorrect");
        }
    }
}
