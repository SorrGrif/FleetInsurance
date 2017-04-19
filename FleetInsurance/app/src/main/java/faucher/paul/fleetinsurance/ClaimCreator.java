package faucher.paul.fleetinsurance;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


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
    private String stringDate;
    private String imageLocation;
    private EditText date;
    private EditText time;
    private EditText firstName;
    private EditText lastName;
    private EditText title;
    private EditText description;
    private ImageView dateButton;
    private ImageView timeButton;
    private ImageView cameraButton;
    private LinearLayout imageLayout;
    private FloatingActionButton fab;
    private File picture = null;


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
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_claim_creation, container, false);

        //all the declarations and initializations done here
        date = (EditText) view.findViewById(R.id.DateValue);
        time = (EditText) view.findViewById(R.id.TimeValue);
        firstName = (EditText) view.findViewById(R.id.FirstNameValue);
        lastName = (EditText) view.findViewById(R.id.LastNameValue);
        description = (EditText) view.findViewById(R.id.DescriptionValue);
        title = (EditText) view.findViewById(R.id.TitleValue);
        dateButton = (ImageView) view.findViewById(R.id.CalendarButton);
        timeButton = (ImageView) view.findViewById(R.id.ClockButton);
        cameraButton = (ImageView) view.findViewById(R.id.CameraButton);
        imageLayout = (LinearLayout) view.findViewById(R.id.ImageLayout);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);


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

                                stringDate = selectedday + "/" + selectedmonth + "/" + selectedyear;
                                date.setText(stringDate);
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
        time.setText(convertTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
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

        //when the camera button is pressed it will launch the camera where to user
        //can take a picture of the damage to his or her car and upload it to the claim
        cameraButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                boolean hasPermission = true;
                //these if statements are required to ask for required permsision to read write and use the camera
                //this one is for the write external storage permission
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    hasPermission = false;
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }

                //this one is for the read external storage permission
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    hasPermission = false;
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }

                //this one is for the camera permission
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    hasPermission = false;
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 0);
                }

                if(hasPermission)
                {
                    try
                    {
                        //init picture object with the create image method
                        picture = createImage();
                    } catch (IOException e)
                    {
                        //if theres an error print it to the console
                        e.printStackTrace();
                    }

                    //create a new intent which will be used to store the image
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                    {
                        startActivityForResult(intent, 1);
                    }
                }

            }
        });

        fab.setImageResource(R.drawable.ic_done_white_24dp);
        //the fab will be used to add the claim to the database
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(getContext());
                if(picture == null)
                {
                    db.addClaim(new Claims(title.getText().toString(), stringDate, description.getText().toString(), ""));
                }
                else
                {
                    db.addClaim(new Claims(title.getText().toString(), stringDate, description.getText().toString(), picture.getPath()));

                }
                picture = null;
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content_main, new ClaimViewer());
                ft.commit();

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

    /**
     * This method is used to capture and create a new image
     * @return picture
     * @throws IOException
     */
    File createImage() throws IOException{
        //Create a timestamp to help create a collision free name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHss").format(new Date());
        //Create the name of the image
        String fileName = "claim_" + timeStamp;

        //Grab the directory we want to save the image
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //Create the image in that directory
        File picture = File.createTempFile(fileName, ".jpg", directory);

        //Save the location of the image
        imageLocation = picture.getAbsolutePath();

        return picture;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if the activity result is the camera create a new image view add the image to it
        //and add the imageview to the imagelayout which will display on the bottom of the fragment
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap image = BitmapFactory.decodeFile(imageLocation);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageBitmap(image);
            imageView.setPadding(0,0,15,0);
            imageLayout.addView(imageView);
            /**
             * Add the photo to the database
             */
//            DatabaseHandler db = new DatabaseHandler(getContext());
//            int picID = db.addPicture(new Picture(imageLocation));
//            if(picID != -1){
//                Location location = (Location) spin.getSelectedItem();
//                db.addImageLocation(picID, location.getId());
//                Toast.makeText(getActivity(), "Photo Added",
//                        Toast.LENGTH_LONG).show();
//            }
//            else{
//                Toast.makeText(getActivity(), "Photo Not Added",
//                        Toast.LENGTH_LONG).show();
//            }
//            db.closeDB();
        }
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
