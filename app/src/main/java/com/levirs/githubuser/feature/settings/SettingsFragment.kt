package com.levirs.githubuser.feature.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.levirs.githubuser.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}