package group14.multiorder.multiorderonline.Store;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Menu;
import group14.multiorder.multiorderonline.obj.Store;


/**
 * A simple {@link Fragment} subclass.
 */
public class infoFragment extends Fragment {

    private DatabaseReference _databaseRefs;


    public infoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Store post = getActivity().getIntent().getParcelableExtra("STORE");
        Log.d("System", post.getTitle() + post.getPhoneNumber());
        //Toast.makeText(getActivity(), post.getTitle(), Toast.LENGTH_SHORT).show();
        TextView address = getView().findViewById(R.id.info_address);
        address.setText("Address : " + post.getAddress());

        TextView phoneNumber = getView().findViewById(R.id.info_phonenumber);
        phoneNumber.setText("Phonenumber : " + post.getPhoneNumber());

        TextView openClose = getView().findViewById(R.id.info_open_close);
        openClose.setText("Opened - Closed : " + post.getOpenClose());
    }
}
