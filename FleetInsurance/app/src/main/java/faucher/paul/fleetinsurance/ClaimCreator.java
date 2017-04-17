package faucher.paul.fleetinsurance;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClaimCreator.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClaimCreator#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClaimCreator extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText date;
    EditText time;
    EditText firstName;
    EditText lastName;
    EditText description;
    ImageView dateButton;
    ImageView timeButton;
    ImageView cameraButton;


    private OnFragmentInteractionListener mListener;

    public ClaimCreator() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClaimCreator.
     */
    // TODO: Rename and change types and number of parameters
    public static ClaimCreator newInstance(String param1, String param2) {
        ClaimCreator fragment = new ClaimCreator();
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
        View view = inflater.inflate(R.layout.fragment_claim_creation, container, false);

        //all the declarations and initializations done here
        date = (EditText) view.findViewById(R.id.DateValue);
        time = (EditText) view.findViewById(R.id.TimeValue);
        firstName = (EditText) view.findViewById(R.id.FirstNameValue);
        lastName = (EditText) view.findViewById(R.id.LastNameValue);
        description = (EditText) view.findViewById(R.id.DescriptionValue);
        dateButton = (ImageView) view.findViewById(R.id.CalendarButton);
        timeButton = (ImageView) view.findViewById(R.id.ClockButton);
        cameraButton = (ImageView) view.findViewById(R.id.CameraButton);
        //this calendar is used to get the current time and day
        final Calendar calendar = Calendar.getInstance();

        //when the layout first loads set the text of the date field to the current date
        date.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.YEAR));
        //when the user clicks on the calendar button a calendar will pop up so they may choose a date
        dateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //a date picker dialog is the dialog that pops up so the user
                //may choose a date, each one of the fields is needed when creating a new datepicker object
                DatePickerDialog datePicker = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener()
                        {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday)
                            {
                                //when the date is set the date edittext field will be set to the chosen date
                                date.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);
                            }
                        },
                        //these fields are used to set the default date shown when the dialog pops up
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                //this sets the title of the dialog when it pops up
                datePicker.setTitle("Select date");
                //this shows the dialog
                datePicker.show();
            }
        });

        //when the layout first loads set the text of the time field to the current time
        time.setText(convertTime(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE)));
        //when the user clicks on the clock button a clock will pop up so they may choose a time
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //a time picker dialog is the dialog that pops up so the user
                //may choose a time, each one of the fields is needed when creating a new timepicker object
                TimePickerDialog timePicker = new TimePickerDialog(
                        getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hour, int minute)
                            {
                                //when the time is set the time edittext field will be set to the chosen time
                                //the converTime method converts the time which is given in military 24 hour time
                                //to a string with AM PM format
                                time.setText(convertTime(hour,minute));
                            }
                        },
                        //these fields are used to set the default time shown when the dialog pops up
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false
                );
                //this sets the title of the dialog when it pops up
                timePicker.setTitle("Select Time");
                //this shows the dialog
                timePicker.show();
            }
        });


        return view;
    }

    /**
     * This method will convert the given military time to a string
     * with 12 our AM PM format
     * @param hour
     * @param minute
     * @return
     */
    private String convertTime(int hour, int minute)
    {
        //set the default state to AM
        String hourState = "AM";

        //change the state of the hour to PM if it is past noon
        //eg 12-23 hours
        if(hour >= 12)
        {
            hourState = "PM";
        }

        //if the hour is 13-23 subtracting 12 will give the correct hour
        //if the hour is 0 that is techincally midnight so subtract 12 aswell,
        //however that would be -12, with Math ABS (absolute) it takes the positive value
        //of the number
        if(hour > 12 || hour == 0)
        {

            hour = Math.abs(hour - 12);
        }

        //return the converted time in hour:minute AM||PM format
        return hour + ":" + minute + " " + hourState;
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
