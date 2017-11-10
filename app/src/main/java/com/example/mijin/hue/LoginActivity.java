package com.example.mijin.hue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mijin.hue.LoginTab.LoginTabActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);

        FacebookSdk.sdkInitialize(getApplicationContext());
        Button login = (Button) findViewById(R.id.login);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton)findViewById(R.id.login_buttonF);
        TextView registerButton = (TextView) findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "로그인 취소", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "로그인 에러", Toast.LENGTH_LONG).show();
            }
        });


        login.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                String ID = idText.getText().toString();
                String password = passwordText.getText().toString();
                Toast.makeText(getApplicationContext(), ID+"/"+password, Toast.LENGTH_LONG).show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            //boolean success = jsonResponse.getBoolean("success");
                            boolean success = false;
                            Toast.makeText(getApplicationContext(), "success"+success, Toast.LENGTH_LONG).show();
                            if(!success) {
                                //String ID = jsonResponse.getString("ID");
                                //String password = jsonResponse.getString("password");
                                //Toast.makeText(getApplicationContext(), ID+password+"??", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, LoginTabActivity.class);

                                //id, password를 logintab으로 넘겨줌
                                //intent.putExtra("ID", ID);
                                //intent.putExtra("password", password);
                                LoginActivity.this.startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인 실패").
                                        setNegativeButton("다시 시도", null).
                                        create().show();
                            }
                        }
                        catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(ID, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
