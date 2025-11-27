package br.com.cursojsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.dao.DaoGeneric;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoPessoa;
import br.com.repository.IDaoPessoaImpl;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pessoa pessoa = new Pessoa();
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private IDaoPessoa iDaoPessoa = (IDaoPessoa) new IDaoPessoaImpl();

	public void setiDaoPessoa(IDaoPessoa iDaoPessoa) {
		this.iDaoPessoa = iDaoPessoa;
	}

	public IDaoPessoa getiDaoPessoa() {
		return iDaoPessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

	public String salvar() {
		try {

			pessoa = daoGeneric.merge(pessoa);
			carregarPessoas();

			// FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",
			// "Pessoa salva com sucesso!");
			// FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
					"Falha ao salvar pessoa: " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

		return "";
	}

	public String novo() {
		pessoa = new Pessoa();
		carregarPessoas();

		return "";
	}

	public String remover() {
		daoGeneric.delete(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		return "";
	}

	@PostConstruct
	public void carregarPessoas() {

		try {
			pessoas = daoGeneric.listAll(Pessoa.class);
		} catch (Exception e) {
			System.err.println("Erro ao carregar pessoas: ");
			e.printStackTrace();
			// pessoas = new ArrayList<>();
		}
	}

	public String logar() {

		Pessoa pessoaUser = iDaoPessoa.consultarPessoa(pessoa.getLogin(), pessoa.getSenha());

		if (pessoaUser != null) { // Acho o usuário

			// adiciona usuário na sessão
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			// externalContext.getSessionMap().put("usuarioLogado", pessoaUser.getLogin());

			HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
			HttpSession session = request.getSession();

			session.setAttribute("login", pessoaUser);

			return "paginapessoa.jsf";
		}

		return "index.jsf";
	}

	public boolean permitirAcesso(String acesso) {

		// adiciona usuário na sessão
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		// externalContext.getSessionMap().put("usuarioLogado", pessoaUser.getLogin());

		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = request.getSession();
		Pessoa pessoaUser = (Pessoa) session.getAttribute("login");
		
		return pessoaUser.getPerfil().equals(acesso);
				
	}

}
