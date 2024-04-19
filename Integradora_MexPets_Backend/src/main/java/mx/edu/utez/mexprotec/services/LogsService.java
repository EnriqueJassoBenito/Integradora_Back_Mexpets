package mx.edu.utez.mexprotec.services;

import mx.edu.utez.mexprotec.models.logs.Logs;
import mx.edu.utez.mexprotec.models.logs.LogsRepository;
import mx.edu.utez.mexprotec.utils.CustomResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.sql.SQLException;
import java.util.List;


@Service
@Transactional
public class LogsService {
    private final LogsRepository repository;
    public LogsService(LogsRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public CustomResponse<List<Logs>> getAll() {
        return new CustomResponse<>(
                this.repository.findAllByOrderByCreatedAt(),
                false,
                200,
                "OK"
        );
    }

    @Transactional(readOnly = true)
    public CustomResponse<Page<Logs>> getAll(Pageable pageable) {
        return new CustomResponse<>(
                this.repository.findAllByOrderByCreatedAt(pageable),
                false,
                200,
                "OK"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Logs> save(Logs logs) throws SQLException {
        return new CustomResponse<>(this.repository.save(logs), false, 200, "Correcto");
    }
}
