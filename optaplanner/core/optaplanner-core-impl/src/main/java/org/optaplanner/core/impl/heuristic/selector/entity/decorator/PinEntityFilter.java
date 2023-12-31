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

import java.util.Objects;

import org.optaplanner.core.api.domain.entity.PlanningPin;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.score.director.ScoreDirector;
import org.optaplanner.core.impl.domain.common.accessor.MemberAccessor;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionFilter;

/**
 * Filters out entities that return true for the {@link PlanningPin} annotated boolean member.
 *
 * @param <Solution_> the solution type, the class with the {@link PlanningSolution} annotation
 */
public class PinEntityFilter<Solution_> implements SelectionFilter<Solution_, Object> {

    private final MemberAccessor memberAccessor;

    public PinEntityFilter(MemberAccessor memberAccessor) {
        this.memberAccessor = memberAccessor;
    }

    @Override
    public boolean accept(ScoreDirector<Solution_> scoreDirector, Object entity) {
        Boolean pinned = (Boolean) memberAccessor.executeGetter(entity);
        if (pinned == null) {
            throw new IllegalStateException("The entity (" + entity + ") has a @" + PlanningPin.class.getSimpleName()
                    + " annotated property (" + memberAccessor.getName() + ") that returns null.");
        }
        return !pinned;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        PinEntityFilter<?> that = (PinEntityFilter<?>) other;
        return Objects.equals(memberAccessor, that.memberAccessor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberAccessor);
    }
}
