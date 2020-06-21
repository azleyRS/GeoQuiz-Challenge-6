package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val EXTRA_ANSWER_SHOW = "EXTRA_ANSWER_SHOW"
private const val EXTRA_ANSWER_IS_TRUE = "EXTRA_ANSWER_IS_TRUE"
private const val KEY_CHEATER = "KEY_CHEATER"

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    private var answerIsTrue = false
    private var isAnswerShown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        isAnswerShown = savedInstanceState?.getBoolean(KEY_CHEATER, false) ?: false

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        if (isAnswerShown) {
            answerTextView.setText(getAnswerText())
            setAnswerShownResult(isAnswerShown)
        }
        showAnswerButton.setOnClickListener {
            val answerText = getAnswerText()
            answerTextView.setText(answerText)
            isAnswerShown = true
            setAnswerShownResult(isAnswerShown)
        }
    }

    private fun getAnswerText() = if (answerIsTrue) R.string.true_button else R.string.false_button

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_CHEATER, isAnswerShown)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply { putExtra(EXTRA_ANSWER_SHOW, isAnswerShown) }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean) =
            Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
    }
}