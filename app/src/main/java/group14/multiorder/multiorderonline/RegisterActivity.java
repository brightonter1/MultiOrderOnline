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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
<<<<<<< HEAD
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;
=======
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
>>>>>>> bf267ada25f149c3acc8dd98373846e6525eaa10

public class RegisterActivity extends AppCompatActivity {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private Context context;
    String _emailStr;
    String _pwdStr;
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
        _emailStr = _email.getText().toString();
        _pwdStr = _pwd.getText().toString();

        if (checkPwdCondition(_pwdStr)){
            mAuth.createUserWithEmailAndPassword(_emailStr, _pwdStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    sendVerifiedEmail(authResult.getUser());
                    Log.d("System", "[Register] Register Complete");

                    Toast.makeText(context, "Register Complete!!", Toast.LENGTH_SHORT).show();
                    createDBforUser(mAuth.getCurrentUser().getUid());
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                    createDBforUser(mAuth.getCurrentUser().getUid());
//                    pullDBallUser(mAuth.getCurrentUser().getUid());

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

    public void pullDBallUser(String _uid){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference docRef = db.collection("buyer").document(mAuth.getCurrentUser().getUid());
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot doc = task.getResult();
//                    if (doc.exists()){
//                        Log.d("pull", String.valueOf(doc.getData()));
//                    }else
//                        Log.d("pull", "No Such");
//                }else{
//                    Log.d("pull", "get failed with ", task.getException());
//                }
//            }
//        });

        db.collection("buyer")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("pull", document.getId() + " => " + document.getData());
                                test(document.getId());
                            }
                        } else {
                            Log.d("pull", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
    public void test(String _uid){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference doc = db.collection("buyer").document(_uid);
        doc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("pull", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    if (snapshot.get("name") != null){
                        Log.d("pull", "Name data: " + snapshot.get("name"));
                        Log.d("pull", "Lastname data: " + snapshot.get("lastname"));
                        Log.d("pull", "Age data: " + snapshot.get("age"));
                    }
                } else {
                    Log.d("pull", "Current data: null");
                }
            }

        });
    }
    public void createDBforUser(String _uid) {
        User setUser = new User();
        setUser.setProfile("", "", "", 0, "", "", _emailStr, "", _pwdStr);
        FirebaseFirestore _fireStore = FirebaseFirestore.getInstance();
        _fireStore.collection("buyer").document(_uid).set(setUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Register", "Create Success");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Register", "Create Failed");
            }
        });
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
    public void sendVerifiedEmail(final FirebaseUser _user) {
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("System", "[Register] Send verifiedEmail ");
                firebaseFirestore.collection("customer")
                        .document("sdasd");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "ERROR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createDBforUser(String _uid){
        Map<String, String> data = new HashMap<>();
        data.put("name", "BBBBRIGHT");
        data.put("lastname", "123456");
        data.put("age", "21");
        FirebaseFirestore _fireStore = FirebaseFirestore.getInstance();
        _fireStore.collection("customer").document(_uid).set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("System", "Done");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("System", "Faild");
            }
        });


    }

}
