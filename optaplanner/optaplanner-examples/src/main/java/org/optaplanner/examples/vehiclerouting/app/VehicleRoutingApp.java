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

package org.optaplanner.examples.vehiclerouting.app;

import java.util.Collections;
import java.util.Set;

import org.optaplanner.examples.common.app.CommonApp;
import org.optaplanner.examples.common.persistence.AbstractSolutionImporter;
import org.optaplanner.examples.vehiclerouting.domain.VehicleRoutingSolution;
import org.optaplanner.examples.vehiclerouting.persistence.VehicleRoutingImporter;
import org.optaplanner.examples.vehiclerouting.persistence.VehicleRoutingSolutionFileIO;
import org.optaplanner.examples.vehiclerouting.swingui.VehicleRoutingPanel;
import org.optaplanner.persistence.common.api.domain.solution.SolutionFileIO;

public class VehicleRoutingApp extends CommonApp<VehicleRoutingSolution> {

    public static final String SOLVER_CONFIG = "org/optaplanner/examples/vehiclerouting/vehicleRoutingSolverConfig.xml";

    public static final String DATA_DIR_NAME = "vehiclerouting";

    public static void main(String[] args) {
        prepareSwingEnvironment();
        new VehicleRoutingApp().init();
    }

    public VehicleRoutingApp() {
        super("Vehicle routing",
                "Official competition name: Capacitated vehicle routing problem (CVRP), " +
                        "optionally with time windows (CVRPTW)\n\n" +
                        "Pick up all items of all customers with a few vehicles.\n\n" +
                        "Find the shortest route possible.\n" +
                        "Do not overload the capacity of the vehicles.\n" +
                        "Arrive within the time window of each customer.",
                SOLVER_CONFIG, DATA_DIR_NAME,
                VehicleRoutingPanel.LOGO_PATH);
    }

    @Override
    protected VehicleRoutingPanel createSolutionPanel() {
        return new VehicleRoutingPanel();
    }

    @Override
    public SolutionFileIO<VehicleRoutingSolution> createSolutionFileIO() {
        return new VehicleRoutingSolutionFileIO();
    }

    @Override
    protected Set<AbstractSolutionImporter<VehicleRoutingSolution>> createSolutionImporters() {
        return Collections.singleton(new VehicleRoutingImporter());
    }

}
