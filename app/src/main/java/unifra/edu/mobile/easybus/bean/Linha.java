package unifra.edu.mobile.easybus.bean;

import unifra.edu.mobile.easybus.Periodo;

/**
 * Created by icone on 24/11/17.
 */

public class Linha {
    private String descricao;
    private Periodo periodo;
    private String direcao;

      public Linha(String descricao, Periodo periodo, String direcao) {
        this.descricao = descricao;
        this.periodo = periodo;
        this.direcao = direcao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public String getDirecao() {
        return direcao;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }
}
