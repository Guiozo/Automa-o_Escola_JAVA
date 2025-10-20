package escola.service;

import escola.model.Disciplina;
import java.util.*;
import java.util.stream.Collectors;

public class CadastroDisciplinas {
    private final LinkedHashSet<Disciplina> disciplinas = new LinkedHashSet<>();

    public boolean adicionarDisciplina(Disciplina d) {
        return disciplinas.add(d);
    }

    public boolean verificarDisciplina(String codigo) {
        return disciplinas.stream().anyMatch(d -> d.getCodigo().equalsIgnoreCase(codigo));
    }

    public boolean removerDisciplina(String codigo) {
        return disciplinas.removeIf(d -> d.getCodigo().equalsIgnoreCase(codigo));
    }

    public Set<Disciplina> obterTodasDisciplinas() {
        return new LinkedHashSet<>(disciplinas);
    }

    public Set<Disciplina> obterOrdenadas() {
        return disciplinas.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
