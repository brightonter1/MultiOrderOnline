package group14.multiorder.multiorderonline;

import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    EditText userName;
    EditText pwd;
    Button btn;
    String _userStr;
    String _pwdStr;
    Typeface typeface;
    FirebaseAuth mAuth;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setFont();
        btnLogin();
        btnRegister();
    }

    public void btnRegister(){
        TextView btnReg = getView().findViewById(R.id.login_register);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    
    public void btnLogin(){
        Button btnLogin = getActivity().findViewById(R.id.login_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = getView().findViewById(R.id.login_username);
                pwd = getView().findViewById(R.id.login_pwd);
                _userStr = userName.getText().toString();
                _pwdStr = pwd.getText().toString();
                if (_userStr.isEmpty() || _pwdStr.isEmpty()){
                    Log.d("System", "[Login] username or password is empty");
                    Toast.makeText(getActivity(), "Please fill your info", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.signInWithEmailAndPassword(_userStr,_pwdStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if (authResult.getUser().isEmailVerified()){
                                Log.d("System", "[Login] login complete");
                            }
                            else if (authResult.getUser().isEmailVerified() == false){
                                Log.d("System", "[Login] email not verified");
                                Toast.makeText(getActivity(), "Please confirm your email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("System", "[Login] Invalid " + e.getMessage());
                            Toast.makeText(getActivity(), "Invalid user or password", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void setFont(){
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "font/Franchise-Bold.ttf");
        userName = getActivity().findViewById(R.id.login_username);
        pwd = getActivity().findViewById(R.id.login_pwd);
        btn = getActivity().findViewById(R.id.login_login);
        userName.setTypeface(typeface);
        pwd.setTypeface(typeface);
        btn.setTypeface(typeface);
    }
}
