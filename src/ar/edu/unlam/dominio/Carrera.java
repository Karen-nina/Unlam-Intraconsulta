package ar.edu.unlam.dominio;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Carrera {

	private Integer id;
	private String nombre;
	private Set<Materia> planDeEstudios;

	public Carrera(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.planDeEstudios = new HashSet<>();

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

	public Set<Materia> getPlanDeEstudios() {
		return planDeEstudios;
	}

	public void setPlanDeEstudios(Set<Materia> planDeEstudios) {
		this.planDeEstudios = planDeEstudios;
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
		Carrera other = (Carrera) obj;
		return Objects.equals(id, other.id);
	}

	public boolean agregarMateria(Materia materia) {
		if (planDeEstudios.contains(materia)) {
			return false;
		}
		this.planDeEstudios.add(materia);
		return true;
	}

}
