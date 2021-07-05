package Entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseEntity {

    protected final Logger log;
    private String loggerName;

    public BaseEntity(String loggerName){
        this.log = LogManager.getLogger(loggerName);
    }

}
