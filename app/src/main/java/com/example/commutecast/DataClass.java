package com.example.commutecast;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DataClass extends BottomSheetDialogFragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    public static final String TAG = "AddNewTask";
    private TextView setAlarm;
    private TextView setCalendar;
    private EditText mReminderName, mLocation;
    private Button mCreateReminder;
    private FirebaseFirestore firestore;
    private Context context;
    private ImageButton buttonTimePicker;
    private ImageButton buttonDatePicker;

    public static DataClass newInstance() {
        return new DataClass();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.activity_adding_reminder, container, false);

        setAlarm = rootView.findViewById(R.id.textViewLocationAlarm);
        setCalendar = rootView.findViewById(R.id.textViewCalendar);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonTimePicker = view.findViewById(R.id.imageBtnAlarm);
        buttonDatePicker = view.findViewById(R.id.imageBtnCalendar);
        setAlarm = view.findViewById(R.id.textViewLocationAlarm);
        mReminderName = view.findViewById(R.id.tName);
        mLocation = view.findViewById(R.id.tLocation);
        mCreateReminder = view.findViewById(R.id.createReminderBtn);

        firestore = FirebaseFirestore.getInstance();

        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment(DataClass.this);
                timePicker.show(getChildFragmentManager(), "time picker");
            }
        });

        buttonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment(DataClass.this);
                datePicker.show(getChildFragmentManager(), "date picker");
            }
        });

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

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        setAlarm.setText("Hour: " + hourOfDay + " Minute: " + minute);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        setCalendar.setText(currentDateString);
    }
}

