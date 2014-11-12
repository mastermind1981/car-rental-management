package jp.co.worksap.roster.ejb;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionRolledbackLocalException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import jp.co.worksap.roster.entity.User;

@Stateless
public class UserEJB {
	@PersistenceContext(unitName="RosterManagement")
	private EntityManager em;

	public List<User> findAllUsers() {
		TypedQuery<User> q = em.createNamedQuery("findAllUsers", User.class);
		return q.getResultList();
	}

	public Set<ConstraintViolation<?>> createUser(User user) {
		try {
			em.persist(user);
			return null;
		} catch (ConstraintViolationException ex) {
			return ((ConstraintViolationException)ex).getConstraintViolations();
		} catch (Exception ex) {
			return new HashSet<ConstraintViolation<?>>();
		}
	}
}
