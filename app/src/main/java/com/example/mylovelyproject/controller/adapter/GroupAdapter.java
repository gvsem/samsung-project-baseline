package com.example.mylovelyproject.controller.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mylovelyproject.R;
import com.example.mylovelyproject.controller.GroupActivity;
import com.example.mylovelyproject.controller.GroupProfileDialogFragment;
import com.example.mylovelyproject.controller.StudentActivity;
import com.example.mylovelyproject.model.entity.Group;

import java.util.List;

public class GroupAdapter extends ArrayAdapter<Group> {

    public GroupAdapter(@NonNull Context context, @NonNull List<Group> groups) {
        super(context, R.layout.item_group, groups);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_group, null);
        }

        TextView noTextView = convertView.findViewById(R.id.groupNoTextView);
        TextView facultyTextView = convertView.findViewById(R.id.facultyTextView);

        Group group = getItem(position);

        noTextView.setText(group.getNo() != null ? group.getNo() : "№ Unknown");
        facultyTextView.setText(group.getFacultyName() != null ? group.getFacultyName() : "Faculty of Wonder");

        ImageButton editGroupButton = convertView.findViewById(R.id.editGroupButton);
        editGroupButton.setOnClickListener((v) -> {
            new GroupProfileDialogFragment(group).show(
                    ((AppCompatActivity) getContext()).getSupportFragmentManager(), GroupProfileDialogFragment.TAG);
        });

        ImageButton deleteGroupButton = convertView.findViewById(R.id.deleteGroupButton);
        deleteGroupButton.setOnClickListener((v) -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Delete group")
                    .setMessage("Do you really want to delete group?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        if (getContext() instanceof GroupActivity) {
                            ((GroupActivity) (getContext())).groupDeletionConfirmed(group);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        });


        convertView.setOnClickListener((v) -> {
            getContext().startActivity(
                    new Intent(getContext(), StudentActivity.class).putExtra("id", group.getId())
            );
        });

        return convertView;
    }
}
