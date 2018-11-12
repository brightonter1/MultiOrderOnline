package group14.multiorder.multiorderonline.Account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import group14.multiorder.multiorderonline.LoginActivity;
import group14.multiorder.multiorderonline.R;
import group14.multiorder.multiorderonline.RegisterActivity;

public class AccountActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth = FirebaseAuth.getInstance();
        userExist();
    }

    private void userExist(){
        if (mAuth.getCurrentUser() == null){
            Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
            startActivity(intent);
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new AccountFragment())
                    .commit();
        }
    }

}
