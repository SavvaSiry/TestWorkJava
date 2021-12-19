import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DbConnector implements Closeable {

    static final String FINDALL = "SELECT * FROM acc";
    static final String GETBYNAME = "SELECT * FROM acc where name = ?";
    static final String INSERTACCOUNT = "Insert into acc (name, surname, pass, age) values (?,?,?,?) ;";
    static final String UPDATENAME = "UPDATE acc SET name = ? WHERE id = ?";
    static final String UPDATESURNAME = "UPDATE acc SET surname = ? WHERE id = ?";
    static final String UPDATEPASS = "UPDATE acc SET pass = ? WHERE id = ?";
    static final String UPDATEAGE = "UPDATE acc SET age = ? WHERE id = ?";
    static final String UPDATEALL = "UPDATE acc SET name = ? , surname = ? , pass = ? , age = ? WHERE id = ?";
    static final String DELETEBYID = "DELETE FROM acc WHERE id = ?";

    private Connection connection;

    public DbConnector(String DB_URL, String USER, String PASS) {
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id){
        try (PreparedStatement statement = connection.prepareStatement(DELETEBYID)){
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateName(int id, String name){
        try (PreparedStatement statement = connection.prepareStatement(UPDATENAME)){
            statement.setInt(2, id);
            statement.setString(1, name);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSurname(int id, String surname){
        try (PreparedStatement statement = connection.prepareStatement(UPDATESURNAME)){
            statement.setInt(2, id);
            statement.setString(1, surname);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePass(int id, String pass){
        try (PreparedStatement statement = connection.prepareStatement(UPDATEPASS)){
            statement.setInt(2, id);
            statement.setString(1, pass);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAge(int id, int age){
        try (PreparedStatement statement = connection.prepareStatement(UPDATEAGE)){
            statement.setInt(2, id);
            statement.setInt(1, age);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAll(int id, String name, String surname, String pass, int age){
        try (PreparedStatement statement = connection.prepareStatement(UPDATEALL)){
            statement.setInt(5, id);
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, pass);
            statement.setInt(4, age);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Account getAccByName(String name){
        try (PreparedStatement statement = connection.prepareStatement(GETBYNAME)){
            Account account = null;
            statement.setString(1, name);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                account = new Account(
                        set.getInt("id"),
                        set.getString("name"),
                        set.getString("surname"),
                        set.getString("pass"),
                        set.getInt("age"));
            }
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> getAllAcc(){
        try (Statement statement = connection.createStatement()){
            List<Account> accounts = new ArrayList<>();
            ResultSet set = statement.executeQuery(FINDALL);
            while (set.next()){
                Account account = new Account(
                        set.getInt("id"),
                        set.getString("name"),
                        set.getString("surname"),
                        set.getString("pass"),
                        set.getInt("age"));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertAccount(Account account) {
        try (PreparedStatement statement = connection.prepareStatement(INSERTACCOUNT)){
            statement.setString(1,account.getName());
            statement.setString(2,account.getSurname());
            statement.setString(3,account.getPassword());
            statement.setInt(4,account.getAge());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAccounts(List<Account> list) {
        if (list != null) {
            StringBuffer query = new StringBuffer("Insert into acc (name, surname, pass, age) values ");
            try (Statement statement = connection.createStatement()){
                for (int i = 0; i < list.size(); i++) {
                    query.append("('"+ list.get(i).getName() +
                            "','" + list.get(i).getSurname() +
                            "','" + list.get(i).getPassword() +
                            "','" + list.get(i).getAge() + "'),");
                }
                query.delete(query.length() - 1, query.length());
                query.append(";");
                statement.execute(query.toString());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void loadSql(String sql){
        try (Statement statement = connection.createStatement()){
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}