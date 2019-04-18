package secure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import secure.domain.Reader;

/**
 * Desciption
 *
 * @author Claire.Chen
 * @create_time 2019 -03 - 28 9:38
 */
public interface ReaderRepository extends JpaRepository<Reader,String> {
    Reader findByUsername(String username);
}
