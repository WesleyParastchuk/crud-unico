/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.servlet;

import com.example.dao.ProdutoDAO;
import com.example.entity.Produto;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 *
 * @author wesle
 */

@WebServlet("/ProdutoServlet")
public class ProdutoServlet extends HttpServlet {

    private ProdutoDAO dao = new ProdutoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            String nome = request.getParameter("nome");
            double preco = Double.parseDouble(request.getParameter("preco"));
            int quantidade = Integer.parseInt(request.getParameter("quantidade"));

            Produto produto = new Produto();
            produto.setNome(nome);
            produto.setPreco(preco);
            produto.setQuantidade(quantidade);

            dao.salvar(produto);
        } else if ("update".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            String nome = request.getParameter("nome");
            double preco = Double.parseDouble(request.getParameter("preco"));
            int quantidade = Integer.parseInt(request.getParameter("quantidade"));

            Produto produto = dao.buscarPorId(id);
            if (produto != null) {
                produto.setNome(nome);
                produto.setPreco(preco);
                produto.setQuantidade(quantidade);
                dao.atualizar(produto);
            }
        } else if ("delete".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            dao.excluir(id);
        }

        response.sendRedirect("produtos.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("produtos", dao.listarTodos());
        RequestDispatcher dispatcher = request.getRequestDispatcher("produtos.jsp");
        dispatcher.forward(request, response);
    }
}
