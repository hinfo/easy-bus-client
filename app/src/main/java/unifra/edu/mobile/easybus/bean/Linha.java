package unifra.edu.mobile.easybus.bean;

import unifra.edu.mobile.easybus.Periodo;

/**
 * Created by icone on 24/11/17.
 */

public class Linha {
    private int id;
    private String hora;
    private String descricao;
    private Periodo periodo;
    private String direcao;
    private String nome;
    private String empresa;

    public Linha() {
    }

    public Linha(String hora, String descricao, Periodo periodo, String direcao, String nome, String empresa) {
        this.hora = hora;
        this.descricao = descricao;
        this.periodo = periodo;
        this.direcao = direcao;
        this.nome = nome;
        this.empresa = empresa;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getId() {
        return id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
