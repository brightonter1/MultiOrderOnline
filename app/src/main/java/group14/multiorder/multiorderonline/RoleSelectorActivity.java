package group14.multiorder.multiorderonline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RoleSelectorActivity extends AppCompatActivity{

    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selector);
        context = this;
        suplierButton();
        customerButton();

    }

    public void suplierButton(){
        Button _supplierBtn = findViewById(R.id.role_selector_suplier_btn);
        _supplierBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
//
                Intent intent = new Intent(RoleSelectorActivity.this, LoginActivity.class);

                finish();
                startActivity(intent);
            }
        });
    }

    public void customerButton(){
        Button _customerBtn = findViewById(R.id.role_selector_customer_btn);
        _customerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RoleSelectorActivity.this, RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
