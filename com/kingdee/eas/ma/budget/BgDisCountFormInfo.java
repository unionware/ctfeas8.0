package com.kingdee.eas.ma.budget;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import com.kingdee.bos.ctrl.excel.model.struct.Book;
import com.kingdee.bos.ctrl.kdf.util.file.KDF;
import com.kingdee.bos.ctrl.kdf.util.file.KDFException;
import com.kingdee.bos.ctrl.kdf.util.file.ZipKDF;
import com.kingdee.eas.ma.view.util.IOHelper;

public class BgDisCountFormInfo extends AbstractBgDisCountFormInfo implements Serializable 
{
	 private transient HashMap adjustMap = null;
	 
    public BgDisCountFormInfo()
    {
        super();
    }
    protected BgDisCountFormInfo(String pkField)
    {
        super(pkField);
    }
    
    
    public void setKDF(KDF kdf) throws IOException {
      super.setKdtData(ZipKDF.pack(kdf));
    }
    
    public KDF getKDF() throws IOException, KDFException {
      if (super.getKdtData() == null)
        return null;
      return ZipKDF.unpack(super.getKdtData());
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
    

    public RefBgFormInfo getRefBgFormInfo(String bgFormId)
    {
      if (getBgForm().getId().toString().equals(bgFormId)) {
        return null;
      }
      
      Iterator iter = getRefBgForms().iterator();
      
      while (iter.hasNext()) {
        RefBgFormInfo refForm = (RefBgFormInfo)iter.next();
        if (refForm.getBgForm().getId().toString().equals(bgFormId)) {
          return refForm;
        }
      }
      
      return null;
    }
    
  
  
  
  
  
  
    public HashMap getAdjustMapByBgFormId(String bgFormId)
      throws IOException, ClassNotFoundException
    {
      if (getBgForm().getId().toString().equals(bgFormId)) {
        return getAdjustMap();
      }
      return getRefBgFormInfo(bgFormId).getAdjustMap();
    }
    
    public String getLogInfo()
    {
      return getBgForm().getNumber() + " - " + getBgForm().getName();
    }
    
    public Book getBook() throws Exception
    {
      return IOHelper.unpackBook(super.getKdtData());
    }
    
    public void setBook(Book book) throws IOException {
      super.setKdtData(IOHelper.packBook(book));
    }
}