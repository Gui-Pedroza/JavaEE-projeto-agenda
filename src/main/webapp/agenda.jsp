<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.JavaBeans"%>
<%@ page import="java.util.ArrayList"%>
<%
	ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>) request.getAttribute("contatos");
	
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Agenda de Contatos</title>
<link rel="shortcut icon" href="imagens/favicon.png" type="image/x-icon">
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>Agenda de contados</h1>
	<a href="novo.html" class="botao1">Novo contato</a>
	<a href="report" class="botao2">Relatório</a>
	<table id="agenda">
		<thead>
			<tr>
				<th class="listagem" scope="col">Id</th>
				<th class="listagem" class="listagem" scope="col">Nome</th>
				<th class="listagem" scope="col">Fone</th>
				<th class="listagem" scope="col">E-mail</th>
				<th class="listagem" scope="col">Opções</th>
			</tr>
		</thead>
		<tbody>
			<%for (JavaBeans contatos : lista) { %>
				<tr>
					<td class="listagem"><%=contatos.getIdcon()%></td>
					<td class="listagem"><%=contatos.getNome()%></td>
					<td class="listagem"><%=contatos.getFone()%></td>
					<td class="listagem"><%=contatos.getEmail()%></td>
					<td class="listagem">
					<a href="select?idcon=<%= contatos.getIdcon()%>" class="botao1">Editar</a>
					<a href="javascript: confirmar(<%= contatos.getIdcon()%>)" class="botao2">Excluir</a>
					</td>
				</tr>
			<%} %>
		</tbody>
	</table>
	<script src="scripts/confirmador.js"></script>
</body>
</html>