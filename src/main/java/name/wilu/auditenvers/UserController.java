package name.wilu.auditenvers;

import name.wilu.auditenvers.User.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static java.util.UUID.randomUUID;

@RestController
@SuppressWarnings("unused")
class UserController {

    private final UserService service;

    UserController(UserService service) {this.service = service;}

    @ResponseBody
    @PostMapping("users")
    public User addUser(@RequestBody User user) {
        return service.add(user);
    }

    @ResponseBody
    @PostMapping("users/{userName}/books")
    public User addUser(@RequestBody Book book, @PathVariable String userName) {
        return service.add(userName, book);
    }

    @Service
    static class UserService {
        //
        private final @PersistenceContext EntityManager em;

        UserService(EntityManager em) {this.em = em;}

        @Transactional
        public User add(User user) {
            user.id = randomUUID();
            em.persist(user);
            return user;
        }

        @Transactional
        public User add(String userName, Book book) {
            book.id = randomUUID();
            User user = em.createQuery(
                    "from User where name=:name", User.class
            ).setParameter("name", userName).getSingleResult();
            user.addBook(book);
            em.persist(book);
            return user;
        }
    }
}
