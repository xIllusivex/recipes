package app.recipes.services;

import app.recipes.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService
{
    Set<UnitOfMeasureCommand> listAllUoms();
}
