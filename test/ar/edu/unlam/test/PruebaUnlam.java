package ar.edu.unlam.test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Date;
import org.junit.Test;
import ar.edu.unlam.dominio.Alumno;
import ar.edu.unlam.dominio.CicloLectivo;
import ar.edu.unlam.dominio.Comision;
import ar.edu.unlam.dominio.Docente;
import ar.edu.unlam.dominio.Materia;
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
        Date fechaIngreso = new Date(2022,4,01);
        
        Alumno alumno1 = new Alumno(12345, "Juan", "Pérez", fechaNacimiento, fechaIngreso);
        Alumno alumno2 = new Alumno(12345, "Lucía", "García", fechaNacimiento, fechaIngreso); // Duplicado de DNI
        
        assertTrue(universidad.agregarAlumno(alumno1));
        assertFalse(universidad.agregarAlumno(alumno2));
    }

    @Test
    public void agregarCicloLectivo() {
        Universidad universidad = new Universidad();
        CicloLectivo ciclo1 = new CicloLectivo(2023, new Date(), new Date(), new Date(), new Date());
        CicloLectivo ciclo2 = new CicloLectivo(2023, new Date(), new Date(), new Date(), new Date()); // Mismo ID
        
        assertTrue(universidad.agregarCicloLectivo(ciclo1));
        assertFalse(universidad.agregarCicloLectivo(ciclo2)); // Intentar agregar un ciclo con el mismo ID debe fallar
    }

    @Test
    public void agregarComision() {
        Universidad universidad = new Universidad();
        Materia materia = new Materia(1, "Matemáticas");
        CicloLectivo ciclo = new CicloLectivo(2023, new Date(), new Date(), new Date(), new Date());
        Comision comision1 = new Comision(1, materia, ciclo, "Mañana");
        Comision comision2 = new Comision(1, materia, ciclo, "Tarde"); // Mismo ID
        
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
        CicloLectivo ciclo = new CicloLectivo(2023, new Date(), new Date(), new Date(), new Date());
        Comision comision = new Comision(1, materia, ciclo, "Mañana");
        
        assertTrue(universidad.agregarDocente(profesor1));
        assertTrue(universidad.agregarDocente(profesor2));
        assertTrue(universidad.agregarComision(comision));
        
        assertTrue(universidad.asignarDocentesAComision(comision.getId(), profesor1.getDni()));
        assertFalse(universidad.asignarDocentesAComision(comision.getId(), profesor1.getDni())); // El mismo docente no puede ser profesor de la misma comisión 2 veces
    }

    @Test
    public void agregarCorrelatividad() {
        Universidad universidad = new Universidad();
        Materia materia1 = new Materia(1, "Matemáticas");
        Materia materia2 = new Materia(2, "Física");
        
        assertTrue(universidad.agregarMateria(materia1));
        assertTrue(universidad.agregarMateria(materia2));
        
        assertTrue(universidad.agregarCorrelatividad(materia1.getId(), materia2.getId()));
        assertFalse(universidad.agregarCorrelatividad(materia1.getId(), materia2.getId())); // Intentar agregar la misma correlatividad debe fallar
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
        assertFalse(universidad.eliminarCorrelatividad(materia1.getId(), materia2.getId())); // Intentar eliminar la misma correlatividad debe fallar
    }

    @Test
    public void inscribirAlumnoAComision() {
        Universidad universidad = new Universidad();
        Materia materia = new Materia(1, "Matemáticas");
        CicloLectivo ciclo = new CicloLectivo(2023, new Date(), new Date(), new Date(), new Date());
        Comision comision = new Comision(1, materia, ciclo, "Mañana");
        Alumno alumno = new Alumno(12345, "Juan", "Pérez");
        
        assertTrue(universidad.agregarMateria(materia));
        assertTrue(universidad.agregarCicloLectivo(ciclo));
        assertTrue(universidad.agregarComision(comision));
        assertTrue(universidad.agregarAlumno(alumno));
        
        assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision.getId()));
        assertFalse(universidad.inscribirAlumnoAComision(alumno.getDni(), comision.getId())); // No se puede inscribir al mismo alumno dos veces
    }

    @Test
    public void asignarProfesorAlaComision() {
        Universidad universidad = new Universidad();
        Docente profesor = new Docente(1001, "Ana", "Gómez");
        Materia materia = new Materia(1, "Matemáticas");
        CicloLectivo ciclo = new CicloLectivo(2023, new Date(), new Date(), new Date(), new Date());
        Comision comision = new Comision(1, materia, ciclo, "Mañana");
        
        assertTrue(universidad.agregarMateria(materia));
        assertTrue(universidad.agregarCicloLectivo(ciclo));
        assertTrue(universidad.agregarComision(comision));
        assertTrue(universidad.agregarDocente(profesor));
        
        assertTrue(universidad.asignarProfesorAlaComision(comision.getId(), profesor.getDni()));
        assertFalse(universidad.asignarProfesorAlaComision(comision.getId(), profesor.getDni())); // No se puede asignar al mismo profesor dos veces
    }
    @Test
    public void asignarAulaAlaComision() {
        Universidad universidad = new Universidad();
        Docente profesor = new Docente(1001, "Ana", "Gómez");
        Materia materia = new Materia(1, "Matemáticas");
        CicloLectivo ciclo = new CicloLectivo(2023, new Date(), new Date(), new Date(), new Date());
        Comision comision = new Comision(1, materia, ciclo, "Mañana");
        Aula aula = new Aula(101, 30); // Una aula con capacidad para 30 alumnos
        
        assertTrue(universidad.agregarMateria(materia));
        assertTrue(universidad.agregarCicloLectivo(ciclo));
        assertTrue(universidad.agregarComision(comision));
        assertTrue(universidad.agregarDocente(profesor));
        assertTrue(universidad.agregarAula(aula));
        
        assertTrue(universidad.asignarAulaAlaComision(comision.getId(), aula.getId()));
        assertFalse(universidad.asignarAulaAlaComision(comision.getId(), aula.getId())); // No se puede asignar el mismo aula dos veces
    }

    @Test
    public void registrarNota() {
        Universidad universidad = new Universidad();
        Materia materia = new Materia(1, "Matemáticas");
        CicloLectivo ciclo = new CicloLectivo(2023, new Date(), new Date(), new Date(), new Date());
        Comision comision = new Comision(1, materia, ciclo, "Mañana");
        Alumno alumno = new Alumno(12345, "Juan", "Pérez");
        
        assertTrue(universidad.agregarMateria(materia));
        assertTrue(universidad.agregarCicloLectivo(ciclo));
        assertTrue(universidad.agregarComision(comision));
        assertTrue(universidad.agregarAlumno(alumno));
        assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision.getId()));
        
        assertTrue(universidad.registrarNota(comision.getId(), alumno.getDni(), "1erParc", 8)); // Registrar una nota válida
        assertTrue(universidad.registrarNota(comision.getId(), alumno.getDni(), "2doParc", 7));
        assertFalse(universidad.registrarNota(comision.getId(), alumno.getDni(), "Final", 6)); // No se puede asignar una nota final si no se aprobaron ambos parciales
    }

    @Test
    public void obtenerMateriasAprobadasParaUnAlumno() {
        Universidad universidad = new Universidad();
        Materia materia1 = new Materia(1, "Matemáticas");
        Materia materia2 = new Materia(2, "Física");
        CicloLectivo ciclo = new CicloLectivo(2023, new Date(), new Date(), new Date(), new Date());
        Comision comision1 = new Comision(1, materia1, ciclo, "Mañana");
        Comision comision2 = new Comision(2, materia2, ciclo, "Tarde");
        Alumno alumno = new Alumno(12345, "Juan", "Pérez");
        
        assertTrue(universidad.agregarMateria(materia1));
        assertTrue(universidad.agregarMateria(materia2));
        assertTrue(universidad.agregarCicloLectivo(ciclo));
        assertTrue(universidad.agregarComision(comision1));
        assertTrue(universidad.agregarComision(comision2));
        assertTrue(universidad.agregarAlumno(alumno));
        assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision1.getId()));
        assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision2.getId()));
        
        universidad.registrarNota(comision1.getId(), alumno.getDni(), "Final", 7);
        universidad.registrarNota(comision2.getId(), alumno.getDni(), "Final", 6);
        
        List<Materia> materiasAprobadas = universidad.obtenerMateriasAprobadasParaUnAlumno(alumno.getDni());
        assertEquals(2, materiasAprobadas.size());
        assertTrue(materiasAprobadas.contains(materia1));
        assertTrue(materiasAprobadas.contains(materia2));
    }

    @Test
    public void calcularPromedio() {
        Universidad universidad = new Universidad();
        Materia materia1 = new Materia(1, "Matemáticas");
        Materia materia2 = new Materia(2, "Física");
        CicloLectivo ciclo = new CicloLectivo(2023, new Date(), new Date(), new Date(), new Date());
        Comision comision1 = new Comision(1, materia1, ciclo, "Mañana");
        Comision comision2 = new Comision(2, materia2, ciclo, "Tarde");
        Alumno alumno = new Alumno(12345, "Juan", "Pérez");
        
        assertTrue(universidad.agregarMateria(materia1));
        assertTrue(universidad.agregarMateria(materia2));
        assertTrue(universidad.agregarCicloLectivo(ciclo));
        assertTrue(universidad.agregarComision(comision1));
        assertTrue(universidad.agregarComision(comision2));
        assertTrue(universidad.agregarAlumno(alumno));
        assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision1.getId()));
        assertTrue(universidad.inscribirAlumnoAComision(alumno.getDni(), comision2.getId()));
        
        universidad.registrarNota(comision1.getId(), alumno.getDni(), "Final", 7);
        universidad.registrarNota(comision2.getId(), alumno.getDni(), "Final", 6);
        
        double promedio = universidad.calcularPromedio(alumno.getDni());
        assertEquals(6.5, promedio, 0.01); // Comprobar que el promedio sea aproximadamente 6.5
    }
}
