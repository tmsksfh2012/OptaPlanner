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

package org.optaplanner.persistence.xstream.api.score.buildin.simplebigdecimal;

import org.optaplanner.core.api.score.buildin.simplebigdecimal.SimpleBigDecimalScore;
import org.optaplanner.persistence.xstream.api.score.AbstractScoreXStreamConverter;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @deprecated Prefer JAXB for serialization into XML.
 */
@Deprecated(forRemoval = true)
public class SimpleBigDecimalScoreXStreamConverter extends AbstractScoreXStreamConverter {

    @Override
    public boolean canConvert(Class type) {
        return SimpleBigDecimalScore.class.isAssignableFrom(type);
    }

    @Override
    public void marshal(Object scoreObject, HierarchicalStreamWriter writer, MarshallingContext context) {
        SimpleBigDecimalScore score = (SimpleBigDecimalScore) scoreObject;
        writer.setValue(score.toString());
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String scoreString = reader.getValue();
        return SimpleBigDecimalScore.parseScore(scoreString);
    }

}
