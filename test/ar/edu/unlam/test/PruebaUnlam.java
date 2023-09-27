package ar.edu.unlam.test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Set;

import org.junit.Test;
import ar.edu.unlam.dominio.Alumno;
import ar.edu.unlam.dominio.Aula;
import ar.edu.unlam.dominio.Carrera;
import ar.edu.unlam.dominio.CicloLectivo;
import ar.edu.unlam.dominio.Comision;
import ar.edu.unlam.dominio.Docente;
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
	public void queSePuedaAgregarCicloLectivo() {
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
	public void queSePuedaAgregarComision() {
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
	public void queSePuedaAgregarDocentes() {
		Universidad universidad = new Universidad();
		Docente profesor1 = new Docente(1001, "Ana", "Gómez");
		Docente profesor2 = new Docente(1002, "Juan", "López");
		Docente profesor3 = new Docente(1001, "Carlos", "Martínez"); // Mismo DNI

		assertTrue(universidad.agregarDocente(profesor1));
		assertTrue(universidad.agregarDocente(profesor2));
		assertFalse(universidad.agregarDocente(profesor3)); // Intentar agregar un docente con el mismo DNI debe fallar
	}

	@Test
	public void queSePuedaAsignarDocentesAComision() {
		Universidad universidad = new Universidad();
		Docente profesor1 = new Docente(1001, "Ana", "Gómez");
		Docente profesor2 = new Docente(1002, "Juan", "López");
		Materia materia = new Materia(1, "Matemáticas");
		//ciclo lectivo
		Integer id = 01;
		LocalDate fechaInicio = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacion = LocalDate.of(2023, 12, 31);
		LocalDate fechaInicioInscripcion = LocalDate.of(2022, 4, 1);
		LocalDate fechaFinalizacionInscripcion = LocalDate.of(2022, 12, 31);
		CicloLectivo ciclo = new CicloLectivo(id, fechaInicio, fechaFinalizacion, fechaInicioInscripcion,
				fechaFinalizacionInscripcion);
		//aula
		Aula aula = new Aula(1, 60);
		Comision comision = new Comision(1, materia, ciclo, "Mañana", "MieJue", aula);
		//alumno
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
		assertFalse(universidad.asignarDocentesAComision(comision.getId(), profesor1.getDni()));
		// El mismo docente no puede ser profesor de la misma comisión 2 veces
	}

	@Test
	public void queSePuedaAgregarCorrelatividad() {
		Universidad universidad = new Universidad();
		Materia materia1 = new Materia(1, "Matemáticas");
		Materia materia2 = new Materia(2, "Física");

		assertTrue(universidad.agregarMateria(materia1));
		assertTrue(universidad.agregarMateria(materia2));
		assertTrue(universidad.agregarCorrelatividad(materia1.getId(), materia2.getId()));
		assertFalse(universidad.agregarCorrelatividad(materia1.getId(), materia2.getId()));

		// Intentar agregar la misma correlatividad debe fallar
	}

	@Test
	public void queSePuedaEliminarCorrelatividad() {
		Universidad universidad = new Universidad();
		Materia materia1 = new Materia(1, "Matemáticas");
		Materia materia2 = new Materia(2, "Física");

		assertTrue(universidad.agregarMateria(materia1));
		assertTrue(universidad.agregarMateria(materia2));
		assertTrue(universidad.agregarCorrelatividad(materia1.getId(), materia2.getId()));
		assertTrue(universidad.eliminarCorrelatividad(materia1.getId(), materia2.getId()));
		assertFalse(universidad.eliminarCorrelatividad(materia1.getId(), materia2.getId()));
		// Intentar eliminar la misma correlatividad debe fallar

	}

	@Test
	public void queSePuedaInscribirAlumnoAComision() {
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
	public void queSePuedaAsignarAulaAlaComisionSiLaMismaNoFueCreadaConUnAula() {
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

	}

	@Test
	public void queSePuedaRegistrarNota() {
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
		assertFalse(universidad.registrarNota(comision.getId(), alumno.getDni(), nota));
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
		assertFalse(universidad.registrarNota(comision.getId(), alumno.getDni(), nota));
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
		TipoDeNota tipoNota = TipoDeNota.FINAL;
		Nota nota = new Nota(valorNota, tipoNota);

		assertTrue(universidad.agregarMateria(materia));
		assertTrue(universidad.agregarCicloLectivo(ciclo));
		assertTrue(universidad.agregarComision(comision));
		assertTrue(universidad.agregarAlumno(alumno));
		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision.getId(),
				fechaInscripcionAlumnoAMateria));
		assertTrue(universidad.registrarNota(comision.getId(), alumno.getDni(), nota));
		assertTrue(universidad.aprobarMateria(comision, alumno));
	}

	@Test
	public void queNoSePuedaRegistrarNotaMayorOIgualASieteSiNoEstanLasCorrelativasAproadas() {
		Universidad universidad = new Universidad();
		Materia materia1 = new Materia(1, "Matemáticas");
		Materia materia2 = new Materia(2, "Física");
		CicloLectivo ciclo1 = new CicloLectivo(1, LocalDate.of(2022, 4, 1), LocalDate.of(2023, 12, 31),
				LocalDate.of(2022, 4, 1), LocalDate.of(2022, 12, 31));
		CicloLectivo ciclo2 = new CicloLectivo(2, LocalDate.of(2022, 4, 1), LocalDate.of(2023, 12, 31),
				LocalDate.of(2022, 4, 1), LocalDate.of(2022, 12, 31));

		assertTrue(universidad.agregarMateria(materia1));
		assertTrue(universidad.agregarMateria(materia2));
		assertTrue(universidad.agregarCicloLectivo(ciclo1));
		assertTrue(universidad.agregarCicloLectivo(ciclo2));
		assertTrue(universidad.agregarCorrelatividad(materia2.getId(), materia1.getId()));

		Aula aula = new Aula(1, 60);
		Comision comision = new Comision(1, materia2, ciclo2, "Mañana", "MieJue", aula);

		Alumno alumno = new Alumno(21351, "Matias", "Guarnieri", LocalDate.of(1997, 8, 6), LocalDate.of(2022, 4, 10));
		assertTrue(universidad.agregarComision(comision));
		assertTrue(universidad.agregarAlumno(alumno));

		// Nota mayor o igual a siete (no debería ser posible sin correlativa aprobada)
		Double valorNotaMayorOIgualASiete = 7.50;
		TipoDeNota tipoNota = TipoDeNota.PRIMER_PARCIAL;
		Nota nota = new Nota(valorNotaMayorOIgualASiete, tipoNota);

		// Intentar registrar la nota (esto debería fallar)
		assertFalse(universidad.registrarNota(comision.getId(), alumno.getDni(), nota));
	}

	@Test
	public void queSePuedaObtenerMateriasAprobadasParaUnAlumno() {
		Universidad universidad = new Universidad();
		Materia materia = new Materia(1, "Matemáticas");
		Materia materia2 = new Materia(2, "Matemáticas");
		Materia materia3 = new Materia(3, "Matemáticas");
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
		Aula aula2 = new Aula(2, 60);
		Aula aula3 = new Aula(3, 60);
		// Comision
		Comision comision = new Comision(1, materia, ciclo, "Mañana", "MieJue", aula);
		Comision comision2 = new Comision(2, materia2, ciclo, "Mañana", "MieJue", aula2);
		Comision comision3 = new Comision(3, materia3, ciclo, "Mañana", "MieJue", aula3);
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
		TipoDeNota tipoNota = TipoDeNota.FINAL;
		Nota nota = new Nota(valorNota, tipoNota);

		assertTrue(universidad.agregarMateria(materia));
		assertTrue(universidad.agregarMateria(materia2));
		assertTrue(universidad.agregarMateria(materia3));
		assertTrue(universidad.agregarCicloLectivo(ciclo));
		assertTrue(universidad.agregarComision(comision));
		assertTrue(universidad.agregarComision(comision2));
		assertTrue(universidad.agregarComision(comision3));
		assertTrue(universidad.agregarAlumno(alumno));
		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision.getId(),
				fechaInscripcionAlumnoAMateria));
		assertTrue(universidad.registrarNota(comision.getId(), alumno.getDni(), nota));
		assertTrue(universidad.registrarNota(comision2.getId(), alumno.getDni(), nota));
		assertTrue(universidad.registrarNota(comision3.getId(), alumno.getDni(), nota));
		assertTrue(universidad.aprobarMateria(comision, alumno));
		assertTrue(universidad.aprobarMateria(comision2, alumno));
		assertTrue(universidad.aprobarMateria(comision3, alumno));

		Set<Materia> materiasAprobadas = universidad.obtenerMateriasAprobadasParaUnAlumno(alumno.getDni());

		assertEquals(3, materiasAprobadas.size());
		assertTrue(materiasAprobadas.contains(materia));
		assertTrue(materiasAprobadas.contains(materia2));
		assertTrue(materiasAprobadas.contains(materia3));

	}

	@Test
	public void queSePuedaObtenerMateriasQueFaltanCursarParaUnAlumno() {
		Universidad universidad = new Universidad();
		Carrera carrera = new Carrera(23, "Tecnología Web");
		assertTrue(universidad.agregarCarrera(carrera));

		Materia materia1 = new Materia(1, "Matemáticas");
		Materia materia2 = new Materia(2, "Programación");
		Materia materia3 = new Materia(3, "Base de Datos");
		Materia materia4 = new Materia(4, "Diseño Web");
		assertTrue(universidad.agregarMateriaACarrera(carrera, materia1));
		assertTrue(universidad.agregarMateriaACarrera(carrera, materia2));
		assertTrue(universidad.agregarMateriaACarrera(carrera, materia3));
		assertTrue(universidad.agregarMateriaACarrera(carrera, materia4));

		CicloLectivo ciclo = new CicloLectivo(1, LocalDate.of(2022, 4, 1), LocalDate.of(2023, 12, 31),
				LocalDate.of(2022, 4, 1), LocalDate.of(2022, 12, 31));
		assertTrue(universidad.agregarCicloLectivo(ciclo));
		Aula aula = new Aula(1, 60);
		Comision comision = new Comision(1, materia1, ciclo, "Mañana", "MieJue", aula);
		assertTrue(universidad.agregarComision(comision));

		Alumno alumno = new Alumno(21351, "Matias", "Guarnieri", LocalDate.of(1997, 8, 6), LocalDate.of(2022, 4, 10));
		assertTrue(universidad.agregarAlumno(alumno));

		LocalDate fechaInscripcionAlumnoAMateria = LocalDate.of(2022, 4, 2);
		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision.getId(),
				fechaInscripcionAlumnoAMateria));

		Double valorNota = 8.00;
		TipoDeNota tipoNota = TipoDeNota.FINAL;
		Nota nota = new Nota(valorNota, tipoNota);
		assertTrue(universidad.registrarNota(comision.getId(), alumno.getDni(), nota));
		assertTrue(universidad.aprobarMateria(comision, alumno));

		Set<Materia> materiasFaltantes = universidad.obtenerMateriasFaltantes(alumno.getDni(), carrera.getId());

		assertEquals(3, materiasFaltantes.size());
		assertTrue(materiasFaltantes.contains(materia2));
		assertTrue(materiasFaltantes.contains(materia3));
		assertTrue(materiasFaltantes.contains(materia4));
	}

	@Test
	public void queSePuedaComprobarSiLaMateriaCorrelativaEstaAprobada() {
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

		Integer idCicloDos = 02;
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
		assertTrue(universidad.agregarAlumno(alumno));
		// Fecha inscripcion del alumno
		LocalDate fechaInscripcionAlumnoAMateriaUno = LocalDate.of(2022, 4, 2);
		// Nota
		Double valorNota1 = 10.00;
		TipoDeNota tipoNota1 = TipoDeNota.FINAL;
		Nota nota1 = new Nota(valorNota1, tipoNota1);

		assertTrue(universidad.agregarMateria(materia1));
		assertTrue(universidad.agregarMateria(materia2));
		assertTrue(universidad.agregarCicloLectivo(ciclo1));
		assertTrue(universidad.agregarCicloLectivo(ciclo2));
		assertTrue(universidad.agregarComision(comision1));
		assertTrue(universidad.agregarComision(comision2));

		assertTrue(universidad.agregarCorrelatividad(materia2.getId(), materia1.getId()));
		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision1.getId(),
				fechaInscripcionAlumnoAMateriaUno));
		assertTrue(universidad.registrarNota(comision1.getId(), alumno.getDni(), nota1));
		assertTrue(universidad.aprobarMateria(comision1, alumno));

		assertTrue(universidad.tieneTodasLasCorrelativasAprobadas(comision2, alumno));
	}

	@Test
	public void queSePuedaObtenerNotaFinalDeMateriaAprobadaPorAlumno() {
		Universidad universidad = new Universidad();
		Materia materia1 = new Materia(1, "Matemáticas basica");
		Materia materia2 = new Materia(2, "Informatica");
		Materia materia3 = new Materia(3, "Programacion basica 1");

		CicloLectivo ciclo = new CicloLectivo(1, LocalDate.of(2022, 4, 1), LocalDate.of(2023, 12, 31),
				LocalDate.of(2022, 4, 1), LocalDate.of(2022, 12, 31));

		// Crear tres comisiones, una para cada materia
		Comision comision1 = new Comision(1, materia1, ciclo, "Mañana", "Lunes", new Aula(1, 60));
		Comision comision2 = new Comision(2, materia2, ciclo, "Tarde", "Martes", new Aula(2, 60));
		Comision comision3 = new Comision(3, materia3, ciclo, "Noche", "Miércoles", new Aula(3, 60));

		// Crear un alumno
		Alumno alumno = new Alumno(21351, "Matias", "Guarnieri", LocalDate.of(1997, 8, 6), LocalDate.of(2022, 4, 10));

		// Fecha de inscripción del alumno a las comisiones
		LocalDate fechaInscripcionAlumnoAMateria1 = LocalDate.of(2022, 4, 2);
		LocalDate fechaInscripcionAlumnoAMateria2 = LocalDate.of(2022, 4, 3);
		LocalDate fechaInscripcionAlumnoAMateria3 = LocalDate.of(2022, 4, 4);

		// Crear y registrar notas finales para el alumno en las comisiones
		Double valorNotaFinal1 = 8.00;
		Double valorNotaFinal2 = 7.50;
		Double valorNotaFinal3 = 9.00;
		TipoDeNota tipoNotaFinal = TipoDeNota.FINAL;
		Nota notaFinal1 = new Nota(valorNotaFinal1, tipoNotaFinal);
		Nota notaFinal2 = new Nota(valorNotaFinal2, tipoNotaFinal);
		Nota notaFinal3 = new Nota(valorNotaFinal3, tipoNotaFinal);

		assertTrue(universidad.agregarMateria(materia1));
		assertTrue(universidad.agregarMateria(materia2));
		assertTrue(universidad.agregarMateria(materia3));
		assertTrue(universidad.agregarCicloLectivo(ciclo));
		assertTrue(universidad.agregarComision(comision1));
		assertTrue(universidad.agregarComision(comision2));
		assertTrue(universidad.agregarComision(comision3));
		assertTrue(universidad.agregarAlumno(alumno));
		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision1.getId(),
				fechaInscripcionAlumnoAMateria1));
		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision2.getId(),
				fechaInscripcionAlumnoAMateria2));
		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision3.getId(),
				fechaInscripcionAlumnoAMateria3));
		assertTrue(universidad.registrarNota(comision1.getId(), alumno.getDni(), notaFinal1));
		assertTrue(universidad.registrarNota(comision2.getId(), alumno.getDni(), notaFinal2));
		assertTrue(universidad.registrarNota(comision3.getId(), alumno.getDni(), notaFinal3));
		assertTrue(universidad.aprobarMateria(comision1, alumno));
		assertTrue(universidad.aprobarMateria(comision2, alumno));
		assertTrue(universidad.aprobarMateria(comision3, alumno));

		// Obtener las notas finales de las materias aprobadas por el alumno
		Nota notaObtenida1 = universidad.obtenerNotaDeMateriaAprobadaDeAlumno(materia1, alumno);
		Nota notaObtenida2 = universidad.obtenerNotaDeMateriaAprobadaDeAlumno(materia2, alumno);
		Nota notaObtenida3 = universidad.obtenerNotaDeMateriaAprobadaDeAlumno(materia3, alumno);

		// Verificar que las notas obtenidas sean las notas finales registradas
		assertNotNull(notaObtenida1);
		assertNotNull(notaObtenida2);
		assertNotNull(notaObtenida3);

		assertEquals(valorNotaFinal1, notaObtenida1.getValor(), 0.01);
		assertEquals(valorNotaFinal2, notaObtenida2.getValor(), 0.01);
		assertEquals(valorNotaFinal3, notaObtenida3.getValor(), 0.01);

		assertEquals(tipoNotaFinal, notaObtenida1.getTipo());
		assertEquals(tipoNotaFinal, notaObtenida2.getTipo());
		assertEquals(tipoNotaFinal, notaObtenida3.getTipo());
	}

	@Test
	public void queSePuedaCalcularElPromedioDeNotasDeUnaComision() {
		Universidad universidad = new Universidad();
		Integer idMateria = 1;
		Materia materia = new Materia(idMateria, "Matemáticas");
		// CicloLectivo
		Integer id = 1;
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
		// Notas con tipos diferentes
		Double valorNotaPrimerParcial = 8.00;
		TipoDeNota tipoNotaPrimerParcial = TipoDeNota.PRIMER_PARCIAL;
		Nota notaPrimerParcial = new Nota(valorNotaPrimerParcial, tipoNotaPrimerParcial);
		Double valorNotaSegundoParcial = 9.00;

		TipoDeNota tipoNotaSegundoParcial = TipoDeNota.SEGUNDO_PARCIAL;

		Nota notaSegundoParcial = new Nota(valorNotaSegundoParcial, tipoNotaSegundoParcial);
	
		Double promedioNotas = (valorNotaPrimerParcial + valorNotaSegundoParcial) / 2.0;

		assertTrue(universidad.agregarMateria(materia));
		assertTrue(universidad.agregarCicloLectivo(ciclo));
		assertTrue(universidad.agregarComision(comision));
		assertTrue(universidad.agregarAlumno(alumno));
		assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision.getId(),
				fechaInscripcionAlumnoAMateria));
		assertTrue(universidad.registrarNota(comision.getId(), alumno.getDni(), notaPrimerParcial));
		assertTrue(universidad.registrarNota(comision.getId(), alumno.getDni(), notaSegundoParcial));
		assertEquals(promedioNotas, universidad.calcularPromedioDeNotas(comision.getId(), alumno.getDni()), 0.0);
	}
}