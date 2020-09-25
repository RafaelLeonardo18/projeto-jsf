package br.com.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery (name = "login", query = "select p from Pessoa p where p.login = :login and p.senha = :senha")
})

@Entity
public class Pessoa  implements Serializable{

	private static final long serialVersionUID = 1L;

	//Atributos da entidade
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private int id;
	
	@Column (nullable = false)
	private String nome;
	
	@Column (nullable = false)
	private String sobrenome;
	
	@Column (nullable = false)
	private int idade;
	
	@Column (nullable = false)
	private String sexo;
	
	@Column
	private String [] frameworks;
	
	@Column
	private Boolean ativo;
	
	@Column
	private String login;
	
	@Column
	private String senha;
	
	@Column
	private String perfil;
	
	
	@Temporal (TemporalType.DATE)
	private Date dataNascimento = new Date();
	
	//Setters e Getters
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	public String getSobrenome() {
		return sobrenome;
	}
	
	public void setIdade(int idade) {
		this.idade = idade;
	}
	
	public int getIdade() {
		return idade;
	}
	
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public String getSexo() {
		return sexo;
	}
	
	public void setFrameworks(String[] frameworks) {
		this.frameworks = frameworks;
	}
	
	public String[] getFrameworks() {
		return frameworks;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setPerfil (String perfil) {
		this.perfil = perfil;
	}
	
	public String getPerfil() {
		return perfil;
	}
	
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public Date getDataNascimento() {
		return dataNascimento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Pessoa other = (Pessoa) obj;
		if (id != other.id)
			return false;
		return true;
	}	
	
//Fim da classe
}
