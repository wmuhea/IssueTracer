package controllers;

import Data.IssuesDao;
import com.google.gson.Gson;
import models.Issue;
import models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ApplicationController", urlPatterns = "/app")
public class ApplicationController extends HttpServlet {
    Gson objectJsonConverter = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getAttribute("redirectUrl") == null) {
            request.getRequestDispatcher("/app/dashboard.jsp").forward(request, response);
        }
        else if (request.getParameter("param").equals("assignedIssues")){

            User loggedInUser = (User) request.getSession().getAttribute("user");
            List<Issue> issuesAssignedToUser = IssuesDao.getInstance().readIssuesFromDb(loggedInUser.getUsername());

            String jsonListOfIssues = objectJsonConverter.toJson(issuesAssignedToUser);
            PrintWriter out = response.getWriter();
            out.println(jsonListOfIssues);
        }
        else{
            response.sendRedirect((String) request.getAttribute("redirectUrl"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = ((User) request.getSession().getAttribute("user")).getUsername();
        Issue issue = objectJsonConverter.fromJson(request.getParameter("issue"), Issue.class);
        IssuesDao.getInstance().removeIssue(username, issue);

    }
}
