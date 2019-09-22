package co.edu.uan.madlibs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_historia.*
import java.util.*
import kotlin.collections.ArrayList

class Historia : AppCompatActivity() {

    val HISTORIA_NOMBRE: String = "madlib0_simple"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inputs = intent.getStringArrayListExtra("inputs")
        escritura(inputs, resources.getIdentifier(HISTORIA_NOMBRE, "raw", packageName))
    }

    fun escritura(inputs: ArrayList<String>, storyID: Int){
        val builder = StringBuilder()
        val reader = Scanner(resources.openRawResource(storyID))

        val first_line = reader.nextLine()
        builder.append(first_line)
        while(reader.hasNextLine()){
            val line = reader.nextLine()
            builder.append(" ")
            builder.append(line)
        }
        var story = builder.toString()
        val r = Regex("<.*?>")
        val blanks = r.findAll(story).map { it.value }
        var i: Int = 0
        for(blank in blanks){
            story = story.replaceFirst(blank, inputs.get(i))
            i++
        }
        textView3.text = "$story"
    }

}
