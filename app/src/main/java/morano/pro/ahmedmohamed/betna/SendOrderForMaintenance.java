package morano.pro.ahmedmohamed.betna;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class SendOrderForMaintenance extends AppCompatActivity {

    private int position;
    private boolean state;
    private String serviceName;
    private Spinner citiesNames;
    private String recipientMail;
    private String subject;
    private String message;
    private String password;
    private TextInputLayout nameEditTex;
    private TextInputLayout phoneEditText;
    private TextInputLayout addressEditText;
    private TextInputLayout informationEditText;
    private ValueEventListener listenerForEmail;
    private ValueEventListener listenerForPassword;
    private ValueEventListener listenerForState;
    private DatabaseReference databaseReferenceEmail;
    private DatabaseReference databaseReferencePassword;
    private DatabaseReference databaseReferenceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order_for_maintenance);
        setTitle(getString(R.string.app_name));


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        Intent intent = getIntent();
        serviceName = intent.getExtras().getString("Name");
        position = intent.getExtras().getInt("Position");

        TextView textView = findViewById(R.id.service_name_to_send);
        textView.setText(serviceName);

        nameEditTex = (TextInputLayout) findViewById(R.id.name_edit_text);

        phoneEditText = (TextInputLayout) findViewById(R.id.phone_number_edit_text);

        // For spinner
        citiesNames = findViewById(R.id.cities_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SendOrderForMaintenance.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.cities_names));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citiesNames.setAdapter(adapter);

        addressEditText = (TextInputLayout) findViewById(R.id.address_edit_text);

        informationEditText = (TextInputLayout) findViewById(R.id.fault_type_edit_text);


        Button maintenanceSubmitButton = (Button) findViewById(R.id.submit_button);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/cairo_regular.ttf");
        maintenanceSubmitButton.setTypeface(typeface);

        subject = "     From android phone:   " + serviceName;


        maintenanceSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (confirmInput()) {
                    if (isInternetAvailable() && recipientMail != null && state) {

                        setMessageContent();
                        sendEmail(SendOrderForMaintenance.this, recipientMail, password,
                                subject, message);

                    } else if (!state && recipientMail != null) {

                        AlertDialog.Builder dialog = new AlertDialog.Builder(
                                SendOrderForMaintenance.this);
                        dialog.setTitle(getString(R.string.no_service_title));
                        dialog.setMessage(getString(R.string.no_service));
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        dialog.create().show();

                    } else {

                        AlertDialog.Builder dialog = new AlertDialog.Builder(
                                SendOrderForMaintenance.this);
                        dialog.setMessage(getString(R.string.no_internet));
                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.create().show();

                    }

                }


            }
        });

    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetWork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetWork != null && activeNetWork.isConnectedOrConnecting();
        return isConnected;
    }

    private void sendEmail(Context mContext, String mRecipientMail, String mSubject,
                           String mMessage, String mPassword) {
        //Creating SendMail object
        SendMail sm = new SendMail(mContext, mRecipientMail,
                mSubject, mMessage, mPassword);

        //Executing sendmail to send email
        sm.execute();
    }

    public boolean validateUserName() {
        String userName = nameEditTex.getEditText().getText().toString().trim();
        if (userName.isEmpty()) {
            nameEditTex.setError(getString(R.string.name_error));
            return false;
        } else {
            nameEditTex.setError(null);
            return true;
        }
    }

    public boolean validatePhoneNumber() {
        String userPhone = phoneEditText.getEditText().getText().toString().trim();
        if (userPhone.isEmpty()) {
            phoneEditText.setError(getString(R.string.phone_number_error));
            return false;
        } else {
            phoneEditText.setError(null);
            return true;
        }
    }

    public boolean confirmInput() {

        if (!validateUserName() | !validatePhoneNumber()) {
            Toast.makeText(this, getString(R.string.empty_field_toast),
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    public void setMessageContent() {

        message = "\nService name: " + serviceName;
        message += "\nPerson name: " + nameEditTex.getEditText().getText().toString().trim();
        message += "\nPhone number: " + phoneEditText.getEditText().getText().toString().trim();
        message += "\nArea: " + citiesNames.getSelectedItem().toString();
        message += "\nAddress: " + addressEditText.getEditText().getText().toString().trim();
        message += "\nInformation about the required service: \n" +
                informationEditText.getEditText().getText().toString().trim();

    }

    @Override
    protected void onStart() {
        super.onStart();

        listenerForEmail = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipientMail = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listenerForPassword = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                password = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listenerForState = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                state = (boolean) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        databaseReferenceEmail =
                FirebaseDatabase.getInstance().getReference("email");
        databaseReferenceEmail.addValueEventListener(listenerForEmail);


        databaseReferencePassword =
                FirebaseDatabase.getInstance().getReference("password");
        databaseReferencePassword.addValueEventListener(listenerForPassword);

        databaseReferenceState = FirebaseDatabase.getInstance().getReference("state").child(position + "");
        databaseReferenceState.addValueEventListener(listenerForState);

    }


    @Override
    protected void onStop() {
        super.onStop();
        databaseReferenceEmail.removeEventListener(listenerForEmail);
        databaseReferencePassword.removeEventListener(listenerForPassword);
    }
}