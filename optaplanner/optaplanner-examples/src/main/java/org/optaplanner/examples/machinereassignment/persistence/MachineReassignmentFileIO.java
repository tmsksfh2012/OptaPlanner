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

package org.optaplanner.examples.machinereassignment.persistence;

import java.io.File;

import org.optaplanner.examples.machinereassignment.domain.MachineReassignment;
import org.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;

public class MachineReassignmentFileIO implements SolutionFileIO<MachineReassignment> {

    private MachineReassignmentImporter importer = new MachineReassignmentImporter();
    private MachineReassignmentExporter exporter = new MachineReassignmentExporter();

    @Override
    public String getInputFileExtension() {
        return "txt";
    }

    @Override
    public MachineReassignment read(File inputSolutionFile) {
        return importer.readSolution(inputSolutionFile);
    }

    @Override
    public void write(MachineReassignment solution, File outputSolutionFile) {
        exporter.writeSolution(solution, outputSolutionFile);
    }

}
