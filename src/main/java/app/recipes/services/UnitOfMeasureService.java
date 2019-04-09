package app.recipes.services;

import app.recipes.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureService
{
    Flux<UnitOfMeasureCommand> listAllUoms();
}
