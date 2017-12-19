package de.tsa.homecosts.presenters;

import java.util.List;

/**
 * Created by Teguh Santoso on 25.11.2017.
 */

public interface MainPresenterCallback {
    void fillDataExpenditures(List expenditures);
    void showMessage(String s);
}
