package com.adityaamk.youniversity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Objects;

public class ChangeDialog extends AppCompatDialogFragment {
    private EditText one, two;
    private ChangeDialogListener listener;
    //Did not use, originally for Change button instead of up and down arrows

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialogbro, null);

        builder.setView(view)
                .setTitle("Enter Numbers")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String posOne = one.getText().toString();
                        String posTwo = two.getText().toString();
                        listener.applyTexts(posOne, posTwo);
                    }
                });

        one = view.findViewById(R.id.editText2);
        two = view.findViewById(R.id.editText);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ChangeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Must implement ChangeDialogListener");
        }
    }

    public interface ChangeDialogListener {
        void applyTexts(String posOne, String posTwo);
    }
}
