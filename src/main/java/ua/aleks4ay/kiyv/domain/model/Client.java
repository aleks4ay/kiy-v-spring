package ua.aleks4ay.kiyv.domain.model;

import javax.persistence.*;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "clientName")
    private String clientName;

    public Client() {
    }

    public Client(String id, String clientName) {
        this.id = id;
        this.clientName = clientName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "Client{" +
                ", id='" + id + '\'' +
                ", clientName='" + clientName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(Client.class)) {
            Client client = (Client) obj;
            return this.id.equals(client.getId()) && this.clientName.equals(client.getClientName());
        }
        return false;
    }

    public Client getClient() {
        return this;
    }
}
