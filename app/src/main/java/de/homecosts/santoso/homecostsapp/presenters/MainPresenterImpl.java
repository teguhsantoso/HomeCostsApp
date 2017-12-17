package de.homecosts.santoso.homecostsapp.presenters;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Teguh Santoso on 25.11.2017.
 */

public class MainPresenterImpl implements MainPresenter {
    private Context                 cTxt;
    private MainPresenterCallback   presenterCallback;

    public MainPresenterImpl(MainPresenterCallback presenterCallback) {
        this.presenterCallback = presenterCallback;
    }

    @Override
    public void setContext(Activity activity) {
        this.cTxt = activity;
    }

}
