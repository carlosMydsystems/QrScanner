package kz.zhakhanyergali.qrscanner.Entidades;

import java.io.Serializable;

public class PedidoSimplificado implements Serializable {

    private Integer index;
    private String Monto;

    public PedidoSimplificado() {
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getMonto() {
        return Monto;
    }

    public void setMonto(String monto) {
        Monto = monto;
    }
}
