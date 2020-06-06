package server;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * The Server.Server.ServerSingleton class implements 'singleton' design pattern and is a 'global variable'
 * to  communicate between threads
 */
public class ServerSingleton {

    private final static Logger logger = LogManager.getLogger("server");
    private static ServerSingleton communicator;
    private final ArrayList<SrvThread> threads;
    private final ArrayList<String> names;
    private final Lock lock = new ReentrantLock(true);

    /**
     * private constructor
     */
    private ServerSingleton(){
        threads = new ArrayList<>();
        names = new ArrayList<>();
        logger.info("Singleton instance created");
    }

    /**
     * replaces public constructor - singleton design pattern
     * @return if exist - object of the class SingletonThread
     * @return creates new object of the class SingletonThread otherwise
     */
    public static ServerSingleton getSingleton() {
        if (communicator == null) {
            communicator = new ServerSingleton();
        }
        return communicator;
    }

    /**
     * adds new thread to the list
     * @param thread SrvThread with connected socket
     * @param name connected socket client's name
     */
    public void addNewThread(SrvThread thread, String name) {
        lock.lock();
        try {
            this.threads.add(thread);
            this.names.add(name);
            logger.info("new thread added");

        } catch (Exception e) {
            logger.error("Cannot add user");
        }finally {
            lock.unlock();
        }
    }

    /**
     * searches for concrete thread
     * @param name name to be searched
     * @return thread corresponding to the name
     */
    public SrvThread getThreadByName(String name){
        int index = this.names.indexOf(name);
        return this.threads.get(index);
    }

    /**
     * searches and removes concrete thread and corresponding name from the list
     * @param name name to be searched
     */
    public void removeByName(String name){
        int index = this.names.indexOf(name);
        this.threads.remove(index);
        this.names.remove(index);
    }

    /**
     * @return list with all names
     */
    public ArrayList<String> getAllNames(){ return names; }
}