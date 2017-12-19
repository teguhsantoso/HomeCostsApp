package de.tsa.homecosts.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import de.tsa.homecosts.entities.Expenditure;

/**
 * Created by Teguh Santoso on 20.11.2017.
 */
@Dao
public interface ExpenditureDAO {

    @Query("SELECT * FROM expenditures")
    List<Expenditure> getAll();

    @Insert
    int insertProduct(Expenditure expenditure);

    @Delete
    int delete(Expenditure expenditure);

}
