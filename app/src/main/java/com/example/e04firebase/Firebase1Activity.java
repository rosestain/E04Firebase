package com.example.e04firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Firebase1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase1);

        final DatabaseReference myData01 = FirebaseDatabase.getInstance().getReference("myData01");
        ValueEventListener listener1 = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                String value = dataSnapshot.getValue(String.class);
                TextView textView = (TextView)findViewById(R.id.textView);
                textView.setText(value);
                Log.d("내태그", "받은 데이터" + value);
            }

            @Override
            public void onCancelled(DatabaseError error)
            {
                Log.e("내태그", "서버 에러", error.toException());
            }
        };
        myData01.addValueEventListener(listener1);

        Button button = (Button)findViewById(R.id.btnSaveIntoServer);
        View.OnClickListener listener2 = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EditText editText = (EditText)findViewById(R.id.editText);
                String s = editText.getText().toString();
                myData01.setValue(s);
            }
        };
        button.setOnClickListener(listener2);
    }
}
