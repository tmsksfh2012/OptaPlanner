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

package org.optaplanner.core.impl.solver.termination;

import java.time.Clock;

import org.optaplanner.core.impl.phase.scope.AbstractPhaseScope;
import org.optaplanner.core.impl.solver.scope.SolverScope;
import org.optaplanner.core.impl.solver.thread.ChildThreadType;

public class UnimprovedTimeMillisSpentTermination<Solution_> extends AbstractTermination<Solution_> {

    private final long unimprovedTimeMillisSpentLimit;

    private final Clock clock;

    public UnimprovedTimeMillisSpentTermination(long unimprovedTimeMillisSpentLimit) {
        this(unimprovedTimeMillisSpentLimit, Clock.systemUTC());
    }

    protected UnimprovedTimeMillisSpentTermination(long unimprovedTimeMillisSpentLimit, Clock clock) {
        this.unimprovedTimeMillisSpentLimit = unimprovedTimeMillisSpentLimit;
        if (unimprovedTimeMillisSpentLimit < 0L) {
            throw new IllegalArgumentException("The unimprovedTimeMillisSpentLimit (" + unimprovedTimeMillisSpentLimit
                    + ") cannot be negative.");
        }
        this.clock = clock;
    }

    public long getUnimprovedTimeMillisSpentLimit() {
        return unimprovedTimeMillisSpentLimit;
    }

    // ************************************************************************
    // Terminated methods
    // ************************************************************************

    @Override
    public boolean isSolverTerminated(SolverScope<Solution_> solverScope) {
        long bestSolutionTimeMillis = solverScope.getBestSolutionTimeMillis();
        return isTerminated(bestSolutionTimeMillis);
    }

    @Override
    public boolean isPhaseTerminated(AbstractPhaseScope<Solution_> phaseScope) {
        long bestSolutionTimeMillis = phaseScope.getPhaseBestSolutionTimeMillis();
        return isTerminated(bestSolutionTimeMillis);
    }

    protected boolean isTerminated(long bestSolutionTimeMillis) {
        long now = clock.millis();
        long unimprovedTimeMillisSpent = now - bestSolutionTimeMillis;
        return unimprovedTimeMillisSpent >= unimprovedTimeMillisSpentLimit;
    }

    // ************************************************************************
    // Time gradient methods
    // ************************************************************************

    @Override
    public double calculateSolverTimeGradient(SolverScope<Solution_> solverScope) {
        long bestSolutionTimeMillis = solverScope.getBestSolutionTimeMillis();
        return calculateTimeGradient(bestSolutionTimeMillis);
    }

    @Override
    public double calculatePhaseTimeGradient(AbstractPhaseScope<Solution_> phaseScope) {
        long bestSolutionTimeMillis = phaseScope.getPhaseBestSolutionTimeMillis();
        return calculateTimeGradient(bestSolutionTimeMillis);
    }

    protected double calculateTimeGradient(long bestSolutionTimeMillis) {
        long now = clock.millis();
        long unimprovedTimeMillisSpent = now - bestSolutionTimeMillis;
        double timeGradient = unimprovedTimeMillisSpent / ((double) unimprovedTimeMillisSpentLimit);
        return Math.min(timeGradient, 1.0);
    }

    // ************************************************************************
    // Other methods
    // ************************************************************************

    @Override
    public UnimprovedTimeMillisSpentTermination<Solution_> createChildThreadTermination(
            SolverScope<Solution_> solverScope, ChildThreadType childThreadType) {
        return new UnimprovedTimeMillisSpentTermination<>(unimprovedTimeMillisSpentLimit);
    }

    @Override
    public String toString() {
        return "UnimprovedTimeMillisSpent(" + unimprovedTimeMillisSpentLimit + ")";
    }
}
