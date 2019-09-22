package co.edu.uan.madlibs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_words_view.*
import java.util.*

class wordsView : AppCompatActivity() {

    private val REQ_CODE = 123
    private lateinit var myAdapter: ArrayAdapter<String>
    private val palabras = ArrayList<String>()
    private val palabrasType = ArrayList<String>()
    private var aux = 0
    private var aux2 = 0
    private var storyID = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words_view)
        val story ="madlib0_simple"
        extract(story)
    }


    fun extract(story: String){
        val builder = StringBuilder()
        this.storyID = resources.getIdentifier(story, "raw", packageName)
        val reader = Scanner(resources.openRawResource(storyID))
        while(reader.hasNextLine()){
            val line = reader.nextLine()
            builder.append(line)
        }
        var story = builder.toString()
        val r = Regex("<.*?>")
        val found = r.findAll(story)
        found.forEach{ f ->
            val m = f.value
            palabrasType.add(m)
            aux++
        }
        aux2 = aux
        val first_type = palabrasType.get(0)
        editText.hint = "por favor ingrese $first_type"
        remainingAttempts.setText("$aux / $aux2")
    }

    fun agregarClick(view: View){
        if(editText.text.toString().isEmpty() || editText.text.toString().trim().isEmpty()){
            val Toast = Toast.makeText(this, "Ingrese la palabra!", Toast.LENGTH_SHORT)
            Toast.show()
        }
        else{
            val word = editText.text.toString().trim()
            palabras.add(word)
            aux--
            editText.setText("")
            if(aux >= 1){
                val next_type = palabrasType.get(palabrasType.size - aux)
                editText.hint = "Ungrese $next_type"
                remainingAttempts.setText("$aux / $aux2")
            }
        /*
            if(aux == 1)
                btnAgregar.text = "LISTO!"
        */

            if(aux == 0){
                val intent = Intent(this, Historia::class.java)
                intent.putExtra("inputs", palabras)
                //intent.putExtra("storyID", storyID)
                startActivityForResult(intent, REQ_CODE)
            }
        }
    }
}
