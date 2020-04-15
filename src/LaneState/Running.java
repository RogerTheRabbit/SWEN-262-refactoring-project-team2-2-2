package LaneState;

import temp.*;

import java.util.Date;

import static java.lang.Thread.sleep;

public class Running implements LaneStatus {
    private Lane lane;

    public Running(Lane lane) {
        this.lane = lane;
    }

    @Override
    public void run() {
        if (bowlerIterator.hasNext()) {
            currentThrower = bowlerIterator.next();

            canThrowAgain = true;
            tenthFrameStrike = false;
            ball = 0;
            while (canThrowAgain) {
                setter.ballThrown(); // simulate the thrower's ball hitting
                ball++;
            }

            if (lane.frameNumber == 9) {
                finalScores[bowlIndex][gameNumber] = cumuliScores[bowlIndex][9];

                Date date = new Date();
                String dateString = "" + date.getHours() + ":" + date.getMinutes() + " " + date.getMonth()
                        + "/" + date.getDay() + "/" + (date.getYear() + 1900);
                Score score = new Score(currentThrower.getNickName(), dateString,
                        Integer.toString(cumuliScores[bowlIndex][9]));
                score.addScoreToFile();

            }

            setter.reset();
            bowlIndex++;

        } else {
            frameNumber++;
            resetBowlerIterator();
            bowlIndex = 0;
            if (frameNumber > 9) {
                gameFinished = true;
                gameNumber++;
            }
        }
    }

    @Override
    public void receivePinsetterEvent() {
        if (pinsetterEvent.pinsDownOnThisThrow() >= 0) { // this is a real throw
            markScore(currentThrower, frameNumber + 1, pinsetterEvent.getThrowNumber(), pinsetterEvent.pinsDownOnThisThrow());

            // next logic handles the ?: what conditions dont allow them another throw?
            // handle the case of 10th frame first
            if (frameNumber == 9) {
                if (pinsetterEvent.totalPinsDown() == 10) {
                    setter.resetPins();
                    if (pinsetterEvent.getThrowNumber() == 1) {
                        tenthFrameStrike = true;
                    }
                }

                if ((pinsetterEvent.totalPinsDown() != 10) && (pinsetterEvent.getThrowNumber() == 2 && !tenthFrameStrike)) {
                    canThrowAgain = false;
                    // publish( lanePublish() );
                }

                if (pinsetterEvent.getThrowNumber() == 3) {
                    canThrowAgain = false;
                    // publish( lanePublish() );
                }
            } else { // its not the 10th frame

                if (pinsetterEvent.pinsDownOnThisThrow() == 10) { // threw a strike
                    canThrowAgain = false;
                    // publish( lanePublish() );
                } else if (pinsetterEvent.getThrowNumber() == 2) {
                    canThrowAgain = false;
                    // publish( lanePublish() );
                } else if (pinsetterEvent.getThrowNumber() == 3)
                    System.out.println("I'm here...");
            }
        }
    }

    @Override
    public void assignParty(Party theParty) {

    }

    @Override
    public boolean isPartyAssigned() {
        return true; // Can only be running if part is assigned.
    }

    @Override
    public void subscribe(LaneObserver adding) {

    }

    @Override
    public void publish(LaneEvent event) {

    }

    @Override
    public Pinsetter getPinsetter() {
        return null;
    }

    @Override
    public void maintenanceCallToggle() {
        lane.setStatus(new Maintenance(lane));
    }

    @Override
    public boolean isGameFinished() {
        return false;
    }

    /**
     * markScore()
     * <p>
     * Method that marks a bowlers score on the board.
     *
     * @param currentBowler The current bowler
     * @param frame         The frame that bowler is on
     * @param ball          The ball the bowler is on
     * @param score         The bowler's score
     */
    private void markScore(Bowler currentBowler, int frame, int ball, int score) {
        int[] curScore;
        int index = ((frame - 1) * 2 + ball);

        curScore = lane.scores.get(currentBowler);

        curScore[index - 1] = score;
        lane.scores.put(currentBowler, curScore);
        getScore(currentBowler, frame);
        publish(lane.lanePublish());
    }

