package ua.aleks4ay.kiyv.domain.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.aleks4ay.kiyv.domain.model.Worker;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkerDaoJdbcTest {
    private static Connection connPostgresTest = UtilDao.getConnTest();
    private WorkerDao workerDao = new WorkerDaoJdbc(connPostgresTest);

    List<Worker> workerList = new ArrayList<>();

    @Before
    public void setUp() {
        try {
            connPostgresTest.createStatement().executeUpdate("DELETE FROM worker;");
            connPostgresTest.createStatement().executeUpdate("INSERT INTO worker(id, name) VALUES ('w1_', 'worker name 1'), " +
                    "('w2_', 'worker name 2'), ('w3_', 'worker name 3'), ('w4_', 'worker name 4');");
            connPostgresTest.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Worker worker1 = new Worker("w1_", "worker name 1");
        Worker worker2 = new Worker("w2_", "worker name 2");
        Worker worker3 = new Worker("w3_", "worker name 3");
        Worker worker4 = new Worker("w4_", "worker name 4");

        workerList = Arrays.asList(worker1, worker2, worker3, worker4);
    }

    @Test
    public void testGetById() throws Exception {
        Worker result = workerDao.getById("w2_");
        Assert.assertEquals(new Worker("w2_", "worker name 2"), result);
    }

/*    @Test
    public void testSave() throws Exception {
        boolean result = workerDao.save(new Worker("new_w_1", "new worker name_1"));
        Assert.assertEquals(true, result);
    }

    @Test
    public void testDelete() throws Exception {
        boolean result = workerDao.delete("w2_");
        Assert.assertEquals(true, result);
    }*/

    @Test
    public void testGetAll() throws Exception {
        List<Worker> result = workerDao.getAll();
        Assert.assertEquals(workerList, result);
    }

    @Test
    public void testSaveAll() throws Exception {
        boolean result = workerDao.saveAll(Arrays.asList(
                new Worker("new_list_1", "new_w_name_1"),
                new Worker("new_list_2", "new_w_name_2")));
        Assert.assertEquals(true, result);
    }

    @Test
    public void testUpdateAll() throws Exception {
        boolean result = workerDao.updateAll(Arrays.asList(
                new Worker("w2_", "updated name 2"),
                new Worker("w4_", "updated name 4")));
        Assert.assertEquals(true, result);
    }

    @Test
    public void testDeleteAll() throws Exception {
        boolean result = workerDao.deleteAll(Arrays.asList("w1_", "w2_"));
        Assert.assertEquals(true, result);
    }
}
