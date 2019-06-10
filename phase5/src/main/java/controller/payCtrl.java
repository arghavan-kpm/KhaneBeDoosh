package controller;

import domain.*;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.Line;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class payCtrl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out =new PrintWriter (new OutputStreamWriter(response.getOutputStream(), "UTF8"), true);
        response.setContentType("application/json ; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String result;

        try {

            JSONObject json = new JSONObject((String)request.getAttribute("body"));
            String house_id = json.getString("house_id");

            String user = (String)request.getAttribute("user");

            if(user == null)
                user = "";

            if(user.equals("")){
                result = "No User";
                response.setStatus(401);
                out.println("{ \"result\" : \"" + result + "\"}");
                return;
            }

            else if( ( InfoBank.estate.getHouseById(house_id)!=null)
                    && InfoBank.getUserByUsername(user) != null
                    ) {

                if (!InfoBank.getUserByUsername(user).searchHouse(InfoBank.estate.getHouseById(house_id))) {
                    if (InfoBank.getUserByUsername(user).getCredit() >= 1000) {

                        InfoBank.getUserByUsername(user).AddHouse(InfoBank.estate.getHouseById(house_id));

                        result = "OK";
                        response.setStatus(200);
                    }
                    else {
                        response.setStatus(402);
                        result = "insufficient credit";
                        // request.setAttribute("ERROR", "اعتبار کافی نیست");
                    }

                }
                else{
                    result = "paid";
                    response.setStatus(208);
                }
            }
            else{
                result = "invalid Param";
                response.setStatus(406);
            }
            /*
            request.setAttribute("house_id",request.getParameter("house_id"));
            request.setAttribute("user",InfoBank.getUserByUsername(request.getParameter("user")));
            request.getRequestDispatcher("cmoreInfo").forward(request, response);*/

            out.println("{ \"result\" : \"" + result + "\",\"newCredit\" : " + InfoBank.getUserByUsername(user).getCredit() + "}");
        }catch(Exception e){}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
