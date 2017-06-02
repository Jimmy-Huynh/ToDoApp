package com.tvnsoftware.simpleapp.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by TamHH on 6/1/2017.
 */
@DatabaseTable(tableName = "tasks")
public class Task implements Serializable{
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_NAME = "name";
    public static final String FIELD_NAME_NOTE = "note";
    public static final String FIELD_NAME_PRIORITY = "priority";
    public static final String FIELD_NAME_STATUS = "status";

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;

    @DatabaseField(columnName = FIELD_NAME_NAME)
    private String name;

    @DatabaseField(columnName = FIELD_NAME_NOTE)
    private String note;

    @DatabaseField(columnName = FIELD_NAME_PRIORITY)
    private String priority;

    @DatabaseField(columnName = FIELD_NAME_STATUS)
    private String status;

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
