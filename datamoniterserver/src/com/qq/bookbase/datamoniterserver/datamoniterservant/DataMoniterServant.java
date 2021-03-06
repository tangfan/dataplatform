// **********************************************************************
// This file was generated by a TAF parser!
// TAF version 1.6.0 by WSRD Tencent.
// Generated from `DataMoniterServant.jce'
// **********************************************************************

package com.qq.bookbase.datamoniterserver.datamoniterservant;

public abstract class DataMoniterServant extends com.qq.taf.server.Servant
{
    private  java.util.Map<String, com.qq.taf.server.ResponseHandler> responseHandlerMap=new java.util.HashMap<String, com.qq.taf.server.ResponseHandler>();

    public DataMoniterServant()
    {
        responseHandlerMap.put("test", new com.qq.taf.server.ResponseHandler()
        {
            public com.qq.taf.jce.JceOutputStream handle(com.qq.taf.jce.JceInputStream _is, com.qq.taf.server.JceCurrent _jc)
            {
                return response_test(_is, _jc);
            }
            public com.qq.jce.wup.UniAttribute handle_wup(com.qq.jce.wup.UniAttribute _unaIn, com.qq.taf.server.JceCurrent _jc)
            {
                return response_test_wup(_unaIn, _jc);
            }
        });
    }

    public abstract int test(com.qq.taf.server.JceCurrent _jc);

    private com.qq.taf.jce.JceOutputStream response_test(com.qq.taf.jce.JceInputStream _is, com.qq.taf.server.JceCurrent _jc)
    {
        com.qq.taf.jce.JceOutputStream _os = null;
        int _ret = test(_jc);
        if(_jc.isResponse())
        {
            _os = new com.qq.taf.jce.JceOutputStream(0);
            _os.write(_ret, 0);
        }
        return _os;
    }

    private com.qq.jce.wup.UniAttribute response_test_wup(com.qq.jce.wup.UniAttribute _unaIn, com.qq.taf.server.JceCurrent _jc)
    {
        com.qq.jce.wup.UniAttribute _unaOut = null;
        int _ret = test(_jc);
        if(_jc.isResponse())
        {
            _unaOut = new com.qq.jce.wup.UniAttribute();
            if(isWup3(_jc)) _unaOut.useVersion3();
            _unaOut.put("",_ret);
        }
        return _unaOut;
    }

    protected void async_response_test(com.qq.taf.server.JceCurrent _jc, int _ret)
    {
        if(isWupRequest(_jc)){
            async_response_test_wup(_jc, _ret);
        }else{
            com.qq.taf.jce.JceOutputStream _os = new com.qq.taf.jce.JceOutputStream(0);
            _os.write(_ret, 0);
            sendResponseMessage(_jc, _os);
        }
    }

    protected void async_response_test_wup(com.qq.taf.server.JceCurrent _jc, int _ret)
    {
        com.qq.jce.wup.UniAttribute _unaOut = new com.qq.jce.wup.UniAttribute();
        if(isWup3(_jc)) _unaOut.useVersion3();
        _unaOut.put("",_ret);
        sendResponseMessage_wup(_jc, _unaOut);
    }

    protected com.qq.taf.jce.JceOutputStream doResponse(String funcName, com.qq.taf.jce.JceInputStream _is, com.qq.taf.server.JceCurrent _jc)
    {
        return responseHandlerMap.get(funcName).handle(_is, _jc);
    }

    protected com.qq.jce.wup.UniAttribute doResponse_wup(String funcName, com.qq.jce.wup.UniAttribute _unaIn, com.qq.taf.server.JceCurrent _jc)
    {
        return responseHandlerMap.get(funcName).handle_wup(_unaIn, _jc);
    }
}
