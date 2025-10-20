package escola;

import escola.model.*;
import escola.service.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ListaEstudantes lista = new ListaEstudantes();
        CadastroDisciplinas cad = new CadastroDisciplinas();
        HistoricoNotas hist = new HistoricoNotas();

        Estudante a = new Estudante(1, "Ana");
        Estudante b = new Estudante(2, "Bruno");
        Estudante c = new Estudante(3, "Carla");
        Estudante d = new Estudante(4, "Diego");
        Estudante e = new Estudante(5, "Elisa");

        lista.adicionarEstudante(a);
        lista.adicionarEstudante(b);
        lista.adicionarEstudante(c);
        lista.adicionarEstudante(d);
        lista.adicionarEstudante(e);

        cad.adicionarDisciplina(new Disciplina("MAT101", "Matemática"));
        cad.adicionarDisciplina(new Disciplina("PRG201", "Programação"));
        cad.adicionarDisciplina(new Disciplina("BD301", "Banco de Dados"));
        cad.adicionarDisciplina(new Disciplina("EDF110", "Educação Física"));

        hist.adicionarMatricula(1, "MAT101", 8.5);
        hist.adicionarMatricula(1, "PRG201", 9.0);
        hist.adicionarMatricula(2, "PRG201", 7.0);
        hist.adicionarMatricula(2, "MAT101", 5.0);
        hist.adicionarMatricula(3, "BD301", 6.5);
        hist.adicionarMatricula(3, "MAT101", 7.5);
        hist.adicionarMatricula(4, "PRG201", 8.0);
        hist.adicionarMatricula(5, "EDF110", 10.0);

        System.out.println("== Lista de Estudantes ==");
        lista.obterTodos().forEach(System.out::println);

        System.out.println("\n== Disciplinas e Médias ==");
        cad.obterTodasDisciplinas().forEach(materias -> {
            System.out.printf("%s: %.2f%n", materias.getCodigo(), hist.mediaDaDisciplina(materias.getCodigo()));
        });

        System.out.println("\n== Matrículas ==");
        for (Estudante st : lista.obterTodos()) {
            List<Matricula> ms = hist.obterMatriculas(st.getId());
            String disciplinas = ms.stream().map(Matricula::toString).reduce((x, y) -> x + ", " + y).orElse("(nenhuma)");
            System.out.printf("%s: %s | Média: %.2f%n", st.getNome(), disciplinas, hist.mediaDoEstudante(st.getId()));
        }
    



        System.out.println("\n== Análises Acadêmicas ==");
        
        
        System.out.println("\n○ Alunos com média ≥ 8.0:");
        Map<Integer, Double> medias = hist.mediasPorEstudante();
        boolean encontrouAlunoTop = false;
        for (Estudante est : lista.obterTodos()) {
            double media = medias.getOrDefault(est.getId(), 0.0);
            if (media >= 8.0) {
                System.out.printf("  %s: %.2f%n", est.getNome(), media);
                encontrouAlunoTop = true;
            }
        }
        if (!encontrouAlunoTop) {
            System.out.println("  Nenhum aluno com média ≥ 8.0");
        }

        
        System.out.println("\n○ Disciplinas com média < 6.0:");
        List<String> disciplinasAbaixo = hist.disciplinasComMediaAbaixo(6.0);
        if (disciplinasAbaixo.isEmpty()) {
            System.out.println("  Nenhuma disciplina com média < 6.0");
        } else {
            disciplinasAbaixo.forEach(codigo -> {
                double media = hist.mediaDaDisciplina(codigo);
                String nomeDisciplina = cad.obterTodasDisciplinas().stream()
                        .filter(materias -> materias.getCodigo().equalsIgnoreCase(codigo))
                        .findFirst()
                        .map(Disciplina::getNome)
                        .orElse("Desconhecida");
                System.out.printf("  %s - %s: %.2f%n", codigo, nomeDisciplina, media);
            });
        }

        
        System.out.println("\n○ Duplicatas de disciplinas detectadas:");
       
        Set<String> codigosVistos = new HashSet<>();
        Set<String> duplicatas = new HashSet<>();
        
        for (Disciplina disciplina : cad.obterTodasDisciplinas()) {
            String codigoUpper = disciplina.getCodigo().toUpperCase();
            if (!codigosVistos.add(codigoUpper)) {
                duplicatas.add(codigoUpper);
            }
        }
        
        if (duplicatas.isEmpty()) {
            System.out.println("  Nenhuma duplicata detectada");
        } else {
            duplicatas.forEach(codigo -> {
                System.out.println("  " + codigo + " (adicionada múltiplas vezes)");
            });
        }

        
        System.out.println("\n== Busca Avançada ==");
        String substringBusca = "An"; 
        System.out.println("○ Estudantes contendo '" + substringBusca + "' no nome:");
        
        List<Estudante> estudantesEncontrados = lista.buscarEstudantesPorNome(substringBusca);
        if (estudantesEncontrados.isEmpty()) {
            System.out.println("  Nenhum estudante encontrado");
        } else {
            for (Estudante est : estudantesEncontrados) {
                System.out.println("\n  " + est.getNome() + ":");
                List<Matricula> matriculas = hist.obterMatriculas(est.getId());
                
                for (Matricula mat : matriculas) {
                    double mediaDisciplina = hist.mediaDaDisciplina(mat.getCodigoDisciplina());
                    double notaEstudante = mat.getNota();
                    String status = notaEstudante > mediaDisciplina ? "ACIMA" : 
                                   notaEstudante < mediaDisciplina ? "ABAIXO" : "NA MÉDIA";
                    
                    String nomeDisciplina = cad.obterTodasDisciplinas().stream()
                            .filter(materias -> materias.getCodigo().equalsIgnoreCase(mat.getCodigoDisciplina()))
                            .findFirst()
                            .map(Disciplina::getNome)
                            .orElse("Desconhecida");
                    
                    System.out.printf("    %s (%s): %.1f vs Média(%.1f) → %s%n", 
                            nomeDisciplina, mat.getCodigoDisciplina(), 
                            notaEstudante, mediaDisciplina, status);
                }
            }
        }

        // Top 3 estudantes por média
        System.out.println("\n== Top 3 Estudantes por Média ==");
        List<Integer> topEstudantes = hist.topNEstudantesPorMedia(3);
        for (int i = 0; i < topEstudantes.size(); i++) {
            int id = topEstudantes.get(i);
            Estudante estudante = lista.obterTodos().stream()
                    .filter(estudantes -> estudantes.getId() == id)
                    .findFirst()
                    .orElse(null);
            if (estudante != null) {
                double media = hist.mediaDoEstudante(id);
                System.out.printf("%dº: %s - Média: %.2f%n", i + 1, estudante.getNome(), media);
            }
        }
    }   
}