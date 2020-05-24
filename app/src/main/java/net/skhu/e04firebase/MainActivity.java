package net.skhu.e04firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.example.e04firebase.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void button_clicked(View view)
    {
        Intent intent = new Intent(this, Firebase1Activity.class);
        startActivity(intent);
    }
}
