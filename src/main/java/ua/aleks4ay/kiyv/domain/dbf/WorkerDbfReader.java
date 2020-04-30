package ua.aleks4ay.kiyv.domain.dbf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.kiyv.domain.dao.UtilDao;
import ua.aleks4ay.kiyv.domain.model.Client;
import ua.aleks4ay.kiyv.domain.model.Worker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static ua.aleks4ay.kiyv.log.ClassNameUtil.getCurrentClassName;

public class WorkerDbfReader implements WorkerDbf {

    private static Connection connDbf;
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String SQL_GET_WORKER = "select ID, DESCR from SC1670;";

    public WorkerDbfReader() {
        connDbf = UtilDao.getConnDbf();
        log.debug("Get connection to 'dbf-files' 1C from {}.", WorkerDbfReader.class);
    }

    @Override
    public List<Worker> getAll() {

        List<Worker> listWorker = new ArrayList<>();

        try (Statement st = connDbf.createStatement()) {

            ResultSet rs1 = st.executeQuery(SQL_GET_WORKER);
            log.debug("Select all 'Worker' from 1C. SQL = {}.", SQL_GET_WORKER);
            while (rs1.next()) {
                String name = rs1.getString(2);
                if (name != null) {
                    byte[] bytes = rs1.getBytes(2);
                    name = new String(bytes, "Windows-1251");
                } else {
                    name = "-";
                }
                listWorker.add(new Worker(rs1.getString(1), name));
            }
            log.debug("Was read {} Worker.", listWorker.size());
            return listWorker;
        } catch (Exception e) {
            log.warn("Exception during writing all 'Worker'. SQL = {}", SQL_GET_WORKER, e);
        }
        log.debug("Workers not found.");
        return null;
    }
}
