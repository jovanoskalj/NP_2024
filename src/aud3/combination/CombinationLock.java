package aud3.combination;

public class CombinationLock {
    private int combination;
    private boolean isOpen;
    private static int DEFAULT_COMBINATION = 100;

    private boolean isCombinationValid(int combination) {
        return combination >= 100 && combination <= 999;
    }

    public boolean open(int combination) {
        this.isOpen = (combination == this.combination);
        return this.isOpen;
    }

    public boolean changeCombination(int oldCombination, int newCombination) {
        if (open(oldCombination) && isCombinationValid(newCombination)) {
            this.combination = newCombination;
            return true;
        }
        return false;
    }

    public void lock() {
        this.isOpen = false;

    }

    public boolean isOpen() {
        return isOpen;
    }

    public CombinationLock(int combination) throws InvalidCombinationException {
        if (isCombinationValid(combination)) {
            this.combination = combination;
        } else {
//            throw new InvalidCombinationException();
            this.combination = DEFAULT_COMBINATION;
        }

    }

    public static void main(String[] args) throws InvalidCombinationException {
        CombinationLock validLock = new CombinationLock(234);

        System.out.println("TEST IS OPEN");
        System.out.println(validLock.isOpen());

        System.out.println("TEST OPEN");
        System.out.println(validLock.open(233));
        System.out.println(validLock.open(236));
        System.out.println(validLock.open(234));

        validLock.lock();

        System.out.println("TEST CHANGE COMBINATION");
        System.out.println(validLock.changeCombination(234, 777));
        System.out.println(validLock.open(777));

        CombinationLock invalidLock = new CombinationLock(234567);

        System.out.println("TEST IS OPEN");
        System.out.println(invalidLock.isOpen());

        System.out.println("TEST OPEN");
        System.out.println(invalidLock.open(103));
        System.out.println(invalidLock.open(236));
        System.out.println(invalidLock.open(100));

        invalidLock.lock();

        System.out.println("TEST CHANGE COMBINATION");
        System.out.println(invalidLock.changeCombination(100, 900));
        System.out.println(invalidLock.open(900));
    }
}
