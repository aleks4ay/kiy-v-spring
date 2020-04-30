package ua.aleks4ay.kiyv.copiller_db.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.kiyv.domain.dao.*;
import ua.aleks4ay.kiyv.domain.dbf.WorkerDbf;
import ua.aleks4ay.kiyv.domain.dbf.WorkerDbfReader;
import ua.aleks4ay.kiyv.domain.model.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ua.aleks4ay.kiyv.log.ClassNameUtil.getCurrentClassName;

public class CopyWorker {

    private static final CopyWorker copyClient = new CopyWorker();
    private static final WorkerDao workerDao = new WorkerDaoJdbc(UtilDao.getConnPostgres());
    private static final WorkerDbf workerDbfReader = new WorkerDbfReader();
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());

    private CopyWorker() {
    }

    public static CopyWorker getInstance() {
        return copyClient;
    }

    public static void main(String[] args) {

        CopyWorker.getInstance().doCopyNewRecord();

    }

    public void doCopyNewRecord() {
        long start = System.currentTimeMillis();
        log.info("Start writing 'W O R K E R'.");

        List<Worker> listNewWorker = new ArrayList<>();
        List<Worker> listUpdatingWorker = new ArrayList<>();

        Map<String, String> oldWorkers = workerDao.getAll()
                .stream()
                .filter(w -> ! w.getId().equals("     0"))
                .collect(Collectors.toMap(Worker::getId, Worker::getName));

        List<Worker> listClientFrom1C = workerDbfReader.getAll();

        for (Worker worker : listClientFrom1C) {
            if (!oldWorkers.containsKey(worker.getId())) {
                listNewWorker.add(worker);
            } else if (!oldWorkers.get(worker.getId()).equals(worker.getName())) {
                listUpdatingWorker.add(worker);
                oldWorkers.remove(worker.getId());
            }
            else {
                oldWorkers.remove(worker.getId());
            }
        }

        if (listNewWorker.size() > 0) {
            log.debug("Save to DataBase. Must be added {} new workers.", listNewWorker.size());
            workerDao.saveAll(listNewWorker);
        }
        if (listUpdatingWorker.size() > 0) {
            log.debug("Write change to DataBase. Must be updated {} workers.", listUpdatingWorker.size());
            workerDao.updateAll(listUpdatingWorker);
        }
        if (oldWorkers.size() > 0) {
            log.debug("Delete old workers from DataBase. Must be deleted {} workers.", oldWorkers.size());
            workerDao.deleteAll(oldWorkers.keySet());
        }

        long end = System.currentTimeMillis();
        log.info("End writing 'W O R K E R'. Time = {} c.", (double)(end-start)/1000);
    }
}
