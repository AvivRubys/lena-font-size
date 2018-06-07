package rubys.aviv.fontsize2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private void setFontScale(float scale) {
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

    private void setFontScaleFromUI(Context context) {
        final EditText fontScale = findViewById(R.id.fontScale);

        try {
            float newFontScale = Float.parseFloat(fontScale.getText().toString());
            setFontScale(newFontScale);
        } catch (Exception error) {
            Toast.makeText(context, "Failed to parse float", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setFontScaleFromUI(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = this;
        final Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFontScaleFromUI(context);
            }
        });
    }
}
