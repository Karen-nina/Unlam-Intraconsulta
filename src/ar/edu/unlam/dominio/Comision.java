package ar.edu.unlam.dominio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Comision {

	private Integer id;
	private Materia materia;
	private CicloLectivo cicloLectivo;
	private String turno;
	private String dia;
	private Aula aula;
	private Set<Docente> docentes;
	private Set<Alumno> alumnos;
	private Map<Alumno, Nota> registroNotas;

	public Comision(Integer id, Materia materia, CicloLectivo cicloLectivo, String turno, String dia, Aula aula) {
		super();
		this.id = id;
		this.materia = materia;
		this.cicloLectivo = cicloLectivo;
		this.turno = turno;
		this.dia = dia;
		this.aula = aula;
		this.docentes = new HashSet<>();
		this.alumnos = new HashSet<>();
		this.registroNotas = new HashMap<>();
	}

	public Comision(Integer id, Materia materia, CicloLectivo cicloLectivo, String turno, String dia) {
		this.id = id;
		this.materia = materia;
		this.cicloLectivo = cicloLectivo;
		this.turno = turno;
		this.dia = dia;
		this.docentes = new HashSet<>();
		this.alumnos = new HashSet<>();
		this.registroNotas = new HashMap<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public CicloLectivo getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(CicloLectivo cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public Aula getAula() {
		return aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}

	public Set<Docente> getDocentes() {
		return docentes;
	}

	public void setDocentes(Set<Docente> docentes) {
		this.docentes = docentes;
	}

	public Set<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(Set<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public Map<Alumno, Nota> getRegistroNotas() {
		return registroNotas;
	}

	public void setRegistroNotas(Map<Alumno, Nota> registroNotas) {
		this.registroNotas = registroNotas;
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
		Comision other = (Comision) obj;
		return Objects.equals(id, other.id);
	}

}
