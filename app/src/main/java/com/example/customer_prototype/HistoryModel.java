package com.example.customer_prototype;

public class HistoryModel {
    String Details, Issue, status, id;


    public HistoryModel(String details, String issue, String status, String id) {
        Details = details;
        Issue = issue;
        this.status = status;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getIssue() {
        return Issue;
    }

    public void setIssue(String issue) {
        Issue = issue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
