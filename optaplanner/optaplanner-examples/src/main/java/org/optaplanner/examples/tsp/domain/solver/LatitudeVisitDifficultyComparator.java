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

package org.optaplanner.examples.tsp.domain.solver;

import java.util.Comparator;

import org.optaplanner.examples.tsp.domain.Visit;

public class LatitudeVisitDifficultyComparator implements Comparator<Visit> {

    // TODO experiment with (aLatitude - bLatitude) % 10
    private static final Comparator<Visit> COMPARATOR = Comparator
            .comparingDouble((Visit visit) -> visit.getLocation().getLatitude())
            .thenComparingDouble(visit -> visit.getLocation().getLongitude())
            .thenComparingLong(Visit::getId);

    @Override
    public int compare(Visit a, Visit b) {
        return COMPARATOR.compare(a, b);
    }
}
