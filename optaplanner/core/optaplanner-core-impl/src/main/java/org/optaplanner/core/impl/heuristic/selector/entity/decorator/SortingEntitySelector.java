/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.optaplanner.core.impl.heuristic.selector.entity.decorator;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;

import org.optaplanner.core.config.heuristic.selector.common.SelectionCacheType;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorter;
import org.optaplanner.core.impl.heuristic.selector.entity.EntitySelector;
import org.optaplanner.core.impl.solver.scope.SolverScope;

public final class SortingEntitySelector<Solution_> extends AbstractCachingEntitySelector<Solution_> {

    private final SelectionSorter<Solution_, Object> sorter;

    public SortingEntitySelector(EntitySelector<Solution_> childEntitySelector, SelectionCacheType cacheType,
            SelectionSorter<Solution_, Object> sorter) {
        super(childEntitySelector, cacheType);
        this.sorter = sorter;
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    @Override
    public void constructCache(SolverScope<Solution_> solverScope) {
        super.constructCache(solverScope);
        sorter.sort(solverScope.getScoreDirector(), cachedEntityList);
        logger.trace("    Sorted cachedEntityList: size ({}), entitySelector ({}).",
                cachedEntityList.size(), this);
    }

    @Override
    public boolean isNeverEnding() {
        return false;
    }

    @Override
    public Iterator<Object> iterator() {
        return cachedEntityList.iterator();
    }

    @Override
    public ListIterator<Object> listIterator() {
        return cachedEntityList.listIterator();
    }

    @Override
    public ListIterator<Object> listIterator(int index) {
        return cachedEntityList.listIterator(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        SortingEntitySelector<?> that = (SortingEntitySelector<?>) o;
        return Objects.equals(sorter, that.sorter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sorter);
    }

    @Override
    public String toString() {
        return "Sorting(" + childEntitySelector + ")";
    }

}
