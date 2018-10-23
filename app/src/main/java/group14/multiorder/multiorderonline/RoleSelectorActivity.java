package group14.multiorder.multiorderonline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;


public class RoleSelectorActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selector);
        context = this;
        mAuth = FirebaseAuth.getInstance();
        suplierButton();
        customerButton();

    }

    public void suplierButton(){
        ImageView _supplierBtn = findViewById(R.id.role_selector_suplier_img);
        _supplierBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//
                Intent intent = new Intent(RoleSelectorActivity.this, SuplierRegisterActivity.class);
                finish();
                startActivity(intent);
//                go to suplieractivity
            }
        });
    }

    public void customerButton(){
        ImageView _customerBtn = findViewById(R.id.role_selector_customer_img);
        _customerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RoleSelectorActivity.this, RegisterActivity.class);
                finish();
                startActivity(intent);
//                go to customer activity
            }
        });
    }
}
