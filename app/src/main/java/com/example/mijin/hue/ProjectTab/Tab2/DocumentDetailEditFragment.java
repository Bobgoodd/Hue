package com.example.mijin.hue.ProjectTab.Tab2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mijin.hue.R;

/**
 * Created by mijin on 2017-10-03.
 */

public class DocumentDetailEditFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View fragment_edit = inflater.inflate(R.layout.fragment_documentdetail_edit, container, false);

        return fragment_edit;
    }
}
