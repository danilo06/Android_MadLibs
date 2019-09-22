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
        setContentView(R.layout.activity_historia)
        val inputs = intent.getStringArrayListExtra("inputs")  // recupera las palabras ingresadas
        writer(inputs)
    }

    fun writer(inputs: ArrayList<String>){
        val builder = StringBuilder()
        val reader = Scanner(resources.openRawResource(resources.getIdentifier(HISTORIA_NOMBRE, "raw", packageName))
        val first_line = reader.nextLine()
        builder.append(first_line)
        while(reader.hasNextLine()){
            val line = reader.nextLine()
            builder.append(" ")
            builder.append(line)
        }

        var theStory = builder.toString()
        val regex = Regex("<.*?>")
        val blanks = regex.findAll(theStory).map { it.value }
        var i: Int = 0
        for(blank in blanks){
            theStory = theStory.replaceFirst(blank, inputs.get(i))
            i++
        }
        textView3.text = "$theStory"
    }
    fun nuevaHistoriaBtn(view: View){
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent) // ir a la página de inicio
    }
}
