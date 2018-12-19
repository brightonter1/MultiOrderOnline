package group14.multiorder.multiorderonline.Account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import group14.multiorder.multiorderonline.BaseFragment;
import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.obj.Store;

public class EditInfoFragment extends BaseFragment {
    private DatabaseReference _databaseRefs;
    private FirebaseAuth mAuth;
    Store myStore = new Store();

    RadioGroup radioGroup;
    RadioButton radioButton;


    EditText _address;

    EditText _openClose;

    EditText _description;

    EditText _phoneNumber;

    ImageView _image;

    Boolean status = false;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        toolbar = setToolbar("Edit Info");
        backBtn();
        Log.d("System", "onActivity " + status);


        _address = getView().findViewById(R.id.edit_info_address);
        _openClose = getView().findViewById(R.id.edit_info_openclose);
        _description = getView().findViewById(R.id.edit_info_description);
        _phoneNumber = getView().findViewById(R.id.edit_info_phonenumber);
        _image = getView().findViewById(R.id.edit_info_image);
        _databaseRefs = FirebaseDatabase.getInstance().getReference("Store");
        _databaseRefs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot store : dataSnapshot.getChildren()){
                    Store st = store.getValue(Store.class);
//                    Log.d("System", st.getTitle());
                    Log.d("System", st.getUser_id() + " " + mAuth.getCurrentUser().getUid());
                    if(st.getUser_id().equals(mAuth.getCurrentUser().getUid()) && !status){
                        status = true;
                        myStore = st;Log.d("System", "Found "  + status + store.getKey());
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
                Toast.makeText(getActivity(), "Save Complete", Toast.LENGTH_LONG).show();
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

        radioGroup = getView().findViewById(R.id.edit_info_radiogroup);
        radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        String type = (String) radioButton.getText();

        Log.d("System", "save DB : " + type);

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
        newStoreInfo.setTag(type.toLowerCase());
        newStoreInfo.setTitle(myStore.getTitle());
        newStoreInfo.setImage(myStore.getImage());
        newStoreInfo.setUser_id(myStore.getUser_id());
        newStoreInfo.setPost_id(myStore.getPost_id());
        newStoreInfo.setShop_id(myStore.getShop_id());

        _databaseRefs.child(myStore.getTitle()).setValue(newStoreInfo);
    }

    public void getFromDB(){
//        if(myStore.getTag().equals("dessert")){
//            radioButton = getView().findViewById(R.id.edit_info_radio_dessert);
//            radioButton.setChecked(true);
//            Log.d("System", "Checked Dessert");
//
//        }else if(myStore.getTag().equals("fastfood")){
//            radioButton = getView().findViewById(R.id.edit_info_radio_fast);
//            radioButton.setChecked(true);
//            Log.d("System", "Checked Fastfood");
//        }

        _address.setText(myStore.getAddress());
        _openClose.setText(myStore.getOpenClose());
        _description.setText(myStore.getDescription());
        _phoneNumber.setText(myStore.getPhoneNumber());
        Picasso.with(getContext())
                .load(myStore.getImage())
                .fit()
                .centerCrop()
                .into(_image);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_info, container, false);

    }

    @Override
    public void backBtn() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_view, new AccountFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public Toolbar setToolbar(String nPager) {
        return super.setToolbar(nPager);
    }
}
