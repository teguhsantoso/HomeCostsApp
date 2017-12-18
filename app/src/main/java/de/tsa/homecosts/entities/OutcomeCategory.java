package de.tsa.homecosts.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Teguh Santoso on 17.12.2017.
 */
@Entity(tableName = "categories")
public class OutcomeCategory implements Serializable {

    private static final long serialVersionUID = 8101812822083445920L;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_uid")
    private int id;

    @ColumnInfo(name = "category_name")
    private String name;

    public OutcomeCategory(String name) {
        this.name = name;
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
}
