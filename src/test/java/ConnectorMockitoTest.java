import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ConnectorMockitoTest {

    @Mock
    Connection connection;
    @InjectMocks
    DbConnector connector = new DbConnector();
    @Mock
    PreparedStatement mockPreparedStmnt;
    @Mock
    ResultSet mockResultSet;

    int userId = 100;

    @Before
    public void setUp() throws SQLException {
        doNothing().when(connection).commit();
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStmnt);
        when(connection.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        doNothing().when(mockPreparedStmnt).setString(anyInt(), anyString());
        when(mockPreparedStmnt.execute()).thenReturn(Boolean.TRUE);
        when(mockPreparedStmnt.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
        when(mockResultSet.getInt(anyInt())).thenReturn(userId);
    }

    @Test
    public void testCreateWithNoExceptions() throws SQLException {
        connector.insertAccount(new Account(UUID.randomUUID() + "name", "a", "asdasd", 123));
        verify(connection, times(1)).prepareStatement(DbConnector.INSERTACCOUNT);
        verify(mockPreparedStmnt, times(1)).execute();
        verify(mockPreparedStmnt, times(3)).setString(anyInt(), anyString());
        verify(mockPreparedStmnt, times(1)).setInt(anyInt(), anyInt());
        connector.close();
        verify(connection, times(1)).close();
    }

}
