package ar.edu.unlam.dominio;

public class HistorialAcademico {

	private Integer id;
	private Alumno alumno;
	private Comision comision;
	private Nota nota;
	
	public HistorialAcademico(Integer id, Alumno alumno, Comision comision, Nota nota) {
		this.id = id;
		this.alumno = alumno;
		this.comision = comision;
		this.nota = nota;
	}
	
	public HistorialAcademico(Alumno alumno, Comision comision, Nota nota) {
		this.alumno = alumno;
		this.comision = comision;
		this.nota = nota;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Alumno getAlumno() {
		return alumno;
	}
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	public Comision getComision() {
		return comision;
	}
	public void setComision(Comision comision) {
		this.comision = comision;
	}
	public Nota getNota() {
		return nota;
	}
	public void setNota(Nota nota) {
		this.nota = nota;
	}
	
}
