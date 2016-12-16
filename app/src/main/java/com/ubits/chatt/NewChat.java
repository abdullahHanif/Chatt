package com.ubits.chatt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Abdullah
 */

public class NewChat
  extends AppCompatActivity {


  protected void onCreate(Bundle paramBundle) {
      super.onCreate(paramBundle);

      setContentView(R.layout.new_chat);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
      setSupportActionBar(toolbar);


      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);


      toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

  }

  public boolean onCreateOptionsMenu(Menu paramMenu) {
    getMenuInflater().inflate(R.menu.newchat, paramMenu);
    return super.onCreateOptionsMenu(paramMenu);
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
      int id = paramMenuItem.getItemId();

    if (paramMenuItem.getItemId() == R.id.menu_delete) {
      finish();
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }
}


