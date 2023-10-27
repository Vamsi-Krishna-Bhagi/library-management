package learn.vk.microservices.service;

import learn.vk.microservices.entity.Genre;
import learn.vk.microservices.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Long genreId) {
        return genreRepository.findById(genreId).orElseThrow(() -> new RuntimeException("Genre not found"));
    }

    public Genre addGenre(Genre genre) {
        genreRepository.findById(genre.getGenreId()).ifPresent(a -> {
            throw new RuntimeException("Genre already exists");
        });
        return genreRepository.save(genre);
    }

    public Genre updateGenre(Long genreId, Genre genre) {
        Genre existingGenre = genreRepository.findById(genreId).orElseThrow(() -> new RuntimeException("Genre not found"));
        existingGenre.setName(genre.getName());
        return genreRepository.save(existingGenre);
    }

    public void deleteGenre(Long genreId) {
        genreRepository.findById(genreId).orElseThrow(() -> new RuntimeException("Genre not found"));
        genreRepository.deleteById(genreId);
    }
}
