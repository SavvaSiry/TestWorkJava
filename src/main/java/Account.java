public class Account {

    private int id;
    private String name;
    private String surname;
    private String password;
    private int age;

    public Account(int id, String name, String surname, String password, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.age = age;
    }

    public Account(String name, String surname, String password, int age) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return  "id       = " + id + "\n" +
                "name     = " + name + "\n" +
                "surname  = " + surname + "\n" +
                "password = " + password + "\n" +
                "age      = " + age;
    }
}
