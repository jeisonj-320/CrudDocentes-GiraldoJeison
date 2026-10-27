package com.ucsur.cruddocentes.modelo;

public class Docente {
    private int codigo;
    private String nombre, paterno, materno, sexo;
    private double sueldo;
    private int hijos;

    public Docente() { }

    public Docente(int codigo, String nombre, String paterno, String materno,
                   String sexo, double sueldo, int hijos) {
        this.codigo = codigo; this.nombre = nombre; this.paterno = paterno;
        this.materno = materno; this.sexo = sexo; this.sueldo = sueldo; this.hijos = hijos;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPaterno() { return paterno; }
    public void setPaterno(String paterno) { this.paterno = paterno; }
    public String getMaterno() { return materno; }
    public void setMaterno(String materno) { this.materno = materno; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public double getSueldo() { return sueldo; }
    public void setSueldo(double sueldo) { this.sueldo = sueldo; }
    public int getHijos() { return hijos; }
    public void setHijos(int hijos) { this.hijos = hijos; }
}