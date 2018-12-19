package group14.multiorder.multiorderonline;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import group14.multiorder.multiorderonline.obj.Store;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private Context context;
    private String _emailStr;
    private String _pwdStr;
    private Toolbar toolbar;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Store");
        context = this;
        btnRegister();
        BackBtn();

        radioGroup = findViewById(R.id.select_radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                EditText hide;
                switch (checkedId){
                    case R.id.seller:
                        hide = findViewById(R.id.register_name_store);
                        hide.setVisibility(View.VISIBLE);
                        break;
                    case R.id.buyer:
                        hide = findViewById(R.id.register_name_store);
                        hide.setVisibility(View.INVISIBLE);
                        break;
                }
//                Log.d("System", String.valueOf(checkedId));

            }
        });


    }

    public void BackBtn(){
        TextView backBtn = findViewById(R.id.register_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void btnRegister(){
        Button btnRegis = findViewById(R.id.register_regisBtn);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regisNewuser();
            }
        });
    }
    public void regisNewuser(){
        EditText _email = findViewById(R.id.register_email);
        EditText _pwd = findViewById(R.id.register_password);
        _emailStr = _email.getText().toString();
        _pwdStr = _pwd.getText().toString();


        if (checkPwdCondition(_pwdStr)){
            if(ifSeller()){
                mAuth.createUserWithEmailAndPassword(_emailStr, _pwdStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        sendVerifiedEmail(authResult.getUser());
                        Log.d("System", "[Register] Register Complete");
                        Toast.makeText(context, "Register Complete!!", Toast.LENGTH_SHORT).show();
                        createDBforUser(mAuth.getCurrentUser().getUid());
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("System", "[Register] Register failed");
                        Toast.makeText(context, "ERROR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(context, "Please fill name store", Toast.LENGTH_LONG).show();
            }


        }
    }
    public Boolean checkPwdCondition(String _pwd){
        if (_pwd.length() >= 6){
            return true;

        }else{
            Log.d("System", "[Register] pwd at least 6 character");
            Toast.makeText(context, "pass word must be at least 6 character", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    boolean isSeller;

    public boolean ifSeller(){
        Log.d("System", "isseller method");

        radioGroup = findViewById(R.id.select_radio);
        radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        if(radioButton.getId() == R.id.seller){
            isSeller = true;
        }else{
            isSeller = false;
        }
        if (isSeller){
            EditText _registerNameStore = findViewById(R.id.register_name_store);
            Log.d("System", "Game : " + _registerNameStore.getText().toString()+ " " + _registerNameStore.getText().toString().isEmpty());
            if (_registerNameStore.getText().toString().isEmpty()) {
                Log.d("System", "name store is emtpy");
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }

    }

    public void sendVerifiedEmail(final FirebaseUser _user) {
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("System", "[Register] Send verifiedEmail ");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "ERROR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    int i = 0;
    public void createDBforUser(String _uid){
        radioGroup = findViewById(R.id.select_radio);
        radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        final String type = (String) radioButton.getText();

        HashMap<String,String> mAccount = new HashMap<>();
        mAccount.put("email", _emailStr);
        mAccount.put("type", type.toLowerCase());

        firebaseFirestore.collection("customer").document(_uid).set(mAccount)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("System", "Done" + type.toLowerCase());
                        switch (type.toLowerCase()){
                            case "customer":
                                Log.d("System", "This cus");
                                break;
                            case "supplier":
                                Log.d("System", "This sup");
                                createDBforSuppiler(type);
                                break;
                            default:
                                Log.d("System", "This Default");
                                break;

                        }
                        mAuth.signOut();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("System", "Faild");
            }
        });
    }

    public void createDBforSuppiler(String type){
        if (type.toLowerCase().equals("supplier")){
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    i = (int) dataSnapshot.getChildrenCount();
                    Log.d("System", "child "+String.valueOf(dataSnapshot.getChildrenCount()));
                    EditText name = findViewById(R.id.register_name_store);
                    String Sname = name.getText().toString();

                        Toast.makeText(context, "Please fill name store ", Toast.LENGTH_LONG).show();
                        Store newStoreInfo = new Store();
                        newStoreInfo.setAddress("");
                        newStoreInfo.setOpenClose("");
                        newStoreInfo.setDescription("");
                        newStoreInfo.setPhoneNumber("");
                        newStoreInfo.setTag("");
                        newStoreInfo.setTitle(Sname);
                        newStoreInfo.setImage("none");
                        newStoreInfo.setUser_id("");
                        newStoreInfo.setPost_id("");
                        newStoreInfo.setShop_id(i);
                        myRef.child(newStoreInfo.getTitle()).setValue(newStoreInfo);
                    mAuth.signOut();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_menu, menu);
        return true;
    }



}
