package com.example.commutecast;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DataClass extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";
    private TextView setAlarm;
    private EditText mReminderName, mLocation;
    private Button mCreateReminder;
    private FirebaseFirestore firestore;
    private Context context;

    public static DataClass newInstance() {
        return new DataClass();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_adding_reminder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setAlarm = view.findViewById(R.id.textViewLocationAlarm);
        mReminderName = view.findViewById(R.id.tName);
        mLocation = view.findViewById(R.id.tLocation);
        mCreateReminder = view.findViewById(R.id.createReminderBtn);

        firestore = FirebaseFirestore.getInstance();

        mReminderName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")) {
                    mCreateReminder.setEnabled(false);
                    mCreateReminder.setBackgroundColor(Color.GRAY);
                }
                else {
                    mCreateReminder.setEnabled(true);
                    mCreateReminder.setBackgroundColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCreateReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reminderName = mReminderName.getText().toString();
                String yourLocation = mLocation.getText().toString();

                if(reminderName.isEmpty() && yourLocation.isEmpty()) {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String, Object> taskMap = new HashMap<>();
                    taskMap.put("task", reminderName);
                    taskMap.put("location", yourLocation);
                    taskMap.put("status", 0);

                    firestore.collection("task").add(taskMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                dismiss();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof OnDialogCloseListener) {
            ((OnDialogCloseListener) activity).onDialogClose(dialog);
        }
    }
}

