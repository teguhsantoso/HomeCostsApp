package de.tsa.homecosts.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import de.tsa.homecosts.entities.Expenditure;
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param Parameter fragment tag.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        List data = new ArrayList<Expenditure>();
        Expenditure data1 = new Expenditure();
        data1.setName("Volksbank Wohnung Ratenzahlung");
        data1.setCategory(0);
        data1.setChargeDate("01.12.2017");
        data1.setMonth(11);
        data1.setYear(2017);
        data1.setAmountPayment(340.42);

        Expenditure data2 = new Expenditure();
        data2.setName("ALLIANZ Rechtschutz");
        data2.setCategory(0);
        data2.setChargeDate("03.12.2017");
        data2.setMonth(11);
        data2.setYear(2017);
        data2.setAmountPayment(25.00);

        data.add(data1);
        data.add(data2);

        this.mAdapter = new ExpenditureItemAdapter(getActivity(), data);
        this.mRecyclerView.setAdapter(mAdapter);
        this.mAdapter.notifyDataSetChanged();
    }

    private void initButtonRefreshList(View rootView) {
        buttonRefreshListExpenditures = rootView.findViewById(R.id.imageButtonRefresh);
        buttonRefreshListExpenditures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
