package group14.multiorder.multiorderonline.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import javax.annotation.Nullable;

import group14.multiorder.multiorderonline.LoginActivity;
import group14.multiorder.multiorderonline.R;


public class CartActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mAuth = FirebaseAuth.getInstance();
        userExist();
    }

    private void userExist(){
        Log.d("System", "Cart Activity here");

        if (mAuth.getCurrentUser() == null){
            Log.d("System", "Go to Login");
            Intent intent = new Intent(CartActivity.this, LoginActivity.class);
            startActivity(intent);

        }else{
            Log.d("System", "Go to CartFrag");
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_cart, new CartFragment())
                    .commit();
        }
    }

}
