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

package org.optaplanner.examples.nurserostering.domain.pattern;

import org.optaplanner.examples.nurserostering.domain.ShiftType;

public class ShiftType3DaysPattern extends Pattern {

    private ShiftType dayIndex0ShiftType;
    private ShiftType dayIndex1ShiftType;
    private ShiftType dayIndex2ShiftType;

    public ShiftType3DaysPattern() {
    }

    public ShiftType3DaysPattern(long id, String code) {
        super(id, code);
    }

    public ShiftType getDayIndex0ShiftType() {
        return dayIndex0ShiftType;
    }

    public void setDayIndex0ShiftType(ShiftType dayIndex0ShiftType) {
        this.dayIndex0ShiftType = dayIndex0ShiftType;
    }

    public ShiftType getDayIndex1ShiftType() {
        return dayIndex1ShiftType;
    }

    public void setDayIndex1ShiftType(ShiftType dayIndex1ShiftType) {
        this.dayIndex1ShiftType = dayIndex1ShiftType;
    }

    public ShiftType getDayIndex2ShiftType() {
        return dayIndex2ShiftType;
    }

    public void setDayIndex2ShiftType(ShiftType dayIndex2ShiftType) {
        this.dayIndex2ShiftType = dayIndex2ShiftType;
    }

    @Override
    public String toString() {
        return "Work pattern: " + dayIndex0ShiftType + ", " + dayIndex1ShiftType + ", " + dayIndex2ShiftType;
    }

}
