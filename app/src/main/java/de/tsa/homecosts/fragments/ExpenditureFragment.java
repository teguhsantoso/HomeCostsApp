package de.tsa.homecosts.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import de.tsa.homecosts.R;
import de.tsa.homecosts.entities.CategoryType;
import de.tsa.homecosts.entities.Expenditure;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenditureFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpenditureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenditureFragment extends Fragment {

    public interface OnFragmentInteractionListener {
        void onStoreData(Expenditure expenditure);
    }

    private static final String             ARG_PARAM_FRAGMENT_TAG = "fragmentTag";
    private View                            rootView;
    private Spinner                         spinnerCategory;
    private EditText                        editTextDescription;
    private EditText                        editTextSumMoney;
    private DatePicker                      datePickerChargeExpenditure;
    private Button                          buttonStoreData;

    private OnFragmentInteractionListener mListener;

    public ExpenditureFragment() {
        // Required empty public constructor
    }

    public static ExpenditureFragment newInstance(String param) {
        ExpenditureFragment fragment = new ExpenditureFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_expenditure, container, false);
        initSpinnerCategory(rootView);
        initEditTextDescription(rootView);
        initEditTextSumMoney(rootView);
        initDatePicker(rootView);
        initButtonStoreData(rootView);
        return rootView;
    }

    private void initEditTextSumMoney(View rootView) {
        editTextSumMoney = rootView.findViewById(R.id.editTextSumMoney);
    }

    private void initButtonStoreData(View rootView) {
        buttonStoreData = rootView.findViewById(R.id.buttonSaveExpenditure);
        buttonStoreData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    Expenditure data = new Expenditure();
                    data.setCategory(spinnerCategory.getSelectedItemPosition());
                    data.setName(editTextDescription.getText().toString().trim());
                    int dayOfMonth = datePickerChargeExpenditure.getDayOfMonth();
                    int month = datePickerChargeExpenditure.getMonth() + 1;
                    int year = datePickerChargeExpenditure.getYear();
                    String strChargeDate = String.valueOf(dayOfMonth) + "." + String.valueOf(month) + "." + String.valueOf(year);
                    data.setChargeDate(strChargeDate);
                    data.setMonth(month);
                    data.setYear(year);
                    data.setAmountPayment(Double.valueOf(editTextSumMoney.getText().toString().trim()));
                    mListener.onStoreData(data);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initDatePicker(View rootView) {
        datePickerChargeExpenditure = rootView.findViewById(R.id.datePickerExpenditure);
    }

    private void initEditTextDescription(View rootView) {
        editTextDescription = rootView.findViewById(R.id.editTextDescription);
    }

    private void initSpinnerCategory(View rootView) {
        spinnerCategory = rootView.findViewById(R.id.spinnerCategory);
        List categories = new ArrayList<String>();
        categories.add(CategoryType.INCOME.toString());
        categories.add(CategoryType.OUTCOME.toString());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(dataAdapter);
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

}
