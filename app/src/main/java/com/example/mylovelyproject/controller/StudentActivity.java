package com.example.mylovelyproject.controller;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mylovelyproject.MyLovelyApplication;
import com.example.mylovelyproject.R;
import com.example.mylovelyproject.controller.adapter.StudentAdapter;
import com.example.mylovelyproject.model.entity.Group;
import com.example.mylovelyproject.model.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private long groupId;
    private String searchLastName;
    private MyLovelyApplication app;
    private ListView studentListView;
    private StudentAdapter studentAdapter;
    private List<Student> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        groupId = getIntent().getLongExtra("id", 0);
        searchLastName = getIntent().getStringExtra("searchLastname");

        if (getSupportActionBar() != null) {
            if (searchLastName == null) {
                getSupportActionBar().setTitle("Students");
            } else {
                getSupportActionBar().setTitle("Search lastname: " + searchLastName);
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        app = (MyLovelyApplication) getApplication();
        studentListView = findViewById(R.id.studentListView);
        studentListView.setEmptyView(findViewById(android.R.id.empty));

        new Thread(() -> {
            Group group = app.groupService.getGroup(groupId);
            if (group == null) {
                return;
            }
            runOnUiThread(() -> {
                studentAdapter = new StudentAdapter(this, students, group);
                studentListView.setAdapter(studentAdapter);
            });
        }).start();

    }

    @Override
    protected void onStart() {
        super.onStart();

        new Thread(() -> {
            List<Student> students;
            if (searchLastName == null) {
                students = app.studentService.getStudents(groupId);
            } else {
                students = app.studentService.searchStudentsByLastname(searchLastName, groupId);
            }

            runOnUiThread(() -> {
                studentAdapter.clear();
                studentAdapter.addAll(students);
                studentAdapter.notifyDataSetChanged();
            });
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().getStringExtra("searchLastname") == null) {
            getMenuInflater().inflate(R.menu.menu_students, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_create_student:
                startActivity(new Intent(this, StudentProfileActivity.class).putExtra("groupId", groupId));
                return true;
            case R.id.action_search_student:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                LinearLayout view = new LinearLayout(this);
                view.setOrientation(LinearLayout.VERTICAL);

                final EditText lastnameEditText = new EditText(this);
                lastnameEditText.setHint(R.string.student_lastname);
                view.addView(lastnameEditText);
                builder.setView(view);

                builder.setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    startActivity(new Intent(this, StudentActivity.class)
                            .putExtra("id", groupId)
                            .putExtra("searchLastname", lastnameEditText.getText().toString()));
                });

                builder.setNegativeButton(android.R.string.no, null);
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}