package com.example.mylovelyproject.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.mylovelyproject.MyLovelyApplication;
import com.example.mylovelyproject.R;
import com.example.mylovelyproject.model.entity.Group;
import com.example.mylovelyproject.service.GroupService;

public class GroupProfileDialogFragment extends DialogFragment {

    public static String TAG = "CreateGroupDialogFragment";

    private GroupService groupService;

    private Group group = null;
    private Boolean isForEditing = false;

    public GroupProfileDialogFragment() {
        super();
    }

    public GroupProfileDialogFragment(Group group) {
        super();
        this.group = group;
        this.isForEditing = true;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        groupService = ((MyLovelyApplication) (requireActivity().getApplication())).groupService;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LinearLayout view = new LinearLayout(getActivity());
        view.setOrientation(LinearLayout.VERTICAL);

        final EditText noEditText = new EditText(getActivity());
        noEditText.setHint(R.string.group_no);
        final EditText facultyEditText = new EditText(getActivity());
        facultyEditText.setHint(R.string.group_faculty);

        view.addView(noEditText);
        view.addView(facultyEditText);
        builder.setView(view);

        if (isForEditing) {
            noEditText.setText(group.getNo());
            facultyEditText.setText(group.getFacultyName());
        }

        builder.setMessage(R.string.create_group)
                .setPositiveButton(R.string.action_ok, (dialog, id) -> {

                    if (!isForEditing) {
                        group = new Group();
                    }

                    group.setNo(noEditText.getText().toString());
                    group.setFacultyName(facultyEditText.getText().toString());

                    if (group.getNo() == null || group.getNo().length() == 0) {
                        return;
                    }

                    if (group.getFacultyName() == null || group.getFacultyName().length() == 0) {
                        return;
                    }

                    new Thread(() -> {

                        if (!isForEditing) {
                            groupService.createGroup(group);
                        } else {
                            groupService.editGroup(group);
                        }

                        getActivity().runOnUiThread(() -> {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), isForEditing ? "Group updated" : "Group created", Toast.LENGTH_SHORT).show();
                            if (getActivity() instanceof GroupActivity) {
                                ((GroupActivity) getActivity()).updateGroups();
                            }
                        });
                    }).start();

                })
                .setNegativeButton(R.string.action_cancel, (dialog, id) -> {
                    dialog.cancel();
                });

        return builder.create();
    }

}
