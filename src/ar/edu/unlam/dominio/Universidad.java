package ar.edu.unlam.dominio;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Date;
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
        if(materias.contains(materia)){
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
    	/*for (CicloLectivo listaCiclos : ciclosLectivos) {
            if (listaCiclos.getId() == ciclo.getId() ||
                (ciclo.getFechaInicio().isAfter(listaCiclos.getFechaInicio()) &&
                 ciclo.getFechaInicio().isBefore(listaCiclos.getFechaFinalizacion())) ||
                (ciclo.getFechaFinalizacion().isAfter(listaCiclos.getFechaInicio()) &&
                 ciclo.getFechaFinalizacion().isBefore(listaCiclos.getFechaFinalizacion()))) {
                return false; // No se puede asignar 2 ciclos Lectivo con mismo ID o superponer rangos de fechas
            }
        }
        ciclosLectivos.add(ciclo);
        return true;
        */
    	
    	
    }

    public boolean agregarComision(Comision comision) {
        for (Comision listaComisiones : comisiones) {
            if (listaComisiones.getId() == comision.getId() ||
                (listaComisiones.getMateria().getId() == comision.getMateria().getId() &&
                 listaComisiones.getCicloLectivo().getId() == comision.getCicloLectivo().getId() &&
                 listaComisiones.getTurno().equals(comision.getTurno()))) {
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

    public boolean asignarDocentesAComision(int idComision, int dniDocente) {
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
    
    public void registrarNota(Integer idComision, Integer dniAlumno, Nota nota) {
        Comision comision = buscarComisionPorId(idComision);
        Alumno alumno = buscarAlumnoPorDni(dniAlumno);

        if (nota.getValor() >= 1 && nota.getValor() <= 10) {
            if (comision.getAlumnos().contains(alumno) && tipoNotaValido(nota) && notaValidaParaFinal(nota, comision, alumno)) {
                comision.getRegistroNotas().put(alumno, nota);
            }
        }
    }

    private boolean tipoNotaValido(Nota nota) {
        return nota.getTipo().equals(TipoDeNota.PRIMER_PARCIAL) || 
        		nota.getTipo().equals(TipoDeNota.SEGUNDO_PARCIAL) || nota.getTipo().equals(TipoDeNota.RECUPERACION_PRIMER_PARCIAL) 
        		|| nota.getTipo().equals(TipoDeNota.RECUPERACION_SEGUNDO_PARCIAL) || nota.getTipo().equals(TipoDeNota.FINAL);
    }

    private boolean notaValidaParaFinal(Nota nota, Comision comision, Alumno alumno) {
    	if (nota.getTipo().equals(TipoDeNota.FINAL) && nota.getValor()>=4) {
            for (Materia correlativa : alumno.getMateriasAprobadas()) {
                if (!comision.getMateria().getCorrelatividades().contains(correlativa.getId())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean inscribirAlumnoAComision(int dniAlumno, int idComision) {
        Comision comision = buscarComisionPorId(idComision);
        Alumno alumno = buscarAlumnoPorDni(dniAlumno);
        
        if (!esFechaDeInscripcionValida(comision) || 
        		!tieneTodasLasCorrelativasAprobadas(comision, alumno) || 
        		!hayCupoEnAula(comision) || estaInscritoEnOtraComisionMismoDiaTurno(comision, alumno) 
        		|| alumno.getMateriasAprobadas().contains(comision.getMateria())) {
            return false; 
        }

        comision.getAlumnos().add(alumno);
        return true;
    }

    private boolean esFechaDeInscripcionValida(Comision comision) {
       LocalDate fechaActual = LocalDate.of(0,0,0);
        return comision.getCicloLectivo().getFechaInicioInscripcion().isBefore(fechaActual) && 
        		comision.getCicloLectivo().getFechaFinalizacionInscripcion().isAfter(fechaActual);
    }

	private boolean tieneTodasLasCorrelativasAprobadas(Comision comision, Alumno alumno) {
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

    private boolean estaInscritoEnOtraComisionMismoDiaTurno(Comision comision, Alumno alumno) {
        for (Comision otraComision : comisiones) {
            if (otraComision != comision && otraComision.getCicloLectivo().equals(comision.getCicloLectivo()) && 
            		otraComision.getTurno().equals(comision.getTurno())) {
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
	
	
}