package kz.zhakhanyergali.qrscanner.Entidades;

import java.io.Serializable;

public class Pedido implements Serializable {

    private String idPedido;
    private String indice;
    private String codArticulo;
    private String cantidad;
    private String undMedida;
    private String precio;
    private String tasaDscto;
    private String precioFinal;
    private String subTotal;
    private String stock;
    private String descripcion;

    public Pedido() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUndMedida() {
        return undMedida;
    }

    public void setUndMedida(String undMedida) {
        this.undMedida = undMedida;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getTasaDscto() {
        return tasaDscto;
    }

    public void setTasaDscto(String tasaDscto) {
        this.tasaDscto = tasaDscto;
    }

    public String getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(String precioFinal) {
        this.precioFinal = precioFinal;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    public String getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(String codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
