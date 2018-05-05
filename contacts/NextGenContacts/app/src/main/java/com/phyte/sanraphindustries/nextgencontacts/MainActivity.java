package com.phyte.sanraphindustries.nextgencontacts;

import android.Manifest;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.Contacts;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements ListActivity {
    SimpleCursorAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor = getContentResolver().query(Contacts.People.CONTENT_URI, null, null, null, null);
        startManagingCursor(cursor);

        String[] columns = new String[]{Contacts.People.NAME};
        int[] names = new int[]{R.id.entry_row};


        mAdapter = new SimpleCursorAdapter(this, R.layout.activity_main, cursor, columns, names);
        setListAdapter(mAdapter);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(Intent.ACTION_CALL);

        long phoneId;
        try (Cursor cursor = (Cursor) mAdapter.getItem(position)) {
            phoneId = cursor.getLong(cursor.getColumnIndex(Contacts.People.PRESENCE_STATUS));
        }
        final Intent intent1 = Intent.setData(Contacts.People.CONTENT_URI.addId(phoneId));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }
}
