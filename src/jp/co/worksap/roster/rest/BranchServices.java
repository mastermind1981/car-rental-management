package jp.co.worksap.roster.rest;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import jp.co.worksap.roster.ejb.BranchEJB;
import jp.co.worksap.roster.ejb.UserEJB;
import jp.co.worksap.roster.entity.Branch;
import jp.co.worksap.roster.entity.User;
import jp.co.worksap.roster.entity.UserRole;

@Path("/branches")
@Stateless
public class BranchServices {
	@EJB
	private BranchEJB branchEJB;

	@EJB
	private UserEJB userEJB;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("")
	@RolesAllowed({"admin" ,"hr"})
	public Response create(Branch branch) {
		branchEJB.createBranch(branch);
		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("")
	public List<Branch> index(@Context SecurityContext context) {
		List<UserRole> roles = new LinkedList<UserRole>();

		if (context.getUserPrincipal() != null) {
			String userId = context.getUserPrincipal().getName();
			roles = userEJB.findAllAssignedRoles(userId);

			boolean displayAllBranch = false;
			for (UserRole role : roles) {
				if (role.getRoleName().equals("director") || role.getRoleName().equals("customer") || role.getRoleName().equals("hr")) {
					displayAllBranch = true;
				}
			}

			if (displayAllBranch) {
				return branchEJB.findBranches();
 			} else {
 				User user = userEJB.findUser(userId);
 				return user.getBranches();
 			}
		} else {
			return branchEJB.findBranches();
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin" ,"hr"})
	public Response destroy(@PathParam("id") int id) {
		branchEJB.deleteBranch(id);
		return Response.status(Status.ACCEPTED).type(MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin" ,"hr"})
	public Response update(@PathParam("id") int id, Branch branch) {
		branchEJB.updateBranch(id, branch);
		return Response.status(Status.ACCEPTED).type(MediaType.APPLICATION_JSON).build();
	}

	@POST
	@Path("/{id}/users")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin" ,"hr"})
	public Response addUser(@PathParam("id") int id, @QueryParam("userId") String userId) {
		User user = userEJB.findUser(userId);
		branchEJB.addUser(id, user);
		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path("/{id}/users")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin" ,"hr"})
	public Response removeUser(@PathParam("id") int id, @QueryParam("userId") String userId) {
		User user = userEJB.findUser(userId);
		branchEJB.removeUser(id, user);
		return Response.status(Status.CREATED).type(MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({"admin" ,"hr"})
	public Branch show(@PathParam("id") int id) {
		Branch b = branchEJB.findBranch(id);
		// Eager load
		b.getUsers();
		return b;
	}
}