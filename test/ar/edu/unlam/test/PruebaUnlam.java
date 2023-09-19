package ar.edu.unlam.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import org.junit.Test;
import ar.edu.unlam.dominio.Alumno;
import ar.edu.unlam.dominio.Aula;
import ar.edu.unlam.dominio.CicloLectivo;
import ar.edu.unlam.dominio.Comision;
import ar.edu.unlam.dominio.Docente;
import ar.edu.unlam.dominio.HistorialAcademico;
import ar.edu.unlam.dominio.Materia;
import ar.edu.unlam.dominio.Nota;
import ar.edu.unlam.dominio.TipoDeNota;
import ar.edu.unlam.dominio.Universidad;

public class PruebaUnlam {

	@Test
	public void queSePuedaAgregarMateria() {
		Universidad universidad = new Universidad();
		Materia materia1 = new Materia(1, "Informatica general");
		Materia materia2 = new Materia(1, "Matematica general");

		assertTrue(universidad.agregarMateria(materia1));
		assertFalse(universidad.agregarMateria(materia2)); // Intentar agregar una materia con el mismo ID debe fallar
	}

	@Test
	public void queSePuedaAgregarAlumno() {
		Universidad universidad = new Universidad();
		LocalDate fechaNacimiento = LocalDate.of(1995, 9, 17);
		LocalDate fechaIngreso = LocalDate.of(2022, 4, 01);

		Alumno alumno1 = new Alumno(12345, "Juan", "Pérez", fechaNacimiento, fechaIngreso);
		Alumno alumno2 = new Alumno(12345, "Lucía", "García", fechaNacimiento, fechaIngreso); // Duplicado de DNI

		assertTrue(universidad.agregarAlumno(alumno1));
		assertFalse(universidad.agregarAlumno(alumno2));
	}

	@Test
	public void agregarCicloLectivo() {
		Universidad universidad = new Universidad();

		Integer id = 01;
		LocalDate fechaInicio = LocalDate.of(2022, 4, 01);
		LocalDate fechaFinalizacion = LocalDate.of(2022, 4, 01);
		LocalDate fechaInicioInscripcion = LocalDate.of(2022, 4, 01);
		LocalDate fechaFinalizacionInscripcion = LocalDate.of(2022, 4, 01);

		CicloLectivo ciclo1 = new CicloLectivo(id, fechaInicio, fechaFinalizacion, fechaInicioInscripcion,
				fechaFinalizacionInscripcion);
		CicloLectivo ciclo2 = new CicloLectivo(id, fechaInicio, fechaFinalizacion, fechaInicioInscripcion,
				fechaFinalizacionInscripcion); // Mismo ID

		assertTrue(universidad.agregarCicloLectivo(ciclo1));
		assertFalse(universidad.agregarCicloLectivo(ciclo2)); // Intentar agregar un ciclo con el mismo ID debe fallar
	}

	@Test
	public void agregarComision() {
		Universidad universidad = new Universidad();
		Materia materia = new Materia(1, "Matemáticas");

		Integer id = 01;
		LocalDate fechaInicio = LocalDate.of(2022, 4, 01);
		LocalDate fechaFinalizacion = LocalDate.of(2022, 4, 01);
		LocalDate fechaInicioInscripcion = LocalDate.of(2022, 4, 01);
		LocalDate fechaFinalizacionInscripcion = LocalDate.of(2022, 4, 01);
		CicloLectivo ciclo = new CicloLectivo(id, fechaInicio, fechaFinalizacion, fechaInicioInscripcion,
				fechaFinalizacionInscripcion);
		Aula aula = new Aula(1, 60);

		Comision comision1 = new Comision(1, materia, ciclo, "Mañana", "MieJue", aula);
		Comision comision2 = new Comision(1, materia, ciclo, "Tarde", "MarJue", aula); // Mismo ID

		assertTrue(universidad.agregarComision(comision1));
		assertFalse(universidad.agregarComision(comision2)); // Intentar agregar una comisión duplicada debe fallar
	}

