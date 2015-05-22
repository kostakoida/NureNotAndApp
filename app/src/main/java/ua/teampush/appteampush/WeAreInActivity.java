package ua.teampush.appteampush;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class WeAreInActivity extends Activity implements View.OnClickListener{
    Button logout;
    final String LOGIN = "LOGIN";
    final String PASSWORD = "PASSWORD";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearein);
        setResult(RESULT_OK, new Intent()); //return 0 code to parent window (loading, login, register)
        logout = (Button) findViewById(R.id.logoutButton);
        logout.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loading, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){
        this.finishActivity(-1);
    }

    public void onClick(View v){ //DELETE SIME KIND OF CACHE
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor r = pref.edit();
        r.remove(LOGIN);
        r.remove(PASSWORD);
        r.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, -1);
    }

}