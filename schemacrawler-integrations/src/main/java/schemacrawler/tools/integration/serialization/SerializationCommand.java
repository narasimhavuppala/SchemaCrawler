/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2018, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/

package schemacrawler.tools.integration.serialization;


import static java.nio.file.Files.newOutputStream;

import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import schemacrawler.tools.executable.BaseSchemaCrawlerCommand;
import sf.util.SchemaCrawlerLogger;

/**
 * Main executor for the graphing integration.
 *
 * @author Sualeh Fatehi
 */
public final class SerializationCommand
  extends BaseSchemaCrawlerCommand
{

  private static final SchemaCrawlerLogger LOGGER = SchemaCrawlerLogger
    .getLogger(SerializationCommand.class.getName());

  static final String COMMAND = "serialize";

  public SerializationCommand()
  {
    this(COMMAND);
  }

  public SerializationCommand(final String command)
  {
    super(command);
  }

  @Override
  public void checkAvailibility()
    throws Exception
  {
    // Nothing additional to check at this point. The Command should be
    // available after the class is loaded, and imports are resolved.
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute()
    throws Exception
  {
    checkCatalog();

    final Path outputFile = outputOptions.getOutputFile()
      .orElseGet(() -> Paths
        .get(".",
             String.format("schemacrawler-%s.%s", UUID.randomUUID(), "data")))
      .normalize().toAbsolutePath();

    final SerializableCatalog serializableCatalog = new XmlSerializedCatalog(catalog);
    try (final OutputStream out = newOutputStream(outputFile);)
    {
      serializableCatalog.save(out);
    }
  }

  @Override
  public boolean usesConnection()
  {
    return false;
  }

}
