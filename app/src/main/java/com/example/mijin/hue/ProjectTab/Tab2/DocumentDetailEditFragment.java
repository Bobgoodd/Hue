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
import android.widget.EditText;

import com.example.mijin.hue.R;
import com.example.mijin.hue.RequestHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mijin on 2017-10-03.
 */

public class DocumentDetailEditFragment extends Fragment {
    String str;
    String url;
    EditText content;
    ContentValues values;
    NetworkTask12 networkTask12;
    String[] split;
    String updated;
    String document_id, project_id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragment_edit = inflater.inflate(R.layout.fragment_documentdetail_edit, container, false);

        content = (EditText) fragment_edit.findViewById(R.id.content2);

        if(getArguments()!=null){
            project_id = getArguments().getString("project_id");
            document_id = getArguments().getString("document_id");
            str = getArguments().getString("content");
            content.setText(str);
        }


        Button rwbtn = (Button) fragment_edit.findViewById(R.id.rwbtn1);

        rwbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = "http://uoshue.dothome.co.kr/updateDocument.php?";
                values = new ContentValues();
                values.put("project_id",project_id);
                values.put("document_id",document_id);
                values.put("content",content.getText().toString());
                networkTask12 = new NetworkTask12(url, values);
                networkTask12.execute();


                /*
                DocumentDetailDefaultFragment documentDetailDefaultFragment = new DocumentDetailDefaultFragment();
                Bundle b = new Bundle();
                b.putString("project_id",project_id);
                b.putString("document_id",document_id);
                documentDetailDefaultFragment.setArguments(b);
                getActivity().getFragmentManager().beginTransaction().replace(R.id.documentFragment,documentDetailDefaultFragment).commit();
*/

            }
        });



        return fragment_edit;
    }
    public class NetworkTask12 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask12(String url, ContentValues values) {

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

                        split = json.getString("keyword").split(",");
                        updated = json.getString("updated");

                        if(onMyListener!=null){
                            onMyListener.onReceivedData(split,updated);
                        }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }
    public interface OnMyListener{
        void onReceivedData(String[] split, String updated);
    }

    private OnMyListener onMyListener;

    public void onAttach(Context context){
        super.onAttach(context);
        if(getActivity()!=null&&getActivity() instanceof DocumentDetailActivity){
            onMyListener = (OnMyListener) getActivity();
        }
    }

}
