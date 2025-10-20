package escola.model;

import java.util.Objects;

public class Estudante {
    private final int id;
    private String nome;

    public Estudante(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estudante)) return false;
        Estudante that = (Estudante) o;
        return id == that.id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
