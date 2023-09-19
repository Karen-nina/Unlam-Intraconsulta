package ar.edu.unlam.dominio;

import java.util.Objects;

public class Aula {

	private Integer id;
	private Integer capacidadMaxima;

	public Aula(Integer id, Integer capacidadMaxima) {
		super();
		this.id = id;
		this.capacidadMaxima = capacidadMaxima;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCapacidadMaxima() {
		return capacidadMaxima;
	}

	public void setCapacidadMaxima(Integer capacidadMaxima) {
		this.capacidadMaxima = capacidadMaxima;
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
		Aula other = (Aula) obj;
		return Objects.equals(id, other.id);
	}

}
