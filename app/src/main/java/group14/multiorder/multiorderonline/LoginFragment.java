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
        asdsd
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
