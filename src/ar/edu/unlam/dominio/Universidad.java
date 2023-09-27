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
	private Set<Carrera> carreras;

	public Universidad() {
		materias = new ArrayList<>();
		alumnos = new HashSet<>();
		docentes = new HashSet<>();
		ciclosLectivos = new HashSet<>();
		comisiones = new HashSet<>();
		aulas = new HashSet<>();
		carreras = new HashSet<>();
	}

	// metodo de agregar

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
		for (CicloLectivo listaCiclos : ciclosLectivos) {
			if (listaCiclos.getId() == ciclo.getId()
					|| (ciclo.getFechaInicio().isAfter(listaCiclos.getFechaInicio())
							&& ciclo.getFechaInicio().isBefore(listaCiclos.getFechaFinalizacion()))
					|| (ciclo.getFechaFinalizacion().isAfter(listaCiclos.getFechaInicio())
							&& ciclo.getFechaFinalizacion().isBefore(listaCiclos.getFechaFinalizacion()))) {
				return false; // No se puede asignar 2 ciclos Lectivo con mismo ID o superponer rangos de
								// fechas
			}
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

	public boolean agregarCarrera(Carrera carrera) {
		if (carreras.contains(carrera)) {
			return false;
		}
		carreras.add(carrera);
		return true;

	}

	public boolean agregarMateriaACarrera(Carrera carrera, Materia materia) {
		if (!carreras.contains(carrera) || carrera.getPlanDeEstudios().contains(materia)) {
			return false;
		}
		carrera.getPlanDeEstudios().add(materia);
		return true;
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

	// metodos de validacion
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

	public Boolean registrarNota(Integer idComision, Integer idAlumno, Nota nota) {
		Comision comision = buscarComisionPorId(idComision);
		Alumno alumno = buscarAlumnoPorDni(idAlumno);

		if (nota.getValor() < 1 || nota.getValor() > 10) {
			return false;
		}
		if (nota.getValor() >= 7 && !correlativasAprobadas(comision, alumno)) {
			return false;
		}
		if (comision.tieneNotaRegistrada(alumno, nota.getTipo())) {
			return false;
		}
		if ((nota.getTipo() == TipoDeNota.RECUPERATORIO_PRIMER_PARCIAL
				|| nota.getTipo() == TipoDeNota.RECUPERATORIO_SEGUNDO_PARCIAL)
				&& comision.tieneRecuperatorioRegistrado(alumno)) {
			return false; // El alumno ya ha rendido un recuperatorio previamente para este tipo de nota.
		}

		if ((nota.getTipo() == TipoDeNota.RECUPERATORIO_PRIMER_PARCIAL
				|| nota.getTipo() == TipoDeNota.RECUPERATORIO_SEGUNDO_PARCIAL)
				&& !puedeRendirRecuperatorio(comision, alumno)) {
			return false; // No se puede rendir más de un recuperatorio.
		}
		Examen examen = new Examen(alumno, nota, comision.getMateria());
		return comision.registrarExamen(examen);
	}

	private Boolean correlativasAprobadas(Comision comision, Alumno alumno) {
		Set<Integer> correlativas = comision.getMateria().getCorrelatividades();

		for (Integer correlativaId : correlativas) {
			Materia correlativa = buscarMateriaPorId(correlativaId);
			if (!alumno.getMateriasAprobadas().contains(correlativa)) {
				return false;
			}
		}
		return true;
	}

	private Boolean puedeRendirRecuperatorio(Comision comision, Alumno alumno) {
		if (comision.tieneRecuperatorioRegistrado(alumno)) {
			return false;
		}
		Examen primerParcial = comision.buscarExamenPorMateriaYTipoDeNota(comision.getMateria(),
				TipoDeNota.PRIMER_PARCIAL);
		Examen segundoParcial = comision.buscarExamenPorMateriaYTipoDeNota(comision.getMateria(),
				TipoDeNota.SEGUNDO_PARCIAL);

		if ((primerParcial != null && primerParcial.getNota().getValor() >= 4)
				|| (segundoParcial != null && segundoParcial.getNota().getValor() >= 4)) {
			return true; // Puede rendir el recuperatorio si ha aprobado al menos uno de los parciales.
		}
		return false;
	}

	public Boolean aprobarMateria(Comision comision, Alumno alumno) {
		if (verificarAlumnoPromocionoMateria(comision, alumno)) {
			alumno.agregarMateriaAprobada(comision.getMateria());
			return true;
		}
		if (verificarAlumnoAproboFinalMateria(comision, alumno)) {
			alumno.agregarMateriaAprobada(comision.getMateria());
			return true;
		}
		return false;
	}

	private Boolean verificarAlumnoPromocionoMateria(Comision comision, Alumno alumno) {
		Set<Integer> correlativas = comision.getMateria().getCorrelatividades();

		for (Integer correlativaId : correlativas) {
			Materia correlativa = buscarMateriaPorId(correlativaId);
			Examen examenFinalCorrelativa = comision.buscarExamenPorMateriaYTipoDeNota(correlativa, TipoDeNota.FINAL);
			if (examenFinalCorrelativa == null || examenFinalCorrelativa.getNota().getValor() < 7) {
				return false;
			}
		}
		Examen primerParcial = comision.buscarExamenPorMateriaYTipoDeNota(comision.getMateria(),
				TipoDeNota.PRIMER_PARCIAL);
		Examen segundoParcial = comision.buscarExamenPorMateriaYTipoDeNota(comision.getMateria(),
				TipoDeNota.SEGUNDO_PARCIAL);

		if (primerParcial != null && primerParcial.getNota().getValor() >= 7 && segundoParcial != null
				&& segundoParcial.getNota().getValor() >= 7) {
			return true;
		}
		return false;
	}

	public Boolean verificarAlumnoAproboFinalMateria(Comision comision, Alumno alumno) {
		Set<Integer> correlativas = comision.getMateria().getCorrelatividades();

		for (Integer correlativaId : correlativas) {
			Materia correlativa = buscarMateriaPorId(correlativaId);
			Examen examenFinalCorrelativa = comision.buscarExamenPorMateriaYTipoDeNota(correlativa, TipoDeNota.FINAL);
			if (examenFinalCorrelativa == null || examenFinalCorrelativa.getNota().getValor() < 4) {
				return false;
			}
		}
		Examen examenFinal = comision.buscarExamenPorMateriaYTipoDeNota(comision.getMateria(), TipoDeNota.FINAL);
		if (examenFinal != null && examenFinal.getNota().getValor() >= 4) {
			return true;
		}
		return false;
	}

	public Set<Materia> obtenerMateriasAprobadasParaUnAlumno(Integer dni) {
		Set<Materia> materiasAprobadas = new HashSet<>();
		for (Alumno alumno : alumnos) {
			if (alumno.getDni().equals(dni)) {
				materiasAprobadas.addAll(alumno.getMateriasAprobadas());
				break;
			}
		}
		return materiasAprobadas;
	}

	public Set<Materia> obtenerMateriasFaltantes(Integer dniAlumno, int idCarrera) {
		Set<Materia> materiasAprobadas = new HashSet<>();
		Set<Materia> materiasFaltantes = new HashSet<>();
		Alumno alumno = null;
		for (Alumno a : alumnos) {
			if (a.getDni().equals(dniAlumno)) {
				alumno = a;
				break;
			}
		}
		if (alumno != null) {
			materiasAprobadas.addAll(alumno.getMateriasAprobadas());

			Carrera carreraSeleccionada = null;
			for (Carrera carrera : carreras) {
				if (carrera.getId() == idCarrera) {
					carreraSeleccionada = carrera;
					break;
				}
			}
			if (carreraSeleccionada != null) {
				Set<Materia> planDeEstudios = carreraSeleccionada.getPlanDeEstudios();
				for (Materia materia : planDeEstudios) {
					if (!materiasAprobadas.contains(materia)) {
						materiasFaltantes.add(materia);
					}
				}
			}
		}

		return materiasFaltantes;
	}

	public Nota obtenerNotaDeMateriaAprobadaDeAlumno(Materia materia, Alumno alumno) {
		if (alumno.tieneMateriaAprobada(materia)) {
			Comision comision = null;
			for (Comision c : comisiones) {
				if (c.getMateria().equals(materia)) {
					comision = c;
					break;
				}
			}
			if (comision != null) {
				for (Examen examen : comision.getExamenes()) {
					if (examen.getAlumno().equals(alumno)) {
						return examen.getNota();
					}
				}
			}
		}
		return null;
	}

	public double calcularPromedioDeNotas(Integer idComision, Integer dni) {
		Comision comision = buscarComisionPorId(idComision);
		Alumno alumno = buscarAlumnoPorDni(dni);
		Double promedio = comision.calcularPromedioNotas(alumno);
		return promedio;
	}

	// metodos buscar
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

	private Aula buscarAulaPorId(Integer idAula) {
		for (Aula aula : aulas) {
			if (aula.getId().equals(idAula)) {
				return aula;
			}
		}
		return null;
	}

	// getters y setters
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

	public Set<Carrera> getCarreras() {
		return carreras;
	}

	public void setCarreras(Set<Carrera> carreras) {
		this.carreras = carreras;
	}
}