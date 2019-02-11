package se.j.androidprojekt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    RadarView mRadarView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mRadarView = (RadarView) findViewById(R.id.radarView);
        mRadarView.setShowCircles(true);
        if (mRadarView != null) mRadarView.startAnimation();
    }

    public void SettingsButtonClicked(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}
