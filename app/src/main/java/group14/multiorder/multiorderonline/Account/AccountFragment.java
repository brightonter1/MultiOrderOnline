package group14.multiorder.multiorderonline.Account;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
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

import group14.multiorder.multiorderonline.BaseFragment;
import group14.multiorder.multiorderonline.MainActivity;
import group14.multiorder.multiorderonline.OrderHistory;
import group14.multiorder.multiorderonline.R;

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
            option.add("Notification");
            option.add("Track Order");
            option.add("History Order");
        } else {
            option.add("Info");
            option.add("Menu");
            option.add("Delete Store");
        }
        final ListView _optionList = getView().findViewById(R.id.account_list);
        final ArrayAdapter<String> _optionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, option);
        _optionList.setAdapter(_optionAdapter);

        _optionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (_optionAdapter.getItem(position)) {
                    case "Notification":
                        Log.d("System", "Notification");
                        break;
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
                    case "Info":
                        Log.d("System", "Edit info");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new OrderHistory()) // chang back to edit info duay
                                .addToBackStack(null)
                                .commit();
                        break;
                    case "Menu":
                        Log.d("System", "Add Menu");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new MenuFragment())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case "Delete Store":
                        Log.d("System", "Edit Menu");
                        break;
                }
            }
        });


    }

    private void getProfile(){
        String mUid = mAuth.getCurrentUser().getUid();
        _Email = mAuth.getCurrentUser().getEmail();
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
