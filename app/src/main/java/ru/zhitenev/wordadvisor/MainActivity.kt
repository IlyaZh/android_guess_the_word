package ru.zhitenev.wordadvisor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.zhitenev.wordadvisor.database.DataBase
import ru.zhitenev.wordadvisor.database.Word
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var lettersEditVector : Vector<EditText>
    private lateinit var includeEditLetters : EditText
    private lateinit var excludeEditLetters : EditText
    private lateinit var searchButton : Button
    private lateinit var countView : TextView
    private lateinit var answerLayout : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DataBase(this)

        lettersEditVector = Vector()
        lettersEditVector.add(findViewById(R.id.firstLetter))
        lettersEditVector.add(findViewById(R.id.secondLetter))
        lettersEditVector.add(findViewById(R.id.thirdLetter))
        lettersEditVector.add(findViewById(R.id.fourthLetter))
        lettersEditVector.add(findViewById(R.id.fifthLetter))

        includeEditLetters = findViewById(R.id.includeLetters)
        excludeEditLetters = findViewById(R.id.excludeLetters)
        searchButton = findViewById(R.id.searchButton)
        countView = findViewById(R.id.countView)

        answerLayout = findViewById(R.id.answersLayout)
        answerLayout.layoutManager = LinearLayoutManager(this)

        answerLayout.adapter = WordAdapter(emptyList<Word>())

        searchButton.setOnClickListener {
            var mask = Vector<Char>()
            val regexp = Regex("[^А-Яа-я ]")
            for(letterEdit in lettersEditVector) {
                val letter = regexp.replace(letterEdit.text.toString().lowercase(), "")
                if(letter.isEmpty()) {
                    mask.addElement('*')
                } else {
                    mask.addElement(letter[0])
                }
            }

            val words = db.get_words(5)
            words?.let {
                val goodWords = Advisor.adviseWords(
                    regexp.replace(includeEditLetters.text.toString(), ""),
                    regexp.replace(excludeEditLetters.text.toString(), ""),
                    mask,
                    words
                )
                val adapter = WordAdapter(goodWords)
                answerLayout.adapter = adapter
                countView.text = String.format("(%d)", goodWords.count())
                Toast.makeText(
                    this,
                    String.format(getString(R.string.x_words_founded), goodWords.count()),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}