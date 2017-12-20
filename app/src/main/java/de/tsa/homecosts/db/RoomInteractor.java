package de.tsa.homecosts.db;

import android.content.Context;

import java.util.List;

import de.tsa.homecosts.entities.Expenditure;

/**
 * Created by Teguh Santoso on 25.11.2017.
 */

public interface RoomInteractor {

    interface OnRoomInteractionListener {
        void onResponse(List expenditures);
        void affectedRow(int rows);
        void insertedId(long id);
    }

    void getAllData(Context cTxt, OnRoomInteractionListener listener);

    void getAllDataByMonthAndYear(Context cTxt, int mMonth, int mYear, OnRoomInteractionListener listener);

    void storeData(Context cTxt, Expenditure expenditure, OnRoomInteractionListener listener);

    void deleteData(Context cTxt, Expenditure expenditure, OnRoomInteractionListener listener);

    void unsubscribe();

}