    /**
     * Method that calculates a bowlers score
     *
     * @param currentBowler The bowler that is currently up
     * @param frame         The frame the current bowler is on
     */
    private void getScore(Bowler currentBowler, int frame) {
        int[] curScore;
        int strikeballs;
        curScore = scores.get(currentBowler);
        for (int i = 0; i != 10; i++) {
            cumuliScores[bowlIndex][i] = 0;
        }
        int current = 2 * (frame - 1) + ball - 1;
        // Iterate through each ball until the current one.
        for (int i = 0; i != current + 2; i++) {
            // Spare:
            if (i % 2 == 1 && curScore[i - 1] + curScore[i] == 10 && i < current - 1 && i < 19) {
                // This ball was a the second of a spare.
                // Also, we're not on the current ball.
                // Add the next ball to the ith one in cumul.
                cumuliScores[bowlIndex][(i / 2)] += curScore[i + 1] + curScore[i];
            } else if (i < current && i % 2 == 0 && curScore[i] == 10 && i < 18) {
                strikeballs = 0;
                // This ball is the first ball, and was a strike.
                // If we can get 2 balls after it, good add them to cumul.
                if (curScore[i + 2] != -1) {
                    strikeballs = 1;
                    if (curScore[i + 3] != -1) {
                        // Still got em.
                        strikeballs = 2;
                    } else if (curScore[i + 4] != -1) {
                        // Ok, got it.
                        strikeballs = 2;
                    }
                }

                if (strikeballs == 2) {
                    // Add up the strike.
                    // Add the next two balls to the current cumulScore.
                    cumuliScores[bowlIndex][i / 2] += 10;
                    if (curScore[i + 1] != -1) {
                        cumuliScores[bowlIndex][i / 2] += curScore[i + 1] + cumuliScores[bowlIndex][(i / 2) - 1];
                        if (curScore[i + 2] != -1) {
                            if (curScore[i + 2] != -2) {
                                cumuliScores[bowlIndex][(i / 2)] += curScore[i + 2];
                            }
                        } else {
                            if (curScore[i + 3] != -2) {
                                cumuliScores[bowlIndex][(i / 2)] += curScore[i + 3];
                            }
                        }
                    } else {
                        if (i / 2 > 0) {
                            cumuliScores[bowlIndex][i / 2] += curScore[i + 2] + cumuliScores[bowlIndex][(i / 2) - 1];
                        } else {
                            cumuliScores[bowlIndex][i / 2] += curScore[i + 2];
                        }

                        if (curScore[i + 3] != -1) {
                            if (curScore[i + 3] != -2) {
                                cumuliScores[bowlIndex][(i / 2)] += curScore[i + 3];
                            }
                        } else {
                            cumuliScores[bowlIndex][(i / 2)] += curScore[i + 4];
                        }
                    }
                } else {
                    break;
                }
            } else {
                // We're dealing with a normal throw, add it and be on our way.
                if (i % 2 == 0 && i < 18) {

                    if (i / 2 == 0) {
                        // First frame, first ball. Set his cumul score to the first ball
                        if (curScore[i] != -2) {
                            cumuliScores[bowlIndex][i / 2] += curScore[i];
                        }
                    } else {
                        // add his last frame's cumul to this ball, make it this frame's cumul.
                        if (curScore[i] != -2) {
                            cumuliScores[bowlIndex][i / 2] += cumuliScores[bowlIndex][i / 2 - 1] + curScore[i];
                        } else {
                            cumuliScores[bowlIndex][i / 2] += cumuliScores[bowlIndex][i / 2 - 1];
                        }
                    }

                } else if (i < 18) {
                    if (curScore[i] != -1 && i > 2) {
                        if (curScore[i] != -2) {
                            cumuliScores[bowlIndex][i / 2] += curScore[i];
                        }
                    }
                }

                if (i / 2 == 9) {
                    if (i == 18) {
                        cumuliScores[bowlIndex][9] += cumuliScores[bowlIndex][8];
                    }
                    if (curScore[i] != -2) {
                        cumuliScores[bowlIndex][9] += curScore[i];
                    }
                } else if (i / 2 == 10) {
                    if (curScore[i] != -2) {
                        cumuliScores[bowlIndex][9] += curScore[i];
                    }
                }
            }
        }
    }
}
