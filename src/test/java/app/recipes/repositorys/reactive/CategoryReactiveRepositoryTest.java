package app.recipes.repositorys.reactive;

import app.recipes.models.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest
{
    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() throws Exception
    {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSave() throws Exception
    {
        Category category = new Category();
        category.setDescription("Foo");

        categoryReactiveRepository.save(category).block();

        Long count = categoryReactiveRepository.count().block();

        assertEquals(Long.valueOf(1L), count);
    }

    @Test
    public void findByDescription() throws Exception
    {
        Category category = new Category();
        category.setDescription("Foo");

        categoryReactiveRepository.save(category).block();

        Category foundCategory = categoryReactiveRepository.findByDescription("Foo").block();

        assertNotNull(foundCategory);
    }
}