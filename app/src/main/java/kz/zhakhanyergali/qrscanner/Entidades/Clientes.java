package kz.zhakhanyergali.qrscanner.Entidades;

import java.io.Serializable;

public class Clientes implements Serializable {

    private String idCliente;
    private String nombre;
    private String codCliente;
    private String direccion;
    private String documentoCliente;
    private String giro;
    private String tipoCliente;
    private String deuda;
    private String estado;
    private String fechaultpedido;
    private String usuarioultpedido;
    private String tipoDocumento;
    private String detalleCliente;
    private String codFPago;
    private String formaPago;
    private String direccionSucursal;
    private String distrito;
    private String provincia;
    private String departamento;
    private String documentoSeleccionado;

    public Clientes() {
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDocumentoCliente() {
        return documentoCliente;
    }

    public void setDocumentoCliente(String documentoCliente) {
        this.documentoCliente = documentoCliente;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getDeuda() {
        return deuda;
    }

    public void setDeuda(String deuda) {
        this.deuda = deuda;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaultpedido() {
        return fechaultpedido;
    }

    public void setFechaultpedido(String fechaultpedido) {
        this.fechaultpedido = fechaultpedido;
    }

    public String getUsuarioultpedido() {
        return usuarioultpedido;
    }

    public void setUsuarioultpedido(String usuarioultpedido) {
        this.usuarioultpedido = usuarioultpedido;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDetalleCliente() {
        return detalleCliente;
    }

    public void setDetalleCliente(String detalleCliente) {
        this.detalleCliente = detalleCliente;
    }

    public String getCodFPago() {
        return codFPago;
    }

    public void setCodFPago(String codFPago) {
        this.codFPago = codFPago;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getDireccionSucursal() {
        return direccionSucursal;
    }

    public void setDireccionSucursal(String direccionSucursal) {
        this.direccionSucursal = direccionSucursal;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDocumentoSeleccionado() {
        return documentoSeleccionado;
    }

    public void setDocumentoSeleccionado(String documentoSeleccionado) {
        this.documentoSeleccionado = documentoSeleccionado;
    }
}
