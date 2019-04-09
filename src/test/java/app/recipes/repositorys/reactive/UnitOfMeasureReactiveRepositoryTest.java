package app.recipes.repositorys.reactive;

import app.recipes.models.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest
{
    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;


    @Before
    public void setUp() throws Exception
    {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSaveUOM() throws Exception
    {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("Each");

        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        Long count = unitOfMeasureReactiveRepository.count().block();

        assertEquals(Long.valueOf(1L), count);
    }

    @Test
    public void testFindByDescription() throws Exception
    {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("Each");

        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        UnitOfMeasure foundUnitOfMeasure = unitOfMeasureReactiveRepository.findByDescription("Each").block();

        assertNotNull(foundUnitOfMeasure);
    }
}