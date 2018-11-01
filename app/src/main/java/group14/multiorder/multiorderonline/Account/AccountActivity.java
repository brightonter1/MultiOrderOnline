package group14.multiorder.multiorderonline.Account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import group14.multiorder.multiorderonline.R;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_setting, new AccountListFragment())
                    .commit();
        }
    }

}
