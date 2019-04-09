package app.recipes.repositorys.reactive;

import app.recipes.models.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String>
{
    Mono<Category> findByDescription(String description);
}
