/*
 * This file is part of GumTree.
 *
 * GumTree is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GumTree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GumTree.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2011-2015 Jean-Rémy Falleri <jr.falleri@gmail.com>
 * Copyright 2011-2015 Floréal Morandat <florealm@gmail.com>
 */

package chibi.gumtreediff.client;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

@Register(description = "List things (matchers, generators, properties, ...)")
public class List extends Client {

    public static final String SYNTAX = "Syntax: list " + Arrays.toString(Listable.values());
    private final Listable item;

    public List(String[] args) {
        super(args);

        if (args.length == 0)
            throw new Option.OptionException(SYNTAX);

        Listable listable = Listable.valueOf(args[0].toUpperCase());
        if (listable == null)
            throw new Option.OptionException(SYNTAX);
        item = listable;
    }

    @Override
    public void run() throws IOException {
        item.print(System.out);
    }

    enum Listable {
        MATCHERS {
            @Override
            Collection<?> list() {
//                return MatcherFactories.listFactories().stream().map(
// Class::getEnclosingClass).map(Class::getName).collect(Collectors.toList());
                return null;
            }
        },
        GENERATORS {
            @Override
            Collection<?> list() {
                return null; //Generators.getInstance().listGenerators();
            }
        },
        PROPERTIES {
            @Override
            Collection<?> list() {
                return Arrays.asList(properties);
            }
        },
        CLIENTS {
            @Override
            Collection<?> list() {
                return null; // Run.clients.keySet();
            }
        };

        void print(PrintStream out) {
            this.list().forEach(item -> out.println(item));
        }

        abstract Collection<?> list();
    }

    // This list is generated using (manually) list_properties (it is only an heuristic), some properties may be missing
    public static final String[] properties = new String[] {
            "gumtree.client.experimental (chibi.gumtreediff.client.Run)",
            "gumtree.client.web.port (chibi.gumtreediff.client.diff.WebDiff)",
            "gumtree.generator.experimental (chibi.gumtreediff.gen.TreeGeneratorRegistry)",
            "line.separator (chibi.gumtreediff.io.IndentingXMLStreamWriter)",
            "user.dir (chibi.gumtreediff.client.diff.AbstractDiffClient)"
    };
}
