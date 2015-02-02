package com.kingdee.eas.ma.budget;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import com.kingdee.bos.ctrl.excel.model.struct.Book;
import com.kingdee.bos.ctrl.kdf.util.file.KDF;
import com.kingdee.bos.ctrl.kdf.util.file.KDFException;
import com.kingdee.bos.ctrl.kdf.util.file.ZipKDF;
import com.kingdee.eas.ma.view.util.IOHelper;

public class RefDisCountBgFormInfo extends AbstractRefDisCountBgFormInfo implements Serializable 
{
	
	private transient HashMap adjustMap = null;
    public RefDisCountBgFormInfo()throws IOException, ClassNotFoundException
    {
        super();
    }
    protected RefDisCountBgFormInfo(String pkField)
    {
        super(pkField);
    }
    
    
    
    
    
    public HashMap getAdjustMap() throws IOException, ClassNotFoundException {
      if (this.adjustMap == null) {
        this.adjustMap = BgHelper.buildHashMapByByte(getAdjustMapData());
      }
      return this.adjustMap;
    }
    
    public void saveAdjustMap() throws IOException {
      if (this.adjustMap != null) {
        super.setAdjustMapData(BgHelper.storeHashMapToByte(this.adjustMap));
      }
    }
    
    public KDF getKDF() throws IOException, KDFException {
      if (super.getKdtData() == null)
        return null;
      return ZipKDF.unpack(super.getKdtData());
    }
    
    public Book getBook() throws Exception {
      return IOHelper.unpackBook(super.getKdtData());
    }
    
    public void setBook(Book book) throws IOException {
      super.setKdtData(IOHelper.packBook(book));
    }
}