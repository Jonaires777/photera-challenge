import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Iterator;

import model.Funcionario;

public class App {
        public static void main(String[] args) throws Exception {

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
                nf.setMinimumFractionDigits(2);
                nf.setMaximumFractionDigits(2);

                List<Funcionario> funcionarios = new ArrayList<>();

                addFuncionarios(funcionarios, dtf);

                System.out.println("*====DESAFIO PHOTERA====*");
                System.out.println("===================================================================");
                System.out.println("Lista com todos os funcionários:\n");
                funcionarios.forEach(x -> System.out.println(x.toString()));

                Iterator<Funcionario> iterator = funcionarios.iterator();
                while (iterator.hasNext()) {
                        Funcionario funcionario = iterator.next();
                        if (funcionario.getNome().equals("João")) {
                                iterator.remove();
                        }
                }

                System.out.println("===================================================================");
                System.out.println("Lista com todos os funcionários cujo nome é diferente de João:\n");
                funcionarios.forEach(x -> System.out.println(x.toString()));

                Iterator<Funcionario> iterator2 = funcionarios.iterator();
                while (iterator2.hasNext()) {
                        Funcionario funcionario = iterator2.next();
                        funcionario.setSalario(funcionario.getSalario()
                                        .add(funcionario.getSalario().multiply(new BigDecimal(0.1))));
                }

                System.out.println("===================================================================");
                System.out.println("Lista com todos os funcionários com aumento de salário:\n");
                funcionarios.forEach(x -> System.out.println(x.toString()));
                System.out.println("===================================================================");

                Map<String, List<Funcionario>> map = new HashMap<>();
                for (Funcionario funcionario : funcionarios) {
                        String funcao = funcionario.getFuncao();
                        if (!map.containsKey(funcao)) {
                                map.put(funcao, new ArrayList<>());
                        }
                        map.get(funcao).add(funcionario);
                }

                System.out.println("Lista com todos funcionários agrupados por função:\n");
                for (Map.Entry<String, List<Funcionario>> entry : map.entrySet()) {
                        System.out.println("Função: " + entry.getKey());
                        for (Funcionario funcionario : entry.getValue()) {
                                System.out.println("Nome: " + funcionario.getNome() + "\nData de Nascimento: "
                                                + funcionario.getDataDeNascimento().format(dtf) + "\nSalário: "
                                                + nf.format(funcionario.getSalario()) + "\n-----------");
                        }
                        System.out.println();
                }
                System.out.println("===================================================================");

                List<Funcionario> aniversariantes = new ArrayList<>();
                funcionarios.forEach(x -> {
                        int day = x.getDataDeNascimento().getDayOfMonth();
                        if (day == 10 || day == 12) {
                                aniversariantes.add(x);
                        }
                });
                if (aniversariantes.isEmpty()) {
                        System.out.println("Não há nenhum funcionário que faz aniversário no dia 10 ou 12");
                        System.out.println("===================================================================");
                } else {
                        System.out.println("Lista com todos os funcionários que fazem aniversário no dia 10 e 12\n");
                        aniversariantes.forEach(x -> System.out.println(x.toString()));
                        System.out.println("===================================================================");
                }

                int idade = 0;
                Funcionario funcionarioMaisVelho = new Funcionario();
                System.out.println("Funionário mais velho:");
                for (Funcionario funcionario : funcionarios) {
                        int funcionarioIdade = calcularIdade(funcionario.getDataDeNascimento(), LocalDate.now());
                        if (funcionarioIdade > idade) {
                                idade = funcionarioIdade;
                                funcionarioMaisVelho = funcionario;
                        }
                }
                System.out.println("Nome: " + funcionarioMaisVelho.getNome() + "\nIdade: " + idade);
                System.out.println();
                System.out.println("===================================================================");

                System.out.println("Lista de todos os funcionários em ordem alfabética:\n");
                List<Funcionario> funcionariosEmOrdem = funcionarios.stream()
                                .sorted(Comparator.comparing(Funcionario::getNome)).collect(Collectors.toList());
                funcionariosEmOrdem.forEach(x -> System.out.println(x.toString()));
                System.out.println("===================================================================");

                System.out.println("Total dos salários:");
                BigDecimal somaDosSalarios = BigDecimal.valueOf(0);
                for (Funcionario funcionario : funcionarios) {
                        somaDosSalarios = somaDosSalarios.add(funcionario.getSalario());
                }
                System.out.println(nf.format(somaDosSalarios));
                System.out.println();
                System.out.println("===================================================================");

                System.out.println("Lista de salários mínimos que cada funcionário ganha:\n");
                for (Funcionario funcionario : funcionarios) {
                        BigDecimal salariosMinimos = funcionario.getSalario().divide(BigDecimal.valueOf(1212.00), 2,
                                        RoundingMode.HALF_UP);
                        System.out.println("Nome: " + funcionario.getNome() + "\nNúmero de salários mínimos: "
                                        + salariosMinimos + "\n");
                }
                System.out.println("===================================================================");

                System.out.println("*====TAREFA CONCLUÍDA====*");
        }

        public static int calcularIdade(LocalDate birhtDate, LocalDate currentDate) {
                return Period.between(birhtDate, currentDate).getYears();
        }

        public static void addFuncionarios(List<Funcionario> funcionarios, DateTimeFormatter dtf) {
                funcionarios.add(
                                new Funcionario("Maria", LocalDate.parse("18/05/1990", dtf),
                                                BigDecimal.valueOf(2009.44), "Operador"));
                funcionarios.add(
                                new Funcionario("João", LocalDate.parse("12/10/2000", dtf), BigDecimal.valueOf(2284.38),
                                                "Operador"));
                funcionarios.add(new Funcionario("Caio", LocalDate.parse("02/05/1961", dtf), BigDecimal.valueOf(9836.14),
                                "Coordenador"));
                funcionarios.add(
                                new Funcionario("Miguel", LocalDate.parse("14/10/1988", dtf),
                                                BigDecimal.valueOf(19119.88), "Diretor"));
                funcionarios.add(new Funcionario("Alice", LocalDate.parse("05/01/1995", dtf),
                                BigDecimal.valueOf(2234.68),
                                "Recepcionista"));
                funcionarios.add(
                                new Funcionario("Heitor", LocalDate.parse("19/11/1999", dtf),
                                                BigDecimal.valueOf(1582.72), "Operador"));
                funcionarios.add(
                                new Funcionario("Arthur", LocalDate.parse("31/03/1993", dtf),
                                                BigDecimal.valueOf(4071.84), "Contador"));
                funcionarios.add(
                                new Funcionario("Laura", LocalDate.parse("08/07/1994", dtf),
                                                BigDecimal.valueOf(3017.45), "Gerente"));
                funcionarios.add(new Funcionario("Heloísa", LocalDate.parse("24/05/2003", dtf),
                                BigDecimal.valueOf(1606.85),
                                "Eletricista"));
                funcionarios.add(
                                new Funcionario("Helena", LocalDate.parse("02/09/1996", dtf),
                                                BigDecimal.valueOf(2799.93), "Gerente"));
        }
}
