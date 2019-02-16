package com.pearl.shell.fragments;

import com.android.internal.logging.nano.MetricsProto;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.content.ContentResolver;
import android.content.res.Resources;

import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v14.preference.SwitchPreference;
import android.provider.Settings;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import java.util.Locale;
import android.text.TextUtils;
import android.view.View;

import java.util.List;
import java.util.ArrayList;

import com.pearl.shell.preferences.CustomSeekBarPreference;

public class QuickSettings extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String QS_TILE_STYLE = "qs_tile_style";
    private ListPreference mQsTileStyle;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.quick_settings);

        PreferenceScreen prefScreen = getPreferenceScreen();
        ContentResolver resolver = getActivity().getContentResolver();

      // QS Styles
       mQsTileStyle = (ListPreference) findPreference(QS_TILE_STYLE);
       int qsTileStyle = Settings.System.getIntForUser(resolver,
               Settings.System.QS_TILE_STYLE, 0,
	       UserHandle.USER_CURRENT);
       int valueIndex = mQsTileStyle.findIndexOfValue(String.valueOf(qsTileStyle));
       mQsTileStyle.setValueIndex(valueIndex >= 0 ? valueIndex : 0);
       mQsTileStyle.setSummary(mQsTileStyle.getEntry());
       mQsTileStyle.setOnPreferenceChangeListener(this);
        }

     @Override
     public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mQsTileStyle) {
            int qsTileStyleValue = Integer.valueOf((String) newValue);
            Settings.System.putIntForUser(resolver, Settings.System.QS_TILE_STYLE,
                    qsTileStyleValue, UserHandle.USER_CURRENT);
            mQsTileStyle.setSummary(mQsTileStyle.getEntries()[qsTileStyleValue]);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.SHELL;
    }
}
