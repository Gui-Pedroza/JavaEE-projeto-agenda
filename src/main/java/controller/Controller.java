package controller;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = {"/main", "/insert", "/select", "/update"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		// apenas para imprimir a URL do servlet:
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else {
			response.sendRedirect("index.html");
		}
	}

	// renderiza os contatos atualizados na página principal
	protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<JavaBeans> lista = dao.listarContatos();
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}
	
	// adicionar contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response) throws IOException {		
		
		// adicionando as variáves ao objeto
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// invocar o método inserirContato() passando como parametro o objeto contato
		dao.inserirContato(contato);
		// redirecionar para a página agenda.jsp
		response.sendRedirect("main");
	}
	
	// editar contato: passo 1, primeiro localiza o contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// recebe o id do contato que será editado:
		String idcon = request.getParameter("idcon");
		// armazena o id na variável do contato JavaBeans
		contato.setIdcon(idcon);
		// busca no banco o contato correspondente e armazena nas demais variáveis o restante dos outros atributos
		dao.selecionarContato(contato);
		// preenche automaticamente os atributos do formulário com os dados do contato a ser editado
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		// enviar tudo ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);		
	}
	
	// editar contato: passo 2, depois de localizado o contato, o método abaixo irá atualiza-lo
	protected void editarContato(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		dao.alterarContato(contato);
		// redireciona para a lista de contatos atualizada
		response.sendRedirect("main");
	}
	
	
}
