package group14.multiorder.multiorderonline.Account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Store;

public class EditInfoFragment extends Fragment {
    private DatabaseReference _databaseRefs;
    private FirebaseAuth mAuth;
    Store myStore = new Store();

    RadioGroup radioGroup;
    RadioButton radioButton;


    EditText _address;

    EditText _openClose;

    EditText _description;

    EditText _phoneNumber;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        radioGroup = getView().findViewById(R.id.edit_info_radiogroup);



        _address = getView().findViewById(R.id.edit_info_address);
        _openClose = getView().findViewById(R.id.edit_info_openclose);
        _description = getView().findViewById(R.id.edit_info_description);
        _phoneNumber = getView().findViewById(R.id.edit_info_phonenumber);

        _databaseRefs = FirebaseDatabase.getInstance().getReference("Stores");
        _databaseRefs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot store : dataSnapshot.getChildren()){
                    Store st = store.getValue(Store.class);
                    Log.d("System", st.getTitle());
                    if(st.getUser_id().equals(mAuth.getCurrentUser().getUid())){
                        myStore = st;
                        getFromDB();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Button _saveBTN = getView().findViewById(R.id.edit_info_save);
        _saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDB();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new AccountFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });




    }

    public void saveDB(){

        final Store newStoreInfo = new Store();

        radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        if(radioButton.getId() == R.id.edit_info_radio_dessert){
            newStoreInfo.setTag("dessert");
        }else{
            newStoreInfo.setTag("fastfood");
        }

        _address = getView().findViewById(R.id.edit_info_address);
        _openClose = getView().findViewById(R.id.edit_info_openclose);
        _description = getView().findViewById(R.id.edit_info_description);
        _phoneNumber = getView().findViewById(R.id.edit_info_phonenumber);

        String newAddress = _address.getText().toString();
        String newOpenClose = _openClose.getText().toString();
        String newDescription = _description.getText().toString();
        String newPhoneNumber = _phoneNumber.getText().toString();

        newStoreInfo.setAddress(newAddress);
        newStoreInfo.setOpenClose(newOpenClose);
        newStoreInfo.setDescription(newDescription);
        newStoreInfo.setPhoneNumber(newPhoneNumber);

        newStoreInfo.setTitle(myStore.getTitle());
        newStoreInfo.setImage(myStore.getImage());
        newStoreInfo.setUser_id(myStore.getUser_id());
        newStoreInfo.setPost_id(myStore.getPost_id());
        newStoreInfo.setShop_id(myStore.getShop_id());

        _databaseRefs.child(myStore.getTitle())
                .setValue(newStoreInfo);








    }

    public void getFromDB(){
        if(myStore.getTag().equals("dessert")){
            radioButton = getView().findViewById(R.id.edit_info_radio_dessert);
            radioButton.toggle();
        }else{
            radioButton = getView().findViewById(R.id.edit_info_radio_fastfood);
            radioButton.toggle();
        }
        _address.setText(myStore.getAddress());
        _openClose.setText(myStore.getOpenClose());
        _description.setText(myStore.getDescription());
        _phoneNumber.setText(myStore.getPhoneNumber());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_info, container, false);

    }
}
