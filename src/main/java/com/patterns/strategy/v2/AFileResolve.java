package com.patterns.strategy.v2;

public class AFileResolve implements IFileStrategy {
    
    @Override
    public FileTypeResolveEnum gainFileType() {
        return FileTypeResolveEnum.File_A_RESOLVE;
    }

    @Override
    public void resolve(Object objectparam) {
        System.out.println("A 类型解析文件，参数：{}" + objectparam);
      //A类型解析具体逻辑
    }
}