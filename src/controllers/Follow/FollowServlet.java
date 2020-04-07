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
 * Servlet implementation class FollowServlet
 */
@WebServlet("/follow")
public class FollowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = DBUtil.createEntityManager();

        Employee follower = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

        Employee follow = (Employee) request.getSession().getAttribute("login_employee");

        Follow f = new Follow();
        f.setFollow(follow);
        f.setFollower(follower);

        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        em.close();

        if (follow.getAdmin_flag() == 1) {
            response.sendRedirect(request.getContextPath() + "/employees/index");
        } else {
            response.sendRedirect(request.getContextPath() + "/list");
        }
    }
}
