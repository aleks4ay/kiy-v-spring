package ua.aleks4ay.kiyv.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.kiyv.domain.model.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static ua.aleks4ay.kiyv.log.ClassNameUtil.getCurrentClassName;

public class WorkerDaoJdbc implements WorkerDao {

    private static Connection connPostgres;
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String SQL_GET_ONE = "SELECT name FROM worker WHERE id = ?;";
    private static final String SQL_GET_ALL = "SELECT id, name FROM worker;";
    private static final String SQL_DELETE = "DELETE FROM worker WHERE id = ?;";
    private static final String SQL_SAVE = "INSERT INTO worker (id, name) VALUES (?, ?);";
    private static final String SQL_UPDATE = "UPDATE worker SET name = ? WHERE id = ?;";


    public WorkerDaoJdbc(Connection connection) {
        connPostgres = connection;
        log.debug("Get connection to PostgreSQL from {}.", UtilDao.class);
    }

    @Override
    public Worker getById(String id) {
        try (PreparedStatement statement = connPostgres.prepareStatement(SQL_GET_ONE)) {
            statement.setString(1, id);
            log.debug("Select 'Worker'. SQL = {}. id = {}.", SQL_GET_ONE, id);
            ResultSet rs = statement.executeQuery();

            rs.next();
            log.debug("return new 'Worker'. id = {}.", id);
            return new Worker(id, rs.getString("name"));

        } catch (SQLException e) {
            log.warn("Exception during reading 'Client' with id = {}. SQL={}.", id, SQL_GET_ONE, e);
        }
        log.debug("Worker with id = {} not found.", id);
        return null;
    }

/*    @Override
    public boolean save(Worker newWorker) {
        try (PreparedStatement statement = connPostgres.prepareStatement(SQL_SAVE)){
            log.debug("Prepared to save.");
            statement.setString(1, newWorker.getId());
            statement.setString(2, newWorker.getName());
            int result = statement.executeUpdate();
            if (result == 1) {
                log.debug("Save new 'Worker'. SQL = {}. Worker = {}.", SQL_SAVE, newWorker);
                connPostgres.commit();
                return true;
            }
            else {
                connPostgres.rollback();
            }
        } catch (SQLException e) {
            log.warn("Exception during saving new 'Worker'. SQL = {}. Worker = {}.", SQL_SAVE, newWorker, e);
        }
        log.debug("'Worker' don't Saved!!! SQL = {}. Worker = {}.", SQL_SAVE, newWorker);
        return false;
    }

    @Override
    public boolean delete(String id) {
        try (PreparedStatement statement = connPostgres.prepareStatement(SQL_DELETE)){
            log.debug("Prepared to delete.");
            statement.setString(1, id);
            int result = statement.executeUpdate();
            if (result == 1) {
                connPostgres.commit();
                log.debug("Worker with id = {} deleted. SQL = {}.", id, SQL_DELETE);
                return true;
            }
            else {
                connPostgres.rollback();
            }
        } catch (SQLException e) {
            log.warn("Exception during delete old 'Worker'. SQL = {}.", SQL_DELETE, e);
        }
        log.debug("Worker with id = {} don't Deleted!!! SQL = {}.", id, SQL_DELETE);
        return false;
    }*/

    @Override
    public List<Worker> getAll() {
        List<Worker> result = new ArrayList<>();
        try (Statement statement = connPostgres.createStatement()) {
            ResultSet rs = statement.executeQuery(SQL_GET_ALL);
            log.debug("Select all 'Worker'. SQL = {}.", SQL_GET_ALL);
            while (rs.next()) {
                result.add(new Worker( rs.getString("id"), rs.getString("name")));
            }
            log.debug("Was read {} Worker.", result.size());
            return result;
        } catch (SQLException e) {
            log.warn("Exception during reading all 'Worker'.", e);
        }
        log.debug("Workers not found.");
        return null;
    }

    @Override
    public boolean saveAll(List<Worker> listWorker) {
        try {
            int result = 0;
            for (Worker worker : listWorker) {
                PreparedStatement ps = connPostgres.prepareStatement(SQL_SAVE);
                log.debug("Prepared new 'Worker' to batch. SQL = {}. Worker = {}.", SQL_SAVE, worker);
                ps.setString(1, worker.getId());
                ps.setString(2, worker.getName());
                ps.addBatch();
                int[] numberOfUpdates = ps.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
            }
            if (result == listWorker.size()) {
                log.debug("Try commit");
                connPostgres.commit();
                log.debug("Commit - OK. {} Workers Saved. SQL = {}.", result, SQL_SAVE);
                return true;
            }
            else {
                connPostgres.rollback();
                log.debug("Saved {}, but need to save {} Worker. Not equals!!!", result, listWorker.size());
            }

        } catch (SQLException e) {
            log.warn("Exception during saving {} new 'Worker'. SQL = {}.", listWorker.size() , SQL_SAVE, e);
        }
        return false;
    }

    @Override
    public boolean updateAll(List<Worker> listWorker) {
        try {
            int result = 0;
            for (Worker worker : listWorker) {
                log.debug("Prepared updated 'Worker' to batch. SQL = {}. Worker = {}.", SQL_UPDATE, worker);
                PreparedStatement ps = connPostgres.prepareStatement(SQL_UPDATE);
                ps.setString(1, worker.getName());
                ps.setString(2, worker.getId());
                ps.addBatch();
                int[] numberOfUpdates = ps.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();

                for (int i=0; i< numberOfUpdates.length; i++) {
                    System.out.println("k[" + i + "] = " + numberOfUpdates[i]);
                }
            }
            if (result == listWorker.size()) {
                log.debug("Try commit");
                connPostgres.commit();
                log.debug("Commit - OK. {} Worker updated. SQL = {}.", result, SQL_UPDATE);
                return true;
            }
            else {
                connPostgres.rollback();
                log.debug("Updated {}, but need to update {} Worker. Not equals!!!", result, listWorker.size());
            }
        } catch (SQLException e) {
            log.warn("Exception during update {} changed 'Worker'. SQL = {}.", listWorker.size() , SQL_UPDATE, e);
        }
        return false;
    }

    @Override
    public boolean deleteAll(Collection<String> listWorkerId) {
        try {
            int result = 0;
            for (String id : listWorkerId) {
                log.debug("Prepared old 'Worker' for delete to batch. SQL = {}. Id = {}.", SQL_DELETE, id);
                PreparedStatement ps = connPostgres.prepareStatement(SQL_DELETE);
                ps.setString(1, id);
                ps.addBatch();
                int[] numberOfUpdates = ps.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
            }
            if (result == listWorkerId.size()) {
                log.debug("Try commit");
                connPostgres.commit();
                log.debug("Commit - OK. {} Worker updated. SQL = {}.", result, SQL_DELETE);
                return true;
            }
            else {
                connPostgres.rollback();
                log.debug("Deleted {}, but need to delete {} Worker. Not equals!!!", result, listWorkerId.size());
            }
        } catch (SQLException e) {
            log.warn("Exception during delete {} old 'Worker'. SQL = {}.", listWorkerId.size() , SQL_DELETE, e);
        }
        return false;
    }
}
