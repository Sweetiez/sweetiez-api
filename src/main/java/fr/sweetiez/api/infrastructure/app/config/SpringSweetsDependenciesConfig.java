package fr.sweetiez.api.infrastructure.app.config;

import fr.sweetiez.api.adapter.delivery.SweetEndPoints;
import fr.sweetiez.api.adapter.repository.CommentReaderAdapter;
import fr.sweetiez.api.adapter.repository.SweetReaderAdapter;
import fr.sweetiez.api.adapter.shared.CommentMapper;
import fr.sweetiez.api.adapter.shared.SweetMapper;
import fr.sweetiez.api.adapter.repository.SweetWriterAdapter;
import fr.sweetiez.api.core.comments.ports.CommentReader;
import fr.sweetiez.api.core.comments.services.CommentService;
import fr.sweetiez.api.core.sweets.ports.SweetsReader;
import fr.sweetiez.api.core.sweets.ports.SweetsWriter;
import fr.sweetiez.api.core.sweets.services.SweetService;
import fr.sweetiez.api.infrastructure.repository.CommentRepository;
import fr.sweetiez.api.infrastructure.repository.SweetRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringSweetsDependenciesConfig {

    private final SweetRepository sweetRepository;
    private final CommentRepository commentRepository;

    public SpringSweetsDependenciesConfig(SweetRepository sweetRepository, CommentRepository commentRepository) {
        this.sweetRepository = sweetRepository;
        this.commentRepository = commentRepository;
    }

    @Bean
    public CommentMapper commentMapper() {
        return new CommentMapper();
    }

    @Bean
    public CommentReader commentReader() {
        return new CommentReaderAdapter(commentRepository, commentMapper());
    }

    @Bean
    public CommentService commentService() {
        return new CommentService(commentReader());
    }

    @Bean
    public SweetMapper sweetMapper() {
        return new SweetMapper();
    }

    @Bean
    public SweetsReader sweetReader() {
        return new SweetReaderAdapter(sweetRepository, sweetMapper());
    }

    @Bean
    public SweetsWriter sweetWriter() {
        return new SweetWriterAdapter(sweetRepository, sweetMapper());
    }
    
    @Bean
    public SweetService sweetService() {
        return new SweetService(sweetWriter(), sweetReader(), commentService());
    }

    @Bean
    public SweetEndPoints sweetEndPoints() {
        return new SweetEndPoints(sweetService());
    }
}