	@Test
	public void agregarDocentes() {
		Universidad universidad = new Universidad();
		Docente profesor1 = new Docente(1001, "Ana", "Gómez");
		Docente profesor2 = new Docente(1002, "Juan", "López");
		Docente profesor3 = new Docente(1001, "Carlos", "Martínez"); // Mismo DNI

		assertTrue(universidad.agregarDocente(profesor1));
		assertTrue(universidad.agregarDocente(profesor2));
		assertFalse(universidad.agregarDocente(profesor3)); // Intentar agregar un docente con el mismo DNI debe fallar
	}

	@Test
	public void asignarDocentesAComision() {
		Universidad universidad = new Universidad();
		Docente profesor1 = new Docente(1001, "Ana", "Gómez");
		Docente profesor2 = new Docente(1002, "Juan", "López");
		Materia materia = new Materia(1, "Matemáticas");

		Integer id = 01;
		LocalDate fechaInicio = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacion = LocalDate.of(2023, 12, 31);
		LocalDate fechaInicioInscripcion = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacionInscripcion = LocalDate.of(2022, 12, 31);
		CicloLectivo ciclo = new CicloLectivo(id, fechaInicio, fechaFinalizacion, fechaInicioInscripcion,
				fechaFinalizacionInscripcion);
		Aula aula = new Aula(1, 60);
		Comision comision = new Comision(1, materia, ciclo, "Mañana", "MieJue", aula);

		Integer dni = 21351;
		String nombre = "Matias";
		String apellido = "Guarnieri";
		LocalDate fechaNacimiento = LocalDate.of(1997, 8, 6);
		LocalDate fechaIngreso = LocalDate.of(2022, 4, 10);
		Alumno alumno = new Alumno(dni, nombre, apellido, fechaNacimiento, fechaIngreso);

		LocalDate fechaInscripcionAlumnoAMateria = LocalDate.of(2022, 4, 2);

		assertTrue(universidad.agregarAlumno(alumno));
		assertTrue(universidad.agregarCicloLectivo(ciclo));
		assertTrue(universidad.agregarComision(comision));
		assertTrue(universidad.inscribirAlumnoAComision(dni, id, fechaInscripcionAlumnoAMateria));
		assertTrue(universidad.agregarDocente(profesor1));
		assertTrue(universidad.agregarDocente(profesor2));

		assertTrue(universidad.asignarDocentesAComision(comision.getId(), profesor1.getDni()));
		assertFalse(universidad.asignarDocentesAComision(comision.getId(), profesor1.getDni())); // El mismo docente no
																									// puede ser
																									// profesor de la
																									// misma comisión 2
																									// veces
	}

	@Test
	public void agregarCorrelatividad() {
		Universidad universidad = new Universidad();
		Materia materia1 = new Materia(1, "Matemáticas");
		Materia materia2 = new Materia(2, "Física");

		assertTrue(universidad.agregarMateria(materia1));
		assertTrue(universidad.agregarMateria(materia2));

		assertTrue(universidad.agregarCorrelatividad(materia1.getId(), materia2.getId()));
		assertFalse(universidad.agregarCorrelatividad(materia1.getId(), materia2.getId())); // Intentar agregar la misma
																							// correlatividad debe
																							// fallar
	}

	@Test
	public void eliminarCorrelatividad() {
		Universidad universidad = new Universidad();
		Materia materia1 = new Materia(1, "Matemáticas");
		Materia materia2 = new Materia(2, "Física");

		assertTrue(universidad.agregarMateria(materia1));
		assertTrue(universidad.agregarMateria(materia2));

		assertTrue(universidad.agregarCorrelatividad(materia1.getId(), materia2.getId()));
		assertTrue(universidad.eliminarCorrelatividad(materia1.getId(), materia2.getId()));
		assertFalse(universidad.eliminarCorrelatividad(materia1.getId(), materia2.getId())); // Intentar eliminar la
																								// misma correlatividad
																								// debe fallar
	}

