import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;


public class ConnectorTest {

    private final String uuid = UUID.randomUUID().toString();
    private String name;
    private DbConnector connector;

    final String DB_URL = "jdbc:postgresql://localhost/postgres";
    final String USER = "root";
    final String PASS = "1234";

    @Before
    public void init() {
        name = uuid + "name";
        connector = new DbConnector(DB_URL, USER, PASS);
    }

    @Test
    public void insertAcc() {
        Assert.assertNull(connector.getAccByName(name));
        connector.insertAccount(new Account(name, "Попов", "1234", 30));
        Account account = connector.getAccByName(name);
        Assert.assertNotNull(account);
        Assert.assertEquals(name, account.getName());
        connector.deleteById(account.getId());
    }

    @Test
    public void deleteById(){
        connector.insertAccount(new Account(name, "Попов", "1234", 30));
        Account account = connector.getAccByName(name);
        connector.deleteById(account.getId());
        Assert.assertNull(connector.getAccByName(account.getName()));
    }

    @Test
    public void updateAge() {
        connector.insertAccount(new Account(name, "Попов", "1234", 30));
        Account account = connector.getAccByName(name);
        connector.updateAge(account.getId(), 20);
        Account accountNew = connector.getAccByName(name);
        Assert.assertEquals(accountNew.getId(), account.getId());
        Assert.assertEquals(accountNew.getAge(), 20);
        connector.deleteById(account.getId());
    }

    @Test
    public void updateName() {
        connector.insertAccount(new Account(name, "Попов", "1234", 30));
        Account account = connector.getAccByName(name);
        Assert.assertEquals(name, account.getName());
        int id = account.getId();
        connector.updateName(id, uuid + "newName");
        Assert.assertNull(connector.getAccByName(name));
        Account accountNew = connector.getAccByName(uuid + "newName");
        Assert.assertEquals(accountNew.getName(), uuid + "newName");
        connector.deleteById(id);
    }

    @After
    public void after(){
        connector.close();
    }
}
