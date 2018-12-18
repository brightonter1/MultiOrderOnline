package group14.multiorder.multiorderonline.Account;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import group14.multiorder.multiorderonline.LoginActivity;
import group14.multiorder.multiorderonline.MainActivity;
import group14.multiorder.multiorderonline.R;

public class AccountActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        verifyPermissions();
        mAuth = FirebaseAuth.getInstance();
        userExist();

    }

    private void userExist(){
        if (mAuth.getCurrentUser() == null){
            Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new AccountFragment())
                    .commit();
        }
    }

    private void verifyPermissions(){
        //Log.d(TAG, "verifyPermissions: asking user for permissions");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions(AccountActivity.this,
                    permissions,
                    REQUEST_CODE);
        }
    }

}
