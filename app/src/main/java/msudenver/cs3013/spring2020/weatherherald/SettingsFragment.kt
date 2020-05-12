package msudenver.cs3013.spring2020.weatherherald

import android.content.Context
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
import kotlin.math.round
import kotlin.math.truncate

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val tempToggle: SwitchPreferenceCompat? = findPreference("temp_toggle")
        val hotPreference: EditTextPreference? = findPreference("user_temp_high")
        val coldPreference: EditTextPreference? = findPreference("user_temp_low")
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity?.baseContext)

        // OnClick listener for temperature toggle
        tempToggle?.setOnPreferenceClickListener {
            var curr = sharedPreferences.getFloat("current_temp", 0F)
            var high = sharedPreferences.getFloat("today_high", 0F)
            var low = sharedPreferences.getFloat("today_low", 0F)
            var msg = ""

            if (tempToggle.isChecked) {
                msg = "Celsius Mode Enabled"
                Log.i("WEATHERLOG", "$curr")
                curr = toCelsius(curr)
                Log.i("WEATHERLOG", "$curr")
                high = toCelsius(high)
                low = toCelsius(low)
            } else {
                msg = "Fahrenheit Mode Enabled"
                curr = toFahrenheit(curr)
                high = toFahrenheit(high)
                low = toFahrenheit(low)
            }

            // Store converted numbers back in SharedPreferences
            with(sharedPreferences.edit()) {
                putFloat("current_temp", curr)
                putFloat("today_high", high)
                putFloat("today_low", low)
                apply()
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

    private fun toCelsius(num: Float): Float {
        var temp = num
        temp -= 32
        temp *= 5
        temp /= 9F
        temp = round(temp)
        return temp
    }

    private fun toFahrenheit(num: Float): Float {
        var temp = num
        temp *= 9
        temp /= 5F
        temp += 32
        temp = round(temp)
        return temp
    }
}