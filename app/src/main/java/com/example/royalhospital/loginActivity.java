package com.example.royalhospital;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loginActivity extends AppCompatActivity {

    TextView username, password, emailError, passError;
    Button btnlogin;
    private static final String LOGIN_REQUEST_URL = "http://localhost/RoyalHospital/validar_usuario.php";
    public static final int MY_DEFAULT_TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        //INITIALIZE ALL COMPONENTS
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        emailError = findViewById(R.id.mailError);
        passError = findViewById(R.id.passError);
        btnlogin = findViewById(R.id.login);


        //ADDING LISTENERS
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("CE METE EN EL ONLICK");
                //CHECK IF ALL DATA BY THE USER IS CORRECT
                //boolean userIsOk = chekOkUser();
                boolean passIsOk = checkOkPass();

                if (passIsOk) {
                    //Toast.makeText(loginActivity.this, "ENTRA EN EL IF", Toast.LENGTH_LONG).show();
                    //CHECK IF THIS USER EXIST IN THE DATABASE
                    checkUserExist("http://11.65.4.167/RoyalHospital/validar_usuario.php");
                }
            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                emailError.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passError.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


    }

    //CHECK IF USER CAMP ITS NOT EMPTY AND HAVE THE RIGHT STRUCTURE
    public boolean chekOkUser() {

        String userNameValue = username.getText().toString();
        Pattern emailPattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = emailPattern.matcher(userNameValue);
/*
        if (mather.find() != true) {
            emailError.setText("The email is not correct");
            username.setFocusable(true);
            return false;
        }*/

        return true;
    }

    //CHECK IF THE PASSWORD ITS NOT EMPTY
    public boolean checkOkPass() {
        String userPassValue = password.getText().toString();

        if (userPassValue.isEmpty() || userPassValue == null) {
            passError.setText("The password cannot be empty");
            return false;
        }
        return true;
    }

    //CHECK IF THE USER GIVEN EXIST IN THE DATABASE
    private void checkUserExist(String URL) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
               if(!response.isEmpty()){
                   //Toast.makeText(loginActivity.this, "ENTRA EN reponse", Toast.LENGTH_LONG).show();
                   Intent jumpToMain = new Intent(loginActivity.this, MainActivity.class);
                   startActivity(jumpToMain);
               }else{
                   Toast.makeText(loginActivity.this, "usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
               }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(loginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               //Toast.makeText(loginActivity.this, "ENTRA EN PARAMS", Toast.LENGTH_LONG).show();
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("username", username.getText().toString());
                parametros.put("password", password.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
   /*     stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 5000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 5000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });*/
        requestQueue.add(stringRequest);

    }

}
