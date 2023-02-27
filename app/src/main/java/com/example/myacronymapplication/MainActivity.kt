package com.example.myacronymapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import com.example.myacronymapplication.databinding.ActivityMainBinding
import com.example.myacronymapplication.data.AlertType
import com.example.myacronymapplication.view.LongFormMainAdapter
import com.example.myacronymapplication.viewmodel.AcronymsViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), AcronymsViewModel.ToastCallback {
    private val myViewModel: AcronymsViewModel by viewModels()
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(LayoutInflater.from(this))
    }
    private val inputMethodManager: InputMethodManager by lazy {
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = binding.root
        setContentView(view)

        val myLFAdapter = LongFormMainAdapter(this)
        binding.resultRecycler.adapter = myLFAdapter

        myViewModel.setToastCallback(this)

        myViewModel.longFormList.observeForever {
            myLFAdapter.setLFList(it)
        }

        binding.submitButton.setOnClickListener {
            searchResults(binding.inputText.text.toString())
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        binding.inputText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchResults(binding.inputText.text.toString())
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                true
            } else {
                false
            }
        }

    }

    override fun showAlert(message: String, type: AlertType) {
        Snackbar.make(binding.inputText, message, 5000)
            .setBackgroundTint(getColor(type.bgColor))
            .setTextColor(getColor(type.fgColor))
            .show()
    }

    private fun searchResults(s: String) {
        binding.inputText.setText(binding.inputText.text.toString().trim())
        myViewModel.getFullForm(s)
    }

}