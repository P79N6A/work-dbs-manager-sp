
/**
 * ServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.dcdzsoft.sms.impl;

    /**
     *  ServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class ServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public ServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public ServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for sendSMS method
            * override this method for handling normal response from sendSMS operation
            */
           public void receiveResultsendSMS(
                    com.dcdzsoft.sms.impl.ServiceStub.SendSMSResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendSMS operation
           */
            public void receiveErrorsendSMS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sendSMS_Confirmation method
            * override this method for handling normal response from sendSMS_Confirmation operation
            */
           public void receiveResultsendSMS_Confirmation(
                    com.dcdzsoft.sms.impl.ServiceStub.SendSMS_ConfirmationResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendSMS_Confirmation operation
           */
            public void receiveErrorsendSMS_Confirmation(java.lang.Exception e) {
            }
                


    }
    