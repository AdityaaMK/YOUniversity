package com.adityaamk.youniversity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListFragment extends Fragment {
    private ArrayList<University> universities, uni2;
    private CustomAdapter customAdapter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button save;

    void updateList(ArrayList<University> unis){
        universities = unis;
    }

    void updateList2(ArrayList<University> unis){
        universities = unis;
        customAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = view.findViewById(R.id.id_lv);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final Gson gson = new Gson();
        String json = sharedPreferences.getString(getString(R.string.project_id), null);
        Type type = new TypeToken<ArrayList<University>>() {}.getType();
        universities = gson.fromJson(json, type);

        customAdapter = new CustomAdapter(Objects.requireNonNull(getContext()),R.layout.layout_listitem,universities);
        if(universities!=null) listView.setAdapter(customAdapter);
        return view;
    }

    public class CustomAdapter extends ArrayAdapter<University> {
        Context context;
        List list;

        public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<University> objects) {
            super(context, resource, objects);
            this.context = context;
            list = objects;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            @SuppressLint("ViewHolder") View layoutView = layoutInflater.inflate(R.layout.layout_listitem,null);
            TextView rank = layoutView.findViewById(R.id.id_rank);
            TextView name = layoutView.findViewById(R.id.id_name);
            Button up = layoutView.findViewById(R.id.id_btnUp);
            Button down = layoutView.findViewById(R.id.id_btnDown);
            Button delete = layoutView.findViewById(R.id.id_del);

            //Change button was not working so replaced with up and down buttons
//            change.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try {
//                        //openDialog();
//
//                    }catch (IndexOutOfBoundsException e){
//                        e.printStackTrace();
//                    }
//
//                }
//            });

            up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        University temp = universities.get(position);
                        University temp2 = universities.get(position - 1);
                        universities.set(position, temp2);
                        universities.set(position - 1, temp);
                        notifyDataSetChanged();
                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        editor = sharedPreferences.edit();
                        final Gson gson = new Gson();
                        String json = gson.toJson(universities);
                        editor.putString(getString(R.string.project_id), json);
                        editor.apply();
                    }catch (IndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                }
            });

            down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        University temp = universities.get(position);
                        University temp2 = universities.get(position + 1);
                        universities.set(position, temp2);
                        universities.set(position + 1, temp);
                        notifyDataSetChanged();
                        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        editor = sharedPreferences.edit();
                        final Gson gson = new Gson();
                        String json = gson.toJson(universities);
                        editor.putString(getString(R.string.project_id), json);
                        editor.apply();
                    }catch (IndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    universities.remove(position);
                    notifyDataSetChanged();
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    editor = sharedPreferences.edit();
                    final Gson gson = new Gson();
                    String json = gson.toJson(universities);
                    editor.putString(getString(R.string.project_id), json);
                    editor.apply();
                }
            });
            name.setText(universities.get(position).getName());
            rank.setText(""+(position+1));
            return layoutView;
        }
    }

    public void openDialog(){
        ChangeDialog changeDialog = new ChangeDialog();
        changeDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "Change Dialog");
    }

}
