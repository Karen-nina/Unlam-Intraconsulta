package ar.edu.unlam.dominio;

import java.util.Objects;

public class Examen {

	private Alumno alumno;
	private Nota nota;
	private Materia Materia;

	public Examen(Alumno alumno, Nota nota, Materia materia) {
		this.alumno = alumno;
		this.nota = nota;
		this.Materia = materia;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	public Materia getMateria() {
		return Materia;
	}

	public void setMateria(Materia materia) {
		Materia = materia;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Materia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Examen other = (Examen) obj;
		return Objects.equals(Materia, other.Materia);
	}

}
