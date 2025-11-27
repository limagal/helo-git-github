package br.com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import br.com.jpautil.JPAUtil;

public class DaoGeneric<E> {

	public void salvar(E entidade) {

		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();
			entityManager.persist(entidade);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace(); // ou logar o erro com um logger
		} finally {
			entityManager.close();
		}
	}

	public E merge(E entidade) {

		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		E retornoEntidade = null; // declarar antes do try

		try {
			transaction.begin();
			retornoEntidade = entityManager.merge(entidade);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace(); // ou logar o erro com um logger
			// } finally {
			// entityManager.close();

		}
		return retornoEntidade;
	}

	public void delete(E entidade) {

		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();
			// Garantir que a entidade está gerenciada
			entidade = entityManager.merge(entidade);
			entityManager.remove(entidade);
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace(); // ou logar o erro com um logger
		} finally {
			entityManager.close();
		}

	}

	public void deletePorId(E entidade) {
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();
			Object id = JPAUtil.getPrimaryKey(entidade);
			E managedEntity = entityManager.find((Class<E>) entidade.getClass(), id);
			if (managedEntity != null) {
				entityManager.remove(managedEntity);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
	}

	public List<E> listAll(Class<E> entidade) {

		EntityManager entityManager = JPAUtil.getEntityManager();
		// EntityTransaction entityTransaction = entityManager.getTransaction();

		List<E> lista = new ArrayList<>();

		// try {
		// entityTransaction.begin();

		String entityName = entidade.getSimpleName();
		lista = entityManager.createQuery("from " + entityName, entidade).getResultList();

		// entityTransaction.commit();
		// } catch (Exception e) {
		// if (entityTransaction.isActive()) {
		// entityTransaction.rollback();
		// }
		// e.printStackTrace();
		// }finally {
		// entityManager.close();
		// }
		return lista;
	}
	
}
