package controller;

import domain.InfoBank;
import domain.Tools;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class loginCtrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out =new PrintWriter (new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String result, userName = "";
        int credit = 0;
        try{


            JSONObject json = new JSONObject((String)request.getAttribute("body"));

            userName = (String) request.getAttribute("user");

            if(userName == null || InfoBank.getUserByUsername(userName) == null )
                userName = "";

            if(userName.equals("") == false)
                credit = InfoBank.getUserByUsername(userName).getCredit();

            response.setStatus(200);
            result = "OK";

            out.println("{ \"result\" : \"" + result + "\",\"user\" : \"" + userName + "\",\"credit\" : " + String.format("%d",credit) + "}");
        }catch (Exception e){
            System.out.println("clogin " + e.getClass().getName() + " : " + e.getMessage());
        }
        // request.getRequestDispatcher("form.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



    }
}
