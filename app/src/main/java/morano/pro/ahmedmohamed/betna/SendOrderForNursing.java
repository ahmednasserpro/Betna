package morano.pro.ahmedmohamed.betna;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SendOrderForNursing extends AppCompatActivity {

    private Spinner areaSpinner;
    private Spinner patientGender;
    private String recipientMail;
    private String password;
    private String subject;
    private String message;
    private TextInputLayout nameEditTex;
    private TextInputLayout phoneEditText;
    private TextInputLayout addressEditText;
    private TextInputLayout numberOfDays;
    private ValueEventListener listenerForEmail;
    private ValueEventListener listenerForPassword;
    private DatabaseReference databaseReferenceEmail;
    private DatabaseReference databaseReferencePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order_for_nursing);
        setTitle(getString(R.string.app_name));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        nameEditTex = (TextInputLayout) findViewById(R.id.nursing_name_edit_text);

        phoneEditText = (TextInputLayout) findViewById(R.id.nursing_phone_number_edit_text);

        // Set drop down menu for cities names
        areaSpinner = findViewById(R.id.nursing_cities_spinner);
        ArrayAdapter<String> citiesNamesAdapter = new ArrayAdapter<>(
                SendOrderForNursing.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.cities_names));
        citiesNamesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        areaSpinner.setAdapter(citiesNamesAdapter);

        addressEditText = (TextInputLayout) findViewById(R.id.nursing_address_edit_text);

        // Set drop down menu for patient gender
        patientGender = findViewById(R.id.nursing_patient_gender_spinner);
        ArrayAdapter<String> patientSexAdapter = new ArrayAdapter<>(
                SendOrderForNursing.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.patient_gender));
        patientSexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientGender.setAdapter(patientSexAdapter);

        numberOfDays = (TextInputLayout) findViewById(R.id.nursing_number_of_days_edit_text);


        subject = "     From android phone:  Nursing service";


        Button nursingSubmitButton = (Button) findViewById(R.id.nursing_submit_button);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/cairo_regular.ttf");
        nursingSubmitButton.setTypeface(typeface);


        nursingSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (confirmInput()) {

                    if (isInternetAvailable() && recipientMail != null) {

                        setMessageContent();

                        sendEmail(SendOrderForNursing.this, recipientMail, password,
                                subject, message);

                    } else {

                        AlertDialog.Builder dialog = new AlertDialog.Builder(
                                SendOrderForNursing.this);
                        dialog.setMessage(getString(R.string.no_internet));
                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

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

    public void setMessageContent () {

        message = "\n   Nursing Service   ";
        message += "\nPerson name: " + nameEditTex.getEditText().getText().toString().trim();
        message += "\nPhone number: " + phoneEditText.getEditText().getText().toString().trim();
        message += "\nArea: " + areaSpinner.getSelectedItem().toString();
        message += "\nPatien gender: " + patientGender.getSelectedItem().toString();
        message += "\nAddress: " + addressEditText.getEditText().getText().toString().trim();
        message += "\nNumber of days for nursing service: " +
                numberOfDays.getEditText().getText().toString().trim();

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


        databaseReferenceEmail =
                FirebaseDatabase.getInstance().getReference("email");
        databaseReferenceEmail.addValueEventListener(listenerForEmail);


        databaseReferencePassword =
                FirebaseDatabase.getInstance().getReference("password");
        databaseReferencePassword.addValueEventListener(listenerForPassword);

    }


    @Override
    protected void onStop() {
        super.onStop();

        databaseReferenceEmail.removeEventListener(listenerForEmail);
        databaseReferencePassword.removeEventListener(listenerForPassword);

    }
}
