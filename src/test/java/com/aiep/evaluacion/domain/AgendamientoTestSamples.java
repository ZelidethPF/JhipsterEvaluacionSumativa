package com.aiep.evaluacion.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AgendamientoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Agendamiento getAgendamientoSample1() {
        return new Agendamiento()
            .id(1L)
            .paciente("paciente1")
            .medico("medico1")
            .centro("centro1")
            .estado("estado1")
            .origen("origen1")
            .motivo("motivo1")
            .nivelprioridad("nivelprioridad1");
    }

    public static Agendamiento getAgendamientoSample2() {
        return new Agendamiento()
            .id(2L)
            .paciente("paciente2")
            .medico("medico2")
            .centro("centro2")
            .estado("estado2")
            .origen("origen2")
            .motivo("motivo2")
            .nivelprioridad("nivelprioridad2");
    }

    public static Agendamiento getAgendamientoRandomSampleGenerator() {
        return new Agendamiento()
            .id(longCount.incrementAndGet())
            .paciente(UUID.randomUUID().toString())
            .medico(UUID.randomUUID().toString())
            .centro(UUID.randomUUID().toString())
            .estado(UUID.randomUUID().toString())
            .origen(UUID.randomUUID().toString())
            .motivo(UUID.randomUUID().toString())
            .nivelprioridad(UUID.randomUUID().toString());
    }
}
