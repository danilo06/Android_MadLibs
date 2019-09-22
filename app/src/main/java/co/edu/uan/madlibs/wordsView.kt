package co.edu.uan.madlibs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_words_view.*
import java.util.*

class wordsView : AppCompatActivity() {
    private var identificador = 0
    private val posicion = 123
    private var Contador2TotalCampos = 0
    private var ContadorCamposBlanco = 0
    private val Entradas = ArrayList<String>()
    private val ListaPalabras = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_words_view)
        ProcesoTextoConteo("madlib0_simple")
    }

    fun ProcesoTextoConteo(lectura: String){
        val builderObject = StringBuilder()
        this.identificador = resources.getIdentifier(lectura, "raw", packageName)
        val reader = Scanner(resources.openRawResource(identificador))
        while(reader.hasNextLine()){
            val line = reader.nextLine()
            builderObject.append(line)
        }
        var Historia = builderObject.toString()
        val r = Regex("<.*?>")
        val found = r.findAll(Historia)
        found.forEach{ f ->
            val m = f.value
            ListaPalabras.add(m)
            ContadorCamposBlanco++
        }
        Contador2TotalCampos = ContadorCamposBlanco
        val type = ListaPalabras.get(0)
        editText.hint = "$type"
        remainingAttempts.setText("$ContadorCamposBlanco / $Contador2TotalCampos")
    }

    fun agregarClick(view: View){
        if(editText.text.toString().isEmpty() || editText.text.toString().trim().isEmpty()){
            val Toast = Toast.makeText(this, "Ingrese la palabra!", Toast.LENGTH_SHORT)
            Toast.show()
        }
        else{
            val word = editText.text.toString().trim()
            Entradas.add(word)
            ContadorCamposBlanco--
            editText.setText("")
            if(ContadorCamposBlanco >= 1){
                val next_type = ListaPalabras.get(ListaPalabras.size - ContadorCamposBlanco)
                editText.hint = "Ungrese $next_type"
                remainingAttempts.setText("$ContadorCamposBlanco / $Contador2TotalCampos")
            }
        /*
            if(aux == 1)
                btnAgregar.text = "LISTO!"
        */

            if(ContadorCamposBlanco == 0){
                val intent = Intent(this, Historia::class.java)
                intent.putExtra("inputs", Entradas)
                //intent.putExtra("storyID", storyID)
                startActivityForResult(intent, posicion)
            }
        }
    }
}
