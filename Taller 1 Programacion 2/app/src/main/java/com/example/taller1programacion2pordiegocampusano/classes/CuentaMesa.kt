package com.example.taller1programacion2pordiegocampusano.classes

//Clase encargada de diferentes calculos importantes para el funcionamiento de la App.
public class CuentaMesa(val mesa: Int) {
    public var aceptaPropina: Boolean = true //Boolean encargado de verificar si el cliente quiere pagar propina.
    private val _items: MutableList<ItemMesa> = mutableListOf() //Lista de ItemMesa para el calculo total a pagar por el cliente.

    public fun agregarItem(itemMenu: ItemMenu, cantidad: Int) { //Metodo 1 para añadir una instancia de ItemMesa a _items.
        _items.add(ItemMesa(itemMenu, cantidad))
    }

    public fun agregarItem(itemMesa: ItemMesa) {//Metodo 2 para añadir una isntancia de ItemMesa a _items.
        _items.add(itemMesa)
    }
    public fun calcularTotalSinPropina(): Int = _items.sumOf { it.calcularSubtotal() } //Calcula el total a pagar en base a los subtotales.

    public fun calcularPropina(): Int { //Calcula el valor de la propina a pagar (10% del precio total). Retorna 0 si el cliente no quiere pagar propina.
        if (aceptaPropina) {
            return (calcularTotalSinPropina() * 0.10).toInt()
        }
        else {return 0}

    }

    public fun calcularTotalConPropina(): Int = calcularTotalSinPropina() +  calcularPropina() //Calcula y refleja el total a pagar.
}