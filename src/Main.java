import java.io.Serializable;

public class Main {


    public static void main(String[] args) {
        SingletonOne one = SingletonOne.INSTANCE;
        SingletonTwo two = SingletonTwo.getInstance();
        SingletonTwoWithSerializable twoWithSerializable = SingletonTwoWithSerializable.getInstance();
        SingletonThree three = SingletonThree.INSTANCE; // three.doSmth();
        SingletonFour four = SingletonFour.getInstance();
        SingletonFive five = SingletonFive.getInstance();
        SingletonSix six = SingletonSix.getInstance();
        SingletonSeven seven = SingletonSeven.getInstance();
    }

}

/**
 * Singleton with final field.
 * Advantages: simple, clear singleton
 * Troubles: we can run constructor using AccessibleObject.setAccessible.
 * Solution: deny creating second and more instances in constructor.
 *
 * @author Yauheni_Skarakhodau
 */

class SingletonOne {

    public static final SingletonOne INSTANCE = new SingletonOne();

    /*
    also:
      static {
        INSTANCE = new SingletonOne();
      }
    */

    private SingletonOne() {

    }

}

/**
 * Singleton with static factory.
 * Advantages: we can cancel singleton without changing API (getInstance() -> newInstance() for each thread).
 * we can write own generic singleton factory.
 * Supplier<SingletonTwo> singletonTwoSupplier = SingletonTwo::getInstance;
 * Troubles: it's not enough to add implements Serializable for serializable, we should declare all instance fields
 * transient and provide a readResolve method.
 *
 * @author Yauheni_Skarakhodau
 */

class SingletonTwo {

    private static final SingletonTwo INSTANCE = new SingletonTwo();

    private SingletonTwo() {

    }

    public static SingletonTwo getInstance() {
        return INSTANCE;
    }

}

/**
 * Resolved serialization problem.
 *
 * @author Yauheni_Skarakhodau
 */

class SingletonTwoWithSerializable implements Serializable {

    private transient int a = 4;

    private static final SingletonTwoWithSerializable INSTANCE = new SingletonTwoWithSerializable();



    private SingletonTwoWithSerializable() {

    }

    public static SingletonTwoWithSerializable getInstance() {
        return INSTANCE;
    }

    private Object readResolve() {

        return INSTANCE;
    }


}

/**
 * Like a first solution, but stabler, more concise and provides serialization.
 *
 * @author Yauheni_Skarakhodau
 */

enum SingletonThree {

    INSTANCE;

    void doSmth() {

    }
}

/**
 * Lazy non-synchronized singleton.
 * In lazy singleton instance is initialized as needed.
 *
 * @author Yauheni_Skarakhodau
 */

class SingletonFour {

    private static SingletonFour instance;

    private SingletonFour() {

    }

    public static SingletonFour getInstance() {
        if (instance == null) {
            instance = new SingletonFour();
        }

        return instance;
    }
}

/**
 * Lazy synchronized singleton.
 *
 * @author Yauheni_Skarakhodau
 */

class SingletonFive {

    private static volatile SingletonFive instance;

    private SingletonFive() {

    }

    public static synchronized SingletonFive getInstance() {
        if (instance == null) {
            instance = new SingletonFive();
        }

        return instance;
    }

}

/**
 * Double-check idiom for lazy initialization of instance fields.
 *
 *
 * @author Yauheni_Skarakhodau
 */

class SingletonSix {
    private static volatile SingletonSix instance;

    private SingletonSix(){

    }

    public static SingletonSix getInstance(){
        SingletonSix tempInstance = instance;
        if (tempInstance == null){
            synchronized (SingletonSix.class){
                if (tempInstance == null){
                    tempInstance = instance = new SingletonSix();
                }
            }
        }

        return instance;
    }

}

/**
 * Single-check idiom.
 * Possible troubles: in multithreading instance can be reinitialized.
 *
 * @author Yauheni_Skarakhodau
 */

class SingletonSeven{

    private static volatile SingletonSeven field;

    private SingletonSeven(){

    }

    public static SingletonSeven getInstance() {
        SingletonSeven result = field;

        if (result == null) {
            field = result = new SingletonSeven();
        }

        return result;
    }

}