import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

enum Direction {
    UP, DOWN
}

enum State {
    MOVING, STOPPED
}

enum Door {
    OPEN, CLOSED
}
enum Location {
    Internal,
    External
}
class Request{
    private int currentFLoor;
    private Direction direction;
    private int desiredFloor;
    private Location location;

    public long getTime() {
        return time;
    }

    private long time;
    public Request(int currentFLoor, Direction direction, int desiredFloor, Location location) {
        this.currentFLoor = currentFLoor;
        this.direction = direction;
        this.desiredFloor = desiredFloor;
        this.location = location;
        this.time = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "Request{" +
                "currentFLoor=" + currentFLoor +
                ", direction=" + direction +
                ", desiredFloor=" + desiredFloor +
                ", location=" + location +
                '}';
    }

    public int getCurrentFLoor() {
        return currentFLoor;
    }

    public void setCurrentFLoor(int currentFLoor) {
        this.currentFLoor = currentFLoor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getDesiredFloor() {
        return desiredFloor;
    }

    public void setDesiredFloor(int desiredFloor) {
        this.desiredFloor = desiredFloor;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
class ElevatorCar {
    private Door door;
    private Direction direction;
    private State state;
    private int currentFloor;

    public ElevatorCar() {
        this.door = Door.CLOSED;
        this.direction = Direction.UP;
        this.state = State.STOPPED;
        this.currentFloor = 0;
    }

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }
}
class Schedular extends PriorityQueue<Request>{
    ElevatorCar elevatorCar;
    Queue<Request> upQueue = new PriorityQueue<>((a,b) -> a.getDesiredFloor() - b.getDesiredFloor());
    Queue<Request> downQueue = new PriorityQueue<>((a, b) -> b.getDesiredFloor() - a.getDesiredFloor());
    Queue<Request> currentQueue = upQueue;
    Schedular(ElevatorCar elevatorCar) {
        this.elevatorCar = elevatorCar;
    }
    public void schedule(Request request){
        if(this.elevatorCar.getDirection().toString().equals(request.getDirection().toString())){
            if(request.getDesiredFloor()>=this.elevatorCar.getCurrentFloor()) {
                this.currentQueue.add(request);
                System.out.println("Current Queue --------"+this.currentQueue);
            }
            else if(Direction.DOWN.toString().equals(request.getDirection().toString())){
                this.downQueue.add(request);
                System.out.println("down Queue --------"+this.downQueue);
            }
            else {
                this.upQueue.add(request);
                System.out.println("Up Queue --------"+this.upQueue);
            }
        }
        else {
            if(Direction.DOWN.toString().equals(request.getDirection().toString())){
                this.downQueue.add(request);
                System.out.println("down Queue --------"+this.downQueue);
            }
            else {
                this.upQueue.add(request);
                System.out.println("Up Queue --------"+this.upQueue);
            }
        }
    }
    public void process(){
        System.out.println("----------CONSTANTLY POLLING--------");
        while (true){
            Request request = this.currentQueue.peek();
            System.out.println(request);
            if(request!=null){
                System.out.println(request);
                goToFloor(request.getDesiredFloor());
            }
            else {
                this.preProcessQueue();
            }
        }
    }
    public void goToFloor(int desiredFloor) {
        if(elevatorCar.getDirection().toString().equals(Direction.UP.toString())) {
            if(elevatorCar.getCurrentFloor() == desiredFloor){
                System.out.println("---- OPEN AND CLOSE DOOR----");
                elevatorCar.setState(State.STOPPED);
                elevatorCar.setDoor(Door.OPEN);
                elevatorCar.setDoor(Door.CLOSED);
                elevatorCar.setState(State.MOVING);
            }
            else {
                elevatorCar.setDoor(Door.CLOSED);
                elevatorCar.setState(State.MOVING);
                for(int i=elevatorCar.getCurrentFloor();i<=desiredFloor;i++) {
                    try {
                        System.out.println("-----ELEVATOR IS AT FLOOR--  "+i);
                        this.elevatorCar.setCurrentFloor(i);
                        Thread.sleep(100);
                        System.out.println("-----ELEVATOR IS MOVING--------");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        else {
            if(elevatorCar.getCurrentFloor() == desiredFloor){
                elevatorCar.setState(State.STOPPED);
                elevatorCar.setDoor(Door.OPEN);
                System.out.println("---- OPEN AND CLOSE DOOR----");
                elevatorCar.setDoor(Door.CLOSED);
                elevatorCar.setState(State.MOVING);
            }
            else {
                elevatorCar.setDoor(Door.CLOSED);
                elevatorCar.setState(State.MOVING);
                for(int i=elevatorCar.getCurrentFloor();i>=desiredFloor;i--) {
                    try {
                        System.out.println("-----ELEVATOR IS MOVING--------");
                        Thread.sleep(100);
                        this.elevatorCar.setCurrentFloor(i);
                        System.out.println("-----ELEVATOR IS AT FLOOR--  "+i);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        this.currentQueue.poll();
        elevatorCar.setState(State.STOPPED);
        elevatorCar.setCurrentFloor(desiredFloor);
        elevatorCar.setDoor(Door.OPEN);
    }
    public long findMaximumTime(Queue<Request> queue) {
        List<Long> maxTime = queue.stream()
                .map(item -> item.getTime())
                .sorted()
                .collect(Collectors.toList());
        return maxTime.size()>0 ? maxTime.get(0) : -1;
    }
    public void preProcessQueue(){
        /*long upQueueMaxTime = findMaximumTime(upQueue);
        System.out.println("upQueueMaxTime--------- "+ upQueueMaxTime);
        long downQueueMaxTime = findMaximumTime(downQueue);
        System.out.println("downQueueMaxTime---------- "+ downQueueMaxTime);*/

        if(findMaximumTime(upQueue) > findMaximumTime(downQueue) && upQueue.size()>0) {
            this.elevatorCar.setDirection(Direction.UP);
            currentQueue = this.upQueue;
            upQueue = new PriorityQueue<>((a,b) -> a.getDesiredFloor() - b.getDesiredFloor());
        }
        else {
            if(downQueue.size() >0) {
                this.elevatorCar.setDirection(Direction.DOWN);
                currentQueue = this.downQueue;
                downQueue = new PriorityQueue<>((a,b) -> b.getDesiredFloor() - a.getDesiredFloor());
            }
        }
    }


}
public class MyElevator {
    ElevatorCar elevatorCar;
    Schedular schedular;
    public void call(Direction d,int desiredFloor,Location location){
        Request request = new Request(elevatorCar.getCurrentFloor(),d,desiredFloor,location);
        schedular.schedule(request);
    }
    public void goToFloor(int desiredFloor,Location location, Direction direction) {
        Request request = new Request(elevatorCar.getCurrentFloor(), direction,desiredFloor,location);
        schedular.schedule(request);
    }
    public void createElevatorCar(){
        elevatorCar = new ElevatorCar();
        schedular = new Schedular(elevatorCar);
        Thread thread = new Thread(() -> {
                schedular.process();
            });
        thread.start();
    }
    public static void main(String args[]) throws InterruptedException, FileNotFoundException {
        PrintStream printStream = new PrintStream(new File("output.txt"));
        System.setOut(printStream);
        MyElevator myElevator = new MyElevator();
        System.out.println("-------Please provide elevator car details--------");
        myElevator.createElevatorCar();
        System.out.println("-------Welcome to my Elevator--------");
        Thread.currentThread().sleep(100);
        // UP 0
        myElevator.call(Direction.UP,0,Location.External);
        // 3
        Thread.currentThread().sleep(100);
        myElevator.goToFloor(3, Location.Internal, Direction.UP);
        Thread.currentThread().sleep(100);
        //7 UP
        myElevator.call(Direction.UP,7,Location.External);
        Thread.currentThread().sleep(100);
        //2 DOWN
        myElevator.goToFloor(2,Location.Internal, Direction.DOWN);
        Thread.currentThread().sleep(1000);
        //8 UP
        myElevator.call(Direction.UP,8,Location.Internal);
        Thread.currentThread().sleep(100);
        //1 DOWN
        myElevator.goToFloor(1,Location.Internal, Direction.DOWN);
        Thread.currentThread().sleep(3000);
        // 5 DOWN
        myElevator.goToFloor(5,Location.Internal, Direction.DOWN);
    }
}
