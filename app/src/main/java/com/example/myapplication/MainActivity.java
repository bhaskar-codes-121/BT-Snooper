package com.example.myapplication;
/*
Reference:
https://stackoverflow.com/questions/19452269/android-set-text-to-textview/43610226
https://stackoverflow.com/questions/11255568/how-to-read-output-of-android-process-command
 */
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {
    private static final String DEBUG_TAG= "MySimpleAppLogging";
    Button mButton;
    EditText mEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEdit   = (EditText)findViewById(R.id.myeditText);
        mButton = (Button)findViewById(R.id.button);


//in your OnCreate() method

        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        Log.v("EditText", mEdit.getText().toString());
                        String filename = mEdit.getText().toString();
                        if(TextUtils.isEmpty(filename)) {
                            //by default, it reads a log file from WhatsApp
                            filename = "/data/data/com.whatsapp/files/Logs/whatsapp.log";
                        }
                        Toast.makeText(getBaseContext(), "Reading file "+filename+" ..." , Toast.LENGTH_SHORT ).show();
                        TextView myAwesomeTextView = (TextView)findViewById(R.id.mytextView);
                        myAwesomeTextView.setText(read_file(filename));
                    }
                });
    }

    public static StringBuilder read_file(String filename){

        //String[] command = {"df", "-h", "/"};
        //String[] command = {"su", "-c", "ls", "-s"};
        //     String filename = "/data/data/com.whatsapp/files/Logs/whatsapp.log";
        String[] command = {"su", "-c", "cat", filename};
        //    String[] command = {"pwd"};
        StringBuilder cmdReturn = new StringBuilder();
        Log.i(DEBUG_TAG,"this is informational");
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            int c;
            while ((c = inputStream.read()) != -1) {
                cmdReturn.append((char) c);
            }
            Log.i(DEBUG_TAG,"ssssss"+cmdReturn.toString());
            if(cmdReturn.toString() == ""){
                Log.i(DEBUG_TAG,"kkkkkkkk");
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return cmdReturn;
    }
}