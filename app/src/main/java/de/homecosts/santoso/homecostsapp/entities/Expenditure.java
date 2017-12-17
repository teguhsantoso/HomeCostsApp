package de.homecosts.santoso.homecostsapp.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Teguh Santoso on 17.12.2017.
 */
@Entity(tableName = "expenditures")
public class Expenditure implements Serializable{

    private static final long serialVersionUID = -8043104348208425724L;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "expenditure_uid")
    private int uid;

    @ColumnInfo(name = "expenditure_name")
    private String name;

    @ColumnInfo(name = "charge_date")
    private String chargeDate;

    @Embedded
    OutcomeCategory category;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(String chargeDate) {
        this.chargeDate = chargeDate;
    }

    public OutcomeCategory getCategory() {
        return category;
    }

    public void setCategory(OutcomeCategory category) {
        this.category = category;
    }
}
