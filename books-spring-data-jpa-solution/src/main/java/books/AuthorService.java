package books;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService {

    private ModelMapper modelMapper;
    private AuthorRepository repository;

    public List<AuthorDto> getAuthors() {
        Type targetListType = new TypeToken<List<AuthorDto>>(){}.getType();
        List<Author> authors = repository.findAll();
        return modelMapper.map(authors, targetListType);
    }

    public AuthorDto createAuthor(CreateAuthorCommand command) {
        Author author = new Author(command.getName());
        repository.save(author);
        return modelMapper.map(author, AuthorDto.class);
    }

    @Transactional
    public AuthorDto addBook(long id, AddBookCommand command) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Author with id: " + id + " not found."));
        author.addBook(new Book(command.getIsbnNumber(), command.getTitle()));
        return modelMapper.map(author, AuthorDto.class);
    }

    public void deleteAuthor(long id) {
        repository.deleteById(id);
    }
}
