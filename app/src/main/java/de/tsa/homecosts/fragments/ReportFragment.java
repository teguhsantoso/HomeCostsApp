package de.tsa.homecosts.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.tsa.homecosts.R;
import de.tsa.homecosts.entities.Expenditure;
import de.tsa.homecosts.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {

    public interface OnFragmentInteractionListener {
        void onCalculateBalancePeriode(int mMonth, int mYear);
    }

    private static final String             ARG_PARAM_FRAGMENT_TAG = "fragmentTag";
    private View                            rootView;
    private OnFragmentInteractionListener   mListener;
    private Spinner                         spinnerYear;
    private Spinner                         spinnerMonth;
    private TextView                        textViewIncome;
    private TextView                        textViewOutcome;
    private TextView                        textViewBalance;
    private ImageButton                     buttonCalculateBalance;

    public ReportFragment() {
        // Required empty public constructor
    }

    public static ReportFragment newInstance(String param) {
        ReportFragment fragment = new ReportFragment();
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
        rootView = inflater.inflate(R.layout.fragment_report, container, false);
        initSpinnerYear(rootView);
        initSpinnerMonth(rootView);
        iniAllTextViews(rootView);
        initButtonCalculateBalance(rootView);
        return rootView;
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

    private void iniAllTextViews(View rootView) {
        textViewIncome = rootView.findViewById(R.id.textViewSumIncome);
        textViewOutcome = rootView.findViewById(R.id.textViewSumOutcome);
        textViewBalance = rootView.findViewById(R.id.textViewSumBalance);
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

    private void initButtonCalculateBalance(View rootView) {
        buttonCalculateBalance = rootView.findViewById(R.id.imageButtonCalculateBalance);
        buttonCalculateBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    int mMonth = (int) spinnerMonth.getSelectedItemPosition();
                    int mYear = (int) spinnerYear.getSelectedItem();
                    mListener.onCalculateBalancePeriode((mMonth+1), mYear);
                }
            }
        });
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public void updateBalanceData(List expenditures) {
        List<Expenditure> mList = expenditures;

        double sumOfIncome = 0.0;
        for (Expenditure data : mList) {
            if(data.getCategory() == 0){
                sumOfIncome = sumOfIncome + data.getAmountPayment();
            }
        }

        double sumOfOutcome = 0.0;
        for (Expenditure data : mList) {
            if(data.getCategory() == 1){
                sumOfOutcome = sumOfOutcome + data.getAmountPayment();
            }
        }

        double balanceMonth = sumOfIncome - sumOfOutcome;

        textViewIncome.setText(sumOfIncome + " EUR");
        textViewOutcome.setText(sumOfOutcome + " EUR");
        textViewBalance.setTextColor((balanceMonth < 0.0) ? Color.RED : Color.BLUE);
        textViewBalance.setText(balanceMonth + " EUR");
    }

}
