package com.example.commutecast;

import static android.app.Activity.RESULT_OK;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class Home extends Fragment {

    FloatingActionButton addTaskBtn;
    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    ToDoAdapter adapter;
    private List<ToDoModel> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        addTaskBtn = view.findViewById(R.id.floationActionButton);
        firestore = FirebaseFirestore.getInstance();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataClass.newInstance().show(getActivity().getSupportFragmentManager(), DataClass.newInstance().getTag());
            }
        });

        mList = new ArrayList<>();
        adapter = new ToDoAdapter((MainActivity) requireActivity(), mList);

        recyclerView.setAdapter(adapter);
        showData();
        return view;
    }

    private void showData() {
        firestore.collection("tasks").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    for (DocumentChange documentChange : value.getDocumentChanges()) {
                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                            String id = documentChange.getDocument().getId();
                            Map<String, Object> data = documentChange.getDocument().getData();
                            String task = (String) data.get("task");
                            String location = (String) data.get("location");
                            String date = (String) data.get("date");
                            // Retrieve other fields as needed

                            ToDoModel toDoModel = new ToDoModel();
                            toDoModel.setId(id);
                            toDoModel.setTask(task);
                            toDoModel.setLocation(location);
                            toDoModel.setDate(date);
                            // Set other fields as needed

                            mList.add(toDoModel);
                            Log.d("FirestoreData", "Task: " + toDoModel.getTask() + ", Location: " + toDoModel.getLocation());
                        }
                    }
                }
                Collections.reverse(mList);
                MainActivity activity = (MainActivity) requireActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }
}