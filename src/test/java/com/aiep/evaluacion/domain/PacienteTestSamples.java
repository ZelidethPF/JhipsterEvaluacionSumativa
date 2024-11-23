package com.aiep.evaluacion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PacienteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Paciente getPacienteSample1() {
        return new Paciente()
            .id(1L)
            .nombre("nombre1")
            .rut("rut1")
            .edad(1)
            .sexo("sexo1")
            .direccion("direccion1")
            .ciudad("ciudad1")
            .telefono("telefono1")
            .email("email1");
    }

    public static Paciente getPacienteSample2() {
        return new Paciente()
            .id(2L)
            .nombre("nombre2")
            .rut("rut2")
            .edad(2)
            .sexo("sexo2")
            .direccion("direccion2")
            .ciudad("ciudad2")
            .telefono("telefono2")
            .email("email2");
    }

    public static Paciente getPacienteRandomSampleGenerator() {
        return new Paciente()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .rut(UUID.randomUUID().toString())
            .edad(intCount.incrementAndGet())
            .sexo(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString())
            .ciudad(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
