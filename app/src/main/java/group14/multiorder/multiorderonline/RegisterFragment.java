package group14.multiorder.multiorderonline;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false );
    }
    FirebaseAuth mAuth;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        btnRegister();
    }

    public void btnRegister(){
        Button btnRegis = getView().findViewById(R.id.register_regisBtn);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regisNewuser();
            }
        });
    }
    public void regisNewuser(){
        EditText _email = getView().findViewById(R.id.register_email);
        EditText _pwd = getView().findViewById(R.id.register_password);
        String _emailStr = _email.getText().toString();
        String _pwdStr = _pwd.getText().toString();

        if (checkPwdCondition(_pwdStr)){
            mAuth.createUserWithEmailAndPassword(_emailStr, _pwdStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    sendVerifiedEmail(authResult.getUser());
                    Log.d("System", "[Register] Register Complete");
                    Toast.makeText(getActivity(), "Register Complete!!", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new LoginFragment())
                            .addToBackStack(null)
                            .commit();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("System", "[Register] Register failed");
                    Toast.makeText(getActivity(), "ERROR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public Boolean checkPwdCondition(String _pwd){
        if (_pwd.length() >= 6){
            return true;
        }else{
            Log.d("System", "[Register] pwd at least 6 character");
            Toast.makeText(getActivity(), "pass word must be at least 6 character", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "ERROR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
