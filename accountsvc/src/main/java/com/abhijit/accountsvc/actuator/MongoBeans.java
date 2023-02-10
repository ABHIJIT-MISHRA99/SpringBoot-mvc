package com.abhijit.accountsvc.actuator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "mongobeans")
@Slf4j
public class MongoBeans {
    @Autowired
    private ApplicationContext applicationContext;

    @ReadOperation
    public String getData(){
        StringBuilder sb=new StringBuilder();
        String [] beanNamesForType=applicationContext.getBeanNamesForType(MongoRepository.class);
        for(String bean:beanNamesForType){
            sb.append("mongo repository bean:");
            sb.append(bean);
            sb.append(" ");
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
    @WriteOperation
    public  void writeData(){
        log.info("WriteData implementation is not done");

    }
}
