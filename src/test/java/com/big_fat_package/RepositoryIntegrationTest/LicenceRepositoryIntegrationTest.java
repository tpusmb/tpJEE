package com.big_fat_package.RepositoryIntegrationTest;


import com.big_fat_package.TemplateDataTest.TemplateDataTest;
import com.big_fat_package.model.Licence;
import com.big_fat_package.repository.LicenceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LicenceRepositoryIntegrationTest extends TemplateDataTest {

    @Autowired
    private TestEntityManager entityManager;

    @Qualifier("licenceRepository")
    @Autowired
    private LicenceRepository licenceRepository;

    @Test
    public void whenFindByLicenceKey_thenReturnLicence() {
        // Given
        entityManager.persistAndFlush(licence);

        // When
        Licence found = licenceRepository.findByLicenceKey(licence.getLicenceKey());

        // Given
        assertThat(found.getLicenceKey()).isEqualTo(licence.getLicenceKey());
    }
}