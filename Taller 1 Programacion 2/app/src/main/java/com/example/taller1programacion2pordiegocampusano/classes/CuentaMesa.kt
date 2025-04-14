package com.example.taller1programacion2pordiegocampusano.classes

public class CuentaMesa(val mesa: Int) {
    public var aceptaPropina: Boolean = true
    private val _items: MutableList<ItemMesa> = mutableListOf()

    public fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        _items.add(ItemMesa(itemMenu, cantidad))
    }

    public fun agregarItem(itemMesa: ItemMesa) {
        _items.add(itemMesa)
    }
    public fun calcularTotalSinPropina(): Int = _items.sumOf { it.calcularSubtotal() }

    public fun calcularPropina(): Int {
        if (aceptaPropina) {
            return (calcularTotalSinPropina() * 0.10).toInt()
        }
        else {return 0}

    }

    public fun calcularTotalConPropina(): Int = calcularTotalSinPropina() +  calcularPropina()
}