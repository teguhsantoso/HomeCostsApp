package de.tsa.homecosts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.List;

import de.tsa.homecosts.entities.Expenditure;
import de.tsa.homecosts.fragments.ExpenditureFragment;
import de.tsa.homecosts.fragments.HomeFragment;
import de.tsa.homecosts.fragments.ReportFragment;
import de.tsa.homecosts.presenters.MainPresenter;
import de.tsa.homecosts.presenters.MainPresenterCallback;
import de.tsa.homecosts.presenters.MainPresenterImpl;
import de.tsa.homecosts.utils.Constants;
import de.tsa.homecosts.utils.ExpenditureItemAdapter;

public class MainActivity extends AppCompatActivity implements MainPresenterCallback, ExpenditureItemAdapter.OnAdapterInteractionListener, HomeFragment.OnFragmentInteractionListener, ExpenditureFragment.OnFragmentInteractionListener, ReportFragment.OnFragmentInteractionListener{
    private Context                 cTxt;
    private MainPresenter           mPresenter;
    private BottomNavigationView    navigation;
    private HomeFragment            fragmentHome;
    private ExpenditureFragment     fragmentExpenditure;
    private ReportFragment          fragmentReport;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragmentHome, Constants.TAG_FRAGMENT_HOME);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    refreshListData();
                    return true;
                case R.id.navigation_dashboard:
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(Constants.PARAM_KEY_RESET_FIELDS, Boolean.TRUE);
                    fragmentExpenditure.setArguments(bundle);
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragmentExpenditure, Constants.TAG_FRAGMENT_DASHBOARD);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                case R.id.navigation_report:
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragmentReport, Constants.TAG_FRAGMENT_REPORT);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cTxt = this;

        // Set bottom navigation action bar.
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Set reference to the main presenter and its callback.
        mPresenter = new MainPresenterImpl(this);
        mPresenter.setContext(this);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create all fragments to be placed in the activity layout
            fragmentHome = HomeFragment.newInstance(Constants.TAG_FRAGMENT_HOME);
            fragmentExpenditure = ExpenditureFragment.newInstance(Constants.TAG_FRAGMENT_DASHBOARD);
            fragmentReport = ReportFragment.newInstance(Constants.TAG_FRAGMENT_REPORT);

            // Add the default fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragmentHome).commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListData();
    }

    @Override
    public void onBackPressed() {
        // Do nothing.
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void fillDataExpenditures(List expenditures) {
        fragmentHome.updateListData(expenditures);
    }

    @Override
    public void showMessage(String s) {
        // TODO
        // Show snackbar containing the message.

        // Refresh the list of expenditures.
        refreshListData();
    }

    @Override
    public void onExpenditureItemClicked(Expenditure expenditure) {
        // TODO
        // Add implementation here.
    }

    @Override
    public void onDeleteData(Expenditure expenditure) {
        showAlertDialog(expenditure);
    }

    private void showAlertDialog(final Expenditure expenditure) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cTxt);
        alertDialogBuilder.setTitle("App System Dialog");
        alertDialogBuilder
                .setMessage("Diese Daten wirklich löschen?")
                .setCancelable(false)
                .setPositiveButton("Ja",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        mPresenter.deleteData(expenditure);
                    }
                })
                .setNegativeButton("Nein",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        // Create alert dialog instance.
        AlertDialog alertDialog = alertDialogBuilder.create();

        // Show the alert dialog.
        alertDialog.show();
    }

    @Override
    public void onStoreData(Expenditure expenditure) {
        mPresenter.addNewExpenditure(expenditure);
    }

    private void refreshListData(){
        mPresenter.getAllData();
    }

    @Override
    public void onSearchData(int mMonth, int mYear) {
        mPresenter.getAllDataByParams(mMonth, mYear);
    }

    @Override
    public void onCalculateBalancePeriode(int mMonth, int mYear) {

    }
}
