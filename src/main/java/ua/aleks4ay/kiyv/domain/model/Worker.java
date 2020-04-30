package ua.aleks4ay.kiyv.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "worker")
public class Worker {

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "name")
    private String name;

    public Worker() {
    }

    public Worker(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(Worker.class)) {
            Worker worker = (Worker) obj;
            return this.id.equals(worker.getId()) && this.name.equals(worker.getName());
        }
        return false;
    }

    public Worker getWorker() {
        return this;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
