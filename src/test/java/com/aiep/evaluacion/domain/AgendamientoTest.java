package com.aiep.evaluacion.domain;

import static com.aiep.evaluacion.domain.AgendamientoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.evaluacion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgendamientoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agendamiento.class);
        Agendamiento agendamiento1 = getAgendamientoSample1();
        Agendamiento agendamiento2 = new Agendamiento();
        assertThat(agendamiento1).isNotEqualTo(agendamiento2);

        agendamiento2.setId(agendamiento1.getId());
        assertThat(agendamiento1).isEqualTo(agendamiento2);

        agendamiento2 = getAgendamientoSample2();
        assertThat(agendamiento1).isNotEqualTo(agendamiento2);
    }
}
