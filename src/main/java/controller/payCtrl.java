package controller;

import domain.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class payCtrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            if (InfoBank.getUserByUsername(request.getParameter("user")).getCredit() >= 1000)
                InfoBank.getUserByUsername(request.getParameter("user")).AddHouse(request.getParameter("house_id"));
            else {
                request.setAttribute("ERROR", "اعتبار کافی نیست");
            }
            request.setAttribute("house_id",request.getParameter("house_id"));
            request.setAttribute("user",InfoBank.getUserByUsername(request.getParameter("user")));
            request.getRequestDispatcher("cmoreInfo").forward(request, response);
        }catch(Exception e){}
    }
}
