
/**
 * WebServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.dcdzsoft.sms.impl;

    /**
     *  WebServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class WebServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public WebServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public WebServiceCallbackHandler(){
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
                    com.dcdzsoft.sms.impl.WebServiceStub.SendSMSResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendSMS operation
           */
            public void receiveErrorsendSMS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addSender method
            * override this method for handling normal response from addSender operation
            */
           public void receiveResultaddSender(
                    com.dcdzsoft.sms.impl.WebServiceStub.AddSenderResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addSender operation
           */
            public void receiveErroraddSender(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for balanceSMS method
            * override this method for handling normal response from balanceSMS operation
            */
           public void receiveResultbalanceSMS(
                    com.dcdzsoft.sms.impl.WebServiceStub.BalanceSMSResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from balanceSMS operation
           */
            public void receiveErrorbalanceSMS(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for sendSMSWK method
            * override this method for handling normal response from sendSMSWK operation
            */
           public void receiveResultsendSMSWK(
                    com.dcdzsoft.sms.impl.WebServiceStub.SendSMSWKResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sendSMSWK operation
           */
            public void receiveErrorsendSMSWK(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getSenderAvailability method
            * override this method for handling normal response from getSenderAvailability operation
            */
           public void receiveResultgetSenderAvailability(
                    com.dcdzsoft.sms.impl.WebServiceStub.GetSenderAvailabilityResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getSenderAvailability operation
           */
            public void receiveErrorgetSenderAvailability(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for changeAccountPassword method
            * override this method for handling normal response from changeAccountPassword operation
            */
           public void receiveResultchangeAccountPassword(
                    com.dcdzsoft.sms.impl.WebServiceStub.ChangeAccountPasswordResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from changeAccountPassword operation
           */
            public void receiveErrorchangeAccountPassword(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for checkSender method
            * override this method for handling normal response from checkSender operation
            */
           public void receiveResultcheckSender(
                    com.dcdzsoft.sms.impl.WebServiceStub.CheckSenderResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkSender operation
           */
            public void receiveErrorcheckSender(java.lang.Exception e) {
            }
                


    }
    