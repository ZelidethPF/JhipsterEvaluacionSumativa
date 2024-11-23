package com.aiep.evaluacion.domain;

import static com.aiep.evaluacion.domain.CentrosDeSaludTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiep.evaluacion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CentrosDeSaludTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentrosDeSalud.class);
        CentrosDeSalud centrosDeSalud1 = getCentrosDeSaludSample1();
        CentrosDeSalud centrosDeSalud2 = new CentrosDeSalud();
        assertThat(centrosDeSalud1).isNotEqualTo(centrosDeSalud2);

        centrosDeSalud2.setId(centrosDeSalud1.getId());
        assertThat(centrosDeSalud1).isEqualTo(centrosDeSalud2);

        centrosDeSalud2 = getCentrosDeSaludSample2();
        assertThat(centrosDeSalud1).isNotEqualTo(centrosDeSalud2);
    }
}
