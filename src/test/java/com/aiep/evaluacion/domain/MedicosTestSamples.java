package com.aiep.evaluacion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MedicosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Medicos getMedicosSample1() {
        return new Medicos()
            .id(1L)
            .nombre("nombre1")
            .rut("rut1")
            .especialidad("especialidad1")
            .telefono("telefono1")
            .ciudad("ciudad1")
            .email("email1");
    }

    public static Medicos getMedicosSample2() {
        return new Medicos()
            .id(2L)
            .nombre("nombre2")
            .rut("rut2")
            .especialidad("especialidad2")
            .telefono("telefono2")
            .ciudad("ciudad2")
            .email("email2");
    }

    public static Medicos getMedicosRandomSampleGenerator() {
        return new Medicos()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .rut(UUID.randomUUID().toString())
            .especialidad(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .ciudad(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
