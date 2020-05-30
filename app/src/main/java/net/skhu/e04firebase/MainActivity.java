package net.skhu.e04firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int RC_LOGIN = 1;
    FirebaseUser currentUser = null;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView_userName);
        showUserName();
    }

    public void button_clicked(View view)
    {
        Class classObj = null;
        switch(view.getId())
        {
            case R.id.button1:
                classObj = Firebase1Activity.class;
                break;

            case R.id.button2:
                classObj = MemoList1Activity.class;
                break;

            case R.id.button3:
                classObj = MemoList2Activity.class;
                break;

            case R.id.button4:
                classObj = MemoList3Activity.class;
                break;
        }
        Intent intent = new Intent(this, classObj);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem_login = menu.findItem(R.id.action_login);
        MenuItem menuItem_logout = menu.findItem(R.id.action_logout);
        menuItem_login.setVisible(currentUser == null);
        menuItem_logout.setVisible(currentUser != null);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.action_login)
        {
            startLoginActivity();
            return true;
        }
        else if(id == R.id.action_logout)
        {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void startLoginActivity()
    {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers)
                .build(),RC_LOGIN);
    }

    void showUserName()
    {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null)
        {
            textView.setText(currentUser.getDisplayName());
        }
        else
        {
            textView.setText("Anonymous");
        }
    }

    void logout()
    {
        FirebaseAuth.getInstance().signOut();
        showUserName();
        invalidateOptionsMenu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == RC_LOGIN)
        {
            IdpResponse response = IdpResponse.fromResultIntent(intent);
            if(resultCode == RESULT_OK)
            {
                Toast.makeText(MainActivity.this, "로그인 성공", Toast.LENGTH_LONG).show();
            }
            else
            {
                String message = "Authentication failure" + response.getError().getErrorCode()
                        + " " + response.getError().getMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
            showUserName();
            invalidateOptionsMenu();
        }
    }

}
