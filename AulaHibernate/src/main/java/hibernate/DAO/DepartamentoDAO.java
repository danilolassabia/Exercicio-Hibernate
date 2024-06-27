package hibernate.DAO;

import hibernate.model.Departamento;
import hibernate.model.Funcionario;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static hibernate.EntityManager.InSession.inSession;

public class DepartamentoDAO {
    Configuration cfg = new Configuration().configure("/hibernate.cfg.xml");
    SessionFactory factory = cfg.buildSessionFactory();
    List<Departamento> departamentos = new ArrayList<>();
    Departamento departamento;

    public void insertDepartamento(Departamento departamento) {
        inSession(factory, entityManager -> {
            try {
                entityManager.persist(departamento);
            }catch(ConstraintViolationException e){
                System.out.println("Departamento já existente!");
            }

        });
    }

    public List<Departamento> getAllDepartamentos() {
        inSession(factory, entityManager -> {
            departamentos = entityManager.createQuery("select d from Departamento d", Departamento.class).getResultList();
        });
        return departamentos;
    }

    public Departamento getDepartamentoPorNome(String nome) {
        inSession(factory, entityManager -> {
            departamento = entityManager.createQuery("select d from Departamento d where d.nome= :nome", Departamento.class).
                    setParameter("nome", nome).getSingleResultOrNull();
        });
        return departamento;
    }

    public void updateDepartamentoPorNome(String nome) {
        inSession(factory, entityManager -> {
            try {
                departamento = getDepartamentoPorNome(nome);
                Scanner sc = new Scanner(System.in);
                System.out.println("Digite o novo nome que deseja inserir:");
                departamento.setNome(sc.nextLine());
                entityManager.merge(departamento);
            } catch (NullPointerException e) {
                System.out.println("Departamento não encontrado! Tente novamente");
            }
        });
    }

    public void deleteDepartamentoPorNome(String nome) {
        inSession(factory, entityManager -> {
            try {
                entityManager.remove(getDepartamentoPorNome(nome));
            } catch (IllegalArgumentException e) {
                System.out.println("Departamento não encontrado! Tente novamente!");
            }catch (ConstraintViolationException e) {
                System.out.println("Não é possível deletar um Departamento que já possui um Funcionário!");
            }
        });
    }

    public List<Funcionario> getFuncionariosPorDepartamento(String nome) {
        departamento = getDepartamentoPorNome(nome);
        try{
            return departamento.getFuncionarios();
        } catch(NullPointerException e) {
            return null;
        }
    }
}