/*
 * Copyright 1999-2012 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cobar.net.postgres;

/**
 * @see http://www.postgresql.org/docs/9.1/interactive/protocol.html
 * @author xianmao.hexm 2012-6-25
 */
public abstract class PostgresPacket {
    /**
     * <pre>
     * AuthenticationOk (B)   
     * AuthenticationKerberosV5 (B)       
     * AuthenticationCleartextPassword (B)    
     * AuthenticationMD5Password (B)    
     * AuthenticationSCMCredential (B)     
     * AuthenticationGSS (B)     
     * AuthenticationSSPI (B)      
     * AuthenticationGSSContinue (B)
     * </pre>
     */
    public static final byte AUTHENTICATION = (byte) 'R';

    /**
     * BackendKeyData (B)
     */
    public static final byte BACKEND_KEY_DATA = (byte) 'K';

    /**
     * Bind (F)
     */
    public static final byte BIND = (byte) 'B';

    /**
     * BindComplete (B)
     */
    public static final byte BIND_COMPLETE = (byte) '2';

    /**
     * CancelRequest (F)
     */

    /**
     * Close (F)
     */
    public static final byte CLOSE = (byte) 'C';

    /**
     * CloseComplete (B)
     */
    public static final byte CLOSE_COMPLETE = (byte) '3';

    /**
     * CommandComplete (B)
     */
    public static final byte COMMAND_COMPLETE = (byte) 'C';

    /**
     * CopyData (F & B)
     */
    public static final byte COPY_DATA = (byte) 'd';

    /**
     * CopyDone (F & B)
     */
    public static final byte COPY_DONE = (byte) 'c';

    /**
     * CopyFail (F)
     */
    public static final byte COPY_FAIL = (byte) 'f';

    /**
     * CopyInResponse (B)
     */
    public static final byte COPY_IN_RESPONSE = (byte) 'G';

    /**
     * CopyOutResponse (B)
     */
    public static final byte COPY_OUT_RESPONSE = (byte) 'H';

    /**
     * CopyBothResponse (B)
     */
    public static final byte COPY_BOTH_RESPONSE = (byte) 'W';

    /**
     * DataRow (B)
     */
    public static final byte DATA_ROW = (byte) 'D';

    /**
     * Describe (F)
     */
    public static final byte DESCRIBE = (byte) 'D';

    /**
     * EmptyQueryResponse (B)
     */
    public static final byte EMPTY_QUERY_RESPONSE = (byte) 'I';

    /**
     * ErrorResponse (B)
     */
    public static final byte ERROR_RESPONSE = (byte) 'E';

    /**
     * Execute (F)
     */
    public static final byte EXECUTE = (byte) 'E';

    /**
     * Flush (F)
     */
    public static final byte FLUSH = (byte) 'H';

    /**
     * FunctionCall (F)
     */
    public static final byte FUNCTION_CALL = (byte) 'F';

    /**
     * FunctionCallResponse (B)
     */
    public static final byte FUNCTION_CALL_RESPONSE = (byte) 'V';

    /**
     * NoData (B)
     */
    public static final byte NO_DATA = (byte) 'n';

    /**
     * NoticeResponse (B)
     */
    public static final byte NOTICE_RESPONSE = (byte) 'N';

    /**
     * NotificationResponse (B)
     */
    public static final byte NOTIFICATION_RESPONSE = (byte) 'A';

    /**
     * ParameterDescription (B)
     */
    public static final byte PARAMETER_DESCRIPTION = (byte) 't';

    /**
     * ParameterStatus (B)
     */
    public static final byte PARAMETER_STATUS = (byte) 'S';

    /**
     * Parse (F)
     */
    public static final byte PARSE = (byte) 'P';

    /**
     * ParseComplete (B)
     */
    public static final byte PARSE_COMPLETE = (byte) '1';

    /**
     * PasswordMessage (F)
     */
    public static final byte PASSWORD_MESSAGE = (byte) 'p';

    /**
     * PortalSuspended (B)
     */
    public static final byte PORTAL_SUSPENDED = (byte) 's';

    /**
     * Query (F)
     */
    public static final byte QUERY = (byte) 'Q';

    /**
     * ReadyForQuery (B)
     */
    public static final byte READY_FOR_QUERY = (byte) 'Z';

    /**
     * RowDescription (B)
     */
    public static final byte ROW_DESCRIPTION = (byte) 'T';

    /**
     * SSLRequest (F)
     */

    /**
     * StartupMessage (F)
     */

    /**
     * Sync (F)
     */
    public static final byte SYNC = (byte) 'S';

    /**
     * Terminate (F)
     */
    public static final byte TERMINATE = (byte) 'X';

    public byte type;
    public int length;

}
