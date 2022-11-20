package com.finalteam6.HighResAudioRecorder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Debug
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.File
import java.util.*

class fragment_record : Fragment() {
    private var HRARMediaRecorder:android.media.MediaRecorder? = null;
    private var isRecording:Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Request microphone permissions
        val requestPermissionLauncher =registerForActivityResult(ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted)
            {
                Log.d("TAG","We were able to give permissions.");
            }
            else
            {
                Log.d("TAG","We did not get to give permissions.");
            }
        }
        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO);

        //Add button listener
        val fragment_record_Record_Button = view.findViewById<ImageButton>(R.id.fragment_record_Record_Button);
        fragment_record_Record_Button.setOnClickListener{clicked_fragment_record_Record_Button();}
    }

    private fun clicked_fragment_record_Record_Button()
    {
        if(isRecording)
        {
            stopRecording();
        }
        else
        {
            startRecording();
        }

        //Flip whether we are recording
        isRecording = !isRecording;
    }

    private fun prepareRecording()
    {

    }

    private fun startRecording()
    {
        if(ContextCompat.checkSelfPermission(Globals.MainActivitySingleton!!.applicationContext,Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)
        {
            Log.d("TAG", "Did have permission");
        }
        else
        {
            Log.d("TAG", "Did NOT have permission");
        }

        val state = Environment.getExternalStorageState();
        Log.d("TAG", state);
        val ctx: Context = Globals.MainActivitySingleton!!.applicationContext;
        val audioDir = File(ctx.getExternalFilesDir(Environment.DIRECTORY_DCIM), "HighResAudioRecordings");
        audioDir.mkdirs();
        val audioDirPath: String = audioDir.absolutePath;
        Log.d("TAG", "Recording file location: $audioDirPath");
        val currentTime: Date = Calendar.getInstance().time;
        val curTimeStr: String = currentTime.toString().replace(" ", "_");
        val recordingFile = File("$audioDirPath/$curTimeStr.ogg");
        Log.d("TAG", "Created file: " + recordingFile.name);

        HRARMediaRecorder = android.media.MediaRecorder();
        HRARMediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC);
        HRARMediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.OGG);
        HRARMediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.VORBIS);
        HRARMediaRecorder!!.setOutputFile(recordingFile.absolutePath);
        HRARMediaRecorder!!.prepare();

        HRARMediaRecorder!!.start();
    }

    private fun stopRecording()
    {
        HRARMediaRecorder!!.stop();
        HRARMediaRecorder!!.reset();
        HRARMediaRecorder!!.release();
        Toast.makeText(Globals.MainActivitySingleton!!.applicationContext,"MESSAGEGOESHERE",Toast.LENGTH_LONG).show();
    }
}