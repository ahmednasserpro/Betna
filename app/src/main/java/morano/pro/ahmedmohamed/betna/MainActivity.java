package morano.pro.ahmedmohamed.betna;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences preferences;
    String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("Locale", Activity.MODE_PRIVATE);
        lang = preferences.getString("Lang", "ar");
        LocaleManager.setLocale(MainActivity.this, lang);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.app_name));
        Log.v("Saved language", lang);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        TabLayout tabLayout = findViewById(R.id.tabLayout_id);
        ViewPager viewPager = findViewById(R.id.viewpager_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentMaintenance(), getString(R.string.maintenance_tab));
        adapter.addFragment(new FragmentNursing(), getString(R.string.nursing_tab));

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_language_setting) {
            // Handle the language setting action
            showPopup(lang);

        } else if (id == R.id.nav_call_us) {
            // Handle the call us action
            doCall();

        } else if (id == R.id.nav_facebook) {
            // Handle the facebook action
            Intent facebookIntent = openFacebookPage(MainActivity.this);
            startActivity(facebookIntent);

        } else if (id == R.id.nav_twitter) {
            // Handle the twitter action
            Intent twitterIntent = openTwitterPage(MainActivity.this);
            startActivity(twitterIntent);

        } else if (id == R.id.nav_linkedin) {
            // Handle the linkedin action
            Intent linkedIntent = openLinkedInPage(MainActivity.this);
            startActivity(linkedIntent);

        } else if (id == R.id.nav_send_mail) {
            // Handle the facebook action
            sendEmail();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showPopup(final String savedLanguage) {
        final String[] languages = {"عربي", "English"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("choose language");
        builder.setCancelable(false);
        builder.setSingleChoiceItems(languages, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0 && !savedLanguage.equals("ar")) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Lang", "ar");
                    editor.apply();
                } else if (which == 1 && !savedLanguage.equals("en")) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Lang", "en");
                    editor.apply();
                }
            }
        });

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!savedLanguage.equals(preferences.getString("Lang", "ar"))) {
                    /*Intent intentRestart = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                    intentRestart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentRestart);
                    finish();*/
                    Intent restartIntent = new Intent(MainActivity.this, MainActivity.class);
                    int pendingIntentId = 123456;
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, pendingIntentId, restartIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
                    System.exit(0);
                }
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Lang", savedLanguage);
                editor.apply();
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

    /**
     * Open phone app to do call
     */
    private void doCall () {

        Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
        phoneIntent.setData(Uri.parse("tel:" + "01097090591"));
        if (phoneIntent.resolveActivity(getPackageManager()) != null)
            startActivity(phoneIntent);

    }

    /**
     * Open facebook page
     *
     * @param context the main context
     * @return intent
     */
    private Intent openFacebookPage (Context context) {

        Uri facebook = Uri.parse("https://www.facebook.com/%D8%AA%D8%B7%D8%A8%D9%8A%D9%82-%D8%A8%D9%8A%D8%AA%D9%86%D8%A7-%D9%84%D9%84%D8%AE%D8%AF%D9%85%D8%A7%D8%AA-%D8%A7%D9%84%D9%85%D9%86%D8%B2%D9%84%D9%8A%D8%A9-Betna-Home-Services-App-219943322062014/?modal=admin_todo_tour");
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/219943322062014"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, facebook);
        }
    }

    /**
     * Open twitter page
     *
     * @param context the main context
     * @return intent
     */
    private Intent openTwitterPage(Context context) {

        Uri twitter = Uri.parse("https://twitter.com/BetnaApp");
        try {
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=1014085469473312768"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, twitter);
        }

    }

    /**
     * Open linkedin page
     *
     * @param context the main context
     * @return intent
     */
    private Intent openLinkedInPage (Context context) {
        Uri linkedin = Uri.parse("https://www.linkedin.com/in/تطبيق-بيتنا-للخدمات-المنزلية-5973a2167/");
        try {
            context.getPackageManager().getPackageInfo("com.linkedin.android", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://profile/5973a2167"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, linkedin);
        }
    }

    /**
     * Open Gmail app to send email
     */
    private void sendEmail () {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        //only emails apps should handle this
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"betna.info@gmail.com"});
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

}
