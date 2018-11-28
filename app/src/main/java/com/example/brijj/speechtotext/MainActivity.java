package com.example.brijj.speechtotext;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener,RecognitionListener{
    EditText editText;
    ImageButton button;
    Intent intent;
    SpeechRecognizer recognizer;
 public static final int RECORDPERMISSION=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.text);
        button = findViewById(R.id.record);
        checkforpermission();
        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        button.setOnTouchListener(this);
    }
    private void checkforpermission()
    {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.RECORD_AUDIO
                    },RECORDPERMISSION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==RECORDPERMISSION)
        {
            if (grantResults[0]!=PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getApplicationContext(),"you donnt have camera permission",Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    { if(view.getId()==R.id.record)
    {
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_UP:
                              recognizer.stopListening();
                              editText.setText("");
                             editText.setHint("You will the text here ");
                              break;
            case MotionEvent.ACTION_DOWN:
                              recognizer.startListening(intent);
                             editText.setText("");
                             editText.setHint("Listening....");
                             break;

        }
    }
        return false;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onResults(Bundle bundle)
    {
        ArrayList<String> matches=bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if(matches!=null)
        {
            editText.setText(matches.get(0));
        }

    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }
}
