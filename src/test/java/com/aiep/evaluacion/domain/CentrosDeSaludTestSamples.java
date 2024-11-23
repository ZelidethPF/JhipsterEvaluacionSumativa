package com.aiep.evaluacion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CentrosDeSaludTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CentrosDeSalud getCentrosDeSaludSample1() {
        return new CentrosDeSalud()
            .id(1L)
            .nombre("nombre1")
            .tipo("tipo1")
            .ciudad("ciudad1")
            .direccion("direccion1")
            .telefono("telefono1")
            .email("email1");
    }

    public static CentrosDeSalud getCentrosDeSaludSample2() {
        return new CentrosDeSalud()
            .id(2L)
            .nombre("nombre2")
            .tipo("tipo2")
            .ciudad("ciudad2")
            .direccion("direccion2")
            .telefono("telefono2")
            .email("email2");
    }

    public static CentrosDeSalud getCentrosDeSaludRandomSampleGenerator() {
        return new CentrosDeSalud()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .tipo(UUID.randomUUID().toString())
            .ciudad(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString());
    }
}
