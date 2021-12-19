import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        final String DB_URL = "jdbc:postgresql://localhost/postgres";
        final String USER = "root";
        final String PASS = "1234";

        try (DbConnector connector = new DbConnector(DB_URL, USER, PASS)){
            List<Account> accountList = connector.getAllAcc();

            //Имена всех, кто старше 20 лет
            List<String> names = accountList.stream()
                    .filter(account -> account.getAge() < 20)
                    .map(Account::getName)
                    .collect(Collectors.toList());

            //Количество пользователей у которых фамилия на -ов
            long count = accountList.stream()
                    .filter(account -> account.getSurname().endsWith("ов"))
                    .count();
        }
    }
}
