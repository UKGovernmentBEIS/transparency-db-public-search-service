package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.LegalBasis;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LegalBasisResponseTest {

    @Test
    void testLegalBasisResponse() {

        LegalBasis legalBasis = new LegalBasis();
        legalBasis.setLegalBasisText("legal text");
        LegalBasisResponse legalBasisResponse = new LegalBasisResponse(legalBasis);
        assertThat(legalBasisResponse).isNotNull();
        assertThat(legalBasisResponse.getLegalBasisText()).isNotNull();
        assertThat(legalBasisResponse.getLegalBasisText()).isEqualTo("legal text");
    }
}
