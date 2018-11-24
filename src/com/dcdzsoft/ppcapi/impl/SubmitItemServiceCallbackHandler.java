
/**
 * SubmitItemServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.dcdzsoft.ppcapi.impl;

    /**
     *  SubmitItemServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class SubmitItemServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public SubmitItemServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public SubmitItemServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getReasons method
            * override this method for handling normal response from getReasons operation
            */
           public void receiveResultgetReasons(
                    com.dcdzsoft.ppcapi.impl.SubmitItemServiceStub.GetReasonsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getReasons operation
           */
            public void receiveErrorgetReasons(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for submitItemEventByItemCode_Parcel method
            * override this method for handling normal response from submitItemEventByItemCode_Parcel operation
            */
           public void receiveResultsubmitItemEventByItemCode_Parcel(
                    com.dcdzsoft.ppcapi.impl.SubmitItemServiceStub.SubmitItemEventByItemCode_ParcelResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from submitItemEventByItemCode_Parcel operation
           */
            public void receiveErrorsubmitItemEventByItemCode_Parcel(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for submitItemEvent method
            * override this method for handling normal response from submitItemEvent operation
            */
           public void receiveResultsubmitItemEvent(
                    com.dcdzsoft.ppcapi.impl.SubmitItemServiceStub.SubmitItemEventResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from submitItemEvent operation
           */
            public void receiveErrorsubmitItemEvent(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for submitItemEventByItemCode method
            * override this method for handling normal response from submitItemEventByItemCode operation
            */
           public void receiveResultsubmitItemEventByItemCode(
                    com.dcdzsoft.ppcapi.impl.SubmitItemServiceStub.SubmitItemEventByItemCodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from submitItemEventByItemCode operation
           */
            public void receiveErrorsubmitItemEventByItemCode(java.lang.Exception e) {
            }
                


    }
    