package com.example.commutecast;

import static android.content.Context.AUDIO_SERVICE;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;

import android.content.Context;
import androidx.fragment.app.Fragment;


public class CreateAlarm extends Fragment implements AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener {

    private ImageView soundImageView;
    private ImageView alarmImageView;
    private SeekBar soundSeekBar;
    private SeekBar alarmSeekBar;

    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    Button playBtn;
    Button pauseBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_alarm, container, false);

        audioManager = (AudioManager) requireActivity().getSystemService(Context.AUDIO_SERVICE);
        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        mediaPlayer = MediaPlayer.create(requireActivity(), R.raw.ringalarm);
        playBtn = view.findViewById(R.id.playBtn);
        pauseBtn = view.findViewById(R.id.pauseBtn);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(requireActivity(), R.raw.ringalarm);
                mediaPlayer.start();
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });

        Spinner alarmModeSpinner = view.findViewById(R.id.spinner);
        alarmImageView = view.findViewById(R.id.alarm);
        alarmSeekBar = view.findViewById(R.id.seekBar2);
        alarmSeekBar.setMax(maxVol);
        alarmSeekBar.setProgress(currentVol);

        alarmSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Context context = requireContext();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context, R.array.alarm_modes,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alarmModeSpinner.setAdapter(adapter);
        alarmModeSpinner.setOnItemSelectedListener(this);

        alarmSeekBar.setOnSeekBarChangeListener(this);

        return view;
    }

    public void setAlarm(View view) {
        Spinner alarmModeSpinner = view.findViewById(R.id.spinner);
        String selectedOption = alarmModeSpinner.getSelectedItem().toString();
        int soundLevel = soundSeekBar.getProgress();
        int alarmLevel = alarmSeekBar.getProgress();

        ImageView alarmImageView = view.findViewById(R.id.alarm);

        soundImageView.setImageResource(R.drawable.baseline_volume_up_24);
        alarmImageView.setImageResource(R.drawable.baseline_add_alert_24);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedOption = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}