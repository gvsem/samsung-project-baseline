package com.example.mylovelyproject.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mylovelyproject.MyLovelyApplication;
import com.example.mylovelyproject.R;
import com.example.mylovelyproject.controller.adapter.GroupAdapter;
import com.example.mylovelyproject.model.entity.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    private MyLovelyApplication app;
    private ListView groupListView;
    private GroupAdapter groupAdapter;
    private List<Group> groups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Groups");
        }

        app = (MyLovelyApplication) getApplication();

        groupListView = findViewById(R.id.studentListView);
        groupListView.setEmptyView(findViewById(android.R.id.empty));

        groupAdapter = new GroupAdapter(this, groups);
        groupListView.setAdapter(groupAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateGroups();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_groups, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_group:
                new GroupProfileDialogFragment().show(
                        getSupportFragmentManager(), GroupProfileDialogFragment.TAG);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateGroups() {
        new Thread(() -> {
            List<Group> groups = app.groupService.getGroups();
            runOnUiThread(() -> {
                groupAdapter.clear();
                groupAdapter.addAll(groups);
                groupAdapter.notifyDataSetChanged();
            });
        }).start();
    }

    public void groupDeletionConfirmed(Group group) {
        new Thread(() -> {
            try {
                app.groupService.deleteGroup(group);
                runOnUiThread(this::updateGroups);
            } catch (IllegalArgumentException e) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Group contains students", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }

}