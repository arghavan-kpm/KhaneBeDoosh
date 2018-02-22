package controller;

import domain.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class searchCtrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("houses", InfoBank.estate.search(
                    new SearchForm(request.getParameter("minArea"), request.getParameter("buildingType"), request.getParameter("dealType"),
                            request.getParameter("maxPrice"))));

            request.setAttribute("user",InfoBank.getUserByUsername(request.getParameter("user")));
            request.getRequestDispatcher("search.jsp").forward(request, response);

        }catch (Exception e){
            request.setAttribute("mesg",e.getLocalizedMessage());
        }


    }
}
