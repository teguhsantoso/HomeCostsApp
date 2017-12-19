package de.tsa.homecosts;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

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
    private MainPresenter           mPresenter;
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

        // Set bottom navigation action bar.
        BottomNavigationView navigation = findViewById(R.id.navigation);
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
        super.onBackPressed();
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
    public void onFragmentInteraction(Uri uri) {
        // TODO
        // Add implementation here.
    }

    @Override
    public void showExpenditures(List expenditures) {
        // TODO
        // Add implementation here.
    }

    @Override
    public void fillDataExpenditures(List expenditures) {
        fragmentHome.updateListData(expenditures);
    }

    @Override
    public void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onExpenditureItemClicked(Expenditure expenditure) {
        // TODO
        // Add implementation here.
    }

    @Override
    public void onDeleteData(Expenditure expenditure) {
        mPresenter.deleteData(expenditure);
    }

    @Override
    public void onStoreData(Expenditure expenditure) {
        mPresenter.addNewExpenditure(expenditure);
    }

    private void refreshListData(){
        mPresenter.getAllData();
    }

}
