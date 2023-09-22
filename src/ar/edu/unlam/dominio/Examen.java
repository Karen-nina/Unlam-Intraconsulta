package ar.edu.unlam.dominio;

public class Examen {

	private Alumno alumno;
	private Nota nota;

	public Examen(Alumno alumno, Nota nota) {
		this.alumno = alumno;
		this.nota = nota;
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

}
