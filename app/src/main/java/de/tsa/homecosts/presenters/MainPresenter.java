package de.tsa.homecosts.presenters;

import android.app.Activity;

/**
 * Created by Teguh Santoso on 25.11.2017.
 */

public interface MainPresenter {
    void setContext(Activity activity);
    void getAllData();
}
