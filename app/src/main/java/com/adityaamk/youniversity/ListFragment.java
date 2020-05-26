package com.adityaamk.youniversity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListFragment extends Fragment {
    private ArrayList<University> universities;
    private CustomAdapter customAdapter;

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
        View rankFragmentView = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = rankFragmentView.findViewById(R.id.id_lv);

        customAdapter = new CustomAdapter(Objects.requireNonNull(getContext()),R.layout.layout_listitem,universities);
        if(universities!=null) listView.setAdapter(customAdapter);
        return rankFragmentView;
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
            @SuppressLint("ViewHolder") View layoutView = layoutInflater.inflate(R.layout.layout_listitem,null);
            TextView rank = layoutView.findViewById(R.id.id_rank);
            TextView name = layoutView.findViewById(R.id.id_name);
            Button change = layoutView.findViewById(R.id.id_change);
            Button delete = layoutView.findViewById(R.id.id_del);
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        openDialog();
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
                }
            });
            name.setText(universities.get(position).getName());
            rank.setText(""+(position+1));
            return layoutView;
        }
    }

    public void openDialog(){
        ChangeDIalog changeDIalog = new ChangeDIalog();
        changeDIalog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "Change Dialog");
    }

}
