package faucher.paul.fleetinsurance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClaimViewer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClaimViewer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClaimViewer extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView list;

    private OnFragmentInteractionListener mListener;

    public ClaimViewer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClaimViewer.
     */
    // TODO: Rename and change types and number of parameters
    public static ClaimViewer newInstance(String param1, String param2) {
        ClaimViewer fragment = new ClaimViewer();
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
        View view = inflater.inflate(R.layout.fragment_claim_viewer, container, false);

        list = (ListView) view.findViewById(R.id.ClaimsListView);
        DatabaseHandler db = new DatabaseHandler(getContext());
        final ArrayList<Claims> claimsList = db.getAllClaims();
        db.closeDB();
        final CustomAdapter adapter = new CustomAdapter(getContext(), claimsList);
        list.setAdapter(adapter);



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

    public class CustomAdapter extends ArrayAdapter<Claims>
    {

        public CustomAdapter(Context context, ArrayList<Claims> items) {
            super(context, 0, items);
        }

        /**
         * getView is used to take every item in a list
         * and assign a view to it.
         * With this specific adapter we specified item_view as the view
         * we want every item in a list to look like.
         * After that item has item_view attached to it
         * we populate the item_view's name TextView
         */
        public View getView(int position, View convertView, ViewGroup parent){
            final Claims item = getItem(position);

            if(convertView == null){
                convertView =
                        LayoutInflater.from(getContext()).inflate(
                                R.layout.claim_view, parent, false);
            }

            //Grab the gallery layout associated with this location
            //galleryLayout =
              //      (LinearLayout) convertView.findViewById(R.id.galleryLayout);
            //Make the gallery layout invisible
            //galleryLayout.setVisibility(View.GONE);
            //only add items to the gallery if the gallery is empty
//            if(galleryLayout.getChildCount() == 0){
//                //Grab all the photos that match the id of the current location
//                DatabaseHandler db = new DatabaseHandler(getContext());
//                //ArrayList<Picture> pics = db.getAllPictures(item.getId());
//                db.closeDB();
//                //Add those photos to the gallery
//                for(int i =0; i < pics.size(); i++){
//                    Bitmap image = BitmapFactory.decodeFile(pics.get(i).getResource());
//                    ImageView imageView = new ImageView(getContext());
//                    imageView.setImageBitmap(image);
//                    imageView.setAdjustViewBounds(true);
//                    galleryLayout.addView(imageView);
//                }
//            }

            TextView description =
                    (TextView) convertView.findViewById(R.id.DescriptionLabel);
            description.setText(
                    ((Claims) list.getItemAtPosition(position)).getDesc()
            );

            TextView name = (TextView) convertView.findViewById(R.id.ClaimNameLabel);
            name.setText(item.getClaimName());

            return  convertView;
        }



    }
}
