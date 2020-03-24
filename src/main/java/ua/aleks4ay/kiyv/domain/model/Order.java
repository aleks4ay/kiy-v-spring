package ua.aleks4ay.kiyv.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


//@Entity
public class Order {

    private String idDoc;
    private String docNumber = "";
    private String client = "";
    private String manager = "";
    private String designer = "";
    private int durationTime;
    private LocalDateTime dateCreate;
    private LocalDateTime dateToFactory;
    private LocalDateTime dateToShipment;
    private LocalDateTime dateToShipmentFactual;

    private List<Description> descriptions;

    public Order() {
    }

    public Order(String idDoc, String docNumber, String client, String manager, String designer, int durationTime,
                 LocalDateTime dateCreate, LocalDateTime dateToFactory, LocalDateTime dateToShipment,
                 LocalDateTime dateToShipmentFactual) {
        this.idDoc = idDoc;
        this.docNumber = docNumber;
        this.client = client;
        this.manager = manager;
        this.designer = designer;
        this.durationTime = durationTime;
        this.dateCreate = dateCreate;
        this.dateToFactory = dateToFactory;
        this.dateToShipment = dateToShipment;
        this.dateToShipmentFactual = dateToShipmentFactual;
        this.descriptions = new ArrayList<>();
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
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

    public LocalDateTime getDateToShipment() {
        return dateToShipment;
    }

    public void setDateToShipment(LocalDateTime dateToShipment) {
        this.dateToShipment = dateToShipment;
    }

    public LocalDateTime getDateToShipmentFactual() {
        return dateToShipmentFactual;
    }

    public void setDateToShipmentFactual(LocalDateTime dateToShipmentFactual) {
        this.dateToShipmentFactual = dateToShipmentFactual;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }
}
