package hibernate;

import hibernate.DAO.DepartamentoDAO;
import hibernate.DAO.FuncionarioDAO;
import hibernate.DAO.ProjetoDAO;
import hibernate.model.Departamento;
import hibernate.model.Funcionario;
import hibernate.model.Projeto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DepartamentoDAO departamentoDAO = new DepartamentoDAO();
        ProjetoDAO projetoDAO = new ProjetoDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        Departamento departamento = new Departamento();
        Projeto projeto = new Projeto();
        Funcionario funcionario = new Funcionario();
        List<Funcionario> funcionarios = new ArrayList<>();
        int opcao = 1;
        //System.out.println(projetoDAO.getFuncionariosPorProjeto("Beta"));

        while (opcao != 0) {
            System.out.println("Escolha o número da opção que deseja realizar:\n" +
                    "1-Inserir um Departamento\n" +
                    "2-Mostrar todos os Departamentos\n" +
                    "3-Encontrar um Departamento por nome\n" +
                    "4-Alterar um Departamento\n" +
                    "5-Deletar um Departamento\n" +
                    "6-Inserir um Projeto\n" +
                    "7-Mostrar todos os Projetos\n" +
                    "8-Encontrar um Projeto por nome\n" +
                    "9-Alterar um Projeto\n" +
                    "10-Deletar um Projeto\n" +
                    "11-Inserir um Funcionário\n" +
                    "12-Mostrar todos os Funcionários\n" +
                    "13-Encontrar um Funcionário por Nome\n" +
                    "14-Alterar um Funcionário\n" +
                    "15-Deletar um Fuincionário\n" +
                    "16-Mostrar todos os Funcionários de um Departamento\n" +
                    "17-Mostrar todos os Funcionários de um Projeto\n" +
                    "0-Sair");
            try {
                opcao = Integer.parseInt(sc.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.println("Nome do Departamento:");
                        departamentoDAO.insertDepartamento(Departamento.builder().nome(sc.nextLine()).build());
                        break;
                    case 2:
                        System.out.println(departamentoDAO.getAllDepartamentos());
                        break;
                    case 3:
                        System.out.println("Escreva o nome do Departamento que deseja exibir:");
                        departamento = departamentoDAO.getDepartamentoPorNome(sc.nextLine());
                        System.out.println((Objects.isNull(departamento)) ?
                                "Departamento não encontrado! Tente novamente!" :
                                departamento);
                        break;
                    case 4:
                        System.out.println("Escreva o nome do Departamento que deseja alterar:");
                        departamentoDAO.updateDepartamentoPorNome(sc.nextLine());
                        break;
                    case 5:
                        System.out.println("Escreva o nome do Departamento que deseja deletar:");
                        departamentoDAO.deleteDepartamentoPorNome(sc.nextLine());
                        break;
                    case 6:
                        System.out.println("Nome do Projeto:");
                        projetoDAO.insertProjeto(Projeto.builder().nome(sc.nextLine()).build());
                        break;
                    case 7:
                        System.out.println(projetoDAO.getAllProjetos());
                        break;
                    case 8:
                        System.out.println("Escreva o nome do Projeto que deseja exibir:");
                        projeto = projetoDAO.getProjetoPorNome(sc.nextLine());
                        System.out.println((Objects.isNull(projeto)) ?
                                "Projeto não encontrado! Tente novamente!" :
                                projeto);
                        break;
                    case 9:
                        System.out.println("Escreva o nome do Projeto que deseja alterar:");
                        projetoDAO.updateProjetoPorNome(sc.nextLine());
                        break;
                    case 10:
                        System.out.println("Escreva o nome do Projeto que deseja deletar:");
                        projetoDAO.deleteProjetoPorNome(sc.nextLine());
                        break;
                    case 11:
                        System.out.println("Nome do Funcionário:");
                        funcionario.setNome(sc.nextLine());
                        System.out.println("Cargo do Funcionário:");
                        funcionario.setCargo(sc.nextLine());
                        System.out.println("Departamento do Funcionário:");
                        funcionario.setDepartamento(departamentoDAO.getDepartamentoPorNome(sc.nextLine()));
                        System.out.println("Projeto do Funcionário:");
                        funcionario.setProjeto(projetoDAO.getProjetoPorNome(sc.nextLine()));
                        funcionarioDAO.insertFuncionario(funcionario);
                        break;
                    case 12:
                        System.out.println(funcionarioDAO.getAllFuncionarios());
                        break;
                    case 13:
                        System.out.println("Escreva o nome do Funcionário que deseja exibir:");
                        funcionario = funcionarioDAO.getFuncionarioPorNome(sc.nextLine());
                        System.out.println((Objects.isNull(funcionario)) ?
                                "Funcionário não encontrado! Tente novamente!" :
                                funcionario);
                        break;
                    case 14:
                        System.out.println("Novo nome do Funcionário:");
                        funcionario.setNome(sc.nextLine());
                        System.out.println("Novo cargo do Funcionário:");
                        funcionario.setCargo(sc.nextLine());
                        System.out.println("Novo Departamento do Funcionário:");
                        funcionario.setDepartamento(departamentoDAO.getDepartamentoPorNome(sc.nextLine()));
                        System.out.println("Novo Projeto do Funcionário:");
                        funcionario.setProjeto(projetoDAO.getProjetoPorNome(sc.nextLine()));
                        System.out.println("Escreva o nome do Funcionário que deseja alterar:");
                        funcionarioDAO.updateFuncionarioPorNome(funcionarioDAO.getFuncionarioPorNome(sc.nextLine()), funcionario);
                        break;
                    case 15:
                        System.out.println("Escreva o nome do Funcionário que deseja deletar:");
                        funcionarioDAO.deleteFuncionarioPorNome(sc.nextLine());
                        break;
                    case 16:
                        System.out.println("Escreva o nome do Departamento:");
                        funcionarios = departamentoDAO.getFuncionariosPorDepartamento(sc.nextLine());
                        System.out.println((Objects.isNull(funcionarios)) ?
                                "Departamento não encontrado! Tente novamente!" :
                                funcionarios);

                        break;
                    case 17:
                        System.out.println("Escreva o nome do Projeto:");
                        funcionarios = projetoDAO.getFuncionariosPorProjeto(sc.nextLine());
                        System.out.println((Objects.isNull(funcionarios)) ?
                                "Projeto não encontrado! Tente novamente!" :
                                funcionarios);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Insira uma opção válida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Insira um número válido!");
            }
        }
        System.out.println("Programa encerrado!");
    }
}
