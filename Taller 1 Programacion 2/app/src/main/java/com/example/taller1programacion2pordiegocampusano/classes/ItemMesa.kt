package com.example.taller1programacion2pordiegocampusano.classes

public class ItemMesa(itemMenu: ItemMenu, var cant: Int) {
    public val cantidad: Int = cant
    public val itemMenu: ItemMenu = itemMenu

    public fun calcularSubtotal(): Int {
        return (itemMenu.precioItem.toIntOrNull()?: 0) * cantidad
    }
}