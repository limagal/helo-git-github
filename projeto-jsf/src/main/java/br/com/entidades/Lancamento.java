package br.com.entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Lancamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String NumeroNotaFiscal;
	private String EmpresaOrigem;
	private String EmpresaDestino;
	@ManyToOne
	private Pessoa usuarioLogado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroNotaFiscal() {
		return NumeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		NumeroNotaFiscal = numeroNotaFiscal;
	}

	public String getEmpresaOrigem() {
		return EmpresaOrigem;
	}

	public void setEmpresaOrigem(String empresaOrigem) {
		EmpresaOrigem = empresaOrigem;
	}

	public String getEmpresaDestino() {
		return EmpresaDestino;
	}

	public void setEmpresaDestino(String empresaDestino) {
		EmpresaDestino = empresaDestino;
	}

	public Pessoa getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Pessoa usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(EmpresaDestino, EmpresaOrigem, NumeroNotaFiscal, id, usuarioLogado);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		return Objects.equals(EmpresaDestino, other.EmpresaDestino)
				&& Objects.equals(EmpresaOrigem, other.EmpresaOrigem)
				&& Objects.equals(NumeroNotaFiscal, other.NumeroNotaFiscal) && Objects.equals(id, other.id)
				&& Objects.equals(usuarioLogado, other.usuarioLogado);
	}
}
