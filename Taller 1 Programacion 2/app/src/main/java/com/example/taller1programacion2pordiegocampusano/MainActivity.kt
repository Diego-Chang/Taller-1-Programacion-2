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
    var pastelDeChocloEdText: EditText? = null
    var pastelDeChocloPrecioText: TextView? = null
    var cazuelaEdText: EditText? = null
    var cazuelaPrecioText: TextView? = null
    var comidaPrecioText: TextView? = null
    var propinaPrecioText: TextView? = null
    var totalPrecioText: TextView? = null
    var propinaSwitch: Switch? = null

    private var itemPastelDeChoclo = ItemMenu("Pastel de Choclo", "12000")
    private var itemCazuela = ItemMenu("Cazuela", "10000")
    val formatoCLP = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pastelDeChocloEdText = findViewById<EditText>(R.id.pastelDeChocloEdText)
        pastelDeChocloPrecioText = findViewById<TextView>(R.id.pastelDeChocloPrecioText)
        cazuelaEdText = findViewById<EditText>(R.id.cazuelaEdText)
        cazuelaPrecioText = findViewById<TextView>(R.id.cazuelaPrecioText)
        comidaPrecioText = findViewById<TextView>(R.id.comidaPrecioText)
        propinaPrecioText = findViewById<TextView>(R.id.propinaPrecioText)
        totalPrecioText = findViewById<TextView>(R.id.totalPrecioText)
        propinaSwitch = findViewById<Switch>(R.id.propinaSwitch)

        val textWatcher: TextWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                Proceso()
            }
        }

        pastelDeChocloEdText?.addTextChangedListener(textWatcher)
        cazuelaEdText?.addTextChangedListener(textWatcher)
        propinaSwitch?.setOnCheckedChangeListener { _, _ -> Proceso() }

    }

    fun Proceso() {
        var cuentaMesa = CuentaMesa(1)
        val pastelDeChocloCantidad = pastelDeChocloEdText?.text.toString().toIntOrNull() ?: 0
        val cazuelaCantidad = cazuelaEdText?.text.toString().toIntOrNull() ?: 0

        val pastelesDeChocloTotal = ItemMesa(itemPastelDeChoclo, pastelDeChocloCantidad)
        pastelDeChocloPrecioText?.text = formatoCLP.format(pastelesDeChocloTotal.calcularSubtotal())

        val cazuelaTotal = ItemMesa(itemCazuela,cazuelaCantidad)
        cazuelaPrecioText?.text = formatoCLP.format(cazuelaTotal.calcularSubtotal())

        cuentaMesa.agregarItem(pastelesDeChocloTotal)
        cuentaMesa.agregarItem(cazuelaTotal)
        cuentaMesa.aceptaPropina = propinaSwitch?.isChecked?: false

        comidaPrecioText?.text = formatoCLP.format(cuentaMesa.calcularTotalSinPropina())
        propinaPrecioText?.text = formatoCLP.format(cuentaMesa.calcularPropina())
        totalPrecioText?.text = formatoCLP.format(cuentaMesa.calcularTotalConPropina())
    }
}