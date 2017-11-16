package com.example.mijin.hue.ProjectTab.Tab2;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mijin on 2017-10-03.
 */

public class DocumentDetailDefaultFragment extends Fragment {

    TextView content;
    String str, project_id, document_id;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragment_default = inflater.inflate(R.layout.fragment_documentdetail_default, container, false);

        content = (TextView) fragment_default.findViewById(R.id.content);
        String url = "http://uoshue.dothome.co.kr/loadDocumentContent2.php?";
        ContentValues values = new ContentValues();
        if(getArguments()!=null) {
            document_id = getArguments().getString("document_id");
            project_id = getArguments().getString("project_id");
        }
        values.put("project_id",project_id);
        values.put("document_id",document_id);

        NetworkTask13 networkTask13 = new NetworkTask13(url,values);
        networkTask13.execute();

        Button rwbtn = (Button) fragment_default.findViewById(R.id.rwbtn);
        rwbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onMyListener2!=null){
                    onMyListener2.onReceivedData2(content.getText().toString());
                }
                /*
                DocumentDetailEditFragment documentDetailEditFragment = new DocumentDetailEditFragment();
                Bundle b = new Bundle();
                b.putString("content",content.getText().toString());
                b.putString("project_id",project_id);
                b.putString("document_id",document_id);
                documentDetailEditFragment.setArguments(b);
                getActivity().getFragmentManager().beginTransaction().replace(R.id.documentFragment,documentDetailEditFragment).commit();
*/
            }
        });
/*
        String str;
        if(getArguments()!=null) {
            if ((str = getArguments().getString("content")) != null)
                content.setText(str);
        }
*/
        return fragment_default;
    }

    public class NetworkTask13 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask13(String url, ContentValues values) {

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

            try {
                if (result != null) {

                    JSONArray jarr = new JSONArray(result);

                    for (int i = 0; i < jarr.length(); i++) {
                        JSONObject json = (JSONObject) jarr.get(i);

                        str = json.getString("content");
                        content.setText(str);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }

    public interface OnMyListener2{
        void onReceivedData2(String content);
    }

    private OnMyListener2 onMyListener2;

    public void onAttach(Context context){
        super.onAttach(context);
        if(getActivity()!=null&&getActivity() instanceof DocumentDetailActivity){
            onMyListener2 = (OnMyListener2) getActivity();
        }
    }

}
