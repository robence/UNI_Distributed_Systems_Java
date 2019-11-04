package com.parallel;

public class GoatRunnable implements Runnable {
    private final GoatEntity goat;

    GoatRunnable(GoatEntity goat) {
        this.goat = goat;
    }

    @Override
    public void run() {
        System.out.println("Hello there");

        while (!isGameOver()) {
            double nextStepIn = ((Math.random() * 4) + 1) / 2; // every 0.5 - 2.0 seconds
            try {
                Thread.sleep((long) nextStepIn * 1000);
                step();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void step() {
        synchronized (this.goat) {
            System.out.println("#" + this.goat.getId() + " --- STEP START ---");
            if (hasGoatNearby()) {
                moveOpponentGoatBackward();
            }
            moveGoatForward();
            System.out.println("#" + this.goat.getId() + " --- STEP END ---");
        }
    }

    private void moveGoatForward() {
        this.goat.getOpponent().setCurrentPosition(this.goat.getCurrentPosition() - 1);
        System.out.println("#"
                + this.goat.getId()
                + ": Goat moved forward, currently at: "
                + this.goat.getCurrentPosition()
        );

    }

    private void moveOpponentGoatBackward() {
        this.goat.setCurrentPosition(this.goat.getCurrentPosition() + 1);
        System.out.println("#"
                + this.goat.getId()
                + ": Opponent goat moved backwards, currently at: "
                + this.goat.getOpponent().getCurrentPosition()
        );
    }

    private Direction getDirection() {
        return this.goat.getCurrentPosition() < this.goat.getTargetPosition() ? Direction.FORWARD : Direction.BACKWARD;
    }

    private boolean hasGoatNearby() {
        return this.goat.getCurrentPosition() + getDirectionValue(getDirection()) ==
                this.goat.getOpponent().getCurrentPosition();
    }

    private boolean isGameOver() {
        return !canGoatMoveForward() || isGoatPushedOver();
    }

    private boolean isGoatPushedOver() {
        return getDirection() == Direction.FORWARD
                ? this.goat.getCurrentPosition() < this.goat.getStartPosition()
                : this.goat.getCurrentPosition() > this.goat.getStartPosition();
    }

    private boolean canGoatMoveForward() {
        return this.goat.getCurrentPosition() + getDirectionValue(getDirection()) != this.goat.getTargetPosition();
    }

    private int getDirectionValue(Direction d) {
        return d == Direction.FORWARD ? 1 : -1;
    }
}
