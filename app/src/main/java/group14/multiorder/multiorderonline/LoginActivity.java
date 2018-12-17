package group14.multiorder.multiorderonline;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LoginActivity extends AppCompatActivity {

    private EditText userName;
    private EditText pwd;
    private Button btn;
    private String _userStr;
    private String _pwdStr;
    private Typeface typeface;
    private FirebaseAuth mAuth;
    private Context context;
    private String _type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        context = LoginActivity.this;
        btnLogin();
        btnRegister();
        UserExist();

    }

    public void btnRegister(){
        TextView btnReg = findViewById(R.id.login_register);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    public void btnLogin(){
        Button btnLogin = findViewById(R.id.login_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = findViewById(R.id.login_username);
                pwd = findViewById(R.id.login_pwd);
                _userStr = userName.getText().toString();
                _pwdStr = pwd.getText().toString();
                if (_userStr.isEmpty() || _pwdStr.isEmpty()){
                    Log.d("System", "[Login] username or password is empty");
                    Toast.makeText(context, "Please fill your info", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.signInWithEmailAndPassword(_userStr,_pwdStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if (authResult.getUser().isEmailVerified()){
                                Log.d("System", "[Login] login complete");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
                                startActivity(intent);
                                finish();
                            }
                            else if (authResult.getUser().isEmailVerified() == false){
                                Log.d("System", "[Login] email not verified");
                                Toast.makeText(context, "Please confirm your email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("System", "[Login] Invalid " + e.getMessage());
                            Toast.makeText(context, "Invalid user or password", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void shareTypeUser(){
        String _Email;
        FirebaseFirestore mDB;
        String mUid = mAuth.getCurrentUser().getUid();
        mDB = FirebaseFirestore.getInstance();
        _Email = mAuth.getCurrentUser().getEmail();
        mDB.collection("customer")
                .document(mUid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot snapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (snapshot.exists()){
                            _type = snapshot.getString("type");
                            SharedPreferences sp = getSharedPreferences("center", Context.MODE_PRIVATE);
                            sp.edit().putString("type", _type).apply();
                            Log.d("System", "User type : " + _type);
                        }
                    }
                });

    }



    public void UserExist(){
        FirebaseUser _user = mAuth.getCurrentUser();


        if(_user != null && _user.isEmailVerified() == true){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
