package com.qfree.qfree_customer.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Fahad Qureshi on 10/1/2017.
 */

public class Customer implements Serializable {

    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("queueNumber")
    private Long queueNumber;
    @SerializedName("isInQueue")
    private Boolean isInQueue;
    @SerializedName("queue")
    private String queueId;
    @SerializedName("isDummy")
    private Boolean isDummy;

    public Customer() {
    }

    public Customer(String id, String name, Long queueNumber, Boolean isInQueue, String queueId, Boolean isDummy) {
        this.id = id;
        this.name = name;
        this.queueNumber = queueNumber;
        this.isInQueue = isInQueue;
        this.queueId = queueId;
        this.isDummy = isDummy;
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

    public Long getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(Long queueNumber) {
        this.queueNumber = queueNumber;
    }

    public Boolean getInQueue() {
        return isInQueue;
    }

    public void setInQueue(Boolean inQueue) {
        isInQueue = inQueue;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public Boolean getDummy() {
        return isDummy;
    }

    public void setDummy(Boolean dummy) {
        isDummy = dummy;
    }

    public void copy(Customer customer) {
        if (customer != null) {
            this.id = customer.getId();
            this.name = customer.getName();
            this.queueNumber = customer.queueNumber;
            this.isInQueue = customer.getInQueue();
            this.queueId = customer.getQueueId();
            this.isDummy = customer.getDummy();
        }
    }
}
