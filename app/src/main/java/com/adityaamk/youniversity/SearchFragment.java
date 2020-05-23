package com.adityaamk.youniversity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.jar.Attributes;

public class SearchFragment extends Fragment {
    private EditText user;
    private Button add;
    private TextView name, rate, sat, act, cost, debt;
    private ArrayList<University> universities;
    private String input, request;
    private StringBuilder content;
    private JSONObject data;
    private SearchFragmentListener searchFragmentListener;

    String url = "https://api.data.gov/ed/collegescorecard/v1/schools?school.name=princeton+university&fields=school.name,id,latest.admissions.admission_rate.overall,latest.admissions.act_scores.25th_percentile.cumulative,latest.admissions.act_scores.75th_percentile.cumulative,latest.admissions.sat_scores.25th_percentile.math,latest.admissions.sat_scores.75th_percentile.math,latest.admissions.sat_scores.25th_percentile.critical_reading,latest.admissions.sat_scores.75th_percentile.critical_reading,latest.cost.attendance.academic_year,latest.aid.median_debt.completers.overall&api_key=9DCEOKfwyuInWXJ8GTLRrEiudCqu3uZjMEMzM4Vd";


    public interface SearchFragmentListener{
        public void sendData(ArrayList<University> schools);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SearchFragmentListener) {
            searchFragmentListener = (SearchFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SearchFragmentListener");
        }
    }

    public void sendList(){
        Log.d("TAG",universities+"");
        searchFragmentListener.sendData(universities);
    }

    @Override
    public void onDetach() {
        searchFragmentListener = null;
        super.onDetach();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);
        universities = new ArrayList<>();
        user = view.findViewById(R.id.id_edName);
        Button search = view.findViewById(R.id.id_searchBtn);
        add = view.findViewById(R.id.id_add);
        name = view.findViewById(R.id.id_nameTv);
        rate = view.findViewById(R.id.id_rate);
        sat = view.findViewById(R.id.id_sat);
        act = view.findViewById(R.id.id_act);
        cost = view.findViewById(R.id.id_cost);
        debt = view.findViewById(R.id.id_debt);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setText("");
            }
        });

        user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input = s.toString();
                new NameGuide().execute(input);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UniversityData().execute(input);
            }
        });

        return view;
    }

    public class NameGuide extends  AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... input) {
            try {
                String encode = URLEncoder.encode(input[0], "UTF-8");
                Log.d("TAG", encode);
                request = "https://api.data.gov/ed/collegescorecard/v1/schools?school.name=" + encode + "&fields=school.name,id,latest.admissions.admission_rate.overall,latest.admissions.act_scores.25th_percentile.cumulative,latest.admissions.act_scores.75th_percentile.cumulative,latest.admissions.sat_scores.25th_percentile.math,latest.admissions.sat_scores.75th_percentile.math,latest.admissions.sat_scores.25th_percentile.critical_reading,latest.admissions.sat_scores.75th_percentile.critical_reading,latest.cost.attendance.academic_year,latest.aid.median_debt.completers.overall&api_key=LN3juTnlHfdAMGFmjsy7t9SniPWOx1WzFCU8lIeq";
                Log.d("TAG", request);
                URL url = new URL(request);
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String currentLine;
                content = new StringBuilder();
                while ((currentLine = bufferedReader.readLine()) != null){
                    content.append(currentLine);
                }
                Log.d("TAG", content.toString());
            }catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                data = new JSONObject(content.toString());
                Log.d("TAG", data.toString());
                JSONArray results = data.getJSONArray("results");
                JSONObject schoolInfo = results.getJSONObject(0);
                String schoolName = schoolInfo.getString("school.name");
                name.setText(schoolName);
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
    @SuppressLint("StaticFieldLeak")
    public class UniversityData extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                data = new JSONObject(content.toString());
                Log.d("TAG", data.toString());
                JSONArray results = data.getJSONArray("results");
                JSONObject schoolInfo = results.getJSONObject(0);
                String schoolName = schoolInfo.getString("school.name");
                Double admitRate = (Double) schoolInfo.get("latest.admissions.admission_rate.overall");
                double lowEnglish = (double) schoolInfo.get("latest.admissions.sat_scores.25th_percentile.critical_reading");
                double highEnglish = (double) schoolInfo.get("latest.admissions.sat_scores.75th_percentile.critical_reading");
                double lowMath = (double) schoolInfo.get("latest.admissions.sat_scores.25th_percentile.math");
                double highMath = (double) schoolInfo.get("latest.admissions.sat_scores.75th_percentile.math");
                int costInt = (int) schoolInfo.get("latest.cost.attendance.academic_year");
                double debtDub = (double) schoolInfo.get("latest.aid.median_debt.completers.overall");

                final University dream = new University(schoolName, highEnglish+highMath, lowEnglish+lowMath, 0.0, 0.0);

                name.setText(schoolName);
                sat.setText("SAT Range: " + (lowEnglish + lowMath) + " to " + (highEnglish + highMath));
                cost.setText("Cost of Attendance: $" + costInt);
                debt.setText("Average Debt: $"  + debtDub);
                rate.setText("Admissions Rate: " + (admitRate*100));
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(),"Added!", Toast.LENGTH_SHORT).show();
                        universities.add(dream);
                        sendList();
                        Log.d("TAG",dream+"");
                        Log.d("Tag",universities+"");
                        Log.d("TAg",universities.get(0).getName());
                    }
                });

                try {
                    double lowACT = (double) schoolInfo.get("latest.admissions.act_scores.25th_percentile.cumulative");
                    double highACT = (double) schoolInfo.get("latest.admissions.act_scores.75th_percentile.cumulative");
                    dream.setActHigh(highACT);
                    dream.setActLow(lowACT);
                    act.setText("ACT Range: "+lowACT+" to "+highACT);
                } catch (ClassCastException e){
                    act.setText("ACT Range: N/A");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected Void doInBackground(String... input) {
            try {
                String encode = URLEncoder.encode(input[0], "UTF-8");
                Log.d("TAG", encode);
                request = "https://api.data.gov/ed/collegescorecard/v1/schools?school.name=" + encode + "&fields=school.name,id,latest.admissions.admission_rate.overall,latest.admissions.act_scores.25th_percentile.cumulative,latest.admissions.act_scores.75th_percentile.cumulative,latest.admissions.sat_scores.25th_percentile.math,latest.admissions.sat_scores.75th_percentile.math,latest.admissions.sat_scores.25th_percentile.critical_reading,latest.admissions.sat_scores.75th_percentile.critical_reading,latest.cost.attendance.academic_year,latest.aid.median_debt.completers.overall&api_key=LN3juTnlHfdAMGFmjsy7t9SniPWOx1WzFCU8lIeq";
                Log.d("TAG", request);
                URL url = new URL(request);
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String currentLine;
                content = new StringBuilder();
                while ((currentLine = bufferedReader.readLine()) != null){
                    content.append(currentLine);
                }
                Log.d("TAG", content.toString());
            }catch (IOException e){
                e.printStackTrace();
            }

            return null;
        }
    }

}
