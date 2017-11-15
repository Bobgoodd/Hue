package com.example.mijin.hue;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Network-LAB-taeu on 2017-11-13.
 */

public class RegisterActivity extends AppCompatActivity {
    String ID, password, email, name, confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText passwordText2 = (EditText) findViewById(R.id.passwordText2);

        Button duplicationCheckButton = (Button)findViewById(R.id.duplicationCheckButton);
        Button registerButton = (Button) findViewById(R.id.registerButton);
        Button cancelButton = (Button)findViewById(R.id.cancelButton);

        // password Confirm
        passwordText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = passwordText.getText().toString();
                String confirm = passwordText2.getText().toString();

                if(password.equals(confirm)){
                    passwordText.setBackgroundColor(Color.GREEN);
                    passwordText2.setBackgroundColor(Color.GREEN);
                }else{
                    passwordText.setBackgroundColor(Color.RED);
                    passwordText2.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ID = idText.getText().toString();
                password = passwordText.getText().toString();
                confirm = passwordText2.getText().toString();
                email = emailText.getText().toString();
                name = nameText.getText().toString();

                // 이메일 입력확인
                if (ID.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "ID를 입력하세요", Toast.LENGTH_SHORT).show();
                    idText.requestFocus();
                    return;
                }
                // 비밀번호 입력확인
                if (password.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    passwordText.requestFocus();
                    return;
                }
                //이메일 입력확인
                if (email.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "이메일을 입력하세요", Toast.LENGTH_SHORT).show();
                    emailText.requestFocus();
                    return;
                }
                // 이름 입력확인
                if (name.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                    nameText.requestFocus();
                    return;
                }


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원가입 완료").
                                        setPositiveButton("확인", null).
                                        create().show();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("회원가입 실패").
                                        setNegativeButton("다시 시도", null).
                                        create().show();
                            }
                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(ID,email, name, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

//                if(password.equals(confirm)){
//                    Intent result = new Intent();
//                    result.putExtra("ID",idText.getText().toString());
//                    setResult(RESULT_OK, result);
//
//                    registDB rdb = new registDB();
//                    rdb.execute();
//                    finish();
//                }else{
//                    Toast.makeText(RegisterActivity.this, "비밀번호를 확인하세요", Toast.LENGTH_SHORT).show();
//                    passwordText.requestFocus();
//                    return;
//                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });
    }

//    class registDB extends AsyncTask<Void, Integer, Void>{
//        protected Void doInBackground(Void...unused){
//            String param = "ID " + ID + "& password=" + password + "";
//
//            try{
//                URL url = new URL("http://uoshue.dothome.co.kr/html/registerH.php");
//                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//                conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
//                conn.setRequestMethod("POST");
//                conn.setDoInput(true);
//                conn.connect();
//
//                OutputStream outs = conn.getOutputStream();
//                outs.write(param.getBytes("UTF-8"));
//                outs.flush();
//                outs.close();
//
//                InputStream is = null;
//                BufferedReader in = null;
//                String data = "";
//
//                is = conn.getInputStream();
//                in = new BufferedReader(new InputStreamReader(is), 8*1024);
//
//                String line = null;
//                StringBuffer buff = new StringBuffer();
//                while((line = in.readLine())!= null){
//                    buff.append(line+"\n");
//                }
//                data = buff.toString().trim();
//                Log.e("RECV DATA", data);
//            }catch(MalformedURLException e){
//                e.printStackTrace();
//            }catch(IOException e){
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//    }
//


}
