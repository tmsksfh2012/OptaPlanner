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

package org.optaplanner.core.impl.domain.variable.anchor;

import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.variable.VariableListener;
import org.optaplanner.core.api.score.director.ScoreDirector;
import org.optaplanner.core.impl.domain.variable.descriptor.VariableDescriptor;
import org.optaplanner.core.impl.domain.variable.inverserelation.SingletonInverseVariableSupply;
import org.optaplanner.core.impl.score.director.InnerScoreDirector;

/**
 * @param <Solution_> the solution type, the class with the {@link PlanningSolution} annotation
 */
public class AnchorVariableListener<Solution_> implements VariableListener<Solution_, Object>, AnchorVariableSupply {

    protected final AnchorShadowVariableDescriptor<Solution_> anchorShadowVariableDescriptor;
    protected final VariableDescriptor<Solution_> previousVariableDescriptor;
    protected final SingletonInverseVariableSupply nextVariableSupply;

    public AnchorVariableListener(AnchorShadowVariableDescriptor<Solution_> anchorShadowVariableDescriptor,
            VariableDescriptor<Solution_> previousVariableDescriptor,
            SingletonInverseVariableSupply nextVariableSupply) {
        this.anchorShadowVariableDescriptor = anchorShadowVariableDescriptor;
        this.previousVariableDescriptor = previousVariableDescriptor;
        this.nextVariableSupply = nextVariableSupply;
    }

    @Override
    public void beforeEntityAdded(ScoreDirector<Solution_> scoreDirector, Object entity) {
        // Do nothing
    }

    @Override
    public void afterEntityAdded(ScoreDirector<Solution_> scoreDirector, Object entity) {
        insert((InnerScoreDirector<Solution_, ?>) scoreDirector, entity);
    }

    @Override
    public void beforeVariableChanged(ScoreDirector<Solution_> scoreDirector, Object entity) {
        // No need to retract() because the insert (which is guaranteed to be called later) affects the same trailing entities.
    }

    @Override
    public void afterVariableChanged(ScoreDirector<Solution_> scoreDirector, Object entity) {
        insert((InnerScoreDirector<Solution_, ?>) scoreDirector, entity);
    }

    @Override
    public void beforeEntityRemoved(ScoreDirector<Solution_> scoreDirector, Object entity) {
        // No need to retract() because the trailing entities will be removed too or change their previousVariable
    }

    @Override
    public void afterEntityRemoved(ScoreDirector<Solution_> scoreDirector, Object entity) {
        // Do nothing
    }

    protected void insert(InnerScoreDirector<Solution_, ?> scoreDirector, Object entity) {
        Object previousEntity = previousVariableDescriptor.getValue(entity);
        Object anchor;
        if (previousEntity == null) {
            anchor = null;
        } else if (previousVariableDescriptor.isValuePotentialAnchor(previousEntity)) {
            anchor = previousEntity;
        } else {
            anchor = anchorShadowVariableDescriptor.getValue(previousEntity);
        }
        Object nextEntity = entity;
        while (nextEntity != null && anchorShadowVariableDescriptor.getValue(nextEntity) != anchor) {
            scoreDirector.beforeVariableChanged(anchorShadowVariableDescriptor, nextEntity);
            anchorShadowVariableDescriptor.setValue(nextEntity, anchor);
            scoreDirector.afterVariableChanged(anchorShadowVariableDescriptor, nextEntity);
            nextEntity = nextVariableSupply.getInverseSingleton(nextEntity);
        }
    }

    @Override
    public Object getAnchor(Object entity) {
        return anchorShadowVariableDescriptor.getValue(entity);
    }

}
