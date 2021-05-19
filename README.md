# Intercepting and Read JMS Messages

A simple java class that can be deployed on Red Hat AMQ 7 to capture and logging all JMS messages. 

## How to Use

Compile and deploy based on below document.  

```
https://access.redhat.com/documentation/en-us/red_hat_amq/7.2/html/using_amq_broker/interceptors#configuring_the_broker_to_use_interceptors
```

Dont forget to change log file path first (on SimpleInterceptor.java line 39 and 49) , and adjust it to your environment. 