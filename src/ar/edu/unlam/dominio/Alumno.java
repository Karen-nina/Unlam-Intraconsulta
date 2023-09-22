package ar.edu.unlam.dominio;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Alumno {

	private Integer dni;
	private String nombre;
	private String apellido;
	private LocalDate fechaNacimiento;
	private LocalDate fechaIngreso;
	private Set<Materia> materiasAprobadas;

	public Alumno(Integer dni, String nombre, String apellido, LocalDate fechaNacimiento, LocalDate fechaIngreso) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaIngreso = fechaIngreso;
		this.materiasAprobadas = new HashSet<>();
	}

	public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Set<Materia> getMateriasAprobadas() {
		return materiasAprobadas;
	}

	public void setMateriasAprobadas(Set<Materia> materiasAprobadas) {
		this.materiasAprobadas = materiasAprobadas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alumno other = (Alumno) obj;
		return Objects.equals(dni, other.dni);
	}

	public Boolean agregarMateriaAprobada(Materia materia) {
		return materiasAprobadas.add(materia);
	}

}
