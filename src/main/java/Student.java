import java.util.Date;

public class Student {

    public int id;
    public String firstName;
    public String secondName;
    public String patronymic;
    public Date bornDate;
    public int group;

    public Student(int id, String firstName, String secondName, String patronymic, Date bornDate, int group) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.bornDate = bornDate;
        this.group = group;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Имя: %s | Фамилия: %s | Отчество: %s | Дата рождения: %s | Группа: %s",
                id, firstName, secondName, patronymic, bornDate.toString(), group);
    }
}
