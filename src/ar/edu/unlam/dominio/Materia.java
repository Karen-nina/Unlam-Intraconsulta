package ar.edu.unlam.dominio;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Materia {

	private Integer id;
	private String nombre;
	private Set<Integer> correlatividades;

	public Materia(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.correlatividades = new HashSet<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Integer> getCorrelatividades() {
		return correlatividades;
	}

	public void setCorrelatividades(Set<Integer> correlatividades) {
		this.correlatividades = correlatividades;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Materia other = (Materia) obj;
		return Objects.equals(id, other.id);
	}

}
