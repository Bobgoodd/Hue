package com.example.mijin.hue;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

/**
 * Created by Network-LAB-taeu on 2017-11-13.
 */

public class RegisterActivity extends AppCompatActivity {
    String ID, password, email, name, confirm;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText emailText = (EditText) findViewById(R.id.emailText);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final EditText passwordText2 = (EditText) findViewById(R.id.passwordText2);
        final TextView passwordCheck = (TextView) findViewById(R.id.passwordcheck);

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
                    passwordCheck.setText("비밀번호 확인 완료");
                }else{
                    passwordCheck.setText("비밀번호가 다릅니다.");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        duplicationCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ID = idText.getText().toString();

                String url = "http://uoshue.dothome.co.kr/checkK.php?";
                ContentValues values = new ContentValues();
                values.put("id",ID);

                NetworkTask15 networkTask15 = new NetworkTask15(url,values);
                networkTask15.execute();

/*
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("check");

                            if(success) {
                                Toast.makeText(RegisterActivity.this,"ID 사용 가능",Toast.LENGTH_SHORT).show();
                                passwordText.requestFocus();
                                idCheck=1;
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "다른 ID를 입력하세요", Toast.LENGTH_SHORT).show();
                                idText.requestFocus();
                            }
                        }
                        catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                IdCheckRequest idCheckRequest = new IdCheckRequest(ID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(idCheckRequest);*/
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

                            /*
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
*/

                            if (response.equals("success")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.MyAlertDialog);
                                builder.setMessage("회원가입 완료").
                                        setPositiveButton("확인", null).
                                        create().show();

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.MyAlertDialog);
                                builder.setMessage("회원가입 실패").
                                        setNegativeButton("다시 시도", null).
                                        create().show();
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

    public class NetworkTask15 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask15(String url, ContentValues values) {

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
                    flag = true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.MyAlertDialog);
                    builder.setMessage("사용가능합니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.MyAlertDialog);
                    builder.setMessage("다른 아이디를 사용해주세요.");
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
