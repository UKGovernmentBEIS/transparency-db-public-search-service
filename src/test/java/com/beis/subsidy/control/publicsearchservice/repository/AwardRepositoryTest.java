package com.beis.subsidy.control.publicsearchservice.repository;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AwardRepositoryTest {

    public AwardRepository awardRepository = mock(AwardRepository.class);

    @Test
    public void findByServiceCodeTest() {
        when(awardRepository.findByAwardNumber(anyLong())).thenReturn(new Award());
        Award award =  awardRepository.findByAwardNumber(anyLong());
        assertThat(award).isNotNull();
    }
}
