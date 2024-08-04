package itgo.it_secondhand.repository;

import itgo.it_secondhand.domain.Keyword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class KeywordRepositoryTest {
    @Autowired
    KeywordRepository keywordRepository;

    private static Keyword keyword;

    @BeforeEach
    void setUp(){
        keyword = Keyword.create("keyword");
        keywordRepository.save(keyword);
    }

    @Test
    public void findByKeyword() throws Exception {
        //given
        String requestKeyword = keyword.getKeyword();

        //when
        Keyword response = keywordRepository.findByKeyword(requestKeyword);

        //then
        assertThat(response.getKeyword()).isEqualTo(requestKeyword);
    }


}