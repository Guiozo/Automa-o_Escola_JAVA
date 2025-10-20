package escola.service;

import escola.model.Matricula;
import java.util.*;
import java.util.stream.Collectors;

public class HistoricoNotas {
    private final Map<Integer, List<Matricula>> historico = new HashMap<>();

    public void adicionarMatricula(int idEstudante, String codigoDisciplina, double nota) {
        historico.computeIfAbsent(idEstudante, estudante -> new ArrayList<>())
                .add(new Matricula(codigoDisciplina, nota));
    }

    public List<Matricula> obterMatriculas(int idEstudante) {
        return historico.getOrDefault(idEstudante, Collections.emptyList());
    }

    public double mediaDaDisciplina(String codigoDisciplina) {
        List<Double> notas = historico.values().stream()
                .flatMap(List::stream)
                .filter(m -> m.getCodigoDisciplina().equalsIgnoreCase(codigoDisciplina))
                .map(Matricula::getNota)
                .collect(Collectors.toList());
        if (notas.isEmpty()) return 0.0;
        return notas.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public double mediaDoEstudante(int idEstudante) {
        List<Matricula> list = obterMatriculas(idEstudante);
        if (list.isEmpty()) return 0.0;
        return list.stream().mapToDouble(Matricula::getNota).average().orElse(0.0);
    }

    public Map<Integer, Double> mediasPorEstudante() {
        Map<Integer, Double> medias = new HashMap<>();
        for (Integer id : historico.keySet()) {
            medias.put(id, mediaDoEstudante(id));
        }
        return medias;
    }

    public List<Integer> topNEstudantesPorMedia(int N) {
        return historico.keySet().stream()
                .sorted(Comparator.comparingDouble(this::mediaDoEstudante).reversed())
                .limit(N)
                .toList();
    }

    public List<String> disciplinasComMediaAbaixo(double limiar) {
        return historico.values().stream()
                .flatMap(List::stream)
                .map(Matricula::getCodigoDisciplina)
                .map(String::toUpperCase)
                .distinct()
                .filter(c -> mediaDaDisciplina(c) < limiar)
                .toList();
    }
}
