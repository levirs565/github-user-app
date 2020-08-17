package com.levirs.githubuser.feature.settings

import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.preference.SwitchPreferenceCompat
import com.levirs.githubuser.R
import com.levirs.githubuser.feature.reminder.Reminder

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        findPreference<SwitchPreferenceCompat>(Reminder.PREFERENCE_KEY)!!
            .setOnPreferenceChangeListener { preference, newValue ->
                val activity = requireActivity()
                val toastText: Int
                if (newValue as Boolean) {
                    Reminder.startReminder(activity)
                    toastText = R.string.reminder_enabled
                } else {
                    Reminder.cancelReminder(activity)
                    toastText = R.string.reminder_disabled
                }
                Toast.makeText(activity, getString(toastText), Toast.LENGTH_SHORT).show()
                true
        }
    }
}