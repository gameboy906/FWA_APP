package com.wolfs.fwa.fwa2_0;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private static final String pw = "1234";
    EditText pwInput;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pwInput = (EditText) findViewById(R.id.txt_Anmeldung);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.lbl_Anmeldung);
        //tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


    public void onClick_Anmelden(View view) {
        if (loginSucceful()){
            Intent javaFireworkActivity = new Intent(this, FireworkActivity.class);
            startActivity(javaFireworkActivity);
        } else {
            pwInput.setText("");
            Toast.makeText(getApplicationContext(), "Falsches Kennwort", Toast.LENGTH_LONG).show();
        }
    }

    private Boolean loginSucceful(){
        boolean succesful = false;
        if (pw.equals(pwInput.getText().toString())){
            succesful = true;
        }
        return succesful;
    }


    //    setContentView(R.layout.activity_firework);
}
