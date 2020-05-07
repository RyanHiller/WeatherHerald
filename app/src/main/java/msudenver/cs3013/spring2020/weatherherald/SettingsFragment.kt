package msudenver.cs3013.spring2020.weatherherald

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.Preference.SummaryProvider
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val tempToggle: SwitchPreferenceCompat? = findPreference("temp_toggle")
        val hotPreference: EditTextPreference? = findPreference("user_temp_high")
        val coldPreference: EditTextPreference? = findPreference("user_temp_low")

        // OnClick listener for temperature toggle
        tempToggle?.setOnPreferenceClickListener {
            val msg: String = if (tempToggle.isChecked) {
                "Celsius Mode Enabled"
            } else {
                "Fahrenheit Mode Enabled"
            }
            val toast = Toast.makeText(requireActivity().baseContext, msg, Toast.LENGTH_SHORT)
            toast.show()
            true
        }

        // Type binding for user input
        hotPreference?.setOnBindEditTextListener { editText ->
            editText.inputType = InputType.TYPE_CLASS_NUMBER
            editText.filters = arrayOf(InputFilterMinMax("-125", "125"))
        }
        coldPreference?.setOnBindEditTextListener { editText ->
            editText.inputType = InputType.TYPE_CLASS_NUMBER
            editText.filters = arrayOf(InputFilterMinMax("-125", "125"))
        }

        // Custom summary for hot/cold threshold fields
        hotPreference?.summaryProvider = SummaryProvider<EditTextPreference> { preference ->
            val text = preference.text
            if (TextUtils.isEmpty(text)) {
                getText(R.string.str_temp_high_summary)
            } else {
                "Personal Hot Threshold: $text"
            }
        }
        coldPreference?.summaryProvider = SummaryProvider<EditTextPreference> { preference ->
            val text = preference.text
            if (TextUtils.isEmpty(text)) {
                getText(R.string.str_temp_low_summary)
            } else {
                "Personal Cold Threshold: $text"
            }
        }
    }
}