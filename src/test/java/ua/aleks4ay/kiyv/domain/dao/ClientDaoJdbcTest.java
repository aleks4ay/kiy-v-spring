package ua.aleks4ay.kiyv.domain.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.unitils.reflectionassert.ReflectionComparatorMode;
import ua.aleks4ay.kiyv.domain.model.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class ClientDaoJdbcTest {

    private static Connection connPostgres = UtilDao.getConnPostgres();
    private ClientDao clientDao;

    Client client1, client2, client3, client4;
    List<Client> clientList = new ArrayList<>();

    @Before
    public void setUp() {
        clientDao = new ClientDaoJdbc();
        try {
            connPostgres.createStatement().execute("DELETE FROM client;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        client1 = new Client("kod1", "client name 1");
        client2 = new Client("kod2", "client name 2");
        client3 = new Client("kod3", "client name 3");
        client4 = new Client("kod4", "client name 4");

        clientList = Arrays.asList(client1, client2, client3, client4);

        clientDao.saveAll(clientList);
    }

    @Test
    public void testGetById() throws Exception {
        Client result = clientDao.getById("kod2");
        Assert.assertEquals(new Client("kod2", "client name 2"), result);
    }

    @Test
    public void testGetAll() throws Exception {
        List<Client> result = clientDao.getAll();
        assertReflectionEquals(clientList, result,  ReflectionComparatorMode.IGNORE_DEFAULTS);
    }

    @Test
    public void testSave() throws Exception {
        boolean result = clientDao.save(new Client("id 5", "clientName test Save"));
        Assert.assertEquals(true, result);
    }

    @Test
    public void testDelete() throws Exception {
        boolean result = clientDao.delete("kod1");
        Assert.assertEquals(true, result);
    }

    @Test
    public void testSaveAll() throws Exception {
        List<Client> tempClientList = Arrays.asList(new Client("id7", "n7"), new Client("id8", "n8"), new Client("id9", "n9"));
        boolean result = clientDao.saveAll(tempClientList);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testUpdateAll() throws Exception {
        boolean result = clientDao.updateAll(Arrays.asList(new Client("kod1", "update 1"), new Client("kod3", "update 3")));
        Assert.assertEquals(true, result);
    }

    @Test
    public void testDeleteAll() throws Exception {
        Collection<String> clientListId;
        boolean result = clientDao.deleteAll(Arrays.asList("kod1", "kod4", "kod2"));
        Assert.assertEquals(true, result);
    }

    @Test
    public void testIsBlocking() throws Exception {
        connPostgres.createStatement().execute("UPDATE table_flag SET status = TRUE WHERE parametr_nane = 'blocking';");
        boolean result = clientDao.isBlocking();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testIsBlocking2() throws Exception {
        connPostgres.createStatement().execute("UPDATE table_flag SET status = FALSE WHERE parametr_nane = 'blocking';");
        boolean result = clientDao.isBlocking();
        Assert.assertEquals(false, result);
    }

    @Test
    public void testSetLock() throws Exception {
        // prepared
        connPostgres.createStatement().execute("UPDATE table_flag SET status = FALSE WHERE parametr_nane = 'blocking';");
        // test
        boolean result = clientDao.setLock();
        Assert.assertEquals(true, result);
    }

    @Test
    public void testSetUnlock() throws Exception {
        // prepared
        connPostgres.createStatement().execute("UPDATE table_flag SET status = TRUE WHERE parametr_nane = 'blocking';");
        //test
        boolean result = clientDao.setUnlock();
        Assert.assertEquals(true, result);
    }
}
