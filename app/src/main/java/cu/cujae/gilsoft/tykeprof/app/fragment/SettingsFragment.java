package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.app.UiModeManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.util.LocaleHelper;

import static android.content.Context.UI_MODE_SERVICE;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private UiModeManager uiModeManager;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        uiModeManager = (UiModeManager) getActivity().getSystemService(UI_MODE_SERVICE);
        loadLang();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("DarkTheme")) {
            SwitchPreference switchPreferenceTheme = findPreference("DarkTheme");
            if (switchPreferenceTheme.isChecked()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
                    uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
                    uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
        if (key.equals("langSelector")) {
            ListPreference listPreference = findPreference("langSelector");
            if (listPreference.getValue().contains("es")) {
                LocaleHelper.setLocale(getContext(), "es");
            } else {
                LocaleHelper.setLocale(getContext(), "en");
            }
            getActivity().recreate();
        }
    }

    public void loadLang(){
        String lang = LocaleHelper.getLanguage(getContext());
        LocaleHelper.setLocale(getContext(), lang);
        ListPreference listPreference = findPreference("langSelector");
        listPreference.setValue(lang);
        if (lang.equals("es"))
            listPreference.setSummary(R.string.langES);
        else
            listPreference.setSummary(R.string.langEN);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}