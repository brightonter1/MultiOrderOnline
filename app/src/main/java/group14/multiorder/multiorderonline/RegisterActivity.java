package group14.multiorder.multiorderonline;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        context = this;
        btnRegister();
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
        String _emailStr = _email.getText().toString();
        String _pwdStr = _pwd.getText().toString();

        if (checkPwdCondition(_pwdStr)){
            mAuth.createUserWithEmailAndPassword(_emailStr, _pwdStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    sendVerifiedEmail(authResult.getUser());
                    Log.d("System", "[Register] Register Complete");
                    firebaseFirestore.collection("UserRole")
                            .document("customer")
                            .collection("uid")
                            .document(mAuth.getCurrentUser().getUid());
                    Toast.makeText(context, "Register Complete!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("System", "[Register] Register failed");
                    Toast.makeText(context, "ERROR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
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
    public void sendVerifiedEmail(FirebaseUser _user) {
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
}
