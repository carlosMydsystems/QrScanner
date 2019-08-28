package kz.zhakhanyergali.qrscanner.Entidades;

import java.io.Serializable;

public class Producto implements Serializable {

    private String descripcion;
    private String nombre;
    private String codigo;
    private String stock;
    private String precio;
    private String cantidad;
    private String tasaDescuento;
    private String idPedido;
    private String marca;
    private String unidad;
    private String estado;
    private String precioAcumulado;
    private String almacen;
    private String observacion;
    private String distrito;
    private String numPromocion;
    private Integer indice;
    private String presentacion;
    private String equivalencia;
    private String tipoTupla;
    private String codigoSucursalCliente;

    public Producto() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTasaDescuento() {
        return tasaDescuento;
    }

    public void setTasaDescuento(String tasaDescuento) {
        this.tasaDescuento = tasaDescuento;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPrecioAcumulado() {
        return precioAcumulado;
    }

    public void setPrecioAcumulado(String precioAcumulado) {
        this.precioAcumulado = precioAcumulado;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getNumPromocion() {
        return numPromocion;
    }

    public void setNumPromocion(String numPromocion) {
        this.numPromocion = numPromocion;
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getEquivalencia() {
        return equivalencia;
    }

    public void setEquivalencia(String equivalencia) {
        this.equivalencia = equivalencia;
    }

    public String getTipoTupla() {
        return tipoTupla;
    }

    public void setTipoTupla(String tipoTupla) {
        this.tipoTupla = tipoTupla;
    }

    public String getCodigoSucursalCliente() {
        return codigoSucursalCliente;
    }

    public void setCodigoSucursalCliente(String codigoSucursalCliente) {
        this.codigoSucursalCliente = codigoSucursalCliente;
    }
}
