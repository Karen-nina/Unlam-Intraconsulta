package ar.edu.unlam.dominio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	private List<Examen> examenes;

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
		this.examenes = new ArrayList<>();
	}

	public Comision(Integer id, Materia materia, CicloLectivo cicloLectivo, String turno, String dia) {
		this.id = id;
		this.materia = materia;
		this.cicloLectivo = cicloLectivo;
		this.turno = turno;
		this.dia = dia;
		this.docentes = new HashSet<>();
		this.alumnos = new HashSet<>();
		this.examenes = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Examen> getExamenes() {
		return examenes;
	}

	public void setExamenes(List<Examen> examenes) {
		this.examenes = examenes;
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

	public Boolean registrarExamen(Examen examen) {
		return examenes.add(examen);
	}

	public boolean tieneNotaRegistrada(Alumno alumno, TipoDeNota tipo) {
		for (Examen examen : examenes) {
			if (examen.getAlumno().equals(alumno) && examen.getNota().getTipo() == tipo) {
				return true;
			}
		}
		return false;
	}

	public boolean tieneRecuperatorioRegistrado(Alumno alumno) {
		for (Examen examen : examenes) {
			if (examen.getAlumno().equals(alumno)
					&& examen.getNota().getTipo() == TipoDeNota.RECUPERATORIO_PRIMER_PARCIAL
					|| examen.getNota().getTipo() == TipoDeNota.RECUPERATORIO_SEGUNDO_PARCIAL) {
				return true;
			}
		}
		return false;
	}

	public Examen buscarExamenPorMateriaYTipoDeNota(Materia materia, TipoDeNota tipoDeNota) {
		for (Examen examen : examenes) {
			if (examen.getMateria().equals(materia) && examen.getNota().getTipo() == tipoDeNota) {
				return examen;
			}
		}
		return null;
	}

	public Integer cantidadDeNotasPorAlumno(Alumno alumno) {
		Integer cantidadNotas = 0;
		for (Examen examenes : examenes) {
			if (examenes.getAlumno().equals(alumno)) {
				cantidadNotas = cantidadNotas + 1;
			}
		}
		return cantidadNotas;
	}

	public Double calcularPromedioNotas(Alumno alumno) {
		Integer cantidadDeNotas = cantidadDeNotasPorAlumno(alumno);
		Double sumaDeNotas = 0.00;
		for (Examen examenes : examenes) {
			if (examenes.getAlumno().equals(alumno)) {
				sumaDeNotas = sumaDeNotas + examenes.getNota().getValor();
			}
		}
		return sumaDeNotas / cantidadDeNotas;
	}
}
