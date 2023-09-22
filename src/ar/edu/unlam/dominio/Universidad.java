package ar.edu.unlam.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Universidad {

	private List<Materia> materias;
	private Set<Alumno> alumnos;
	private Set<Docente> docentes;
	private Set<CicloLectivo> ciclosLectivos;
	private Set<Comision> comisiones;
	private Set<Aula> aulas;

	public Universidad() {
		materias = new ArrayList<>();
		alumnos = new HashSet<>();
		docentes = new HashSet<>();
		ciclosLectivos = new HashSet<>();
		comisiones = new HashSet<>();
		aulas = new HashSet<>();
	}

	public boolean agregarMateria(Materia materia) {
		if (materias.contains(materia)) {
			return false;
		}
		materias.add(materia);
		return true;
	}

	public boolean agregarAlumno(Alumno alumno) {
		if (alumnos.contains(alumno)) {
			return false;
		}
		alumnos.add(alumno);
		return true;
	}

	public boolean agregarCicloLectivo(CicloLectivo ciclo) {
		if (ciclosLectivos.contains(ciclo)) {
			return false;
		}
		ciclosLectivos.add(ciclo);
		return true;
	}

	public boolean agregarComision(Comision comision) {
		for (Comision listaComisiones : comisiones) {
			if (listaComisiones.getId() == comision.getId()
					|| (listaComisiones.getMateria().getId() == comision.getMateria().getId()
							&& listaComisiones.getCicloLectivo().getId() == comision.getCicloLectivo().getId()
							&& listaComisiones.getTurno().equals(comision.getTurno()))) {
				return false; // No se pueden generar 2 comisiones para la misma materia, cicloLectivo y turno
			}
		}
		comisiones.add(comision);
		return true;
	}

	public boolean agregarDocente(Docente docente) {
		if (docentes.contains(docente)) {
			return false;
		}
		docentes.add(docente);
		return true;
	}

	public boolean asignarDocentesAComision(Integer idComision, Integer dniDocente) {
		Comision comision = buscarComisionPorId(idComision);
		Docente docente = buscarDocentePorDni(dniDocente);

		if (!comision.getDocentes().contains(docente)) {
			Integer cantidadAlumnos = comision.getAlumnos().size();
			Integer cantidadDocentes = comision.getDocentes().size();
			if (cantidadAlumnos > 0 && cantidadDocentes < cantidadAlumnos / 20 + 1) {
				comision.getDocentes().add(docente);
				return true;
			}
		}
		return false;
	}

	public boolean agregarCorrelatividad(Integer idMateria, Integer idCorrelativa) {
		Materia materia = buscarMateriaPorId(idMateria);

		if (!materia.getCorrelatividades().contains(idCorrelativa)) {
			materia.getCorrelatividades().add(idCorrelativa);
			return true;
		}
		return false;
	}

	public boolean eliminarCorrelatividad(Integer idMateria, Integer idCorrelativaAELiminar) {
		Materia materia = buscarMateriaPorId(idMateria);

		if (materia.getCorrelatividades().contains(idCorrelativaAELiminar)) {
			materia.getCorrelatividades().remove(idCorrelativaAELiminar);
			return true;
		}
		return false;
	}

	private Boolean correlativasAprobadas(Comision comision, Alumno alumno) {
		for (Materia correlativa : alumno.getMateriasAprobadas()) {
			if (!comision.getMateria().getCorrelatividades().contains(correlativa.getId())) {
				return false;
			}
		}
		return true;
	}

	private Boolean recibeNotaMayorASieteConCorrelativasAprobadas(Comision comision, Alumno alumno, Nota nota) {
		if (nota.getValor() >= 7 && !correlativasAprobadas(comision, alumno)) {
			return false;
		}
		return true;
	}

	public boolean inscribirAlumnoAComision(Integer dniAlumno, Integer idComision, LocalDate fechaInscripcionActual) {
		Comision comision = buscarComisionPorId(idComision);
		Alumno alumno = buscarAlumnoPorDni(dniAlumno);

		if (!esFechaDeInscripcionValida(comision, fechaInscripcionActual)
				|| !tieneTodasLasCorrelativasAprobadas(comision, alumno) || !hayCupoEnAula(comision)
				|| estaInscritoEnOtraComisionMismoDiaTurno(comision, alumno)
				|| alumno.getMateriasAprobadas().contains(comision.getMateria())
				|| comision.getAlumnos().contains(alumno)) {
			return false;
		}
		comision.getAlumnos().add(alumno);
		return true;
	}

	private boolean esFechaDeInscripcionValida(Comision comision, LocalDate fechaInscripcionActual) {
		if (comision.getCicloLectivo().getFechaInicioInscripcion().isBefore(fechaInscripcionActual)
				&& comision.getCicloLectivo().getFechaFinalizacionInscripcion().isAfter(fechaInscripcionActual)) {
			return true;
		}

		return false;
	}

	public Boolean tieneTodasLasCorrelativasAprobadas(Comision comision, Alumno alumno) {
		Set<Integer> correlativas = comision.getMateria().getCorrelatividades();

		for (Integer correlativaId : correlativas) {
			Materia correlativa = buscarMateriaPorId(correlativaId);
			if (!alumno.getMateriasAprobadas().contains(correlativa)) {
				return false; // El alumno no tiene todas las correlativas aprobadas
			}
		}
		return true;
	}

	private boolean hayCupoEnAula(Comision comision) {
		return comision.getAlumnos().size() < comision.getAula().getCapacidadMaxima();
	}

	public Boolean estaInscritoEnOtraComisionMismoDiaTurno(Comision comision, Alumno alumno) {
		for (Comision otraComision : comisiones) {
			if (otraComision != comision && otraComision.getCicloLectivo().equals(comision.getCicloLectivo())
					&& otraComision.getTurno().equals(comision.getTurno())) {
				if (otraComision.getAlumnos().contains(alumno)) {
					return true; // El alumno ya está inscrito en otra comisión el mismo día y turno
				}
			}
		}
		return false;
	}

	public Materia buscarMateriaPorId(Integer idMateria) {
		for (Materia materia : materias) {
			if (materia.getId() == idMateria) {
				return materia;
			}
		}
		return null;
	}

	private Alumno buscarAlumnoPorDni(Integer dni) {
		for (Alumno alumno : alumnos) {
			if (alumno.getDni() == dni) {
				return alumno;
			}
		}
		return null;
	}

	private Docente buscarDocentePorDni(Integer dni) {
		for (Docente docente : docentes) {
			if (docente.getDni() == dni) {
				return docente;
			}
		}
		return null;
	}

	private Comision buscarComisionPorId(Integer idComision) {
		for (Comision comision : comisiones) {
			if (comision.getId() == idComision) {
				return comision;
			}
		}
		return null;
	}

	public List<Materia> getMaterias() {
		return materias;
	}

	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}

	public Set<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(Set<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public Set<Docente> getDocentes() {
		return docentes;
	}

	public void setDocentes(Set<Docente> docentes) {
		this.docentes = docentes;
	}

	public Set<CicloLectivo> getCiclosLectivos() {
		return ciclosLectivos;
	}

	public void setCiclosLectivos(Set<CicloLectivo> ciclosLectivos) {
		this.ciclosLectivos = ciclosLectivos;
	}

	public Set<Comision> getComisiones() {
		return comisiones;
	}

	public void setComisiones(Set<Comision> comisiones) {
		this.comisiones = comisiones;
	}

	public Set<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(Set<Aula> aulas) {
		this.aulas = aulas;
	}

	public boolean agregarAula(Aula aula) {
		return aulas.add(aula);
	}

	public boolean asignarAulaAlaComision(Integer idComision, Integer idAula) {
		Comision comision = buscarComisionPorId(idComision);
		Aula aula = buscarAulaPorId(idAula);

		if (!comisiones.contains(comision) || !aulas.contains(aula)) {
			return false;
		}

		comision.setAula(aula);
		return true;
	}

	private Aula buscarAulaPorId(Integer idAula) {
		for (Aula aula : aulas) {
			if (aula.getId().equals(idAula)) {
				return aula;
			}
		}
		return null;
	}

	public Boolean registrarNota(Integer idComision, Integer idAlumno, Nota nota) {
		Comision comision = buscarComisionPorId(idComision);
		Alumno alumno = buscarAlumnoPorDni(idAlumno);
		
		if (nota.getValor() < 0 || nota.getValor() > 10) {
			return false;
		}
		if (!recibeNotaMayorASieteConCorrelativasAprobadas(comision, alumno, nota)) {
			return false;
		}
		Examen examen = new Examen(alumno, nota);
		return comision.registrarExamen(examen);
	}

	public Double obtenerNota(Integer idComision, Integer idAlumno, TipoDeNota tipoDeNota) {
		Comision comision = buscarComisionPorId(idComision);
		Alumno alumno = buscarAlumnoPorDni(idAlumno);
		return comision.buscarNota(alumno, tipoDeNota);
	}

	public Boolean aprobarMateria(Integer idComision, Integer idAlumno, Nota nota) {
		Comision comision = buscarComisionPorId(idComision);
		Alumno alumno = buscarAlumnoPorDni(idAlumno);
		

		if(verificarAlumnoAproboFinal(idComision, idAlumno)) {
			alumno.agregarMateriaAprobada(comision.getMateria());
			return true;
		}
		
		if(verificarAlumnoPromociono(idComision, idAlumno)) {
			alumno.agregarMateriaAprobada(comision.getMateria());
			return true;
		}
		return false;
	}

	public Boolean verificarAlumnoPromociono(Integer idComision, Integer idAlumno) {
		Comision comision = buscarComisionPorId(idComision);
		Alumno alumno = buscarAlumnoPorDni(idAlumno);
		Examen primerParcial = comision.buscarExamenPorTipoDeNota(alumno, TipoDeNota.PRIMER_PARCIAL);
		Examen segundoParcial = comision.buscarExamenPorTipoDeNota(alumno, TipoDeNota.SEGUNDO_PARCIAL);
		
		if(primerParcial == null || segundoParcial == null) {
			return false;
		}
		if (primerParcial.getNota().getValor() >= 7 && segundoParcial.getNota().getValor() >= 7) {
			return true;
		}
		if (verificarAlumnoPromocionaPrimerParcialYRecuperatorioDelSegundoParcial(idComision, idAlumno)) {
			return true;
		}
		if (verificarAlumnoPromocionaSegundoParcialYRecuperatorioDelPrimerParcial(idComision, idAlumno)) {
			return true;
		}
		return false;
	}
	
	public Boolean verificarAlumnoPromocionaPrimerParcialYRecuperatorioDelSegundoParcial(Integer idComision, Integer idAlumno) {
		Comision comision = buscarComisionPorId(idComision);
		Alumno alumno = buscarAlumnoPorDni(idAlumno);
		Examen primerParcial = comision.buscarExamenPorTipoDeNota(alumno, TipoDeNota.PRIMER_PARCIAL);
		Examen recuperatorioSegundoParcial = comision.buscarExamenPorTipoDeNota(alumno, TipoDeNota.RECUPERATORIO_SEGUNDO_PARCIAL);
		
		if(recuperatorioSegundoParcial == null) {
			return false;
		}
		if (primerParcial.getNota().getValor() >= 7 && recuperatorioSegundoParcial.getNota().getValor() >= 7) {
			return true;
		}
		return false;
	}
	
	public Boolean verificarAlumnoPromocionaSegundoParcialYRecuperatorioDelPrimerParcial(Integer idComision, Integer idAlumno) {
		Comision comision = buscarComisionPorId(idComision);
		Alumno alumno = buscarAlumnoPorDni(idAlumno);
		Examen segundoParcial = comision.buscarExamenPorTipoDeNota(alumno, TipoDeNota.SEGUNDO_PARCIAL);
		Examen recuperatorioPrimerParcial = comision.buscarExamenPorTipoDeNota(alumno, TipoDeNota.RECUPERATORIO_PRIMER_PARCIAL);
		
		if(recuperatorioPrimerParcial == null) {
			return false;
		}
		if (segundoParcial.getNota().getValor() >= 7 && recuperatorioPrimerParcial.getNota().getValor() >= 7) {
			return true;
		}
		return false;
	}

	public Boolean verificarAlumnoAproboFinal(Integer idComision, Integer idAlumno) {
		Comision comision = buscarComisionPorId(idComision);
		Alumno alumno = buscarAlumnoPorDni(idAlumno);
		Examen examen = comision.buscarExamenPorTipoDeNota(alumno, TipoDeNota.FINAL);
		if (examen == null) {
			return false;
		}
		if (examen.getNota().getValor() >= 4) {
			return true;
		}
		return false;
	}

	public Double calcularPromedioDeNotas(Integer idComision, Integer idAlumno) {
		Comision comision = buscarComisionPorId(idComision);
		Alumno alumno = buscarAlumnoPorDni(idAlumno);
		Double promedio = comision.calcularPromedioNotas(alumno);
		return promedio;
	}

}