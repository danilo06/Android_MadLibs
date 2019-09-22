package co.edu.uan.madlibs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_words_view.*
import java.util.*

class wordsView : AppCompatActivity() {

    private val palabras = ArrayList<String>()
    private val palabrasType = ArrayList<String>()
    private var aux = 0
    val HISTORIA_NOMBRE: String = "madlib0_simple"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words_view)
    }

    fun modificarHistoria(story: String){

        var builder = StringBuilder()
        val reader = Scanner(resources.openRawResource(resources.getIdentifier(HISTORIA_NOMBRE, "raw", packageName)))
        while(reader.hasNextLine()){
            val line = reader.nextLine()
            builder.append(line)
        }
        var theStory = builder.toString()
        val r = Regex("<.*?>")
        val found = r.findAll(theStory)
        found.forEach{ f ->
            val m = f.value
            palabrasType.add(m)
            aux++
        }
        val first_type = palabrasType.get(0)
        editText.hint = "por favor ingrese $first_type"
        remainingAttempts.setText("palabras restantes: $aux")
    }

    fun añadirPalabras(view: View){


        if(editText.text.toString().isEmpty() || editText.text.toString().trim().isEmpty()){
            val Toast = Toast.makeText(this, "Ingrese una palabra", Toast.LENGTH_SHORT)
            Toast.show()
        }
        else{
            val word = editText.text.toString().trim()
            palabras.add(word)
            aux--
            editText.setText("")
            if(aux >= 1){
                val next_type = palabrasType.get(palabrasType.size - aux)
                editText.hint = "por favor ingrese $next_type"
                remainingAttempts.setText("palabras restantes: $aux")
            }

            if(aux == 0){
                val myIntent = Intent(this, Historia::class.java)
                myIntent.putExtra("inputs", palabras)
                myIntent.putExtra("storyID", storyID)
                startActivityForResult(myIntent, REQ_CODE)
            }
            /*
            if(aux == 1){
                aceptarBtn.text = "Listo"
            }
            */

        }
    }
}
