package de.tsa.homecosts.presenters;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import de.tsa.homecosts.db.RoomDatabaseImpl;
import de.tsa.homecosts.db.RoomInteractor;
import de.tsa.homecosts.entities.Expenditure;

/**
 * Created by Teguh Santoso on 25.11.2017.
 */

public class MainPresenterImpl implements MainPresenter, RoomInteractor.OnRoomInteractionListener {
    private Context                 cTxt;
    private MainPresenterCallback   presenterCallback;
    private RoomInteractor          roomInteractor;

    public MainPresenterImpl(MainPresenterCallback presenterCallback) {
        this.presenterCallback = presenterCallback;
        this.roomInteractor = new RoomDatabaseImpl();
    }

    @Override
    public void setContext(Activity activity) {
        this.cTxt = activity;
    }

    @Override
    public void onDestroy() {
        roomInteractor.unsubscribe();
    }

    @Override
    public void getAllData() {
        roomInteractor.getAllData(cTxt, this);
    }

    @Override
    public void addNewExpenditure(Expenditure expenditure) {
        roomInteractor.storeData(cTxt, expenditure, this);
    }

    @Override
    public void deleteData(Expenditure expenditure) {
        roomInteractor.deleteData(cTxt, expenditure, this);
    }

    @Override
    public void onResponse(List expenditures) {
        presenterCallback.fillDataExpenditures(expenditures);
    }

    @Override
    public void affectedRow(int rows) {
        presenterCallback.showMessage("Affected row: " + rows);
    }

    @Override
    public void insertedId(long id) {
        presenterCallback.showMessage("Success added new expenditure data Id-" + id);
    }
}
