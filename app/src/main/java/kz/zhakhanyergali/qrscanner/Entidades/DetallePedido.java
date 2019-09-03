package kz.zhakhanyergali.qrscanner.Entidades;

import java.io.Serializable;

public class DetallePedido implements Serializable {

    private String nroPedido;
    private String codArticulo;
    private String articulo;
    private String undMedida;
    private String cantidad;
    private String precio;
    private String nroOrden;
    private String tipoRegistro;
    private String tasaDscto;
    private String precioOrigen;
    private String precioFinal;
    private String subTotal;
    private String stock;


    public DetallePedido() {
    }

    public String getPrecioOrigen() {
        return precioOrigen;
    }

    public void setPrecioOrigen(String precioOrigen) {
        this.precioOrigen = precioOrigen;
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

    public String getTasaDscto() {
        return tasaDscto;
    }

    public void setTasaDscto(String tasaDscto) {
        this.tasaDscto = tasaDscto;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public String getNroOrden() {
        return nroOrden;
    }

    public void setNroOrden(String nroOrden) {
        this.nroOrden = nroOrden;
    }

    public String getNroPedido() {
        return nroPedido;
    }

    public void setNroPedido(String nroPedido) {
        this.nroPedido = nroPedido;
    }

    public String getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(String codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getUndMedida() {
        return undMedida;
    }

    public void setUndMedida(String undMedida) {
        this.undMedida = undMedida;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

}
