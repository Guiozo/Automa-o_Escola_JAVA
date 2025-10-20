package escola.service;

import escola.model.Estudante;
import java.util.*;
import java.util.stream.Collectors;

public class ListaEstudantes {
    private final List<Estudante> lista = new ArrayList<>();

    public void adicionarEstudante(Estudante e) { lista.add(e); }

    public boolean removerEstudantePorId(int id) {
        return lista.removeIf(s -> s.getId() == id);
    }

    public Estudante obterEstudantePorIndice(int indice) {
        return lista.get(indice);
    }

    public List<Estudante> buscarEstudantesPorNome(String substring) {
        String lower = substring.toLowerCase();
        return lista.stream()
                .filter(e -> e.getNome().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public List<Estudante> ordenarEstudantesPorNome() {
        return lista.stream()
                .sorted(Comparator.comparing(Estudante::getNome))
                .collect(Collectors.toList());
    }

    public List<Estudante> obterTodos() {
        return new ArrayList<>(lista);
    }
}
