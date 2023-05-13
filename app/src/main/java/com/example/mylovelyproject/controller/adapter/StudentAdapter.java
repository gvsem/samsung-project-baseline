package com.example.mylovelyproject.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mylovelyproject.R;
import com.example.mylovelyproject.controller.StudentActivity;
import com.example.mylovelyproject.controller.StudentProfileActivity;
import com.example.mylovelyproject.model.entity.Group;
import com.example.mylovelyproject.model.entity.Student;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final Group group;

    public StudentAdapter(@NonNull Context context, @NonNull List<Student> students, @NonNull Group group) {
        super(context, R.layout.item_group, students);
        this.group = group;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_student, null);
        }

        TextView fioTextView = convertView.findViewById(R.id.fioTextView);
        TextView groupTextView = convertView.findViewById(R.id.groupTextView);
        TextView dateOfBirthTextView = convertView.findViewById(R.id.dateOfBirthTextView);

        Student student = getItem(position);

        fioTextView.setText(student.getFirstname() + " " + student.getLastname()
                + ((student.getPatronymic() != null) ? " " + student.getPatronymic() : ""));
        groupTextView.setText(group.getNo());
        dateOfBirthTextView.setText(
                dateFormatter.format(student.getDateOfBirthAsDate())
        );

        convertView.setOnClickListener((v) -> {
            getContext().startActivity(
                    new Intent(getContext(), StudentProfileActivity.class).putExtra("id", student.getId()).putExtra("groupId", student.getGroupId())
            );
        });


        return convertView;
    }
}
