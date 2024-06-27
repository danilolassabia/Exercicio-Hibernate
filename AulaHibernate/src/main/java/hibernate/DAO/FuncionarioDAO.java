package hibernate.DAO;

import hibernate.model.Funcionario;

import org.hibernate.PropertyValueException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static hibernate.EntityManager.InSession.inSession;


public class FuncionarioDAO {
    Configuration cfg = new Configuration().configure("/hibernate.cfg.xml");
    SessionFactory factory = cfg.buildSessionFactory();
    List<Funcionario> funcionarios = new ArrayList<>();
    Funcionario funcionario;

    public void insertFuncionario(Funcionario funcionario) {
        inSession(factory, entityManager -> {
            try {
                entityManager.persist(funcionario);
            } catch (ConstraintViolationException e) {
                System.out.println("Funcionário já existente!");
            } catch (PropertyValueException e) {
                System.out.println("Departamento ou Projeto não existente! Tente novamente");
            }

        });
    }

    public List<Funcionario> getAllFuncionarios() {
        inSession(factory, entityManager -> {
            funcionarios = entityManager.createQuery("select f from Funcionario f", Funcionario.class).getResultList();
        });
        return funcionarios;
    }

    public Funcionario getFuncionarioPorNome(String nome) {
        inSession(factory, entityManager -> {
            funcionario = entityManager.createQuery("select f from Funcionario f where f.nome= :nome", Funcionario.class).
                    setParameter("nome", nome).getSingleResultOrNull();
        });
        return funcionario;
    }

    public void updateFuncionarioPorNome(Funcionario antigo, Funcionario novo) {
        inSession(factory, entityManager -> {
            try {
                antigo.setNome(novo.getNome());
                antigo.setCargo(novo.getCargo());
                antigo.setDepartamento(novo.getDepartamento());
                antigo.setProjeto(novo.getProjeto());
                entityManager.merge(antigo);
            } catch (NullPointerException e) {
                System.out.println("Funcionário não encontrado! Tente novamente");
             }catch (PropertyValueException e) {
                System.out.println("Departamento ou Projeto não existente! Tente novamente");}
        });
    }

    public void deleteFuncionarioPorNome(String nome) {
        inSession(factory, entityManager -> {
            try {
                entityManager.remove(getFuncionarioPorNome(nome));
            } catch (IllegalArgumentException e) {
                System.out.println("Funcionário não encontrado! Tente novamente!");
            }
        });
    }
}
