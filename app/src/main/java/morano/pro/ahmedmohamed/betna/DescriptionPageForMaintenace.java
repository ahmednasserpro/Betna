package morano.pro.ahmedmohamed.betna;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DescriptionPageForMaintenace extends AppCompatActivity {

    String name;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_page_for_maintenace);
        setTitle(getString(R.string.app_name));


        Intent intent = getIntent();
        name = intent.getExtras().getString("Name");
        int image = intent.getExtras().getInt("Image");
        final String description = intent.getExtras().getString("Description");
        pos = intent.getExtras().getInt("Position");

        ImageView serviceImage = (ImageView) findViewById(R.id.service_image);
        serviceImage.setImageResource(image);

        TextView serviceName = (TextView) findViewById(R.id.service_name);
        serviceName.setText(name);

        TextView serviceDescription = (TextView) findViewById(R.id.service_description);
        serviceDescription.setText(description);


        Button startNow = findViewById(R.id.start_now_button);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/cairo_regular.ttf");
        startNow.setTypeface(typeface);

        startNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent descriptionPageIntent = new Intent
                        (DescriptionPageForMaintenace.this,
                                SendOrderForMaintenance.class);

                descriptionPageIntent.putExtra("Name", name);
                descriptionPageIntent.putExtra("Position", pos);
                startActivity(descriptionPageIntent);
            }
        });

    }
}
