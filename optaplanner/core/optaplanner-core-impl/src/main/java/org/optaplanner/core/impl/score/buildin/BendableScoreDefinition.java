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

package org.optaplanner.core.impl.score.buildin;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.optaplanner.core.api.score.buildin.bendable.BendableScore;
import org.optaplanner.core.config.score.trend.InitializingScoreTrendLevel;
import org.optaplanner.core.impl.score.definition.AbstractBendableScoreDefinition;
import org.optaplanner.core.impl.score.trend.InitializingScoreTrend;

public class BendableScoreDefinition extends AbstractBendableScoreDefinition<BendableScore> {

    public BendableScoreDefinition(int hardLevelsSize, int softLevelsSize) {
        super(hardLevelsSize, softLevelsSize);
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    @Override
    public Class<BendableScore> getScoreClass() {
        return BendableScore.class;
    }

    @Override
    public BendableScore getZeroScore() {
        return BendableScore.zero(hardLevelsSize, softLevelsSize);
    }

    @Override
    public final BendableScore getOneSoftestScore() {
        return BendableScore.ofSoft(hardLevelsSize, softLevelsSize, softLevelsSize - 1, 1);
    }

    @Override
    public BendableScore parseScore(String scoreString) {
        BendableScore score = BendableScore.parseScore(scoreString);
        if (score.hardLevelsSize() != hardLevelsSize) {
            throw new IllegalArgumentException("The scoreString (" + scoreString
                    + ") for the scoreClass (" + BendableScore.class.getSimpleName()
                    + ") doesn't follow the correct pattern:"
                    + " the hardLevelsSize (" + score.hardLevelsSize()
                    + ") doesn't match the scoreDefinition's hardLevelsSize (" + hardLevelsSize + ").");
        }
        if (score.softLevelsSize() != softLevelsSize) {
            throw new IllegalArgumentException("The scoreString (" + scoreString
                    + ") for the scoreClass (" + BendableScore.class.getSimpleName()
                    + ") doesn't follow the correct pattern:"
                    + " the softLevelsSize (" + score.softLevelsSize()
                    + ") doesn't match the scoreDefinition's softLevelsSize (" + softLevelsSize + ").");
        }
        return score;
    }

    @Override
    public BendableScore fromLevelNumbers(int initScore, Number[] levelNumbers) {
        if (levelNumbers.length != getLevelsSize()) {
            throw new IllegalStateException("The levelNumbers (" + Arrays.toString(levelNumbers)
                    + ")'s length (" + levelNumbers.length + ") must equal the levelSize (" + getLevelsSize() + ").");
        }
        int[] hardScores = new int[hardLevelsSize];
        for (int i = 0; i < hardLevelsSize; i++) {
            hardScores[i] = (Integer) levelNumbers[i];
        }
        int[] softScores = new int[softLevelsSize];
        for (int i = 0; i < softLevelsSize; i++) {
            softScores[i] = (Integer) levelNumbers[hardLevelsSize + i];
        }
        return BendableScore.ofUninitialized(initScore, hardScores, softScores);
    }

    public BendableScore createScore(int... scores) {
        return createScoreUninitialized(0, scores);
    }

    public BendableScore createScoreUninitialized(int initScore, int... scores) {
        int levelsSize = hardLevelsSize + softLevelsSize;
        if (scores.length != levelsSize) {
            throw new IllegalArgumentException("The scores (" + Arrays.toString(scores)
                    + ")'s length (" + scores.length
                    + ") is not levelsSize (" + levelsSize + ").");
        }
        return BendableScore.ofUninitialized(initScore,
                Arrays.copyOfRange(scores, 0, hardLevelsSize),
                Arrays.copyOfRange(scores, hardLevelsSize, levelsSize));
    }

    @Override
    public BendableScore buildOptimisticBound(InitializingScoreTrend initializingScoreTrend, BendableScore score) {
        InitializingScoreTrendLevel[] trendLevels = initializingScoreTrend.getTrendLevels();
        int[] hardScores = new int[hardLevelsSize];
        for (int i = 0; i < hardLevelsSize; i++) {
            hardScores[i] = (trendLevels[i] == InitializingScoreTrendLevel.ONLY_DOWN)
                    ? score.hardScore(i)
                    : Integer.MAX_VALUE;
        }
        int[] softScores = new int[softLevelsSize];
        for (int i = 0; i < softLevelsSize; i++) {
            softScores[i] = (trendLevels[hardLevelsSize + i] == InitializingScoreTrendLevel.ONLY_DOWN)
                    ? score.softScore(i)
                    : Integer.MAX_VALUE;
        }
        return BendableScore.ofUninitialized(0, hardScores, softScores);
    }

    @Override
    public BendableScore buildPessimisticBound(InitializingScoreTrend initializingScoreTrend, BendableScore score) {
        InitializingScoreTrendLevel[] trendLevels = initializingScoreTrend.getTrendLevels();
        int[] hardScores = new int[hardLevelsSize];
        for (int i = 0; i < hardLevelsSize; i++) {
            hardScores[i] = (trendLevels[i] == InitializingScoreTrendLevel.ONLY_UP)
                    ? score.hardScore(i)
                    : Integer.MIN_VALUE;
        }
        int[] softScores = new int[softLevelsSize];
        for (int i = 0; i < softLevelsSize; i++) {
            softScores[i] = (trendLevels[hardLevelsSize + i] == InitializingScoreTrendLevel.ONLY_UP)
                    ? score.softScore(i)
                    : Integer.MIN_VALUE;
        }
        return BendableScore.ofUninitialized(0, hardScores, softScores);
    }

    @Override
    public BendableScore divideBySanitizedDivisor(BendableScore dividend, BendableScore divisor) {
        int dividendInitScore = dividend.initScore();
        int divisorInitScore = sanitize(divisor.initScore());
        int[] hardScores = new int[hardLevelsSize];
        for (int i = 0; i < hardLevelsSize; i++) {
            hardScores[i] = divide(dividend.hardScore(i), sanitize(divisor.hardScore(i)));
        }
        int[] softScores = new int[softLevelsSize];
        for (int i = 0; i < softLevelsSize; i++) {
            softScores[i] = divide(dividend.softScore(i), sanitize(divisor.softScore(i)));
        }
        int[] levels = IntStream.concat(Arrays.stream(hardScores), Arrays.stream(softScores)).toArray();
        return createScoreUninitialized(divide(dividendInitScore, divisorInitScore), levels);
    }

    @Override
    public Class<?> getNumericType() {
        return int.class;
    }
}
