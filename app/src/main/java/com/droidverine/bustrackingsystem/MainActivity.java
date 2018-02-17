package com.droidverine.bustrackingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;

public class MainActivity extends AppCompatActivity {
 Button signin;
    int LOGIN_PERMISSION=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signin=(Button)findViewById(R.id.signinbtn);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAllowNewEmailAccounts(true).build(),LOGIN_PERMISSION);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==LOGIN_PERMISSION)
        {
            startNewActivity(resultCode,data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private  void startNewActivity(int resultcode,Intent data)
    {
        if (resultcode==RESULT_OK)
        {
          startActivity(new Intent(this,OnlineStatusActivity.class));
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
        }
    }
}
