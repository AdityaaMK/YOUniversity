package com.adityaamk.youniversity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private EditText satScore, actScore;
    private String satUser, actUser;
    private TextView satTv, actTv;
    private String json = "scores.json";
    private String data;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.fragment_home, container, false);
        satScore = home.findViewById(R.id.id_satScore);
        actScore = home.findViewById(R.id.id_actScore);
        satTv = home.findViewById(R.id.id_displaySAT);
        actTv = home.findViewById(R.id.id_displayACT);
        Button save = home.findViewById(R.id.id_save);

        satScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                satUser = charSequence.toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        satScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                satScore.setText("");
            }
        });

        actScore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                actUser = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        actScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actScore.setText("");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(Objects.requireNonNull(getContext()).openFileOutput(json, Context.MODE_PRIVATE));
                    if(satUser != null){
                        satTv.setText(satUser);
                    }
                    if(actUser != null) {
                        actTv.setText(actUser);
                    }
                    data = "{SAT:" + satUser + ", ACT:" + actUser + "}";
                    outputStreamWriter.write(data);
                    outputStreamWriter.close();
                    Log.d("TAG", data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getContext()).openFileInput(json)));
            String currentLine;
            StringBuilder content = new StringBuilder();
            while ((currentLine = bufferedReader.readLine()) != null){
                content.append(currentLine);
            }
            JSONObject scores = new JSONObject(content.toString());
            if(StringUtils.isNumeric(scores.get("SAT").toString())) {
                Log.d("TAG", ""+scores.get("SAT"));
                satTv.setText("" + scores.get("SAT"));
            }
            if(StringUtils.isNumeric(scores.get("ACT").toString())) {
                Log.d("TAG", ""+scores.get("ACT"));
                actTv.setText("" + scores.get("ACT"));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return home;
    }
}
