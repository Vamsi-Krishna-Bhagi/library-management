package learn.vk.microservices.service;

import learn.vk.microservices.dto.AuthorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthorService {
    private RestTemplate restTemplate;

    @Value("${url.author-service}")
    private String authorServiceUrl;

    @Autowired
    public AuthorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String getAuthorName(Long authorId) {
        return getAuthorById(authorId).map(AuthorDto::getName)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public AuthorDto createAuthor(AuthorDto author) {
        return Objects.requireNonNull(restTemplate.postForObject(authorServiceUrl, author, AuthorDto.class));
    }

    public Optional<AuthorDto> getAuthorById(Long authorId) {
        return Optional.ofNullable(restTemplate.getForObject(authorServiceUrl + "/" + authorId, AuthorDto.class));
    }

    public Long getOrCreateAuthorId(AuthorDto authorDto) {
        Optional<AuthorDto> returnedAuthorOptional = getAuthorById(authorDto.getAuthorId());
        if (returnedAuthorOptional.isEmpty()) {
            return (createAuthor(authorDto)).getAuthorId();
        }
        return returnedAuthorOptional.get().getAuthorId();
    }

    public AuthorDto updateAuthor(Long authorId, AuthorDto authorDto) {
        return restTemplate.patchForObject(authorServiceUrl + "/" + authorId, authorDto, AuthorDto.class);
    }

    public Long updateOrCreateAuthorId(AuthorDto author) {
        Optional<AuthorDto> returnedAuthorOptional = getAuthorById(author.getAuthorId());
        if (returnedAuthorOptional.isEmpty()) {
            return (createAuthor(author)).getAuthorId();
        }
        return updateAuthor(author.getAuthorId(), author).getAuthorId();
    }
}
