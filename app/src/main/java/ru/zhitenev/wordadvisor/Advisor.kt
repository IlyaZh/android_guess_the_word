package ru.zhitenev.wordadvisor

import ru.zhitenev.wordadvisor.database.Word
import java.util.*

class Advisor {
    companion object {
        lateinit var includeSet: MutableSet<Char>
        lateinit var excludeSet: Set<Char>

        fun adviseWords(
            include: String,
            exclude: String,
            mask: Vector<Char>,
            words: List<String>
        ): Vector<Word> {
            includeSet = include.lowercase().toSet().toMutableSet()
            mask.forEach(fun (letter) {
                if(letter != '*')
                    includeSet.add(letter)
            })
            excludeSet = exclude.lowercase().toSet().toMutableSet().minus(includeSet)

            var result = Vector<Word>()
            words.forEach(fun (word){
                if (isSuitableWord(word, mask)) {
                    result.addElement(Word(word))
                }
            })
            return result
        }

        private fun isSuitableWord(word: String, mask: Vector<Char>): Boolean {
            val wordSet: Set<Char> = word.toSet()

            if ((wordSet intersect excludeSet).isEmpty()) {
                if (wordSet.containsAll(includeSet)) {
                    var isOk = true
                    val iterator = word.iterator()
                    for ((idx, wordLetter) in iterator.withIndex()) {
                        if (mask[idx] != '*' && wordLetter != mask[idx]) {
                            isOk = false
                            break
                        }
                    }
                    return isOk
                }
            }
            return false
        }
    }
}