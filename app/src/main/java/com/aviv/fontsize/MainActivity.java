package com.aviv.fontsize;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private float getViewFontScale() {
        EditText fontScaleText = findViewById(R.id.fontScale);
        return Float.parseFloat(fontScaleText.getText().toString());
    }

    @SuppressLint("SetTextI18n")
    private void setViewFontScale(float scale) {
        EditText fontScaleText = findViewById(R.id.fontScale);
        fontScaleText.setText(Float.toString(scale));
    }

    private float getSystemFontScale() {
        try {
            return Settings.System.getFloat(getContentResolver(), Settings.System.FONT_SCALE);
        } catch (Settings.SettingNotFoundException e) {
            return 1.0f;
        }
    }

    private void setSystemFontScale(float scale) {
        if (Settings.System.canWrite(this)) {
            Settings.System.putFloat(getContentResolver(), Settings.System.FONT_SCALE, scale);
            Toast.makeText(this, "Font scale changed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Need permissions to do that", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setSystemFontScale(getViewFontScale());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setViewFontScale(getSystemFontScale());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(view -> setSystemFontScale(getViewFontScale()));
    }
}