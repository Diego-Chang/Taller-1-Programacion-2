package com.example.taller1programacion2pordiegocampusano

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.example.taller1programacion2pordiegocampusano.classes.CuentaMesa
import com.example.taller1programacion2pordiegocampusano.classes.ItemMenu
import com.example.taller1programacion2pordiegocampusano.classes.ItemMesa
import org.w3c.dom.Text
import java.text.Format
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    //Propiedades que contienen las etiquetas llamadas. Sirve para problemas de visibilidad.
    var pastelDeChocloEdText: EditText? = null
    var pastelDeChocloPrecioText: TextView? = null
    var cazuelaEdText: EditText? = null
    var cazuelaPrecioText: TextView? = null
    var comidaPrecioText: TextView? = null
    var propinaPrecioText: TextView? = null
    var totalPrecioText: TextView? = null
    var propinaSwitch: Switch? = null

    private var itemPastelDeChoclo = ItemMenu("Pastel de Choclo", "12000") //Instancia de Pastel de ItemMenu Choclo.
    private var itemCazuela = ItemMenu("Cazuela", "10000") //Instancia de ItemMenu Cazuela.
    val formatoCLP = NumberFormat.getCurrencyInstance(Locale("es", "CL")) //Obtiene formato de moneda chilena CLP.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Llamadas a etiquetas a usar.
        pastelDeChocloEdText = findViewById<EditText>(R.id.pastelDeChocloEdText)
        pastelDeChocloPrecioText = findViewById<TextView>(R.id.pastelDeChocloPrecioText)
        cazuelaEdText = findViewById<EditText>(R.id.cazuelaEdText)
        cazuelaPrecioText = findViewById<TextView>(R.id.cazuelaPrecioText)
        comidaPrecioText = findViewById<TextView>(R.id.comidaPrecioText)
        propinaPrecioText = findViewById<TextView>(R.id.propinaPrecioText)
        totalPrecioText = findViewById<TextView>(R.id.totalPrecioText)
        propinaSwitch = findViewById<Switch>(R.id.propinaSwitch)

        //TextWatcher encargado de ejecutar los calculos de la funcion Proceso() cada vez que se cambia el texto al que esta asignado).
        val textWatcher: TextWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                Proceso()
            }
        }

        //Asignacion de TextWatcher a EditText usados.
        pastelDeChocloEdText?.addTextChangedListener(textWatcher)
        cazuelaEdText?.addTextChangedListener(textWatcher)
        //setOnCheckedChangeListener encargado de mandar una señal que ejecutara Proceso() cada vez que se interactue con propinaSwitch.
        propinaSwitch?.setOnCheckedChangeListener { _, _ -> Proceso() }

    }

    //Funcion encargada de la funcion principal de la aplicacion.
    fun Proceso() {
        var cuentaMesa = CuentaMesa(1) //Instancia local de CuentaMesa para los calculos requeridos.

        //Asignacion de valores proporcionados a los EditText por el usuario a propiedades para su uso. Convierte de String a Int estos valores.
        val pastelDeChocloCantidad = pastelDeChocloEdText?.text.toString().toIntOrNull() ?: 0
        val cazuelaCantidad = cazuelaEdText?.text.toString().toIntOrNull() ?: 0

        //Creacion de instancia de ItemMesa en base al objeto itemPastelDeChoclo y cantidad proporcionada por el usuario.
        //Asigna el valor dado por la funcion calcularSubtotal() de esta instancia a su etiqueta correspondiente.
        val pastelesDeChocloTotal = ItemMesa(itemPastelDeChoclo, pastelDeChocloCantidad)
        pastelDeChocloPrecioText?.text = formatoCLP.format(pastelesDeChocloTotal.calcularSubtotal())

        //Creacion de instancia de ItemMesa en base al objeto itemCazuela y cantidad proporcionada por el usuario.
        //Asigna el valor dado por la funcion calcularSubtotal() de esta instancia a su etiqueta correspondiente.
        val cazuelaTotal = ItemMesa(itemCazuela,cazuelaCantidad)
        cazuelaPrecioText?.text = formatoCLP.format(cazuelaTotal.calcularSubtotal())

        //Agrega las instancias anteriormente creadas a _lista de cuentaMesa para el calculo de precios.
        cuentaMesa.agregarItem(pastelesDeChocloTotal)
        cuentaMesa.agregarItem(cazuelaTotal)

        //Maneja el valor de la propiedad aceptaPropina de cuentaMesa en base a la estado del Switch.
        cuentaMesa.aceptaPropina = propinaSwitch?.isChecked?: false

        comidaPrecioText?.text = formatoCLP.format(cuentaMesa.calcularTotalSinPropina()) //Ejecuta la funcion calcularTotalSinPropina de cuentaMesa y formatea su resultado. Asigna este a su etiqueta correspondiente.
        propinaPrecioText?.text = formatoCLP.format(cuentaMesa.calcularPropina()) //Ejecuta la funcion calcularPropina de cuentaMesa y formatea su resultado. Devuelve 0 si aceptaPropina == false. Asigna este a su etiqueta correspondiente.
        totalPrecioText?.text = formatoCLP.format(cuentaMesa.calcularTotalConPropina()) //Ejecuta la funcion calcularTotalConPropina y formatea su resultado. Asigna este a su etiqueta correspondiente.
    }
}