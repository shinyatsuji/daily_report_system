package controllers.Follow;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowReleaseServlet
 */
@WebServlet("/follow_release")
public class FollowReleaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowReleaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        EntityManager em = DBUtil.createEntityManager();

        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

        Employee login_employee = (Employee) request.getSession().getAttribute("login_employee");

        Follow f = (Follow) em.createNamedQuery("getFollowCheck", Follow.class)
                .setParameter("follow", login_employee)
                .setParameter("follower", e)
                .getSingleResult();

        em.getTransaction().begin();
        em.remove(f);
        em.getTransaction().commit();
        em.close();

        if (login_employee.getAdmin_flag() == 1) {
            response.sendRedirect(request.getContextPath() + "/employees/index");
        } else {
            response.sendRedirect(request.getContextPath() + "/list");
        }

    }

}
