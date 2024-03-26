package com.belajar.githubusernavigationfinal.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.belajar.githubusernavigationfinal.R
import com.belajar.githubusernavigationfinal.data.DataPreference
import com.belajar.githubusernavigationfinal.data.entity.DataModel
import com.belajar.githubusernavigationfinal.databinding.ActivityDataFormBinding

class DataFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDataFormBinding
    private lateinit var dataModel: DataModel
    private lateinit var dataPreference: DataPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataModel = DataModel()
        binding.btnSave.setOnClickListener(this)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = "Change your profile"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_save -> saveData()
        }
    }

    private fun saveData() {
        val name = binding.edName.text.toString().trim()
        val githubLink = binding.edGithub.text.toString().trim()
        if (name.isEmpty()) {
            binding.edName.setText(R.string.text_name)
            return
        }
        if (githubLink.isEmpty()) {
            binding.edGithub.setText("")
            return
        }

        saveDataToPreference(name, githubLink)
        Toast.makeText(this, "Profile Changed", Toast.LENGTH_SHORT).show()

        val resultIntent = Intent().apply {
            putExtra(EXTRA_RESULT, dataModel)
        }

        setResult(RESULT_CODE, resultIntent)
        finish()

    }


    private fun saveDataToPreference(name: String, githubLink: String) {
        dataPreference = DataPreference(this)
        dataModel.name = name
        dataModel.githubLink = githubLink
        dataPreference.setData(dataModel)
    }

    companion object {
        const val EXTRA_RESULT = "extra_result"
        const val RESULT_CODE = 101
    }

}