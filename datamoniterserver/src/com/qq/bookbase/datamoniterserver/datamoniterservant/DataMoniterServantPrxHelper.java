// **********************************************************************
// This file was generated by a TAF parser!
// TAF version 1.6.0 by WSRD Tencent.
// Generated from `DataMoniterServant.jce'
// **********************************************************************

package com.qq.bookbase.datamoniterserver.datamoniterservant;

public final class DataMoniterServantPrxHelper extends com.qq.taf.proxy.ServantProxy implements DataMoniterServantPrx
{

    public DataMoniterServantPrxHelper taf_hash(int hashCode)
    {
        super.taf_hash(hashCode);
        return this;
    }

    public java.util.Map __defaultContext()
    {
        java.util.HashMap _ctx = new java.util.HashMap();
        return _ctx;
    }

    protected String sServerEncoding = "GBK";
    public int setServerEncoding(String se){
        sServerEncoding = se;
        return 0;
    }

    public int test()
    {
        return test(__defaultContext());
    }

    @SuppressWarnings("unchecked")
    public int test(java.util.Map __ctx)
    {
        com.qq.taf.jce.JceOutputStream _os = new com.qq.taf.jce.JceOutputStream(0);
        _os.setServerEncoding(sServerEncoding);
        byte[] _sBuffer = com.qq.taf.jce.JceUtil.getJceBufArray(_os.getByteBuffer());

        java.util.HashMap<String, String> status = new java.util.HashMap<String, String>();

        byte[] _returnSBuffer = taf_invoke("test", _sBuffer, __ctx, status);

        com.qq.taf.jce.JceInputStream _is = new com.qq.taf.jce.JceInputStream(_returnSBuffer);
        _is.setServerEncoding(sServerEncoding);
        int _ret = 0;
        _ret = (int) _is.read(_ret, 0, true);
        return _ret;
    }

    public void async_test(com.qq.bookbase.datamoniterserver.datamoniterservant.DataMoniterServantPrxCallback callback)
    {
        async_test(callback, __defaultContext());
    }

    public void async_test(com.qq.bookbase.datamoniterserver.datamoniterservant.DataMoniterServantPrxCallback callback, java.util.Map __ctx)
    {
        com.qq.taf.jce.JceOutputStream _os = new com.qq.taf.jce.JceOutputStream(0);
        _os.setServerEncoding(sServerEncoding);
        byte[] _sBuffer = com.qq.taf.jce.JceUtil.getJceBufArray(_os.getByteBuffer());

        java.util.HashMap<String, String> status = new java.util.HashMap<String, String>();

        taf_invokeAsync(callback, "test", _sBuffer, __ctx, status);

    }

}
