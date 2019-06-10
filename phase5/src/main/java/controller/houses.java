package controller;

import domain.Individual;
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
import java.util.ArrayList;

public class houses extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out =new PrintWriter (new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String result = "", userName = "";
        Individual ind = null;
        ArrayList<String> res = new ArrayList<String>();
        JSONObject ret = new JSONObject();

        try{
            JSONObject json = new JSONObject((String)request.getAttribute("body"));

            userName = (String) request.getAttribute("user");

            if(userName == null || userName.equals("") || InfoBank.getUserByUsername(userName) == null) {
                response.setStatus(403);
                result = "Forbidden";
                out.println("{ \"result\" : \"" + result + "\"}");
                return;

            }

            response.setStatus(200);
            result = "OK";

            ind = InfoBank.getUserByUsername(userName);
            if(ind.getIsAdmin() == 1){ // user is admin
                ret.put("result", result);
                ret.put("data", InfoBank.getAllUsersHouses());
            }
            else{
                ret.put("result", result);
                ret.put("data", InfoBank.getUserHouses(userName));
            }


            out.println(ret.toString());
        }catch (Exception e){
            System.out.println(e.getClass().getName() + " : " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
