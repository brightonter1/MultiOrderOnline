package group14.multiorder.multiorderonline.Account;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import group14.multiorder.multiorderonline.MainActivity;
import group14.multiorder.multiorderonline.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {



    private ArrayList<String> option = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private String _Name;
    private String _Email;
    private TextView vName;
    private TextView vEmail;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = getActivity().findViewById(R.id.account_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        getActivity().setTitle("My Profile");
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseFirestore.getInstance();
        getProfile();
        ListOption();
        initLogout();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_bottom, menu);
    }


    private void ListOption(){
        option.add("Notification");
        option.add("Track Order");
        option.add("Past Order");
        ListView _optionList = getView().findViewById(R.id.account_list);
        ArrayAdapter<String> _optionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, option);
        _optionList.setAdapter(_optionAdapter);
    }

    private void getProfile(){
        String mUid = mAuth.getCurrentUser().getUid();
        _Email = mAuth.getCurrentUser().getEmail();
        Log.d("System", mUid);
        mDB.collection("customer")
                .document(mUid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot snapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (snapshot.exists()){
                            _Name = snapshot.getString("email");
                            setProfile();
                        }
                    }
                });
    }

    private void setProfile(){
        vName = getActivity().findViewById(R.id.account_name);
        vEmail = getActivity().findViewById(R.id.account_email);
        vName.setText(_Name);
        vEmail.setText(_Email);
    }
    private void initLogout(){
        Button btn = getActivity().findViewById(R.id.account_logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
