package com.example.mijin.hue;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends Activity {

    CallbackManager callbackManager;
    private TextView register;
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
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent,REQUEST_CODE_REGISTER );
            }
        } );
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new Button.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         if (!idText.getText().toString().equals("") && !passwordText.getText().toString().equals("")) {
                                             String url = "http://uoshue.dothome.co.kr/loginK.php";
                                             ContentValues values = new ContentValues();
                                             values.put("id",idText.getText().toString());
                                             values.put("pw",passwordText.getText().toString());

                                             NetworkTask14 networkTask14 = new NetworkTask14(url, values);
                                             networkTask14.execute();
                                         } else {
                                             AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.MyAlertDialog);
                                             builder.setMessage("로그인 정보를 입력해주세요.");
                                             builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                 @Override
                                                 public void onClick(DialogInterface dialogInterface, int i) {

                                                 }
                                             });
                                             builder.show();


                                         }
                                     }
                                 });
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


   /*         }
        });*/
/*
        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final String userId = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{
                            if(response!=null){
                                    JSONArray jarr = new JSONArray(response);
                            for(int i=0;i<jarr.length();i++) {
                                JSONObject jsonResponse = (JSONObject) jarr.get(i);

                               // boolean success = jsonResponse.getBoolean("success");
                                //if (success) {
                                    String userId = jsonResponse.getString("userId");
                                    String userPassword = jsonResponse.getString("userPassword");
                                    Intent intent = new Intent(LoginActivity.this, LoginTabActivity.class);
                                    intent.putExtra("userId", userId);
                                    intent.putExtra("userPassword", userPassword);
                                    startActivity(intent);
                                }
                                if(jarr.length()==0) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("로그인에 실패했습니다.").
                                            setNegativeButton("다시 시도", null)
                                            .create()
                                            .show();
                                }
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userId, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
        */
                FacebookSdk.sdkInitialize(getApplicationContext());

                callbackManager = CallbackManager.Factory.create();
                LoginButton loginButton = (LoginButton) findViewById(R.id.login_buttonF);
                loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_LONG).show();
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
                TextView find = (TextView) findViewById(R.id.find);
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

    public class NetworkTask14 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask14(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);

            return result;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            if(result!=null&&!result.equals("")) {
                if(result.equals("success")){
                    SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("id", idText.getText().toString());
                    editor.putString("pw", passwordText.getText().toString());
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this,R.style.MyAlertDialog);
                    builder.setMessage("로그인 정보가 다릅니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            }



        }
    }

}

