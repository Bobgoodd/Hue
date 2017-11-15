package com.example.mijin.hue;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mijin.hue.LoginTab.LoginTabActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends Activity {

    CallbackManager callbackManager;
    private Button register;
    private Button login;
    private EditText idText;
    private EditText passwordText;
    public static final int REQUEST_CODE_REGISTER = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        idText = (EditText)findViewById(R.id.idText);
        passwordText = (EditText)findViewById(R.id.passwordText);
        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent,REQUEST_CODE_REGISTER );
            }
        } );
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(idText.getText().toString()==null||passwordText.getText().toString()==null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("로그인 정보를 입력해주세요.");
                    builder.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }else {
                    SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("id", idText.getText().toString());
                    editor.putString("pw", passwordText.getText().toString());
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, LoginTabActivity.class);
                    startActivity(intent);
                }
//                final String userId = idText.getText().toString();
//                final String userPassword = passwordText.getText().toString();
//                Response.Listener<String> responseListener = new Response.Listener<String>(){
//
//                    @Override
//                    public void onResponse(String response) {
//                        try{
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
//                            if(success){
//                                String userId = jsonResponse.getString("userId");
//                                String password = jsonResponse.getString("userPassword");
//                                Intent intent = new Intent(LoginActivity.this, LoginTabActivity.class);
//                                intent.putExtra("userId", userId);
//                                intent.putExtra("userPassword", userPassword);
//                                startActivity(intent);
//                            }else{
//                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                                builder.setMessage("로그인에 실패했습니다.").
//                                        setNegativeButton("다시 시도", null)
//                                        .create()
//                                        .show();
//                            }
//                        }catch(Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                };
//
//                LoginRequest loginRequest = new LoginRequest(userId, userPassword, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//                queue.add(loginRequest);


            }
        });

//        login.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                final String userId = idText.getText().toString();
//                final String userPassword = passwordText.getText().toString();
//
//                Response.Listener<String> responseListener = new Response.Listener<String>(){
//
//                    @Override
//                    public void onResponse(String response) {
//                        try{
//                            JSONObject jsonResponse = new JSONObject(response);
//                            boolean success = jsonResponse.getBoolean("success");
//                            if(success){
//                                String userId = jsonResponse.getString("userId");
//                                String password = jsonResponse.getString("userPassword");
//                                Intent intent = new Intent(LoginActivity.this, LoginTabActivity.class);
//                                intent.putExtra("userId", userId);
//                                intent.putExtra("userPassword", userPassword);
//                                startActivity(intent);
//                            }else{
//                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//                                builder.setMessage("로그인에 실패했습니다.").
//                                        setNegativeButton("다시 시도", null)
//                                        .create()
//                                        .show();
//                            }
//                        }catch(Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                };
//
//                LoginRequest loginRequest = new LoginRequest(userId, userPassword, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//                queue.add(loginRequest);
//            }
//        });
        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton)findViewById(R.id.login_buttonF);
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
//
        Button find = (Button) findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindInformationActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_REGISTER && requestCode == RESULT_OK){
            Toast.makeText(LoginActivity.this, "회원가입을 완료했습니다.!",
                    Toast.LENGTH_SHORT).show();
            idText.setText(data.getStringExtra("ID"));
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
