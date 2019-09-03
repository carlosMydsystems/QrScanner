package kz.zhakhanyergali.qrscanner.Entidades;

import java.io.Serializable;

public class Identificadores implements Serializable {

    private String idPedido;
    private String cliente;
    private String fecha;
    private String detalle;
    private String sucursal;
    private String origen;
    private String importeTotal;
    private String Correlativo;

    public Identificadores() {
    }

    public String getCorrelativo() { return Correlativo; }

    public void setCorrelativo(String correlativo) { Correlativo = correlativo; }

    public String getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(String importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
}
