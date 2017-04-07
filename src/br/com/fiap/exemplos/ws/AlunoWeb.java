package br.com.fiap.exemplos.ws;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

@WebService
public class AlunoWeb {
	
	@Resource
	WebServiceContext wsContex;
	
	private List<Aluno> alunos;
	
	public AlunoWeb(){
		this.alunos = new ArrayList<>();
	}
	
	@WebMethod
	public String novo(Aluno a){
		if(autenticado()){
			this.alunos.add(a);
			return "Success";
		}
		
		return "Acesso Negado";
	}
	
	@WebMethod
	public List<Aluno> listar() throws Exception{
		if(autenticado()){
			return this.alunos;
		}
		else{
			throw new Exception("Acesso Negado");
		}
	}
	
	public boolean autenticado(){
		MessageContext mContext = wsContex.getMessageContext();
		Map httpHeaders = (Map) mContext.get(MessageContext.HTTP_REQUEST_HEADERS);
		List usuarios = (List) httpHeaders.get("Username");
		List senhas = (List) httpHeaders.get("Password");
		
		String usuario = "";
		String senha = "";
		
		if(usuario !=null){
			usuario = usuarios.get(0).toString();
		}
		
		if(senha != null){
			senha = senhas.get(0).toString();
		}
		
		if("danilo".equals(usuario) && "1234".equals(senha)){
			return true;
		}
		return false;
	}
	

}
