package jp.co.worksap.roster.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import jp.co.worksap.roster.entity.OrganizationUnit;
import jp.co.worksap.roster.entity.OrganizationUnitTree;

@Stateless
public class OrganizationEJB {
	@PersistenceContext(unitName="RosterManagement")
	private EntityManager em;

	public void createOrganization(OrganizationUnit organizationUnit, int parentId) {
		em.persist(organizationUnit);

		OrganizationUnitTree tree = new OrganizationUnitTree();
		tree.setAncestor(organizationUnit);
		tree.setDescendant(organizationUnit);
		tree.setLength(0);
		em.persist(tree);

		TypedQuery<OrganizationUnitTree> q = em.createNamedQuery("findOrganiaztionUnitTreeAncestors", OrganizationUnitTree.class);
		q.setParameter("descendant_id", parentId);
		List<OrganizationUnitTree> anchestors = q.getResultList();
		for (OrganizationUnitTree anchestor : anchestors) {
			OrganizationUnitTree ascTree = new OrganizationUnitTree();
			ascTree.setAncestor(anchestor.getAncestor());
			ascTree.setDescendant(organizationUnit);
			ascTree.setLength(anchestor.getLength() + 1);
			em.persist(ascTree);
		}
	}

	public List<OrganizationUnitTree> getOrganizationStucture() {
		TypedQuery<OrganizationUnitTree> q = em.createNamedQuery("findAllOrganizationUnitTree", OrganizationUnitTree.class);
		List<OrganizationUnitTree> res = q.getResultList();
		return res;
	}

	public void deleteOrganization(int id) {
		TypedQuery<OrganizationUnitTree> q2 = em.createNamedQuery("deleteOrganizationUnitTree", OrganizationUnitTree.class);
		q2.setParameter("id", id);
		q2.executeUpdate();

		TypedQuery<OrganizationUnit> q = em.createNamedQuery("findOrganizationUnit", OrganizationUnit.class);
		q.setParameter("id", id);
		OrganizationUnit o = q.getSingleResult();
		em.remove(o);
	}

	public void updateOrganization(OrganizationUnit org) {
		TypedQuery<OrganizationUnit> q = em.createNamedQuery("findOrganizationUnit", OrganizationUnit.class);
		q.setParameter("id", org.getId());
		OrganizationUnit o = q.getSingleResult();

		o.setDescription(org.getDescription());
		o.setName(org.getName());
		em.persist(o);
	}
}