	@Test
	public void inscribirAlumnoAComision() {
		Universidad universidad = new Universidad();
		Materia materia = new Materia(1, "Matemáticas");
		// CicloLectivo
		Integer id = 01;
		LocalDate fechaInicio = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacion = LocalDate.of(2023, 12, 31);
		LocalDate fechaInicioInscripcion = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacionInscripcion = LocalDate.of(2022, 12, 31);
		CicloLectivo ciclo = new CicloLectivo(id, fechaInicio, fechaFinalizacion, fechaInicioInscripcion,
				fechaFinalizacionInscripcion);
		// Aula
		Aula aula = new Aula(1, 60);
		// Comision
		Comision comision = new Comision(1, materia, ciclo, "Mañana", "MieJue", aula);
		// Alumno
		Integer dni = 21351;
		String nombre = "Matias";
		String apellido = "Guarnieri";
		LocalDate fechaNacimiento = LocalDate.of(1997, 8, 6);
		LocalDate fechaIngreso = LocalDate.of(2022, 4, 10);
		Alumno alumno = new Alumno(dni, nombre, apellido, fechaNacimiento, fechaIngreso);
		// Fecha inscripcion del alumno
		LocalDate fechaInscripcionAlumnoAMateria = LocalDate.of(2022, 4, 2);

		assertTrue(universidad.agregarMateria(materia));
		assertTrue(universidad.agregarCicloLectivo(ciclo));
		assertTrue(universidad.agregarComision(comision));
		assertTrue(universidad.agregarAlumno(alumno));

		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision.getId(),
				fechaInscripcionAlumnoAMateria));
		assertFalse(universidad.inscribirAlumnoAComision(alumno.getDni(), comision.getId(),
				fechaInscripcionAlumnoAMateria)); // No se puede inscribir al mismo alumno dos veces
	}

	@Test
	public void asignarAulaAlaComisionSiLaMismaNoFueCreadaConUnAula() {
		Universidad universidad = new Universidad();
		Docente profesor = new Docente(1001, "Ana", "Gómez");
		Materia materia = new Materia(1, "Matemáticas");
		Integer id = 01;
		LocalDate fechaInicio = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacion = LocalDate.of(2023, 12, 31);
		LocalDate fechaInicioInscripcion = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacionInscripcion = LocalDate.of(2022, 12, 31);
		CicloLectivo ciclo = new CicloLectivo(id, fechaInicio, fechaFinalizacion, fechaInicioInscripcion,
				fechaFinalizacionInscripcion);
		Aula aula = new Aula(1, 60);
		Comision comision = new Comision(2, materia, ciclo, "Mañana", "MieJue");

		assertTrue(universidad.agregarMateria(materia));
		assertTrue(universidad.agregarCicloLectivo(ciclo));
		assertTrue(universidad.agregarComision(comision));
		assertTrue(universidad.agregarDocente(profesor));
		assertTrue(universidad.agregarAula(aula));

		assertTrue(universidad.asignarAulaAlaComision(comision.getId(), aula.getId()));
		// assertFalse(universidad.asignarAulaAlaComision(comision.getId(),
		// aula.getId())); // No se puede asignar el mismo aula dos veces
	}

	@Test
	public void registrarNota() {
		Universidad universidad = new Universidad();
		Materia materia = new Materia(1, "Matemáticas");
		// CicloLectivo
		Integer id = 01;
		LocalDate fechaInicio = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacion = LocalDate.of(2023, 12, 31);
		LocalDate fechaInicioInscripcion = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacionInscripcion = LocalDate.of(2022, 12, 31);
		CicloLectivo ciclo = new CicloLectivo(id, fechaInicio, fechaFinalizacion, fechaInicioInscripcion,
				fechaFinalizacionInscripcion);
		// Aula
		Aula aula = new Aula(1, 60);
		// Comision
		Comision comision = new Comision(1, materia, ciclo, "Mañana", "MieJue", aula);
		// Alumno
		Integer dni = 21351;
		String nombre = "Matias";
		String apellido = "Guarnieri";
		LocalDate fechaNacimiento = LocalDate.of(1997, 8, 6);
		LocalDate fechaIngreso = LocalDate.of(2022, 4, 10);
		Alumno alumno = new Alumno(dni, nombre, apellido, fechaNacimiento, fechaIngreso);
		// Fecha inscripcion del alumno
		LocalDate fechaInscripcionAlumnoAMateria = LocalDate.of(2022, 4, 2);
		// Nota
		Double valorNota = 8.00;
		TipoDeNota tipoNota = TipoDeNota.PRIMER_PARCIAL;
		Nota nota = new Nota(valorNota, tipoNota);

		assertTrue(universidad.agregarMateria(materia));
		assertTrue(universidad.agregarCicloLectivo(ciclo));
		assertTrue(universidad.agregarComision(comision));
		assertTrue(universidad.agregarAlumno(alumno));

		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision.getId(),
				fechaInscripcionAlumnoAMateria));

		assertTrue(universidad.registrarNota(comision.getId(), alumno.getDni(), nota)); // Registrar una nota válida
	}

	@Test
	public void queNoSePuedaRegistrarNotaMenorAUno() {
		Universidad universidad = new Universidad();
		Materia materia = new Materia(1, "Matemáticas");
		// CicloLectivo
		Integer id = 01;
		LocalDate fechaInicio = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacion = LocalDate.of(2023, 12, 31);
		LocalDate fechaInicioInscripcion = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacionInscripcion = LocalDate.of(2022, 12, 31);
		CicloLectivo ciclo = new CicloLectivo(id, fechaInicio, fechaFinalizacion, fechaInicioInscripcion,
				fechaFinalizacionInscripcion);
		// Aula
		Aula aula = new Aula(1, 60);
		// Comision
		Comision comision = new Comision(1, materia, ciclo, "Mañana", "MieJue", aula);
		// Alumno
		Integer dni = 21351;
		String nombre = "Matias";
		String apellido = "Guarnieri";
		LocalDate fechaNacimiento = LocalDate.of(1997, 8, 6);
		LocalDate fechaIngreso = LocalDate.of(2022, 4, 10);
		Alumno alumno = new Alumno(dni, nombre, apellido, fechaNacimiento, fechaIngreso);
		// Fecha inscripcion del alumno
		LocalDate fechaInscripcionAlumnoAMateria = LocalDate.of(2022, 4, 2);
		// Nota
		Double valorNota = -1.00;
		TipoDeNota tipoNota = TipoDeNota.PRIMER_PARCIAL;
		Nota nota = new Nota(valorNota, tipoNota);

		assertTrue(universidad.agregarMateria(materia));
		assertTrue(universidad.agregarCicloLectivo(ciclo));
		assertTrue(universidad.agregarComision(comision));
		assertTrue(universidad.agregarAlumno(alumno));

		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision.getId(),
				fechaInscripcionAlumnoAMateria));

		assertFalse(universidad.registrarNota(comision.getId(), alumno.getDni(), nota)); // Registrar una nota válida
	}

	@Test
	public void queNoSePuedaRegistrarNotaMayorADiez() {
		Universidad universidad = new Universidad();
		Materia materia = new Materia(1, "Matemáticas");
		// CicloLectivo
		Integer id = 01;
		LocalDate fechaInicio = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacion = LocalDate.of(2023, 12, 31);
		LocalDate fechaInicioInscripcion = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacionInscripcion = LocalDate.of(2022, 12, 31);
		CicloLectivo ciclo = new CicloLectivo(id, fechaInicio, fechaFinalizacion, fechaInicioInscripcion,
				fechaFinalizacionInscripcion);
		// Aula
		Aula aula = new Aula(1, 60);
		// Comision
		Comision comision = new Comision(1, materia, ciclo, "Mañana", "MieJue", aula);
		// Alumno
		Integer dni = 21351;
		String nombre = "Matias";
		String apellido = "Guarnieri";
		LocalDate fechaNacimiento = LocalDate.of(1997, 8, 6);
		LocalDate fechaIngreso = LocalDate.of(2022, 4, 10);
		Alumno alumno = new Alumno(dni, nombre, apellido, fechaNacimiento, fechaIngreso);
		// Fecha inscripcion del alumno
		LocalDate fechaInscripcionAlumnoAMateria = LocalDate.of(2022, 4, 2);
		// Nota
		Double valorNota = 22.00;
		TipoDeNota tipoNota = TipoDeNota.PRIMER_PARCIAL;
		Nota nota = new Nota(valorNota, tipoNota);

		assertTrue(universidad.agregarMateria(materia));
		assertTrue(universidad.agregarCicloLectivo(ciclo));
		assertTrue(universidad.agregarComision(comision));
		assertTrue(universidad.agregarAlumno(alumno));

		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision.getId(),
				fechaInscripcionAlumnoAMateria));

		assertFalse(universidad.registrarNota(comision.getId(), alumno.getDni(), nota)); // Registrar una nota válida
	}
	
	@Test
	public void queSePuedaAprobarUnaMateria() {
		Universidad universidad = new Universidad();
		Materia materia = new Materia(1, "Matemáticas");
		// CicloLectivo
		Integer id = 01;
		LocalDate fechaInicio = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacion = LocalDate.of(2023, 12, 31);
		LocalDate fechaInicioInscripcion = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacionInscripcion = LocalDate.of(2022, 12, 31);
		CicloLectivo ciclo = new CicloLectivo(id, fechaInicio, fechaFinalizacion, fechaInicioInscripcion,
				fechaFinalizacionInscripcion);
		// Aula
		Aula aula = new Aula(1, 60);
		// Comision
		Comision comision = new Comision(1, materia, ciclo, "Mañana", "MieJue", aula);
		// Alumno
		Integer dni = 21351;
		String nombre = "Matias";
		String apellido = "Guarnieri";
		LocalDate fechaNacimiento = LocalDate.of(1997, 8, 6);
		LocalDate fechaIngreso = LocalDate.of(2022, 4, 10);
		Alumno alumno = new Alumno(dni, nombre, apellido, fechaNacimiento, fechaIngreso);
		// Fecha inscripcion del alumno
		LocalDate fechaInscripcionAlumnoAMateria = LocalDate.of(2022, 4, 2);
		// Nota
		Double valorNota = 8.00;
		TipoDeNota tipoNota = TipoDeNota.PRIMER_PARCIAL;
		Nota nota = new Nota(valorNota, tipoNota);

		assertTrue(universidad.agregarMateria(materia));
		assertTrue(universidad.agregarCicloLectivo(ciclo));
		assertTrue(universidad.agregarComision(comision));
		assertTrue(universidad.agregarAlumno(alumno));
		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision.getId(),
				fechaInscripcionAlumnoAMateria));
		assertTrue(universidad.registrarNota(comision.getId(), alumno.getDni(), nota));
		
		assertTrue(universidad.aprobarMateria(comision.getId(), alumno.getDni(), nota));
	}

	@Test
	public void queNoSePuedaRegistrarNotaMayorOIgualASieteSiNoEstanLasCorrelativasAproadas() {
		Universidad universidad = new Universidad();
		Materia materia1 = new Materia(1, "Matemáticas");
		Materia materia2 = new Materia(2, "Física");
		// CicloLectivo
		Integer idCiloUno = 01;
		LocalDate fechaInicioCiloUno = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacionCiloUno = LocalDate.of(2023, 12, 31);
		LocalDate fechaInicioInscripcionCiloUno = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacionInscripcionCiloUno = LocalDate.of(2022, 12, 31);
		CicloLectivo ciclo1 = new CicloLectivo(idCiloUno, fechaInicioCiloUno, fechaFinalizacionCiloUno,
				fechaInicioInscripcionCiloUno, fechaFinalizacionInscripcionCiloUno);

		Integer idCicloDos = 01;
		LocalDate fechaInicioCicloDos = LocalDate.of(2025, 4, 1);
		LocalDate fechaFinalizacionCicloDos = LocalDate.of(2025, 12, 31);
		LocalDate fechaInicioInscripcionCicloDos = LocalDate.of(2025, 4, 1);
		LocalDate fechaFinalizacionInscripcionCicloDos = LocalDate.of(2025, 12, 31);
		CicloLectivo ciclo2 = new CicloLectivo(idCicloDos, fechaInicioCicloDos, fechaFinalizacionCicloDos,
				fechaInicioInscripcionCicloDos, fechaFinalizacionInscripcionCicloDos);
		// Aula
		Aula aula1 = new Aula(1, 60);
		Aula aula2 = new Aula(2, 60);
		// Comision
		Comision comision1 = new Comision(1, materia1, ciclo1, "Mañana", "MieJue", aula1);
		Comision comision2 = new Comision(2, materia2, ciclo2, "Tarde", "Mar", aula2);
		// Alumno
		Integer dni = 21351;
		String nombre = "Matias";
		String apellido = "Guarnieri";
		LocalDate fechaNacimiento = LocalDate.of(1997, 8, 6);
		LocalDate fechaIngreso = LocalDate.of(2022, 4, 10);
		Alumno alumno = new Alumno(dni, nombre, apellido, fechaNacimiento, fechaIngreso);
		// Fecha inscripcion del alumno
		LocalDate fechaInscripcionAlumnoAMateriaUno = LocalDate.of(2022, 4, 2);
		LocalDate fechaInscripcionAlumnoAMateriaDos = LocalDate.of(2025, 4, 2);
		// Nota
		Double valorNota1 = 10.00;
		TipoDeNota tipoNota1 = TipoDeNota.PRIMER_PARCIAL;
		Nota nota1 = new Nota(valorNota1, tipoNota1);
		Double valorNota2 = 4.00;
		TipoDeNota tipoNota2 = TipoDeNota.PRIMER_PARCIAL;
		Nota nota2 = new Nota(valorNota2, tipoNota2);

		assertTrue(universidad.agregarMateria(materia1));
		assertTrue(universidad.agregarMateria(materia2));
		assertTrue(universidad.agregarCicloLectivo(ciclo1));
		assertTrue(universidad.agregarCicloLectivo(ciclo2));
		assertTrue(universidad.agregarComision(comision1));
		assertTrue(universidad.agregarComision(comision2));
		assertTrue(universidad.agregarAlumno(alumno));
		assertTrue(universidad.agregarCorrelatividad(materia2.getId(), materia1.getId()));
		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision1.getId(),
				fechaInscripcionAlumnoAMateriaUno));

		assertTrue(universidad.registrarNota(comision1.getId(), alumno.getDni(), nota1));

		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision2.getId(),
				fechaInscripcionAlumnoAMateriaDos));

	}

	@Test
	public void queSePuedaObtenerUnaNota() {
		Universidad universidad = new Universidad();
		Integer idMateria = 1;
		Materia materia = new Materia(idMateria, "Matemáticas");
		// CicloLectivo
		Integer id = 01;
		LocalDate fechaInicio = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacion = LocalDate.of(2023, 12, 31);
		LocalDate fechaInicioInscripcion = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacionInscripcion = LocalDate.of(2022, 12, 31);
		CicloLectivo ciclo = new CicloLectivo(id, fechaInicio, fechaFinalizacion, fechaInicioInscripcion,
				fechaFinalizacionInscripcion);
		// Aula
		Aula aula = new Aula(1, 60);
		// Comision
		Comision comision = new Comision(1, materia, ciclo, "Mañana", "MieJue", aula);
		// Alumno
		Integer dni = 21351;
		String nombre = "Matias";
		String apellido = "Guarnieri";
		LocalDate fechaNacimiento = LocalDate.of(1997, 8, 6);
		LocalDate fechaIngreso = LocalDate.of(2022, 4, 10);
		Alumno alumno = new Alumno(dni, nombre, apellido, fechaNacimiento, fechaIngreso);
		// Fecha inscripcion del alumno
		LocalDate fechaInscripcionAlumnoAMateria = LocalDate.of(2022, 4, 2);
		// Nota
		Double valorNota = 8.00;
		TipoDeNota tipoNota = TipoDeNota.PRIMER_PARCIAL;
		Nota nota = new Nota(valorNota, tipoNota);

		assertTrue(universidad.agregarMateria(materia));
		assertTrue(universidad.agregarCicloLectivo(ciclo));
		assertTrue(universidad.agregarComision(comision));
		assertTrue(universidad.agregarAlumno(alumno));
		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision.getId(),
				fechaInscripcionAlumnoAMateria));
		assertTrue(universidad.registrarNota2(comision.getId(), alumno.getDni(), nota));
		
		Double valorResultante = universidad.obtenerNota(dni, materia);
		assertEquals(8.00, valorResultante, 0.0001);
}}
