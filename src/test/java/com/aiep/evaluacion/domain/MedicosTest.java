package com.aiep.evaluacion.domain;

import static com.aiep.evaluacion.domain.MedicosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.evaluacion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedicosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medicos.class);
        Medicos medicos1 = getMedicosSample1();
        Medicos medicos2 = new Medicos();
        assertThat(medicos1).isNotEqualTo(medicos2);

        medicos2.setId(medicos1.getId());
        assertThat(medicos1).isEqualTo(medicos2);

        medicos2 = getMedicosSample2();
        assertThat(medicos1).isNotEqualTo(medicos2);
    }
}
