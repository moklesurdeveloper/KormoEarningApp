package com.bdearning.group.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fees")
    @Expose
    private String fees;
    @SerializedName("earn_amount")
    @Expose
    private String earnAmount;
    @SerializedName("total_work")
    @Expose
    private String totalWork;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String description;
    @SerializedName("description")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getEarnAmount() {
        return earnAmount;
    }

    public void setEarnAmount(String earnAmount) {
        this.earnAmount = earnAmount;
    }

    public String getTotalWork() {
        return totalWork;
    }

    public void setTotalWork(String totalWork) {
        this.totalWork = totalWork;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

