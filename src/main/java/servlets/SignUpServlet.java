package servlets;


import dbService.DBException;
import dbService.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Feraijo on 28.11.2016.
 */
public class SignUpServlet extends HttpServlet {
    private final DBService dbService;

    public SignUpServlet(DBService dbService) {
        this.dbService = dbService;
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String login = request.getParameter("login");
        String pass = request.getParameter("password");


        if (pass == null || pass.equals("") ){
            try {
                dbService.addUser(login);
            } catch (DBException e) {
                badReq(response, e);
                return;
            }
        }else{
            try {
                dbService.addUser(login, pass);
            } catch (DBException e) {
                badReq(response, e);
                return;
            }
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Successful registration! Added user: " + login);
        System.out.println("Added user: " + login);

    }

    private void badReq(HttpServletResponse response, DBException e) throws IOException {
        e.printStackTrace();
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("Can't register. There is already that user");
    }
}
