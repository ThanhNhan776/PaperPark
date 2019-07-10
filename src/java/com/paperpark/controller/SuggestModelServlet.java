/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paperpark.controller;

import com.paperpark.config.model.ModelEstimation;
import com.paperpark.contants.ConfigConstants;
import com.paperpark.dao.model.ModelDAO;
import com.paperpark.entity.Model;
import com.paperpark.models.ResultModels;
import com.paperpark.utils.JAXBUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author NhanTT
 */
@WebServlet(name = "SuggestModelServlet", urlPatterns = {"/suggestModel"})
public class SuggestModelServlet extends HttpServlet {

    private static final String URL = "searchResult.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");

        String skillLevelStr = request.getParameter("skillLevel");
        String difficultyStr = request.getParameter("difficulty");
        String totalHoursStr = request.getParameter("totalHours");

        try (PrintWriter writer = response.getWriter()) {
            int skillLevel = Integer.parseInt(skillLevelStr);
            int difficulty = Integer.parseInt(difficultyStr);
            double totalHours = Double.parseDouble(totalHoursStr);

            HttpSession session = request.getSession();
            List<Model> models = getAllModels(session, skillLevel);

            List<Model> foundModels = new ArrayList<>();
            models.forEach((model) -> {
                if ((model.getDifficulty() + 1) / 2 == difficulty
                        && model.getEstimateTime() <= totalHours) {
                    foundModels.add(model);
                }
            });

            ResultModels resultModels = new ResultModels();
            resultModels.setResultModel(foundModels);

            String resultModelsXml = JAXBUtils.marshall(resultModels);

//            System.out.println("DEBUG XML: \n" + resultModelsXml);
            writer.write(resultModelsXml);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            Logger.getLogger(SuggestModelServlet.class.getName())
                    .log(Level.SEVERE, null, e);
        }
    }

    private List<Model> getAllModels(HttpSession session, int skillLevel) {
        List<Model> models = (List<Model>) session.getAttribute("MODELS");
        Long cacheTime = (Long) session.getAttribute("CACHE_TIME");

        long now = System.currentTimeMillis();

        ServletContext context = session.getServletContext();
        
        if (models == null || cacheTime == null
                || (now - cacheTime > ConfigConstants.CACHE_MODELS_TIMEOUT)) {

            models = getAlllModels(session.getServletContext());
            refineModels(context, models, skillLevel);

            session.setAttribute("MODELS", models);
            session.setAttribute("SKILL_LEVEL", skillLevel);
            session.setAttribute("CACHE_TIME", now);
        }

        Integer cachedSkillLevel = (Integer) session.getAttribute("SKILL_LEVEL");
        if (cachedSkillLevel == null || cachedSkillLevel != skillLevel) {
            refineModels(context, models, skillLevel);

            session.setAttribute("MODELS", models);
            session.setAttribute("SKILL_LEVEL", skillLevel);
            session.setAttribute("CACHE_TIME", now);
        }

        return models;
    }

    private List<Model> getAlllModels(ServletContext context) {
        List<Model> models = (List<Model>) context.getAttribute("MODELS");
        Long cacheTime = (Long) context.getAttribute("CACHE_TIME");

        long now = System.currentTimeMillis();

        if (models == null || cacheTime == null
                || (now - cacheTime > ConfigConstants.CACHE_MODELS_TIMEOUT)) {

            ModelDAO modelDAO = ModelDAO.getInstance();
            models = modelDAO.getAllModels();

            context.setAttribute("MODELS", models);
            context.setAttribute("CACHE_TIME", now);
        }

        return models;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void refineModels(ServletContext context, List<Model> models, int skillLevel) {
        ModelEstimation estimation = (ModelEstimation) context.getAttribute("MODEL_ESTIMATION");
        if (estimation == null) {
            estimation = ModelEstimation.getModelEstimation(context.getRealPath("/"));
            context.setAttribute("MODEL_ESTIMATION", estimation);
        }
        
        final ModelEstimation me = estimation;
        
        models.forEach((model) -> {
            model.estimateMakingTime(me, skillLevel);
        });
    }

}
