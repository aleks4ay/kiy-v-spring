package ua.aleks4ay.kiyv.domain.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders", indexes = {@Index( name = "doc_idx", columnList = "id_doc", unique = true)})

public class Order {

    @Id
    @GeneratedValue //(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private int id;

    @Column(name = "id_doc", unique = true)
    private String kod;

    @Column(name = "doc_number")
    private String docNumber;

    @Column(name = "manager")
    private String manager;

    @Column(name = "designer")
    private String designer;

    @Column(name = "client")
    private String client;

    private int durationTime;
    private LocalDateTime dateCreate;
    private LocalDateTime dateToFactory;


    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Description> descriptions;

    public Order() {
    }

    public Order(String kod, String docNumber, String manager, String designer, String client) {
        this.kod = kod;
        this.docNumber = docNumber;
        this.manager = manager;
        this.designer = designer;
        this.client = client;
        this.descriptions = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDateTime getDateToFactory() {
        return dateToFactory;
    }

    public void setDateToFactory(LocalDateTime dateToFactory) {
        this.dateToFactory = dateToFactory;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", kod='" + kod + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", manager='" + manager + '\'' +
                ", designer='" + designer + '\'' +
                ", client='" + client + '\'' +
                ", durationTime=" + durationTime +
                ", dateCreate=" + dateCreate +
                ", dateToFactory=" + dateToFactory +
                '}';
    }
}



