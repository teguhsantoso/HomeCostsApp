package de.tsa.homecosts.entities;

import android.arch.persistence.room.ColumnInfo;
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

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "month")
    private int month;

    @ColumnInfo(name = "amount")
    private double amountPayment;

    @ColumnInfo(name = "category")
    private int category;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getAmountPayment() {
        return amountPayment;
    }

    public void setAmountPayment(double amountPayment) {
        this.amountPayment = amountPayment;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
