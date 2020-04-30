package ua.aleks4ay.kiyv.domain.dao;

import ua.aleks4ay.kiyv.domain.model.Worker;

import java.util.Collection;
import java.util.List;

public interface WorkerDao {

//    boolean save(Worker client);
//
//    boolean delete(String kod);

    Worker getById(String kod);

    List<Worker> getAll();

    boolean saveAll(List<Worker> listWorker);

    boolean updateAll(List<Worker> listWorker);

    boolean deleteAll(Collection<String> listWorker);
}
