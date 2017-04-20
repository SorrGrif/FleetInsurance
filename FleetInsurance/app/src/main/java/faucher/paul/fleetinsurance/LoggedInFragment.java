package faucher.paul.fleetinsurance;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoggedInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoggedInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoggedInFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout claimLayout;
    private LinearLayout planLayout;
    private TextView title;

    private OnFragmentInteractionListener mListener;

    public LoggedInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoggedInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoggedInFragment newInstance(String param1, String param2) {
        LoggedInFragment fragment = new LoggedInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logged_in, container, false);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_exit_to_app_white_24dp);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        final SharedPreferences.Editor prefEdit = getActivity().getPreferences(Context.MODE_APPEND).edit();
        final SharedPreferences pref = getActivity().getPreferences(Context.MODE_APPEND);
        String userName = pref.getString("user", "nouser");
        planLayout = (LinearLayout) view.findViewById(R.id.MyPlanLayout);
        claimLayout = (LinearLayout) view.findViewById(R.id.MyClaimsLayout);
        title = (TextView) view.findViewById(R.id.TitleLabel);
        DatabaseHandler db = new DatabaseHandler(getContext());
        ArrayList<Users> userList = db.getAllUsers();
        Users user = null;
        for(int i = 0; i < userList.size(); i++)
        {
            if(userList.get(i).getId() == pref.getInt("userid", -1))
            {
                user = userList.get(i);
            }
        }
        title.setText("Welcome, " + userName);
        title.setText("Welcome, " + user.getName());




        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                prefEdit.putString("user", "nouser");
                prefEdit.putInt("userid", -1);
                prefEdit.putBoolean("loggedin", false);
                prefEdit.commit();
                ft.replace(R.id.content_main, new ProfileFragment());
                ft.commit();
            }
        });

        planLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ft.replace(R.id.content_main, new PlanInformation());
                ft.commit();
            }
        });

        claimLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ft.replace(R.id.content_main, new ClaimViewer());
                ft.commit();
            }
        });

        return view;
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
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
