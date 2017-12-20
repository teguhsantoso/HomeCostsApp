package de.tsa.homecosts.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.tsa.homecosts.R;
import de.tsa.homecosts.utils.Constants;
import de.tsa.homecosts.utils.ExpenditureItemAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    public interface OnFragmentInteractionListener {
        void onSearchData(int mMonth, int mYear);
    }

    private static final String             ARG_PARAM_FRAGMENT_TAG = "fragmentTag";
    private View                            rootView;
    private OnFragmentInteractionListener   mListener;
    private Spinner                         spinnerYear;
    private Spinner                         spinnerMonth;
    private ImageButton                     buttonRefreshListExpenditures;
    private ExpenditureItemAdapter          mAdapter;
    private RecyclerView                    mRecyclerView;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_FRAGMENT_TAG, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Do nothing.
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initSpinnerYear(rootView);
        initSpinnerMonth(rootView);
        initButtonRefreshList(rootView);
        initRecyclerView(rootView);
        return rootView;
    }

    private void initRecyclerView(View rootView) {
        this.mRecyclerView = rootView.findViewById(R.id.expenditures);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initButtonRefreshList(View rootView) {
        buttonRefreshListExpenditures = rootView.findViewById(R.id.imageButtonRefresh);
        buttonRefreshListExpenditures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    int mMonth = (int) spinnerMonth.getSelectedItemPosition();
                    int mYear = (int) spinnerYear.getSelectedItem();
                    mListener.onSearchData((mMonth+1), mYear);
                }
            }
        });
    }

    private void initSpinnerMonth(View rootView) {
        spinnerMonth = rootView.findViewById(R.id.spinnerMonth);
        List months = new ArrayList<String>();
        for(int i = Constants.START_INDEX; i < Constants.END_INDEX_MONTH; i++){
            String strMonth = new DateFormatSymbols().getMonths()[i];
            months.add(strMonth);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, months);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(dataAdapter);
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        spinnerMonth.setSelection(currentMonth);
    }

    private void initSpinnerYear(View rootView) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        spinnerYear = rootView.findViewById(R.id.spinnerYear);
        List years = new ArrayList<String>();
        for(int i = Constants.START_INDEX; i < Constants.END_INDEX_YEAR; i++){
            years.add(year+i);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(dataAdapter);
        spinnerYear.setSelection(((ArrayAdapter<String>)spinnerYear.getAdapter()).getPosition(String.valueOf(year)));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public void updateListData(List expenditures) {
        this.mAdapter = new ExpenditureItemAdapter(getActivity(), expenditures);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mAdapter.notifyDataSetChanged();
    }

}
