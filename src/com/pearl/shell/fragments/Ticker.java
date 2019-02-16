/*
 * Copyright © 2019 Project Pearl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pearl.shell.fragments;

import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

import com.pearl.shell.preferences.SystemSettingSwitchPreference;
import com.pearl.shell.preferences.CustomSeekBarPreference;

import com.android.internal.logging.nano.MetricsProto;

public class Ticker extends SettingsPreferenceFragment implements OnPreferenceChangeListener{

	private ListPreference mTickerAnimationMode;
	private CustomSeekBarPreference mTickerAnimationDuration;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.ticker);

        PreferenceScreen prefSet = getPreferenceScreen();

		final ContentResolver resolver = getActivity().getContentResolver();

        mTickerAnimationMode = (ListPreference) findPreference("status_bar_ticker_animation_mode");
        mTickerAnimationMode.setOnPreferenceChangeListener(this);
        int tickerAnimationMode = Settings.System.getInt(resolver, Settings.System.STATUS_BAR_TICKER_ANIMATION_MODE, 0);
        mTickerAnimationMode.setValue(String.valueOf(tickerAnimationMode));
        updateTickerAnimationModeSummary(tickerAnimationMode);

	mTickerAnimationDuration = (CustomSeekBarPreference) findPreference("status_bar_ticker_tick_duration");
        int tickerAnimationDuration = Settings.System.getIntForUser(resolver,
                Settings.System.STATUS_BAR_TICKER_TICK_DURATION, 3000, UserHandle.USER_CURRENT);
        mTickerAnimationDuration.setValue(tickerAnimationDuration);
        mTickerAnimationDuration.setOnPreferenceChangeListener(this);

    }

    private void updateTickerAnimationModeSummary(int value) {
        Resources res = getResources();
         if (value == 0) {
            // Fade
             mTickerAnimationMode.setSummary(res.getString(R.string.ticker_animation_mode_alpha_fade));
        } else if (value == 1) {
            // Scroll
            mTickerAnimationMode.setSummary(res.getString(R.string.ticker_animation_mode_scroll));
        }
    }

	@Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        if (preference == mTickerAnimationMode) {
            int tickerAnimationMode = Integer.valueOf((String) objValue);
            Settings.System.putInt(getActivity().getContentResolver(),
		     Settings.System.STATUS_BAR_TICKER_ANIMATION_MODE, tickerAnimationMode);
            updateTickerAnimationModeSummary(tickerAnimationMode);
            return true;
        } else if (preference == mTickerAnimationDuration) {
            int tickerAnimationDuration = (Integer) objValue;
            Settings.System.putIntForUser(getActivity().getContentResolver(),
                    Settings.System.STATUS_BAR_TICKER_TICK_DURATION, tickerAnimationDuration,
                    UserHandle.USER_CURRENT);
            return true;
	}
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.SHELL;
    }
}
