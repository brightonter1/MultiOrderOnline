package group14.multiorder.multiorderonline.Account;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import group14.multiorder.multiorderonline.BaseFragment;
import group14.multiorder.multiorderonline.MainActivity;
import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.ViewOrderedActivity;
import group14.multiorder.multiorderonline.obj.Store;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends BaseFragment {



    private ArrayList<String> option = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private String _Name;
    private String _Email;
    private TextView vName;
    private TextView vEmail;
    private Toolbar toolbar;
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
        toolbar = getActivity().findViewById(R.id.account_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_back);
        TextView title = getActivity().findViewById(R.id.title_bar);

        title.setText("My Profile");
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseFirestore.getInstance();
        getProfile();
        ListOption();
        initLogout();
        toolbar = setToolbar("My Profile");
        backBtn();
    }

    @Override
    public void backBtn(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountFragment.this.getActivity().finish();
            }
        });
    }



    private void ListOption() {
        SharedPreferences sp = getActivity().getSharedPreferences("center", Context.MODE_PRIVATE);
        String type = sp.getString("type", "not found");
        Log.d("System", "get type : " + type);

        if (type.equals("customer")) {
            option.add("Track Order");
            option.add("History Order");
        } else {
            option.add("Edit Info");
            option.add("Menu");
            option.add("View Ordered");
        }
        final ListView _optionList = getView().findViewById(R.id.account_list);
        final ArrayAdapter<String> _optionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, option);
        _optionList.setAdapter(_optionAdapter);

        _optionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (_optionAdapter.getItem(position)) {

                    case "Track Order":
                        Log.d("System", "Track Order");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new TrackOrderFragment())
                                .commit();
                        break;
                    case "History Order":
                        Log.d("System", "History Order");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new OrderHistory())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case "Edit Info":
                        Log.d("System", "Edit info");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new EditInfoFragment()) // chang back to edit info duay
                                .addToBackStack(null)
                                .commit();
                        break;
                    case "Menu":
                        Log.d("System", "Menu");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new MenuFragment())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case "View Ordered":
                        Intent intent = new Intent(getActivity() ,ViewOrderedActivity.class);
                        startActivity(intent);
                        Log.d("System", "Edit Menu");
                        break;
                }
            }
        });


    }

    String name;
    private void getProfile(){
        final String mUid = mAuth.getCurrentUser().getUid();
        _Email = mAuth.getCurrentUser().getEmail();
        final ImageView _img = getView().findViewById(R.id.fragment_account_image);
        DatabaseReference _dataRef = FirebaseDatabase.getInstance().getReference("Store/");
        _dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dat : dataSnapshot.getChildren()){
                    Store myStore  = dat.getValue(Store.class);
                    if(myStore.getUser_id().equals(mAuth.getCurrentUser().getUid())){
                        name = myStore.getTitle();
                        Log.d("System", "title " + name);
                        Picasso.with(getContext())
                                .load(myStore.getImage())
                                .fit()
                                .centerCrop()
                                .into(_img);
                    }
                }
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void setProfile(){
        vEmail = getActivity().findViewById(R.id.account_email);
        vEmail.setText(_Email);
    }
    private void initLogout(){
        Button btn = getActivity().findViewById(R.id.account_logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("center", Context.MODE_PRIVATE);
                sp.edit().putString("type", "not found").apply();
                mAuth.signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
}
