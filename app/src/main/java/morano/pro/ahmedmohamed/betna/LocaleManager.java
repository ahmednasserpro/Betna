package morano.pro.ahmedmohamed.betna;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LocaleManager {


    public static void setLocale(Context context, String lang) {

        persist(context, lang);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, lang);
            return;
        }

        updateResourcesLegacy(context, lang);

    }


    private static void persist(Context context, String lang) {
        SharedPreferences preferences = context.getSharedPreferences("Locale", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Lang", lang);
        editor.apply();
    }


    @TargetApi(Build.VERSION_CODES.N)
    private static void updateResources(Context context, String lang) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        context.createConfigurationContext(configuration);

    }

    @SuppressWarnings("deprecation")
    private static void updateResourcesLegacy(Context context, String lang) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration config = resources.getConfiguration();
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLayoutDirection(locale);

        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }


    private static String getPersistedData(Context context, String language) {

        SharedPreferences preferences = context.getSharedPreferences("Locale", Activity.MODE_PRIVATE);
        return preferences.getString("Lang", language);
    }

}