package br.com.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Lancamento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	@Column (nullable = false)
	private String numeroNotaFiscal;
	
	@Column (nullable = false)
	private String empresaOrigem;
	
	@Column (nullable = false)
	private String empresaDestino;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Pessoa usuario;
	
	//Setters e Getters
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}
	
	public String getNumeroNotaFiscal(){
		return numeroNotaFiscal;
	}
	
	public void setEmpresaOrigem(String empresaOrigem) {
		this.empresaOrigem = empresaOrigem;
	}
	
	public String getEmpresaOrigem() {
		return empresaOrigem;
	}
	
	public void setEmpresaDestino (String empresaDestino) {
		this.empresaDestino = empresaDestino;
	}
	
	public String getEmpresaDestino() {
		return empresaDestino;
	}
	
	public void setUsuario (Pessoa usuario) {
		this.usuario = usuario;
	}
	
	public Pessoa getUsuario() {
		return usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lancamento [id=" + id + ", numeroNotaFiscal=" + numeroNotaFiscal + ", empresaOrigem=" + empresaOrigem
				+ ", empresaDestino=" + empresaDestino + ", usuario=" + usuario + "]";
	}
	
	
	
//Fim da classe
}
