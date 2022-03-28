package utiak.covarrubias.tarjetasd

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import utiak.covarrubias.tarjetasd.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED
        ) || (ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED
        )) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE),
                123
                )
            }

        binding.btnGuardar.setOnClickListener {
            Guardar(binding.etNuevoDato.text.toString())
            binding.tvContenido.text = Cargar()
        }
    }

    fun Guardar(texto: String){
        try{
            val rutaSD = baseContext.getExternalFilesDir(null)?.absolutePath
            val miCarpeta = File(rutaSD, "datos")
            if(!miCarpeta.exists()){
                miCarpeta.mkdir()
            }
            val ficheroFisico = File(miCarpeta, "datos.txt")
            ficheroFisico.appendText("$texto\n")
        } catch (e: Exception){
            Toast.makeText(this,
            "No se ha podido guardar",
            Toast.LENGTH_LONG).show()
        }
    }

    fun Cargar() : String {
        var texto = ""
        try {
            val rutaSD = baseContext.getExternalFilesDir(null)?.absolutePath
            val miCarpeta = File(rutaSD, "datos")
            val ficheroFisico = File(miCarpeta, "datos.txt")
            val fichero = BufferedReader(
                InputStreamReader(FileInputStream(ficheroFisico))
            )
            texto = fichero.use(BufferedReader::readText)
        } catch (e: Exception){
            //Nada
        }
        return texto
    }
}