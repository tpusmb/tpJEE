package com.pneedle.RepositoryIntegrationTest;


import com.pneedle.TemplateDataTest.TemplateDataTest;
import com.pneedle.model.User;
import com.pneedle.repository.UserRepository;
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
public class UserRepositoryIntegrationTest extends TemplateDataTest {

    @Autowired
    private TestEntityManager entityManager;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByEmail_thenReturnUser() {
        // Given
        entityManager.persistAndFlush(this.admin);

        // When
        User found = userRepository.findByEmail(admin.getEmail());

        // Given
        assertThat(found.getEmail()).isEqualTo(admin.getEmail());
    }

}
