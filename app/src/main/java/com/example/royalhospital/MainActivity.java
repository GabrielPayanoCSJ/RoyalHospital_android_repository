package com.example.royalhospital;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    Button btn_goBack;
    View myView;
    LinearLayout btnContainer;
    FrameLayout myFrame;
    String videoPath;
    Uri videoUri;
    VideoView videoView;
    String[] optionList;
    String[] descriptionList;
    TextView optionTitle, option_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        myView = findViewById(R.id.my_view);
        //myButton = findViewById(R.id.my_button);
        btn_goBack = findViewById(R.id.btn_goBack);
        btn_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDown(myView);
            }
        });

        // initialize as invisible (could also do in xml)
        myView.setVisibility(View.INVISIBLE);
        //myButton.setText("Slide up");
        btnContainer = findViewById(R.id.btn_container);
        myFrame = findViewById(R.id.myFrame);


        //INITIALIZE THE USER OPTIONS DESCRIPTION AND TITLE FOR THE POPUP
        optionList = getResources().getStringArray(R.array.option_list);
        descriptionList = getResources().getStringArray(R.array.description_list);

        //INITIALIZE THE TEXTVIEWS FOR THE TITLE AND THE OPTION DESCRIPTION IN THE POPUP
        optionTitle = findViewById(R.id.option_title);
        option_description = findViewById(R.id.option_description);

        //VIDEO
        videoView = findViewById(R.id.video_view);
        videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        videoUri = Uri.parse(videoPath);
        videoView.setVideoURI(videoUri);

        //Define the video controller for the option selected
        MediaController videoController = new MediaController(this);
        videoView.setMediaController(videoController);
        videoController.setAnchorView(videoView);



        //Load the buttons with de manual options
        updateOption();
    }

    //METHODS
    // slide the view from below itself to the current position
    public void showUp(View view, int id) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                view.getHeight(),
                0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);

        deactivateButtons();
        updateSlideViewText(id);
    }

    //CHANGE THE TEXT ON THE POPUP BASED ON THE BUTTON PRESSED
    public void updateSlideViewText(int id) {

        optionTitle.setText(optionList[id]);
        option_description.setText(descriptionList[id]);
        loadVideo(id);
    }

    // slide the view from its current position to below itself
    public void showDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,
                0,
                0,
                view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        activateButtons();
    }

    //METHOD THAT DEACTIVATE THE WHOLE LIST OF BUTTONS IN THE MAIN VIEW
    public void deactivateButtons() {
        for (int i = 0; i < btnContainer.getChildCount(); i++) {
            Button btn = (Button) btnContainer.getChildAt(i);
            btn.setEnabled(false);
            myFrame.setVisibility(LinearLayout.VISIBLE);
        }
    }

    //METHOD THAT RE-ACTIVATE THE WHOLE LIST OF BUTTONS IN THE MAIN VIEW
    public void activateButtons() {
        for (int i = 0; i < btnContainer.getChildCount(); i++) {
            Button btn = (Button) btnContainer.getChildAt(i);
            btn.setEnabled(true);
            myFrame.setVisibility(LinearLayout.GONE);
        }
    }

    // ADD AN ONCLICK LISTENER ON THE BUTTONS IN THE MAIN VIEW AND CHANGE THE TEXT ON THEN BASED ON THE STRING[] "optionList"
    public void updateOption() {

        for (int i = 0; i < btnContainer.getChildCount(); i++) {
            Button btn = (Button) btnContainer.getChildAt(i);
            btn.setId(i);
            btn.setText(optionList[i]);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showUp(myView, btn.getId());
                }
            });

        }
    }

    // CHANGE THE VIDEO TO SHOW ON THE SLIDE VIEW BASED ON THE OPTION SELECTED
    public void loadVideo(int id){
        switch (id){
            case 0:
                //Toast.makeText(this, "OPCION 0", Toast.LENGTH_SHORT).show();
                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.ftp_initserver;
                break;
            case 1:
                //Toast.makeText(this, "OPCION 1", Toast.LENGTH_SHORT).show();
                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.ftp_iniciarsesionftp;
                break;
            case 2:
                //Toast.makeText(this, "OPCION 2", Toast.LENGTH_SHORT).show();
                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.ftp_subirarchivo;
                break;
            case 3:
                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.ftp_descargararchivo;
                break;
            case 4:
                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.newvideo;
                break;
            case 5:
                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.ftp_borrarcarpeta;
                break;
            case 6:
                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.ftp_borrararchivo;
                break;
            case 7:
                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.ftp_desconectar;
                break;
            case 8:
                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.loginemail;
                break;
            case 9:
                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.email_enviodecorreo;
                break;
            case 10:
                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.email_leercorreo;
                break;
            case 11:
                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.email_botonsalircorreo;
                break;
            default:
                videoPath = "android.resource://" + getPackageName() + "/" + R.raw.newvideo;
                break;
        }

        videoUri = Uri.parse(videoPath);
        videoView.setVideoURI(videoUri);
    }
}