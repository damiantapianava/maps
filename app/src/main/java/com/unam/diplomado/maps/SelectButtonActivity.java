package com.unam.diplomado.maps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class SelectButtonActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button btn_map;
    private Button btn_admon;
    private Button btn_picker;

    private final int PLACE_PICKER_REQUEST = 321;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_button);

        btn_map    = (Button) findViewById(R.id.btn_maps);
        btn_admon  = (Button) findViewById(R.id.btn_admon);
        btn_picker = (Button) findViewById(R.id.btn_picker);

        btn_map.setOnClickListener(this);
        btn_admon.setOnClickListener(this);
        btn_picker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Log.d("MAPS", "ID = " + v.getId());
        switch (v.getId())
        {
            case R.id.btn_maps:
                Log.d("MAPS", "btn_maps");
                startActivity(new Intent(SelectButtonActivity.this, MapsActivity.class));
                break;

            case R.id.btn_admon:
                Log.d("MAPS", "btn_admon");
                startActivity(new Intent(SelectButtonActivity.this, AdMobActivity.class));
                break;

            case R.id.btn_picker:
                Log.d("MAPS", "btn_picker");
                try
                {
                    startActivityForResult(new PlacePicker.IntentBuilder().build(SelectButtonActivity.this), PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {

                    e.printStackTrace();

                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PLACE_PICKER_REQUEST)
        {
            if(data != null)
            {
                Place place = PlacePicker.getPlace(SelectButtonActivity.this, data);

                Toast.makeText(SelectButtonActivity.this, place.getAddress(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
