package com.example.mylovelyproject.controller;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mylovelyproject.MyLovelyApplication;
import com.example.mylovelyproject.R;
import com.example.mylovelyproject.model.entity.Student;
import com.example.mylovelyproject.service.StudentService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StudentProfileActivity extends AppCompatActivity {

    private final Calendar myCalendar = Calendar.getInstance();

    private Long groupId = null;
    private Long studentId = null;
    private Student student = null;
    private boolean isForEditing = false;

    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private EditText patronymicEditText;
    private EditText dateEditText;

    private StudentService studentService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        studentService = ((MyLovelyApplication) getApplication()).studentService;

        groupId = getIntent().getLongExtra("groupId", -1);
        if (groupId == -1) {
            finish();
        }

        studentId = getIntent().getLongExtra("id", -1);
        if (studentId == -1) { studentId = null; }
        isForEditing = studentId != null;

        if (getSupportActionBar() != null) {
            if (!isForEditing) {
                getSupportActionBar().setTitle("Create student");
            } else {
                getSupportActionBar().setTitle("Edit student");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        firstnameEditText = findViewById(R.id.firstnameEditText);
        lastnameEditText = findViewById(R.id.lastnameEditText);
        patronymicEditText = findViewById(R.id.patronymicEditText);
        dateEditText = findViewById(R.id.dateEditText);

        Button saveButton = findViewById(R.id.saveStudentButton);
        ImageButton deleteButton = findViewById(R.id.deleteStudentButton);

        if (isForEditing) {
            new Thread(() -> {
                student = studentService.getStudent(studentId);
                runOnUiThread(() -> {
                    firstnameEditText.setText(student.getFirstname());
                    lastnameEditText.setText(student.getLastname());
                    patronymicEditText.setText(student.getPatronymic());
                    setDate(student.getDateOfBirthAsDate());
                });
            }).start();
        }

        saveButton.setOnClickListener((v) -> {

            if (!isForEditing) {
                student = new Student();
            }

            student.setFirstname(firstnameEditText.getText().toString());
            student.setLastname(lastnameEditText.getText().toString());
            student.setPatronymic(patronymicEditText.getText().toString());
            student.setDateOfBirthAsDate(getDate());

            if (student.getFirstname() == null || student.getFirstname().length() == 0) {
                return;
            }

            if (student.getLastname() == null || student.getLastname().length() == 0) {
                return;
            }

            if (student.getDateOfBirthAsDate() == null) {
                return;
            }

            new Thread(() -> {
                if (!isForEditing) {
                    student.setGroupId(groupId);
                    student = studentService.createStudent(student);
                    studentId = student.getId();
                } else {
                    studentService.editStudent(student);
                }

                runOnUiThread(() -> {
                    finish();
                    if (!isForEditing) {
                        deleteButton.setVisibility(View.VISIBLE);
                        isForEditing = true;
                    }
                });

            }).start();
        });

        if (!isForEditing) {
            deleteButton.setVisibility(View.GONE);
        }

        deleteButton.setOnClickListener((v) -> {
            if (isForEditing) {
                new Thread(() -> {
                    try {
                        studentService.deleteStudent(student);
                        runOnUiThread(this::finish);
                    } catch (IllegalArgumentException e) {
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Failed to delete student", Toast.LENGTH_SHORT).show();
                        });
                    }
                }).start();
            }
        });

    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);

    private void setDate(LocalDate date) {
        if (date == null) {
            return;
        }
        Date d = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        dateEditText.setText(dateFormat.format(d));
    }

    private LocalDate getDate() {
        try {
            return dateFormat.parse(dateEditText.getText().toString()).toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
