package group14.multiorder.multiorderonline;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    EditText userName;
    EditText pwd;
    Button btn;
    Typeface typeface;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setFont();
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
