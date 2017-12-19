package de.tsa.homecosts.db;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;

import de.tsa.homecosts.entities.Expenditure;
import de.tsa.homecosts.utils.Constants;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Teguh Santoso on 25.11.2017.
 */

public class RoomDatabaseImpl implements RoomInteractor {
    private AppDatabase                     appDatabase;
    private OnRoomInteractionListener       roomInteractionListener;
    private Subscription                    mProductsSubscription;

    private void getObservableAllData() {
        Single<List<Expenditure>> getAllDataSingle = Single.fromCallable(new Callable<List<Expenditure>>() {

            @Override
            public List<Expenditure> call() throws Exception {
                return appDatabase.expenditureDao().getAll();
            }
        });

        mProductsSubscription = getAllDataSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<List<Expenditure>>() {
                    @Override
                    public void onSuccess(List<Expenditure> value) {
                        Log.d(Constants.LOGGER, ">>> Get all row(s): " + value.size());
                        roomInteractionListener.onResponse(value);
                    }

                    @Override
                    public void onError(Throwable error) {
                        // Do nothing.
                    }
                });
    }

    private void getObservableInsertData(final Expenditure data) {
        Single<Long> insertDataSingle = Single.fromCallable(new Callable<Long>() {

            @Override
            public Long call() throws Exception {
                return appDatabase.expenditureDao().insertProduct(data);
            }
        });

        mProductsSubscription = insertDataSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<Long>() {
                    @Override
                    public void onSuccess(Long value) {
                        Log.d(Constants.LOGGER, ">>> Inserted Id: " + value);
                        roomInteractionListener.insertedId(value);
                    }

                    @Override
                    public void onError(Throwable error) {
                        // DO nothing.
                    }
                });
    }

    private void getObservableDeleteData(final Expenditure data) {
        Single<Integer> deleteDataSingle = Single.fromCallable(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                return appDatabase.expenditureDao().delete(data);
            }
        });

        mProductsSubscription = deleteDataSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<Integer>() {
                    @Override
                    public void onSuccess(Integer value) {
                        Log.d(Constants.LOGGER, ">>> Delete affected row: " + value);
                        roomInteractionListener.affectedRow(value);
                    }

                    @Override
                    public void onError(Throwable error) {
                        // DO nothing.
                    }
                });
    }

    @Override
    public void getAllData(Context cTxt, OnRoomInteractionListener listener) {
        this.appDatabase = AppDatabase.getAppDatabase(cTxt);
        this.roomInteractionListener = listener;
        getObservableAllData();
    }

    @Override
    public void storeData(Context cTxt, Expenditure data, OnRoomInteractionListener listener) {
        this.appDatabase = AppDatabase.getAppDatabase(cTxt);
        this.roomInteractionListener = listener;
        getObservableInsertData(data);
    }

    @Override
    public void deleteData(Context cTxt, Expenditure data, OnRoomInteractionListener listener) {
        this.appDatabase = AppDatabase.getAppDatabase(cTxt);
        this.roomInteractionListener = listener;
        getObservableDeleteData(data);
    }

    @Override
    public void unsubscribe() {
        if (mProductsSubscription != null && !mProductsSubscription.isUnsubscribed()) {
            mProductsSubscription.unsubscribe();
        }
    }

}
