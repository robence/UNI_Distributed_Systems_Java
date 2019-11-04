package com.parallel;

class GoatEntity {
    private final int id;
    private int currentPosition;
    private final int targetPosition;
    private final int startPosition;
    private GoatEntity opponent;

    public GoatEntity(int id, int targetPosition, int startPosition) {
        this.id = id;
        this.targetPosition = targetPosition;
        this.currentPosition = startPosition;
        this.startPosition = startPosition;
    }

    int getCurrentPosition() {
        return currentPosition;
    }

    void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    GoatEntity getOpponent() {
        return opponent;
    }

    public void setOpponent(GoatEntity opponent) {
        this.opponent = opponent;
    }

    public int getId() {
        return id;
    }

    int getTargetPosition() {
        return targetPosition;
    }

    int getStartPosition() {
        return startPosition;
    }
}
