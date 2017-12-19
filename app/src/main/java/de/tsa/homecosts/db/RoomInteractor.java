package de.tsa.homecosts.db;

import android.content.Context;

import java.util.List;

import de.tsa.homecosts.entities.Expenditure;

/**
 * Created by Teguh Santoso on 25.11.2017.
 */

public interface RoomInteractor {

    interface OnRoomInteractionListener {
        void onResponse(List products);
        void affectedRow(int rows);
    }

    void getAllData(Context cTxt, OnRoomInteractionListener listener);

    void storeData(Context cTxt, Expenditure expenditure, OnRoomInteractionListener listener);

    void deleteData(Context cTxt, Expenditure expenditure, OnRoomInteractionListener listener);

    void unsubscribe();

}
