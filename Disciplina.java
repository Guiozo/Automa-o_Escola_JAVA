package escola.model;

import java.util.Objects;

public class Disciplina implements Comparable<Disciplina> {
    private final String codigo;
    private String nome;

    public Disciplina(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Disciplina)) return false;
        Disciplina that = (Disciplina) o;
        return codigo.equalsIgnoreCase(that.codigo);
    }

    @Override
    public int hashCode() { return Objects.hash(codigo.toUpperCase()); }

    @Override
    public String toString() { return codigo + " - " + nome; }

    @Override
    public int compareTo(Disciplina o) { return this.codigo.compareTo(o.codigo); }
}
