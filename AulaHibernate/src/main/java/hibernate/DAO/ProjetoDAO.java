package hibernate.DAO;

import hibernate.model.Funcionario;
import hibernate.model.Projeto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static hibernate.EntityManager.InSession.inSession;

public class ProjetoDAO {
    Configuration cfg = new Configuration().configure("/hibernate.cfg.xml");
    SessionFactory factory = cfg.buildSessionFactory();
    List<Projeto> projetos = new ArrayList<>();
    Projeto projeto;

    public void insertProjeto(Projeto projeto) {
        inSession(factory, entityManager -> {
            try {
                entityManager.persist(projeto);
            }catch(ConstraintViolationException e){
                System.out.println("Projeto já existente!");
            }

        });
    }

    public List<Projeto> getAllProjetos() {
        inSession(factory, entityManager -> {
            projetos = entityManager.createQuery("select p from Projeto p", Projeto.class).getResultList();
        });
        return projetos;
    }

    public Projeto getProjetoPorNome(String nome) {
        inSession(factory, entityManager -> {
            projeto = entityManager.createQuery("select p from Projeto p where p.nome= :nome", Projeto.class).
                    setParameter("nome",nome).getSingleResultOrNull();
        });
        return projeto;
    }

    public void updateProjetoPorNome(String nome) {
        inSession(factory, entityManager -> {
            try{
                projeto = getProjetoPorNome(nome);
                Scanner sc = new Scanner(System.in);
                System.out.println("Digite o novo nome que deseja inserir:");
                projeto.setNome(sc.nextLine());
                entityManager.merge(projeto);
            }catch (NullPointerException e){
                System.out.println("Projeto não encontrado! Tente novamente");
            }
        });
    }

    public void deleteProjetoPorNome(String nome) {
        inSession(factory, entityManager -> {
            try{
                entityManager.remove(getProjetoPorNome(nome));
            }catch (IllegalArgumentException e){
                System.out.println("Projeto não encontrado! Tente novamente!");
            }catch (ConstraintViolationException e) {
                System.out.println("Não é possível deletar um Projeto que já possui um Funcionário!");
            }
        });
    }

    public List<Funcionario> getFuncionariosPorProjeto(String nome) {
        projeto = getProjetoPorNome(nome);
        try{
            return projeto.getFuncionarios();
        } catch(NullPointerException e) {
            return null;
        }
    }
}