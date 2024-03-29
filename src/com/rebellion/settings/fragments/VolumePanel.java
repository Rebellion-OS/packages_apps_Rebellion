/*
 * Copyright (C) 2019 The rebellion Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rebellion.settings.fragments;

import android.content.Context;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.ListPreference;
import android.support.v14.preference.SwitchPreference;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.SettingsPreferenceFragment;

import com.android.settings.R;

public class VolumePanel extends SettingsPreferenceFragment {

    private static final String TAG = "VolumePanel";

    private static final String KEY_NOTIFICATION = "audio_panel_view_notification";

    private SwitchPreference mNotification;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContentResolver resolver = getActivity().getContentResolver();

        addPreferencesFromResource(R.xml.rebellion_settings_button_volume_panel);

        boolean isNotificationLinked = Settings.Secure.getIntForUser(resolver,
                Settings.Secure.VOLUME_LINK_NOTIFICATION, 1, UserHandle.USER_CURRENT) != 0;

        if (isNotificationLinked) {
            mNotification = (SwitchPreference) findPreference(KEY_NOTIFICATION);
            mNotification.setSummary(R.string.audio_panel_view_notification_disabled);
            mNotification.setEnabled(false);
        }

        mFooterPreferenceMixin.createFooterPreference().setTitle(R.string.audio_panel_view_info);
    }

    public static void reset(Context mContext) {
        ContentResolver resolver = mContext.getContentResolver();
        Settings.System.putIntForUser(resolver,
                Settings.System.AUDIO_PANEL_VIEW_MEDIA, 0, UserHandle.USER_CURRENT);
        Settings.System.putIntForUser(resolver,
                Settings.System.AUDIO_PANEL_VIEW_RINGER, 0, UserHandle.USER_CURRENT);
        Settings.System.putIntForUser(resolver,
                Settings.System.AUDIO_PANEL_VIEW_NOTIFICATION, 0, UserHandle.USER_CURRENT);
        Settings.System.putIntForUser(resolver,
                Settings.System.AUDIO_PANEL_VIEW_ALARM, 0, UserHandle.USER_CURRENT);
        Settings.System.putIntForUser(resolver,
                Settings.System.AUDIO_PANEL_VIEW_VOICE, 0, UserHandle.USER_CURRENT);
        Settings.System.putIntForUser(resolver,
                Settings.System.AUDIO_PANEL_VIEW_BT_SCO, 0, UserHandle.USER_CURRENT);
        Settings.Secure.putIntForUser(resolver,
                Settings.Secure.VOLUME_LINK_NOTIFICATION, 1, UserHandle.USER_CURRENT);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.REBELLION_SETTINGS;
    }
}
