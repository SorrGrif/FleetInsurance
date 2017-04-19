package faucher.paul.fleetinsurance;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoggedOutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoggedOutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoggedOutFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText email;
    private EditText password;
    private EditText retypePassword;
    private EditText firstName;
    private EditText lastName;
    private EditText addressValue;
    private EditText phoneNumValue;
    private FloatingActionButton fab;
    private LinearLayout passwordLayout;
    private LinearLayout retypePasswordLayout;
    private LinearLayout nameLayout;
    private LinearLayout genderAgeLayout;
    private boolean newAccount = true;
    private boolean emailIsValid = false;
    private boolean passwordsMatch = false;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private String name;
    private String address;
    private String phoneNum;

    private OnFragmentInteractionListener mListener;

    public LoggedOutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoggedOutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoggedOutFragment newInstance(String param1, String param2) {
        LoggedOutFragment fragment = new LoggedOutFragment();
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
        View view = inflater.inflate(R.layout.fragment_logged_out, container, false);

        //initialize all the items in the layout
        email = (EditText) view.findViewById(R.id.EmailValue);
        password = (EditText) view.findViewById(R.id.PasswordValue);
        retypePassword = (EditText) view.findViewById(R.id.RetypePasswordValue);
        firstName = (EditText) view.findViewById(R.id.FirstNameValue);
        lastName = (EditText) view.findViewById(R.id.LastNameValue);
        addressValue = (EditText) view.findViewById(R.id.AddressValue);
        phoneNumValue = (EditText) view.findViewById(R.id.PhoneNumValue);
        passwordLayout = (LinearLayout) view.findViewById(R.id.PasswordLayout);
        retypePasswordLayout = (LinearLayout) view.findViewById(R.id.RetypePasswordLayout);
        nameLayout = (LinearLayout) view.findViewById(R.id.FirstNameLastNameLayout);
        genderAgeLayout = (LinearLayout) view.findViewById(R.id.GenderAgeLayout);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fm = getActivity().getSupportFragmentManager();
        ft = fm.beginTransaction();
        final SharedPreferences.Editor prefEdit = getActivity().getPreferences(Context.MODE_APPEND).edit();

        //animations used to have the password and retype password fields fly onscreen smoothly
        final Animation passwordSlideInFromRight = AnimationUtils.loadAnimation(getContext(), R.anim.slideinfromright);
        final Animation retypePasswordSlideInFromRight = AnimationUtils.loadAnimation(getContext(), R.anim.slideinfromright);
        final Animation nameSlideInFromRight = AnimationUtils.loadAnimation(getContext(), R.anim.slideinfromright);
        final Animation genderAgeSlideInFromRight = AnimationUtils.loadAnimation(getContext(), R.anim.slideinfromright);
        passwordSlideInFromRight.setDuration(500);
        retypePasswordSlideInFromRight.setDuration(500);
        nameSlideInFromRight.setDuration(500);
        genderAgeSlideInFromRight.setDuration(500);

        //when the text is changed in the email field the color will change according
        //to if the email address is valid or not
        email.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                validateEmails();
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        password.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                validatePasswords();
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
        retypePassword.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                validatePasswords();
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        //set the image of the FAB button to the next button
        fab.setImageResource(R.drawable.ic_send_white_24dp);
        //many different things will happen when the FAB button is clicked
        //first if the email field is shown and the email is a valid account email
        //it will show the password field, however if the email is a new user it will show
        //both the password AND retype password field
        //once the field is shown the button will skip to the next field to be entered or it will
        //turn into a send button and submit your login information
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DatabaseHandler db = new DatabaseHandler(getContext());
                ArrayList<Users> userList = db.getAllUsers();

                if (passwordLayout.getVisibility() != View.VISIBLE && emailIsValid)
                    {

                        passwordLayout.setVisibility(View.VISIBLE);
                        passwordLayout.setAnimation(passwordSlideInFromRight);
                        passwordLayout.animate();
                        passwordSlideInFromRight.start();


                        for(int i = 0; i < userList.size(); i++)
                        {
                            Log.wtf("user", userList.get(i).getName());
                            if(userList.get(i).getName().equals(email.getText().toString()))
                            {
                                prefEdit.putInt("userid", i + 1);
                                newAccount = false;
                            }
                        }

                        if (newAccount)
                        {
                            retypePasswordLayout.setVisibility(View.VISIBLE);
                            retypePasswordLayout.setAnimation(retypePasswordSlideInFromRight);
                            retypePasswordLayout.animate();
                            retypePasswordSlideInFromRight.setStartOffset(passwordSlideInFromRight.getDuration() * 1);
                            retypePasswordSlideInFromRight.start();

                            nameLayout.setVisibility(View.VISIBLE);
                            nameLayout.setAnimation(nameSlideInFromRight);
                            nameLayout.animate();
                            nameSlideInFromRight.setStartOffset(passwordSlideInFromRight.getDuration() * 2);
                            nameSlideInFromRight.start();

                            genderAgeLayout.setVisibility(View.VISIBLE);
                            genderAgeLayout.setAnimation(genderAgeSlideInFromRight);
                            genderAgeLayout.animate();
                            genderAgeSlideInFromRight.setStartOffset(passwordSlideInFromRight.getDuration() * 3);
                            genderAgeSlideInFromRight.start();
                        }
                    }

                    //if the user is making a new account
                    if(newAccount && fieldsFull())
                    {
                        name = firstName.getText().toString() + " " + lastName.getText().toString();
                        address = addressValue.getText().toString();
                        phoneNum = phoneNumValue.getText().toString();
                        db.addUser(new Users(name, address, phoneNum, ""));
                        prefEdit.putString("user", email.getText().toString());
                        prefEdit.putBoolean("loggedin", true);
                        prefEdit.putInt("userid", userList.size() + 1);
                        prefEdit.commit();
                        ft.replace(R.id.content_main, new ProfileFragment());
                        ft.commit();
                    }
                    else if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty())
                    {
                        prefEdit.putString("user", email.getText().toString());
                        prefEdit.putBoolean("loggedin", true);

                        prefEdit.commit();
                        ft.replace(R.id.content_main, new ProfileFragment());
                        ft.commit();
                    }
            }
        });


        return view;
    }

    /**
     * This method is used to check if the current email
     * address entered into the field is valid or not
     * @return valid email address
     */
    private void validateEmails()
    {
        //put the current string of the email field into a string named email
        String email = this.email.getText().toString();

        //check if the email stirng contains @ and .
        //if it does set email is valid to true and textcolor to green
        //otherwise set it to red and set emailisvalid to false
        if(!email.isEmpty())
        {
            //I used RGB instead of Color.GREEN because the default green
            //literally hurt my eyes when i first saw it
            this.email.setTextColor(Color.rgb(0,230,0));
            emailIsValid = true;
        }
        else
        {
            this.email.setTextColor(Color.RED);
            emailIsValid = false;
        }
    }

    private void validatePasswords()
    {
        String password = this.password.getText().toString();
        String retypePassword = this.retypePassword.getText().toString();

        if(password.equals(retypePassword))
        {
            //I used RGB instead of Color.GREEN because the default green
            //literally hurt my eyes when i first saw it
            this.password.setTextColor(Color.rgb(0,230,0));
            this.retypePassword.setTextColor(Color.rgb(0,230,0));
            passwordsMatch = true;
        }
        else
        {
            this.password.setTextColor(Color.RED);
            this.retypePassword.setTextColor(Color.RED);
            passwordsMatch = false;
        }
    }

    private boolean fieldsFull()
    {
        String user = email.getText().toString();
        String password1 = password.getText().toString();
        String password2 = retypePassword.getText().toString();
        String fName = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String address = addressValue.getText().toString();
        String phoneNum = phoneNumValue.getText().toString();

        if(!user.isEmpty() && !password1.isEmpty() && !password2.isEmpty() && !fName.isEmpty() && !lName.isEmpty() && !address.isEmpty() && !phoneNum.isEmpty())
            return true;
        else
            return false;

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
